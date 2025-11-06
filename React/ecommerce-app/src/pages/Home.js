import React, { useEffect, useState } from "react";
import axios from "axios";
import ProductCard from "../components/ProductCard";
import Loader from "../components/Loader";
import "./Home.css"

function Home({search}) {
  const [products, setProducts] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // const [category, setCategory] = useState(products);

  


  useEffect(() => {
    // Fetch product data using axios
    const fetchProducts = async () => {
      try {
        const response = await axios.get("https://fakestoreapi.com/products");
        setProducts(response.data);
      } catch (err) {
        console.error("Error fetching products:", err);
        setError("Failed to load products. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const filtered = products.filter((p) =>
    p.title.toLowerCase().includes(search.toLowerCase())
  );

  if (loading) return <Loader />;
  if (error) return <h3 style={{ textAlign: "center", color: "red" }}>{error}</h3>;

  
  
  return (
    <>
    {/* <div>
      <ul>
        {
          products.map((x) => {
            <li>x.category</li>
          }) 
        }
        <li>ALL</li>
      </ul>
    </div> */}
    <div className="" style={{ padding: "70px" }}>

      <div className="product-grid">
        {filtered.length > 0 ? (
          filtered.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))) : 
          (<h3 style={{ textAlign: "center", width: "100%" }}>No products found</h3>)
        }
      </div>
    </div>
    </>
  ) ;
}

export default Home;
