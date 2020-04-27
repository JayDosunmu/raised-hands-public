import React from 'react';
import Stomp from 'stompjs';
import { SessionParticipantList, SessionService } from '.';
import { InteractionComponent } from '../interactions';

export default class SessionParticipateView extends React.Component {
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
        if (this.state.websocketData) {
            const { websocket, subscribeUrl } = this.state.websocketData;
            websocket.unsubscribe(subscribeUrl);
            websocket.disconnect();
        }
    }

    getSession = async (sessionId) => {
        const sessionData = await SessionService.getSession(sessionId);
        const [userParticipant] = sessionData.participants.filter(p =>
            p.account.accountId === JSON.parse(localStorage.getItem('user')).accountId);
        sessionData.userParticipant = userParticipant;
        this.setState(sessionData);
        this.connectWebsocket(sessionData.websocketData);
    }

    connectWebsocket = ({ connectUrl, topicUrl, appUrl }) => {
        try {
            const websocket = Stomp.client(connectUrl);
            websocket.connect({}, e => {
                console.log("connected to websocket: " + connectUrl);

                websocket.subscribe(topicUrl, message => {
                    const data = JSON.parse(message.body);
                    if (data.type === 'undefined') {
                        this.addParticipant(data);
                    }
                })
            });
            this.setState({
                websocketData: {
                    websocket,
                    publishUrl: appUrl,
                    subscribeUrl: topicUrl,
                }
            });
        } catch (error) {
            console.log("unable to connect to websocket: " + error.message);
        }
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
                        websocketData={this.state.websocketData}
                        />
                </div>
            </div>
        );
    }
}
