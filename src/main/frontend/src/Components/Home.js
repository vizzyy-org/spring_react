import React from "react";
import logo from "../logo.svg";

class Home extends React.Component{
    render() {
        return (
            <div className="Content">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                    Welcome Home.
                </p>
            </div>
        );
    }
}

export default Home;