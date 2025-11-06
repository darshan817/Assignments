import React from "react";
import { Link } from "react-router-dom";
import "./ProductCard.css";

function ProductCard({ product }) {
  return (
    <Link to={`/product/${product.id}`} className="card">
      <img src={product.image} alt={product.title} />
      <h3>{product.title.slice(0, 30)}...</h3>
      <p className="rating">{product.rating.rate}/5</p>
      <p>Price - ₹{product.price}</p>
      <button>View Details</button>
    </Link>
  );
}

export default ProductCard;
