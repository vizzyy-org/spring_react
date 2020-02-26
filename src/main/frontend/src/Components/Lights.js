import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";
import fetchAPI from "./fetchAPI";

class Lights extends React.Component {
    componentDidMount(){ fetchAPI("/lights") }

    render() {
        return (
            <div className="Content">
                <br/>
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
                    <br/>
                    <br/>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("/lights/one?state=true")}>On</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("/lights/two?state=true")}>On</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("/lights/three?state=true")}>On</button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("/lights/one?state=false")}>Off</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("/lights/two?state=false")}>Off</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("/lights/three?state=false")}>Off</button>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Lights;