import React from "react";
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
                        <Col xs={6}>
                            <a href="/users"><button className="button1">List</button></a>
                        </Col>
                        <Col xs={6}>
                            <a href="/users/form"><button className="button1">form</button></a>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Users;