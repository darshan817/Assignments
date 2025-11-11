import React from "react";
import "./Sidebar.css";
import { Link } from "react-router-dom";

function Sidebar({ isOpen, onClose }) {
  return (
    <>
      {/* Overlay (background) */}
      <div
        className={`sidebar-overlay ${isOpen ? "show" : ""}`}
        onClick={onClose}
      ></div>

      {/* Sidebar container */}
      <aside className={`sidebar ${isOpen ? "open" : ""}`}>
        <button className="close-btn" onClick={onClose}>
          ✕
        </button>

        <nav className="sidebar-nav">
          <ul>
            <Link to={'/'}> <li>  🏠 Home</li>  </Link>
            <li>🛍️ Shop</li>
            <li>📦 Categories</li>
            <li>📞 Contact</li>
          </ul>
        </nav>
      </aside>
    </>
  );
}

export default Sidebar;
