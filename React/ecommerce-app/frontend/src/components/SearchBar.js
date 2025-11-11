import React from 'react';

function SearchBar({ search, setSearch }){
    return (
    <input
        className="input"
            type="text"
            placeholder="Search products..."
            value={search}
            onChange={(event) => setSearch(event.target.value)}
        />
    )
}
export default SearchBar;