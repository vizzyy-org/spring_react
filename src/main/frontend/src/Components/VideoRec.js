import React from "react";
import {Col, Grid, Row} from "react-flexbox-grid";

let spot = 0;

function seek(forward){
    if(forward){
        spot = spot + 1;
        fetchImage();
    } else {
        if(spot > 0) {
            spot = spot - 1;
            fetchImage();
        }
    }
}

function fetchImage(){

    const element = document.getElementById('progress');
    function progress({loaded, total}) {
        element.innerHTML = Math.round(loaded/total*100)+'%';
    }

    fetch('/video/recordings?spot='+spot)
        .then(response => {

            if (!response.ok) {
                throw Error(response.status+' '+response.statusText)
            }

            // ensure ReadableStream is supported
            if (!response.body) {
                throw Error('ReadableStream not yet supported in this browser.')
            }

            // store the size of the entity-body, in bytes
            const contentLength = response.headers.get('content-length');

            // ensure contentLength is available
            if (!contentLength) {
                throw Error('Content-Length response header unavailable');
            }

            // parse the integer into a base-10 number
            const total = parseInt(contentLength, 10);

            let loaded = 0;

            return new Response(

                // create and return a readable stream
                new ReadableStream({
                    start(controller) {
                        const reader = response.body.getReader();

                        read();
                        function read() {
                            reader.read().then(({done, value}) => {
                                if (done) {
                                    controller.close();
                                    return;
                                }
                                loaded += value.byteLength;
                                progress({loaded, total});
                                controller.enqueue(value);
                                read();
                            }).catch(error => {
                                console.error(error);
                                controller.error(error)
                            })
                        }
                    }
                })
            );
        })
        .then(response =>
            // construct a blob from the data
            response.blob()
        )
        .then(data => {
            // insert the downloaded image into the page
            document.getElementById('img').src = URL.createObjectURL(data);
            element.innerHTML = "";
        })
        .catch(error => {
            console.error(error);
        })
}


class Video extends React.Component{

    componentDidMount() {
        fetchImage(spot);
    }

    render() {
        return (
            <div className="Content">
                <br/>
                <Grid >
                    <Row>
                        <Col xs={4} >
                            <a href="/video/oculus"><button className="button1">OUT</button></a>
                        </Col>
                        <Col xs={4} >
                            <a href="/video/motion"><button className="button1">REC</button></a>
                        </Col>
                        <Col xs={4} >
                            <a href="/video/door"><button className="button1">VOX</button></a>
                        </Col>
                    </Row>
                    <br/><br/>
                    <img className="stream" id="img" alt=""/>
                    <div className="Big-Text" id="progress"/>
                    <br/><br/>
                    <Row>
                        <Col xs={6} >
                            <button className="button1" onClick={() => seek(false)}>PREV</button>
                        </Col>
                        <Col xs={6} >
                            <button className="button1" onClick={() => seek(true)}>NEXT</button>
                        </Col>
                    </Row>
                </Grid>
            </div>
        );
    }
}

export default Video;