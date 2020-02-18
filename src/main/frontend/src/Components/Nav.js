import React from "react";
import {Link} from "react-router-dom";

class Nav extends React.Component{
    render() {
        return (
            <div>
                <ul id="Navbar">
                    <li><Link to="/lights">Lights</Link></li>
                    <li><Link to="/">Home</Link></li>
                    <li><Link to="/door">Door</Link></li>
                </ul>
                <ul id="Navbar">
                    <li><Link to="/logs">Logs</Link></li>
                    <li><Link to="/video">Video</Link></li>
                    <li><Link to="/users">Users</Link></li>
                </ul>
            </div>
        );
    }
}

export default Nav;