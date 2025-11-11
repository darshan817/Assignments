import React , {useState} from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import ProductDetails from "./pages/ProductDetails";
import "./App.css"
import Header from "./components/Header";
import CartPage from "./pages/CartPage";
import CheckoutPage from "./pages/CheckoutPage";
import OrdersHistoryPage from "./pages/OrdersHistory";

function App() {

  const [search, setSearch] = useState("");

  return (
    <div>
      {<Header search={search} setSearch={setSearch}/>}
      <Routes>

        <Route path="/" element={<Home  search={search} setSearch={setSearch}/>} />
        <Route path="/product/:id" element={<ProductDetails search={search}/>} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/checkout" element={<CheckoutPage />} />
        <Route path="/orders" element={<OrdersHistoryPage />} />
      </Routes>
    </div>
  )
}

export default App;
