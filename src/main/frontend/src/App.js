import React from 'react';
import './App.css';
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import Lights from "./Components/Lights";
import Nav from './Components/Nav';
import Home from './Components/Home';
import Door from "./Components/Door";
import Video from "./Components/Video";
import UsersForm from "./Components/UsersForm";
import UsersList from "./Components/UsersList";
import Logs from "./Components/Logs";
import VideoOculus from "./Components/VideoOculus";
import VideoVox from "./Components/VideoVox";
import VideoRec from "./Components/VideoRec";

function App() {
    return (
        <Router>
            <div className="App">

                <Nav/>

                <Switch>
                    <Route path="/lights">
                        <Lights/>
                    </Route>
                    <Route path="/door">
                        <Door/>
                    </Route>
                    <Route path="/video/door">
                        <VideoVox/>
                    </Route>
                    <Route path="/video/oculus">
                        <VideoOculus/>
                    </Route>
                    <Route path="/video/motion">
                        <VideoRec/>
                    </Route>
                    <Route path="/video">
                        <Video/>
                    </Route>
                    <Route path="/users/form">
                        <UsersForm/>
                    </Route>
                    <Route path="/users">
                        <UsersList/>
                    </Route>
                    <Route path="/logs">
                        <Logs/>
                    </Route>
                    <Route path="/">
                        <Home/>
                    </Route>
                </Switch>

            </div>
        </Router>
    );
}

export default App;
