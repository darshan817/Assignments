package com.example.ecomm_backend.Service;

import com.example.ecomm_backend.DTO.*;
import com.example.ecomm_backend.Model.Order;
import com.example.ecomm_backend.Model.OrderItem;
import com.example.ecomm_backend.Model.Product;
import com.example.ecomm_backend.Model.PromoCode;
import com.example.ecomm_backend.Repository.OrderRepository;
import com.example.ecomm_backend.Repository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PromoCodeService promoCodeService;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        PromoCodeService promoCodeService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.promoCodeService = promoCodeService;
    }

    // Convert Order → DTO
    private OrderDTO convertToDto(Order order) {
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImageUrl(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setAddressLine1(order.getAddressLine1());
        dto.setState(order.getState());
        dto.setPincode(order.getPincode());
        dto.setOriginalPrice(order.getOriginalPrice());
        dto.setPromoCode(order.getPromoCode() != null ? order.getPromoCode().getCode() : null);
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setFinalPrice(order.getFinalPrice());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setOrderItems(orderItemDTOs); // attach items

        // Dynamic fields
        dto.setTimeSinceOrder(calculateTimeSinceOrder(order.getOrderDate()));
        dto.setTimeRemainingToCancel(calculateRemainingCancelTime(order.getOrderDate(), order.getStatus()));
        dto.setTimeRemainingToDeliver(calculateRemainingDeliveryTime(order.getOrderDate(), order.getStatus()));

        return dto;
    }

    private String calculateTimeSinceOrder(LocalDateTime orderDate) {
        Duration diff = Duration.between(orderDate, LocalDateTime.now());
        long hours = diff.toHours();
        long minutes = diff.toMinutesPart();
        return hours + "h " + minutes + "m ago";
    }

    private String calculateRemainingCancelTime(LocalDateTime orderDate, String status) {
        if (!"PENDING".equals(status)) return "N/A";
        Duration remaining = Duration.between(LocalDateTime.now(), orderDate.plusMinutes(30));
        if (remaining.isNegative()) return "Expired";
        long min = remaining.toMinutes();
        return min + " min left to cancel";
    }

    private String calculateRemainingDeliveryTime(LocalDateTime orderDate, String status) {
        if ("DELIVERED".equals(status)) return "Delivered";
        Duration remaining = Duration.between(LocalDateTime.now(), orderDate.plusHours(6));
        if (remaining.isNegative()) return "Any moment now";
        long hrs = remaining.toHours();
        long min = remaining.toMinutesPart();
        return hrs + "h " + min + "m left for delivery";
    }

    // Create new order
    public OrderDTO createOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("Order DTO cannot be null");
        }

        Order order = new Order();
        order.setAddressLine1(orderDTO.getAddressLine1() != null ? orderDTO.getAddressLine1() : "");
        order.setState(orderDTO.getState() != null ? orderDTO.getState() : "");
        order.setPincode(orderDTO.getPincode() != null ? orderDTO.getPincode() : "");
        order.setOriginalPrice(orderDTO.getOriginalPrice());

        // Handle promo code
        PromoCode promoCode = null;
        double discountAmount = 0.0;
        if (orderDTO.getPromoCode() != null && !orderDTO.getPromoCode().trim().isEmpty()) {
            promoCode = promoCodeService.validatePromoCode(orderDTO.getPromoCode());
            discountAmount = promoCodeService.calculateDiscount(orderDTO.getPromoCode(), orderDTO.getOriginalPrice());
        }

        order.setPromoCode(promoCode);
        order.setDiscountAmount(discountAmount);
        order.setFinalPrice(orderDTO.getOriginalPrice() - discountAmount);
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());

        // Build order items
        List<OrderItem> orderItems = new ArrayList<>();
        if (orderDTO.getCartItems() != null) {
            for (CartItemDto cartItem : orderDTO.getCartItems()) {
                if (cartItem != null && cartItem.getProductId() != null) {
                    Product product = productRepository.findById(cartItem.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + cartItem.getProductId()));
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setQuantity(cartItem.getQuantity());
                    item.setPrice(cartItem.getPrice());
                    item.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
                    orderItems.add(item);
                }
            }
        }
        order.setOrderItems(orderItems);


        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    public List<OrderDTO> getOrderHistory() {
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDTO cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be cancelled");
        }

        Duration sinceOrder = Duration.between(order.getOrderDate(), LocalDateTime.now());
        if (sinceOrder.toMinutes() > 30) {
            throw new RuntimeException("Cancellation window expired (30 minutes)");
        }

        order.setStatus("CANCELLED");
        return convertToDto(orderRepository.save(order));
    }

    @Scheduled(fixedRate = 60000) // runs every minute
    public void autoUpdateStatuses() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixHoursAgo = now.minusHours(6);

        List<Order> pendingOrShipped = orderRepository.findAll().stream()
                .filter(o -> List.of("PENDING", "SHIPPED", "OUT_FOR_DELIVERY").contains(o.getStatus()))
                .filter(o -> o.getOrderDate().isBefore(sixHoursAgo))
                .toList();

        for (Order order : pendingOrShipped) {
            order.setStatus("DELIVERED");
            order.setDeliveryDate(LocalDateTime.now());
            orderRepository.save(order);
            System.out.println("✅ Auto-delivered order ID: " + order.getId());
        }
    }
}
