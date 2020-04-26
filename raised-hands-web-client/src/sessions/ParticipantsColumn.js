import React from 'react';
import {SessionParticipantCard} from "."


export default class ParticipantsColumn extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            websocket: null,
            participants: {}
        };
    }

    render() {
        return (
    
    <div>

    <h2 className = "ParticipantsColumnHeader">
    Participants
    </h2>
   


        <ul >
      
        {
            Object.entries(this.state.participants)
                .sort(([idx1, p1], [idx2, p2]) => (p1.sessionParticipantId - p2.sessionParticipantId))
                .map(([_, participant]) =>
                    <SessionParticipantCard participant={participant} key={participant.sessionParticipantId} />
                )
        }
        </ul>
    </div>      
        );
        }

}