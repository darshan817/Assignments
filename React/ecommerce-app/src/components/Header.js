import "./Header.css";


function Header({search,setSearch}) {


    return (
        <header className="header">
            <h2 className="logo">D-Shop</h2>
            <input
                className="input"
                type="text"
                placeholder="Search products..."
                value={search}
                onChange={(event) => setSearch(event.target.value)}
            />
            <div className="icons">
                <span>🛒</span>
                <span>🔔</span>
                <span>👤</span>
            </div>
        </header>
    );
}

export default Header;
