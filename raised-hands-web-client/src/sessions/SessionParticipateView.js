import React from 'react';

import { SessionParticipateHeader, SessionParticipantList, SessionService } from '.';
import { InteractionComponent } from '../interactions';
import { SocketProvider, SocketContext } from '../util';

class SessionParticipateView extends React.Component {
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
            this.state.websocket.unsubscribe();
        } catch (error) {
            console.log('unable to unsubscribe from session socket context')
            console.log(error)
        }
    }

    getSession = async (sessionId) => {
        const sessionData = await SessionService.getSession(sessionId);

        const { participants, websocketData } = sessionData;
        const [userParticipant] = sessionData.participants.filter(p =>
            p.account.accountId === JSON.parse(localStorage.getItem('user')).accountId);
        this.setState({
            participants,
            sessionData,
            userParticipant,
            websocketData,
        });

        const websocket = await this.context.socket.subscribe(
            websocketData.topicUrl,
            (message) => {
                const data = JSON.parse(message.body);
                if (data.type === 'sessionJoin') {
                    this.addParticipant(data);
                } else if (data.type === 'sessionActive') {
                    const { sessionData } = this.state;
                    sessionData.active = data.active;
                    this.setState({ sessionData });
                }
            }
        );
        this.setState({
            websocket,
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
                <div className="col">
                    <SessionParticipateHeader
                        session={this.state.sessionData}
                        participant={this.state.userParticipant}/>
                </div>
                <div className="w-100"/>
                <div className="col-2" >
                    <SessionParticipantList participants={this.state.participants} />
                </div>
                <div className="col">
                    <InteractionComponent
                        sessionId={this.state.sessionId}
                        sessionActive={this.state.sessionData ? this.state.sessionData.active : false}
                        participant={this.state.userParticipant}
                        websocketData={this.state.websocketData}
                        />
                </div>
            </div>
        );
    }
}

export default (props) => (
    <SocketProvider>
        <SessionParticipateView {...props} />
    </SocketProvider>
);
