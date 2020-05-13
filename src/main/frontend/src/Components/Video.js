import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";

class Video extends React.Component{
    render() {
        return (
            <div className="Content">
                <br/>
                <Grid >
                    <Row>
                        {/*<Col xs={4} >*/}
                        {/*    <a href="/video/stream/oculus"><button className="button1">OUT</button></a>*/}
                        {/*</Col>*/}
                        <Col xs={4} >
                            <a href="/video/motion"><button className="button1">REC</button></a>
                        </Col>
                        <Col xs={4} >
                            <a href="/video/stream/door"><button className="button1">VOX</button></a>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Video;