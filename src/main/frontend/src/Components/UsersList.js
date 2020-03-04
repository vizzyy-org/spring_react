import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";
import {Link} from "react-router-dom";

class UsersList extends React.Component{
    constructor(props) {
        super(props);
        this.state = { users: [] }
    }

    componentDidMount() {
        fetch("/users/list")
            .then(response => response.json())
            .then(data => {
                this.setState({ users: data })
            })
    }

    deleteUser(cn){
        fetch("/users/delete?CN="+cn)
            .then((response) => {
                fetch("/users/list")
                    .then(response => response.json())
                    .then(data => {
                        this.setState({ users: data })
                    })
                return response;
            })
            .then((myJson) => {
                console.log(myJson);
            });
    };

    render() {
        return (
            <div className="Users" >
                <Row>
                    <Col xs={6}>
                        <a href="/users"><button className="button1">List</button></a>
                    </Col>
                    <Col xs={6}>
                        <a href="/users/form"><button className="button1">form</button></a>
                    </Col>
                </Row>
                <br/><br/>
                <Grid >
                    {this.state.users.map(user => {
                        return <Row>
                            <Col className="Little-Text" xs>{user.commonName}</Col>
                            <Col className="Little-Text" xs >{user.role}</Col>
                            <Col xs ><button onClick={() => this.deleteUser(user.commonName)}>Delete</button></Col>
                            <br/><br/><br/>
                        </Row>
                    })}
                </Grid>
            </div>
        );
    }
}

export default UsersList;