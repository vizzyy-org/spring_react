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
                            STRIPS
                        </Col>
                    </Row>
                    <br/>
                    <br/>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("one", true)}>On</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("two", true)}>On</button>
                        </Col>
                        <Col xs={4} >
                            <a href="/lights/strips/inside"><button className="button1">IN</button></a>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("one", false)}>Off</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("two", false)}>Off</button>
                        </Col>
                        <Col xs={4} >
                            <a href="/lights/strips/outside"><button className="button1">OUT</button></a>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Lights;