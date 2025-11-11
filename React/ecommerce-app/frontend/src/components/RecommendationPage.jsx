import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ProductCard from './ProductCard';

const Recommendations = ({ currentProduct }) => {
  const [recommendedProducts, setRecommendedProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!currentProduct?.category || !currentProduct?.id) {
      setLoading(false);
      return;
    }

    async function fetchRecommended() {
      try {
        // Fetch products by category from API
        const response = await axios.get(
          `https://fakestoreapi.com/products/category/${encodeURIComponent(currentProduct.category)}`
        );
        const categoryProducts = response.data;

        // Exclude the current product from recommendations
        const filtered = categoryProducts.filter(
          (prod) => prod.id !== currentProduct.id
        );

        setRecommendedProducts(filtered);
      } catch (error) {
        console.error('Failed to fetch recommendations:', error);
      } finally {
        setLoading(false);
      }
    }

    fetchRecommended();
  }, [currentProduct]);

  if (loading) return <p>Loading recommendations...</p>;
  if (!currentProduct) return null;

  return (
    <div style={{ padding: '20px', marginTop: '30px' }}>
      <h2>Recommended Products in "{currentProduct.category}" Category</h2>

      {recommendedProducts.length === 0 ? (
        <p>No recommendations available in this category.</p>
      ) : (
        <div style={{ display: 'flex', gap: '15px', flexWrap: 'wrap' }}>
          {recommendedProducts.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Recommendations;
