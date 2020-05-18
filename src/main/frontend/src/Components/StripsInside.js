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
                <Grid >
                    <Row>
                        <Col xs={4} >

                        </Col>
                        <Col xs={4} >
                            INSIDE
                        </Col>
                        <Col xs={4} >

                        </Col>
                    </Row>
                    <br/>
                    <br/>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("inside", "clear")}>Clear</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("inside", "rainbowCycle")}>Rnbow</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("inside", "green")}>GN</button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("inside", "blue")}>BL</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("inside", "red")}>RD</button>
                        </Col>
                        <Col xs={4} >
                            <button className="button1" onClick={() => fetchAPI("inside", "wipeWhite")}>WPWT</button>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Lights;