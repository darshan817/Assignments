import React from "react";
import { Link } from "react-router-dom";
import "./ProductCard.css";
import AddToCart from "./AddToCart";

function ProductCard({ product }) {
  return (
    <Link to={`/product/${product.id}`} className="card-link">
      <div className="card">
        <img src={product.image} alt={product.title} />

        <h3>
          {product.title?.length > 30
            ? `${product.title.slice(0, 30)}...`
            : product.title}
        </h3>

        {/* Use ratingRate and ratingCount */}
        {product.ratingRate && (
          <p className="rating">
            ⭐ {product.ratingRate}/5{" "}
            <span style={{ color: "#666", fontSize: "13px" }}>
              ({product.ratingCount || 0})
            </span>
          </p>
        )}

        <p>Price - ${product.price?.toFixed(2)}</p>

        {/* Add to Cart button */}
        <AddToCart product={product} />
      </div>
    </Link>
  );
}

export default ProductCard;
