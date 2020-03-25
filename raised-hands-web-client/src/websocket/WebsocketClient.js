import React from 'react';
import Stomp from 'stompjs';

import WebsocketForm from './WebsocketForm';

export default class WebsocketClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            websocket: null,
            users: []
        };
    }

    addUser = (user) => {
        const users = this.state.users;
        users.push(user);
        this.setState({
            users
        });

    }

    connectWebsocket = (websocketUrl) => {
        try {
            const websocket = Stomp.client(websocketUrl);
            websocket.connect({}, e => {
                console.log("connected to websocket: " + websocketUrl);

                websocket.subscribe("/topic/joinSession", message => {
                    console.log(message)
                    this.addUser(JSON.parse(message.body));
                })
            });
            this.setState({
                websocket
            });
        } catch(error) {
            console.log("unable to connect to websocket: " + error.message);
            console.log(error);
        }
    }

    render() {
        return (
            <div>
                <WebsocketForm connectWebsocket={this.connectWebsocket} />
                {
                    this.state.users.map(user => (
                        <div>{user.email}</div>
                    ))
                }
            </div>
        );
    }
}
