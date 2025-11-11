// src/pages/OrdersHistory.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './OrdersHistory.css';

const OrdersHistoryPage = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cancellingOrderId, setCancellingOrderId] = useState(null);
  const navigate = useNavigate();

  const fetchOrders = async () => {
    try {
      setLoading(true);
      const response = await axios.get('http://localhost:8080/api/orders/history');
      setOrders(response.data);
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Failed to fetch orders');
    } finally {
      setLoading(false);
    }
  };

  const handleCancelOrder = async (orderId) => {
    setCancellingOrderId(orderId);
    try {
      const response = await axios.put(`http://localhost:8080/api/orders/${orderId}/cancel`);
      setOrders(orders.map(order => 
        order.id === orderId ? response.data : order
      ));
    } catch (error) {
      const errorMessage = error.response?.data?.error || error.message || 'Failed to cancel order';
      alert(`Error: ${errorMessage}`);
    } finally {
      setCancellingOrderId(null);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);
  const getStatusColor = (status) => {
    switch (status) {
      case 'PENDING': return '#ff9800';
      case 'SHIPPED': return '#2196f3';
      case 'OUT_FOR_DELIVERY': return '#9c27b0';
      case 'DELIVERED': return '#4caf50';
      case 'CANCELLED': return '#f44336';
      default: return '#757575';
    }
  };

  if (loading) {
    return <div className="orders-loading">Loading orders...</div>;
  }

  if (error) {
    return (
      <div className="orders-error">
        <h2>Error loading orders</h2>
        <p>{error}</p>
        <button onClick={() => fetchOrders()}>Retry</button>
      </div>
    );
  }

  if (!orders.length) {
    return (
      <div className="orders-empty">
        <h2>No orders placed yet</h2>
        <button onClick={() => navigate('/')}>Go Shopping</button>
      </div>
    );
  }

  return (
    <div className="orders-container">
      <h1>Order History</h1>
      
      {orders.map((order) => (
        <div key={order.id} className="order-card">
          <div className="order-header">
            <div className="order-info">
              <span className="order-id">Order #{order.id}</span>
              <span className="order-date">{order.timeSinceOrder}</span>
              <span 
                className="order-status" 
                style={{ color: getStatusColor(order.status) }}
              >
                {order.status}
              </span>
            </div>
            <div className="order-total">₹{order.finalPrice?.toFixed(2)}</div>
          </div>

          <div className="order-details">
            <div className="delivery-info">
              <p><strong>Address:</strong> {order.addressLine1}, {order.state} - {order.pincode}</p>
              {order.status === 'PENDING' && (
                <p className="cancel-time">{order.timeRemainingToCancel}</p>
              )}
              {order.status !== 'DELIVERED' && order.status !== 'CANCELLED' && (
                <p className="delivery-time">{order.timeRemainingToDeliver}</p>
              )}
            </div>

            {order.promoCode && (
              <div className="promo-info">
                <span>Promo: {order.promoCode}</span>
                <span>Discount: -₹{order.discountAmount?.toFixed(2)}</span>
              </div>
            )}
          </div>

          <div className="order-items">
            <h4>Items ({order.orderItems?.length || 0})</h4>
            {order.orderItems?.map((item) => (
              <div key={item.id} className="order-item">
                <div className="item-info">
                  {item.productImageUrl && (
                    <img src={item.productImageUrl} alt={item.productName} className="item-image" />
                  )}
                  <div className="item-details">
                    <span className="item-name">{item.productName}</span>
                    <span className="item-quantity">Qty: {item.quantity}</span>
                  </div>
                </div>
                <div className="item-price">₹{item.totalPrice?.toFixed(2)}</div>
              </div>
            ))}
          </div>

          {order.status === 'PENDING' && (
            <div className="order-actions">
              <button 
                className="cancel-btn"
                onClick={() => handleCancelOrder(order.id)}
                disabled={cancellingOrderId === order.id}
              >
                {cancellingOrderId === order.id ? 'Cancelling...' : 'Cancel Order'}
              </button>
            </div>
          )}
        </div>
      ))}

      <button className="back-btn" onClick={() => navigate('/')}>
        ← Continue Shopping
      </button>
    </div>
  );
};

export default OrdersHistoryPage;
