import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './CheckoutPage.css';

const CheckoutPage = () => {
  const cartItems = useSelector((state) => state.cart.items || []);
  const navigate = useNavigate();

  const [addressLine1, setAddressLine1] = useState('');
  const [stateValue, setStateValue] = useState('');
  const [pincode, setPincode] = useState('');
  const [promoCode, setPromoCode] = useState('');
  const [promo, setPromo] = useState(null);
  const [promoMessage, setPromoMessage] = useState('');
  const [loading, setLoading] = useState(false);

  // --- Calculate prices ---
  const subtotal = cartItems.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );
  const discountAmount = promo ? promo.discountAmount : 0;
  const total = subtotal - discountAmount;

  // --- Validate promo code ---
  const validatePromoCode = async () => {
    if (!promoCode.trim()) {
      setPromoMessage('⚠️ Please enter a promo code.');
      return;
    }

    setLoading(true);
    try {
      const response = await axios.post(
        'http://localhost:8080/api/promo/validate',
        null,
        {
          params: {
            code: promoCode.trim(),
            originalPrice: subtotal,
          },
        }
      );

      const data = response.data;

      if (data.valid) {
        const discountPercentage = ((data.discountAmount / subtotal) * 100).toFixed(0);

        setPromo({
          code: promoCode.trim(),
          discountAmount: data.discountAmount,
          discountPercentage: discountPercentage,
          finalPrice: data.finalPrice,
        });

        setPromoMessage(`✅ ${discountPercentage}% discount applied successfully!`);
      } else {
        setPromo(null);
        setPromoMessage('❌ Invalid or expired promo code.');
      }
    } catch (error) {
      console.error('Error validating promo code:', error);
      setPromo(null);
      setPromoMessage('❌ Promo code validation failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  // --- Place order ---
 const handlePlaceOrder = async () => {
  if (!addressLine1 || !stateValue || !pincode) {
    alert('❌ Please fill in all required fields');
    return;
  }

  if (cartItems.length === 0) {
    alert('🛒 Your cart is empty.');
    return;
  }

  // Prepare correct structure for backend
  const orderData = {
  addressLine1,
  state: stateValue,
  pincode,
  originalPrice: subtotal,
  promoCode: promo ? promo.code : null,
  discountAmount,
  finalPrice: total,
  status: 'PENDING',
  orderItems: cartItems.map((item) => ({
    productId: item.id,
    quantity: item.quantity,
    price: item.price,
    totalPrice: item.price * item.quantity,
  })),
};


  console.log('Sending Order Data:', orderData); // Debug

  try {
    const response = await axios.post('http://localhost:8080/api/orders', orderData);
    console.log('Order Response:', response.data);
    alert('✅ Order placed successfully!');
    navigate('/orders');
  } catch (error) {
    console.error('❌ Error placing order:', error.response?.data || error.message);
    alert(`❌ Failed to place order: ${error.response?.data?.message || error.message}`);
  }
};

  // --- Empty Cart UI ---
  if (cartItems.length === 0) {
    return (
      <div className="checkout-empty">
        <h2>Your cart is empty 🛒</h2>
        <button onClick={() => navigate('/')}>Continue Shopping</button>
      </div>
    );
  }

  return (
    <div className="checkout-container">
      <h1>Checkout</h1>

      {/* Shipping Info */}
      <div className="checkout-section">
        <h3>Shipping Information</h3>
        <div className="user-info">
          <input
            type="text"
            placeholder="Address Line 1"
            value={addressLine1}
            onChange={(e) => setAddressLine1(e.target.value)}
          />
          <input
            type="text"
            placeholder="State"
            value={stateValue}
            onChange={(e) => setStateValue(e.target.value)}
          />
          <input
            type="text"
            placeholder="Pincode"
            value={pincode}
            onChange={(e) => setPincode(e.target.value)}
          />
        </div>
      </div>

      {/* Order Summary */}
      <div className="checkout-section">
        <h3>Order Summary</h3>
        {cartItems.map((item) => (
          <div key={item.id} className="checkout-item">
            <span>
              {item.title.slice(0, 40)} (x{item.quantity})
            </span>
            <span>${(item.price * item.quantity).toFixed(2)}</span>
          </div>
        ))}

        <hr />
        <div className="checkout-summary">
          <span>Subtotal:</span>
          <span>${subtotal.toFixed(2)}</span>
        </div>

        {promo && (
          <div className="checkout-discount">
            <span>Discount ({promo.discountPercentage}%):</span>
            <span>- ${discountAmount.toFixed(2)}</span>
          </div>
        )}

        <div className="checkout-total">
          <span>Total:</span>
          <span>${total.toFixed(2)}</span>
        </div>
      </div>

      {/* Promo Code Section */}
      <div className="checkout-section">
        <h3>Promo Code</h3>
        <div className="promo-input-group">
          <input
            type="text"
            placeholder="Enter promo code"
            value={promoCode}
            onChange={(e) => setPromoCode(e.target.value)}
            disabled={loading}
          />
          <button onClick={validatePromoCode} disabled={loading}>
            {loading ? 'Validating...' : 'Apply'}
          </button>
        </div>
        {promoMessage && (
          <p className={`promo-message ${promo ? 'success' : 'error'}`}>
            {promoMessage}
          </p>
        )}
      </div>

      <button
        className="checkout-btn"
        onClick={handlePlaceOrder}
        disabled={loading}
      >
        {loading ? 'Processing...' : `Place Order - $${total.toFixed(2)}`}
      </button>

      <button className="back-btn" onClick={() => navigate('/cart')}>
        ← Back to Cart
      </button>
    </div>
  );
};

export default CheckoutPage;
