import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";
import {Link} from "react-router-dom";

class UsersList extends React.Component{
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
            <div className="Users" >
                <p>
                    Manage Users
                </p>
                <Grid >
                    <Row>
                        <Col xs><Link to="/users/list">List</Link></Col>
                        <Col xs><Link to="/users/form">Register</Link></Col>
                    </Row>
                </Grid>
                <br/><br/>
                <Grid >
                    {this.state.users.map(user => {
                        return <Row><Col xs>{user.commonName}</Col><Col xs >{user.role}</Col></Row>
                    })}
                </Grid>
            </div>
        );
    }
}

export default UsersList;