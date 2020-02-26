import React from "react";
import fetchAPI from "./fetchAPI";

class Users extends React.Component{
    componentDidMount(){ fetchAPI("/users") }

    render() {
        return (
            <div className="Content">
                <p>
                    Users Page.
                </p>
            </div>
        );
    }
}

export default Users;