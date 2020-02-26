import React from "react";
import logo from "../logo.svg";
import fetchAPI from "./fetchAPI";

class Home extends React.Component{
    componentDidMount(){ fetchAPI("/home") }

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