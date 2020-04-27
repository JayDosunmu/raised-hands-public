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
        try{
            console.log('Session websocket at unmount')
            console.log(this.state.websocket)
            this.state.websocket.unsubscribe();
        } catch (error) {
            console.log('unable to unsubscribe from session socket context')
            console.log(error)
        }
    }

    getSession = async (sessionId) => {
        const sessionData = await SessionService.getSession(sessionId);

        const { participants } = sessionData;
        const [userParticipant] = sessionData.participants.filter(p =>
            p.account.accountId === JSON.parse(localStorage.getItem('user')).accountId);

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

        this.setState({
            participants,
            sessionData,
            userParticipant,
            websocket,
            websocketData,
        });
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
                            websocketData={this.state.websocketData}
                            />
                    </div>
                </div>
        );
    }
}
