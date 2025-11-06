import React , {useState} from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import ProductDetails from "./pages/ProductDetails";
import "./App.css"

function App() {

  const [search, setSearch] = useState("");

  return (
    <div>
      <Routes>
        <Route path="/" element={<Home  search={search} setSearch={setSearch}/>} />
        <Route path="/product/:id" element={<ProductDetails search={search}/>} />
      </Routes>
    </div>
  )
}

export default App;
