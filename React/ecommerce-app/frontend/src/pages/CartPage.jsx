// src/components/CartPage.jsx
import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { removeFromCart, increaseQuantity, decreaseQuantity, clearCart } from '../store/cartSlice';
import './CartPage.css';
import {  useNavigate} from 'react-router-dom';

const CartPage = () => {
  const cartItems = useSelector((state) => state.cart.items || []);
  const dispatch = useDispatch();

  const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const navigate = useNavigate();
  
  if (cartItems.length === 0) return <p className="empty-cart">Your cart is empty.</p>;

  return (
    <div className="cart-container">
      <h1>Your Cart</h1>
      {cartItems.map((item) => (
        <div key={item.id} className="cart-item">
          <div className="item-details">
            <img src={item.image} alt={item.title} className="item-image" />
            <div>
              <p className="item-title">{item.title}</p>
              <p className="item-price">${item.price.toFixed(2)}</p>
            </div>
          </div>

          <div className="item-actions">
            <button onClick={() => dispatch(decreaseQuantity(item.id))} className="qty-btn">-</button>
            <span className="item-qty">{item.quantity}</span>
            <button onClick={() => dispatch(increaseQuantity(item.id))} className="qty-btn">+</button>
            <button onClick={() => dispatch(removeFromCart(item.id))} className="remove-btn">
              Remove
            </button>
          </div>
        </div>
      ))}

      <h2 className="total-price">Total: ${totalPrice.toFixed(2)}</h2>

      <div className="cart-actions">
        <button 
          className="checkout-btn"
          onClick={() => navigate('/checkout')}>
        Proceed to Checkout</button>
        <button className="clear-btn" onClick={() => dispatch(clearCart())}>Clear Cart</button>
      </div>
    </div>
  );
};

export default CartPage;
