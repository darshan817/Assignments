import React, { useState, useEffect, useRef } from "react";
import "./Header.css";
import { useNavigate, useLocation, Link } from "react-router-dom";
import Sidebar from "./Sidebar";
import SearchBar from "./SearchBar";
import { useSelector } from "react-redux";

function Header({ search, setSearch }) {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef(null);

  const location = useLocation();
  const isHomePage = location.pathname === "/";

  const cartItems = useSelector((state) => state.cart?.items || []);
  const totalQuantity = cartItems.reduce((sum, item) => sum + item.quantity, 0);

  const navigate = useNavigate();

  // close dropdown when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowDropdown(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <>
      <header className="header">
        <h2 className="logo">
          <button
            className="menu-btn"
            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
          >
            ☰
          </button>
          <Link to="/">D-Shop</Link>
        </h2>

        {isHomePage && <SearchBar search={search} setSearch={setSearch} />}

        <div className="icons">
          {/* Cart Icon */}
          <span>
            <Link
              to="/cart"
              style={{
                position: "relative",
                textDecoration: "none",
                color: "black",
                cursor: "pointer",
              }}
            >
              🛒
              {totalQuantity > 0 && (
                <span
                  style={{
                    position: "absolute",
                    top: "-5px",
                    right: "-10px",
                    background: "red",
                    color: "white",
                    borderRadius: "50%",
                    padding: "2px 6px",
                    fontSize: "12px",
                  }}
                >
                  {totalQuantity}
                </span>
              )}
            </Link>
          </span>

          {/* Notification Icon */}
          <span>🔔</span>

          {/* Profile Dropdown */}
          <div className="profile-container" ref={dropdownRef}>
            <span
              className="profile-icon"
              onClick={() => setShowDropdown((prev) => !prev)}
            >
              👤
            </span>

            {showDropdown && (
              <div className="profile-dropdown">
                <p onClick={() => alert("profile page will be added later")}>Profile</p>
                <p onClick={() => navigate("/orders")}>Orders</p>
                <p
                  onClick={() => {
                    alert("Logged out!");
                    setShowDropdown(false);
                  }}
                >
                  Logout
                </p>
              </div>
            )}
          </div>
        </div>
      </header>

      <Sidebar
        isOpen={isSidebarOpen}
        onClose={() => setIsSidebarOpen(false)}
      />
    </>
  );
}

export default Header;
