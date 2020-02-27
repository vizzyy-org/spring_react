import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";

function fetchAPI(light, state) {
    fetch("/lights/" + light + "?state=" + state)
        .then((response) => {
            return response;
        })
        .then((myJson) => {
            console.log(myJson);
        });
}

class Lights extends React.Component {
    render() {
        return (
            <div className="Content">
                <br></br>
                <Grid >
                    <Row>
                        <Col xs={4} >
                            LAMP
                        </Col>
                        <Col xs={4} >
                            XMAS
                        </Col>
                        <Col xs={4} >
                            TABLE
                        </Col>
                    </Row>
                    <br></br>
                    <br></br>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("one", true)}>On</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("two", true)}>On</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("three", true)}>On</button>
                        </Col>
                    </Row>
                    <br></br>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("one", false)}>Off</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("two", false)}>Off</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("three", false)}>Off</button>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Lights;