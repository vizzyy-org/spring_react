import React from "react";
import { LazyLog, ScrollFollow } from 'react-lazylog';

class Users extends React.Component{
    render() {
        return (
            <div className="logs">
                <ScrollFollow
                    startFollowing={true}
                    render={({ follow, onScroll }) => (
                        <LazyLog url="/logs" stream follow={follow} onScroll={onScroll} />
                    )}
                />
            </div>
        );
    }
}

export default Users;