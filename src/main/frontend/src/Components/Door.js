import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";

function fetchAPI(state) {
    fetch("/door/" + state)
        .then((response) => {
            return response;
        })
        .then((myJson) => {
            console.log(myJson);
        });
}

class Door extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            door: ""
        }
    }

    async getDoor(){
        fetch("/door/state")
            .then(response => response.json())
            .then((data) => {
                console.log("Door state: "+data.state);
                let text = data.state == true ? "OPENED" : "CLOSED";
                this.setState({loaded: true, door: text})
            });
    }

    async componentDidMount() {
        await this.getDoor();
    }


    render() {
        return (
            <div>
                {
                    this.state.loaded &&
                    <div className="Content">
                        <br/>
                        <Grid>
                            <Row>
                                <Col xs={12}>
                                    {this.state.door}
                                </Col>
                            </Row>
                            <br/>
                            <br/>
                            <Row>
                                <Col xs={12}>
                                    DOOR
                                </Col>
                            </Row>
                            <br/>
                            <br/>
                            <Row>
                                <Col xs={12}>
                                    <button className="button1" onClick={() => fetchAPI("open")}>Open</button>
                                </Col>
                            </Row>
                            <br/>
                            <Row>
                                <Col xs={12}>
                                    <button className="button1" onClick={() => fetchAPI("close")}>Close</button>
                                </Col>
                            </Row>
                        </Grid>
                    </div>
                }
            </div>
        );
    }
}

export default Door;