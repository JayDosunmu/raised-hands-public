import React from 'react';
import Stomp from 'stompjs';

import WebsocketForm from './WebsocketForm';

export default class WebsocketClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            websocket: null,
            users: {}
        };
    }

    addUser = (user) => {
        const users = this.state.users;
        if (!users.hasOwnProperty(user.sessionParticipantId)) {
            users[user.sessionParticipantId] = user;
        }
        this.setState({
            users
        });

    }

    connectWebsocket = ({connectUrl, topicUrl, appUrl }) => {
        try {
            const websocket = Stomp.client(connectUrl);
            websocket.connect({}, e => {
                console.log("connected to websocket: " + connectUrl);

                websocket.subscribe(topicUrl, message => {
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
                <ul>
                {
                    Object.entries(this.state.users).map( ([participantId, user]) => (
                        <li key={participantId}>{user.account.email}</li>
                    ))
                }
                </ul>
            </div>
        );
    }
}
