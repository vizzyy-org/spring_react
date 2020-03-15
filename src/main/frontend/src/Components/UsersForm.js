import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";

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
        const { cn, role, pw } = this.state;
        fetch("/users/generate?CN="+cn+"&role="+role+"&pw="+pw)
            .then((response) => {
                return response;
            })
            .then((myJson) => {
                console.log(myJson);
            });
        this.setState( {cn: '', role: '', pw: '', dur: '', email: '' });
    }

    render() {
        const { cn, role, pw, dur, email } = this.state;

        return (
            <div className="Users">
                <Grid>
                    <Row>
                        <Col xs={6}>
                            <a href="/users"><button className="button1">List</button></a>
                        </Col>
                        <Col xs={6}>
                            <a href="/users/form"><button className="button1">form</button></a>
                        </Col>
                    </Row>
                </Grid>
                <br/><br/>
                Create New User
                <br/><br/>
                <form onSubmit={this.onSubmit}>
                    <label>
                        CommonName: <input type="text" name="cn" value={cn} placeholder="blarp-cn" onChange={this.onChange}/> <br/><br/>
                        Role: <input type="text" name="role" value={role} placeholder="power" onChange={this.onChange}/> <br/><br/>
                        Password: <input type="text" name="pw" value={pw} placeholder="pw" onChange={this.onChange}/> <br/><br/>
                        Duration: <input type="text" name="dur" value={dur} placeholder="365" onChange={this.onChange}/> <br/><br/>
                        Email: <input type="text" name="email" value={email} placeholder="something@gmail.com" onChange={this.onChange}/> <br/><br/>
                    </label>
                    <br/>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );
    }
}

export default UsersForm;