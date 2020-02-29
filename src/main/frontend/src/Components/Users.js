import React from "react";

class Users extends React.Component{
    constructor() {
        super()
        this.state = { users: [] }
    }

    componentDidMount() {
        let users = [];

        fetch("/users/list")
            .then(response => response.json())
            .then(data => {
                this.setState({ users: data })
            })
    }


    render() {
        return (
            <div className="Content">
                <p>
                    Users Page.
                </p>
                <ul>
                    {this.state.users.map(user => {
                        return <li key={`user-${user.commonName}`}>{user.commonName} - {user.role}</li>
                    })}
                </ul>
            </div>
        );
    }
}

export default Users;