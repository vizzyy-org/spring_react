import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";
import {Link} from "react-router-dom";

class UsersForm extends React.Component{
    render() {
        return (
            <div className="Content">
                <p>
                    Manage Users
                </p>
                <Grid >
                    <Row>
                        <Col xs ><Link to="/users/list">List</Link></Col>
                        <Col xs ><Link to="/users/form">Register</Link></Col>
                    </Row>
                </Grid>
                <br/><br/>
                Create New User
                <br/><br/>
                <form>
                    <label>
                        CommonName: <input type="text" name="cn" /> <br/><br/>
                        Role: <input type="text" name="role" /> <br/><br/>
                        Password: <input type="text" name="pw" /> <br/><br/>
                        Duration: <input type="text" name="dur" /> <br/><br/>
                        Email: <input type="text" name="email" /> <br/><br/>
                    </label>
                    <br/>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );
    }
}

export default UsersForm;