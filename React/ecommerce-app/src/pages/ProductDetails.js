import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import Loader from "../components/Loader";
import { useNavigate } from "react-router-dom";


function ProductDetails() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(`https://fakestoreapi.com/products/${id}`);
        setProduct(response.data);
      } catch (err) {
        console.error("Error fetching product:", err);
        setError("Failed to load product details. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  if (loading) return <Loader />;
  if (error)
    return (
      <h3 style={{ textAlign: "center", color: "red", marginTop: "30px" }}>
        {error}
      </h3>
    );

  return (
    <div
      style={{
        padding: "20px",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        textAlign: "center",
        maxWidth: "600px",
        margin: "0 auto",
      }}
    >
        <button onClick={()=>navigate("/")} style={{ 
            borderRadius: "15px",
            backgroundColor: "#ecd2d2ff",
            marginBottom: "15px"}}>Go Back</button>
      <img
        src={product.image}
        alt={product.title}
        style={{ width: "250px", height: "300px", objectFit: "contain" }}
      />
      <h2 style={{ marginTop: "20px" }}>{product.title}</h2>
      <p style={{ marginTop: "10px", color: "#555" }}>{product.description}</p>
      <h3 style={{ marginTop: "10px", color: "#666" }}>
        Category: {product.category}
      </h3>
      <h3>Rating : {product.rating.rate}/5</h3>
      <h3>Count : {product.rating.count}</h3>
      <h2 style={{ marginTop: "15px", color: "#2e7d32" }}>₹{product.price}</h2>
    </div>
  );
}

export default ProductDetails;
