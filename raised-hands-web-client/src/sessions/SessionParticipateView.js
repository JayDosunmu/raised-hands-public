import React from 'react';

import { SessionParticipantList, SessionService } from '.';
import { InteractionComponent } from '../interactions';
import { SocketContext } from '../util';

export default class SessionParticipateView extends React.Component {
    static contextType = SocketContext;

    constructor(props) {
        super(props);
        this.state = {
            userParticipant: null,
            participants: {},
        };
    }

    componentDidMount() {
        /*
            Get session data (use sessionId from url params)
            display current users
            display current active interaction events with their details
            display if active
            connect websocket
        */
        const { sessionId } = this.props.match.params;
        this.setState({ sessionId })
        this.getSession(sessionId)
    }

    componentWillUnmount() {
        this.state.websocketData.websocket.unsubscribe();
    }

    getSession = async (sessionId) => {
        const sessionData = await SessionService.getSession(sessionId);

        const [userParticipant] = sessionData.participants.filter(p =>
            p.account.accountId === JSON.parse(localStorage.getItem('user')).accountId);
        sessionData.userParticipant = userParticipant;

        const { websocketData } = sessionData;
        const websocket = await this.context.socket.subscribe(
            websocketData.topicUrl,
            (message) => {
                const data = JSON.parse(message.body);
                if (typeof data.type === 'undefined') {
                    this.addParticipant(data);
                }
            }
        );
        sessionData.websocketData.websocket = websocket

        this.setState(sessionData);
    }

    addParticipant = (participant) => {
        const participants = this.state.participants;
        if (!participants.hasOwnProperty(participant.sessionParticipantId)) {
            participants[participant.sessionParticipantId] = participant;
        }
        this.setState({
            participants
        });
    }

    render() {
        return (
                <div className="row">
                    <div className="col-2" >
                        <SessionParticipantList participants={this.state.participants} />
                    </div>
                    <div className="col">
                        <InteractionComponent
                            participant={this.state.userParticipant}
                            session={this.state.session}
                            />
                    </div>
                </div>
        );
    }
}
