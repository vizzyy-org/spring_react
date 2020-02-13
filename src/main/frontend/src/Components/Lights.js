import React from "react";

function fetchAPI(light, state) {
    fetch("/lights/" + light + "?state=" + state)
        .then((response) => {
            return response;
        })
        .then((myJson) => {
            console.log(myJson);
        });
}

class Lights extends React.Component {
    render() {
        return (
            <div className="Content">
                <p>
                    Lights Page.
                    <br></br>
                    <button onClick={() => fetchAPI("one", true)}>
                        On!
                    </button>
                    <br></br>
                    <button onClick={() => fetchAPI("one", false)}>
                        Off!
                    </button>
                </p>
            </div>
        );
    }
}

export default Lights;