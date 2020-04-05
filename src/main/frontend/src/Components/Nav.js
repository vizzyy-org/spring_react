import React from "react";
import {Link} from "react-router-dom";

class Nav extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            role: ""
        }
    }

    async getRole(){
        fetch("/users/role")
            .then(response => response.json())
            .then((data) => {
                console.log("Role data: "+data.role);
                this.setState({loaded: true, role: data.role})
            });
    }
    //
    // async componentDidMount() {
    //     await this.getRole();
    // }

    render() {
        return (
            <div>
                {/*{*/}
                {/*    this.state.loaded &&*/}
                    <div>
                        <ul id="Navbar">
                            <li><Link to="/lights">Lights</Link></li>
                            <li><Link to="/">Home</Link></li>
                            <li><Link to="/door">Door</Link></li>
                        </ul>
                        {/*{*/}
                        {/*    this.state.role === "ROLE_ADMIN" &&*/}
                            <ul id="Navbar">
                                <li><Link to="/logs">Logs</Link></li>
                                <li><Link to="/video">Video</Link></li>
                                <li><Link to="/users">Users</Link></li>
                            </ul>
                        {/*}*/}
                    </div>
                {/*}*/}
            </div>
        );
    }
}

export default Nav;