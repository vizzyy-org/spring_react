import React from 'react';
import './App.css';
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import Lights from "./Components/Lights";
import Navbar from './Components/Navbar';
import HomePage from './Components/HomePage';
import Door from "./Components/Door";
import Video from "./Components/Video";
import Users from "./Components/Users";

function App() {
    return (
        <Router>
            <div className="App">

                <Navbar/>
                <Switch>
                    <Route path="/lights">
                        <Lights/>
                    </Route>
                    <Route path="/door">
                        <Door/>
                    </Route>
                    <Route path="/video">
                        <Video/>
                    </Route>
                    <Route path="/users">
                        <Users/>
                    </Route>
                    <Route path="/">
                        <HomePage/>
                    </Route>
                </Switch>

                {/*<header className="App-header">*/}
                {/*    */}
                {/*</header>*/}

            </div>
        </Router>
    );
}

export default App;
