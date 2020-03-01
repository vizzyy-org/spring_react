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
import Users from "./Components/Users";
import Logs from "./Components/Logs";

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
                    <Route path="/video">
                        <Video/>
                    </Route>
                    <Route path="/users/form">
                        <UsersForm/>
                    </Route>
                    <Route path="/users/list">
                        <UsersList/>
                    </Route>
                    <Route path="/users">
                        <Users/>
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
