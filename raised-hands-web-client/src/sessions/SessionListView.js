import React from "react";
import Stomp from 'stompjs'

export default class SessionListView extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
          websocketUrl: "ws://localhost:8080/api/connect",
          websocket: null,
          participants: {}
      };
  }

  componentDidMount() {
    this.connectWebsocket(this.state.websocketUrl);
  }

  addParticipant = (participant) => {
      const participants = this.state.participants;
      participants[participant.sessionParticipantId] = participant;
      this.setState({
          participants
      });

  }

  connectWebsocket = (websocketUrl) => {
      try {
          const websocket = Stomp.client(websocketUrl);
          websocket.connect({}, e => {
              console.log("connected to websocket: " + websocketUrl);

              websocket.subscribe("/topic/session/3", message => {
                  console.log(JSON.parse(message.body))
                  this.addParticipant(JSON.parse(message.body));
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
              <div>Connected to {this.state.websocketUrl}</div>
              <ul>
              {
                  Object.entries(this.state.participants).map(([participantId, participant]) => {
                    return(
                      <li key={participantId}>
                        {participant.account.email}
                      </li>
                  )})
              }
              </ul>
          </div>
      );
  }
}
