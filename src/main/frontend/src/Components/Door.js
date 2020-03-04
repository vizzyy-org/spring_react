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
    render() {
        return (
            <div className="Content">
                <br/>
                <Grid >
                    <Row>
                        <Col xs={12} >
                            DOOR
                        </Col>
                    </Row>
                    <br/>
                    <br/>
                    <Row>
                        <Col xs={12} >
                            <button className="button1" onClick={() => fetchAPI("open")}>Open</button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col xs={12} >
                            <button className="button1" onClick={() => fetchAPI("close")}>Close</button>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Door;