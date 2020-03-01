import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";

class Users extends React.Component{
    constructor() {
        super()
        this.state = { users: [] }
    }

    componentDidMount() {
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
                    Users List
                </p>
                <br/>
                <Grid >
                    {this.state.users.map(user => {
                        return <Row><Col xs={6} >{user.commonName}</Col><Col xs={6} >{user.role}</Col></Row>
                    })}
                </Grid>
            </div>
        );
    }
}

export default Users;