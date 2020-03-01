import React from "react";
import {Link} from "react-router-dom";
import {Col, Grid, Row} from "react-flexbox-grid";

class Users extends React.Component{

    render() {
        return (
            <div className="Users">
                <p>
                    Manage Users
                </p>
                <Grid >
                    <Row>
                        <Col xs><Link to="/users/list">List</Link></Col>
                        <Col xs><Link to="/users/form">Register</Link></Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Users;