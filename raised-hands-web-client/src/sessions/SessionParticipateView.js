import React from 'react';
import Stomp from 'stompjs';
import { InteractionEvents, SessionService, ParticipantsColumn} from '.';


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
        
    <div class="container-fluid h-100">
    <div className="row h-100">

        <div className="col-2 d-inline-block h-100 ParticipantsColumn">
            <ParticipantsColumn/>
      
        </div> 

        <div className="col d-inline-block h-75 InteractionEvents " >
            <InteractionEvents />
        </div>
    
    </div>
    </div>
            
        );
    }
}
