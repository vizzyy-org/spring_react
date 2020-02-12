import React from "react";
import logo from "../logo.svg";

class HomePage extends React.Component{
    render() {
        return (
            <div className="Content">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                    Hello World. How are you?
                </p>
            </div>
        );
    }
}

export default HomePage;