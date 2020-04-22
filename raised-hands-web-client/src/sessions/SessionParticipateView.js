import React from 'react';
import Stomp from 'stompjs';
import { InteractionEvents, SessionParticipantCard, SessionService } from '.';


export default class SessionParticipateView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            websocket: null,
            participants: {}
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
        this.setState(sessionData);
        this.connectWebsocket(sessionData.websocketData);
    }

    connectWebsocket = ({ connectUrl, topicUrl, appUrl }) => {
        try {
            const websocket = Stomp.client(connectUrl);
            websocket.connect({}, e => {
                console.log("connected to websocket: " + connectUrl);

                websocket.subscribe(topicUrl, message => {
                    this.addParticipant(JSON.parse(message.body));
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

                    <ul className="col-2" >
                        <div className="ParticipantsColumn">

                        <div className = "ParticipantsColumnHeader">
                            <h2>Participants</h2>
                        </div>
                            {
                                Object.entries(this.state.participants)
                                    .sort(([idx1, p1], [idx2, p2]) => (p1.sessionParticipantId - p2.sessionParticipantId))
                                    .map(([_, participant]) =>
                                        <SessionParticipantCard participant={participant} key={participant.sessionParticipantId} />
                                    )
                            }
                        </div>
                    </ul>

                        <div className="col-10">
                        <div className="InteractionEvents">
                            <InteractionEvents />
                        </div>
                    </div>

                </div>
            
        );
    }
}
