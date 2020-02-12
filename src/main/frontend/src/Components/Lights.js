import React from "react";
import {Link} from "react-router-dom";

function fetchAPI(param, callback) {
    // param is a highlighted word from the user before it clicked the button
    return fetch("https://localhost:8000/light/one")
        .then(callback);
}

class Lights extends React.Component{
    render() {
        return (
            <div className="Content">
                <p>
                    Lights Page.
                    <br></br>
                    <button onClick={fetchAPI}>
                        Click me!
                    </button>

                    <li><Link to="/lights/one">one</Link></li>
                </p>
            </div>
        );
    }
}

export default Lights;