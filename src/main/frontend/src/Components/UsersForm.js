import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";
import {Link} from "react-router-dom";

class UsersForm extends React.Component{
    constructor() {
        super();
        this.state = {
            cn: '',
            role: '',
            pw: '',
            dur: '',
            email: ''
        };
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    onSubmit = (e) => {
        e.preventDefault();
        console.log(this.state);
        const { cn, role, pw, dur, email } = this.state;
        fetch("/users/generate?CN="+cn+"&role="+role+"&pw="+pw)
            .then((response) => {
                return response;
            })
            .then((myJson) => {
                console.log(myJson);
            });
    }

    render() {
        const { cn, role, pw, dur, email } = this.state;

        return (
            <div className="Users">
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
                <form onSubmit={this.onSubmit}>
                    <label>
                        CommonName: <input type="text" name="cn" value={cn} onChange={this.onChange}/> <br/><br/>
                        Role: <input type="text" name="role" value={role} onChange={this.onChange}/> <br/><br/>
                        Password: <input type="text" name="pw" value={pw} onChange={this.onChange}/> <br/><br/>
                        Duration: <input type="text" name="dur" value={dur} onChange={this.onChange}/> <br/><br/>
                        Email: <input type="text" name="email" value={email} onChange={this.onChange}/> <br/><br/>
                    </label>
                    <br/>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );
    }
}

export default UsersForm;