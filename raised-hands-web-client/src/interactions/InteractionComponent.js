import React from 'react';

import { createInteractionMessage } from '../util';
import { InteractionInput, InteractionList } from '.';


export default class InteractionComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            interactions: {},
        }
    }

    componentDidMount = () => {
        // const { websocket, subscribeUrl, publishUrl } = this.props.websocketData;
        // websocket.subscribe(subscribeUrl, message => {
        //     const data = JSON.parse(message.body);
        //     if (data.type === 'message') {
        //         this.addInteraction(data);
        //     }
        // });
    }

    addInteraction = (interaction) => {
        console.log(interaction.message);
        const { interactions } = this.state;
        interactions.push(interaction.message);
        this.setState({ interactions });
    }

    createInteraction = (message) => {
        // const participantId = this.state.userParticipant.sessionParticipantId
        // const { websocket, subscribeUrl } = this.props.websocketData;

        // const messageBundle = createInteractionMessage(message, participantId);
        // websocket.send(subscribeUrl, {}, messageBundle);
    }

    render() {
        return (
            <div className="InteractionEvents">
                <InteractionList interactions={this.state.interactions} />
                <InteractionInput createInteraction={this.createInteraction} />
            </div>
        );
    }
}
