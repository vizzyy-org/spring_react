import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";

class Users extends React.Component{

    currPage = 1;

    constructor(props) {
        super(props);
        this.state = { users: [] }
    }

    getLogPage(page){
        console.log("getLogPage: "+page)
        fetch("/log/paged?page="+page)
            .then(response => response.json())
            .then(data => {
                this.setState({ users: data })
            })
    }

    turnPage(direction){
        console.log("direction: "+direction)
        if(direction === "next"){
            this.currPage++;
            this.getLogPage(this.currPage);
        }
        if(direction === "prev"){
            if(this.currPage > 1) {
                this.currPage--;
                this.getLogPage(this.currPage);
            }
        }
    }

    componentDidMount() {
        this.getLogPage(this.currPage);
    }

    render() {
        return (
            <div>
            <div className="logs">
                <Grid >
                    {this.state.users.map(user => {
                        return <Row>
                           <Col> {user} </Col>
                        </Row>
                    })}
                </Grid>
            </div>
            <div>
                <br/><br/>
                <Grid>
                    <Row>
                        <Col xs={6} >
                            <button className="button1" onClick={() => this.turnPage("next")}>NEXT</button>
                        </Col>
                        <Col xs={6} >
                            <button className="button1" onClick={() => this.turnPage("prev")}>PREV</button>
                        </Col>
                    </Row>
                </Grid>
            </div>

            </div>
        );
    }
}

export default Users;