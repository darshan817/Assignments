import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { addToCart } from '../store/cartSlice';

const Toast = ({ message, visible }) => {
  if (!visible) return null;

  return (
    <div style={{
      position: 'fixed',
      top: '20px',
      right: '20px',
      background: '#4CAF50',
      color: 'white',
      padding: '12px 20px',
      borderRadius: '5px',
      zIndex: 1000,
      boxShadow: '0 2px 10px rgba(0,0,0,0.2)'
    }}>
      {message}
    </div>
  );
};

const AddToCartButton = ({ product, showQuantityControls = false }) => {
  const dispatch = useDispatch();
  const [quantity, setQuantity] = useState(1);
  const [showToast, setShowToast] = useState(false);

  // Toast disappear timer
  useEffect(() => {
    let timeoutId;
    if (showToast) {
      timeoutId = setTimeout(() => setShowToast(false), 3000);
    }
    return () => clearTimeout(timeoutId);
  }, [showToast]);

  if (!product) return null;

  const handleAdd = (e) => {
    e.preventDefault();
    e.stopPropagation(); // prevent navigation or card click
    dispatch(addToCart({ ...product, quantity }));
    setShowToast(true);
    setQuantity(1);
  };

  const handleHomeAdd = (e) => {
    e.preventDefault();
    e.stopPropagation();
    dispatch(addToCart({ ...product, quantity: 1 }));
    setShowToast(true);
  };

  // --- Quantity Selector version (for Product Detail Page) ---
  if (showQuantityControls) {
    return (
      <>
        <Toast message={`✓ ${product.title} added to cart!`} visible={showToast} />
        <div style={{ display: 'flex', flexDirection: 'column', gap: '12px', width: '200px' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
            <label htmlFor="quantity" style={{ fontWeight: 'bold' }}>Quantity:</label>
            <select
              id="quantity"
              value={quantity}
              onChange={(e) => setQuantity(Number(e.target.value))}
              style={{
                padding: '5px 8px',
                borderRadius: '6px',
                border: '1px solid #ccc',
                fontSize: '14px'
              }}
            >
              {[...Array(10).keys()].map((num) => (
                <option key={num + 1} value={num + 1}>{num + 1}</option>
              ))}
            </select>
          </div>
          <button
            onClick={handleAdd}
            style={{
              background: '#FFD814',
              color: '#111',
              border: '1px solid #FCD200',
              padding: '10px',
              borderRadius: '50px',
              cursor: 'pointer',
              fontWeight: '600'
            }}
          >
            Add to Cart
          </button>
        </div>
      </>
    );
  }

  // --- Simple Home Page version ---
  return (
    <>
      <Toast message={`✓ ${product.title} added to cart!`} visible={showToast} />
      <button
        onClick={handleHomeAdd}
        style={{
          background: '#4CAF50',
          color: 'white',
          border: 'none',
          padding: '10px 15px',
          borderRadius: '5px',
          cursor: 'pointer'
        }}
      >
        Add to Cart
      </button>
    </>
  );
};

export default AddToCartButton;
