import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";
import fetchAPI from "./fetchAPI";

class Door extends React.Component{
    componentDidMount(){ fetchAPI("/door") }

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
                            <button className="button1" onClick={() => fetchAPI("/door/open")}>Open</button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col xs={12} >
                            <button className="button1" onClick={() => fetchAPI("/door/close")}>Close</button>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Door;