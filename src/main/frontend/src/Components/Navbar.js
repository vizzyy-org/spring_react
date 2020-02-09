import React from "react";

class Navbar extends React.Component{
    render() {
        return (
            <div>
                <ul id="Navbar">
                    <li><a href="#">Home</a></li>
                    <li><a href="#">About</a></li>
                    <li><a href="#">FAQ</a></li>
                    <li><a href="#">Contact</a></li>
                </ul>
            </div>
        );
    }
}

export default Navbar;