import React from "react";

class Video extends React.Component{
    render() {
        return (
            <div className="Content">
                <img className="stream" src="/video/oculus" alt="oculus"/>
                <br/>
                <img className="stream" src="/video/door"  alt="door"/>
            </div>
        );
    }
}

export default Video;