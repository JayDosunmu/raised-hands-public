import React from 'react';

import { InteractionInput, InteractionList } from '.';
import { createInteractionMessage, SocketContext } from '../util';


export default class InteractionComponent extends React.Component {
    static contextType = SocketContext;

    constructor(props) {
        super(props);
        this.state = {
            interactions: {},
        }
    }

    componentDidMount = async () => {
        if (this.props.websocketData) {
            const { topicUrl } = this.props.websocketData;
            const websocket = await this.context.socket.subscribe(topicUrl, message => {
                const data = JSON.parse(message.body);
                if (data.type === 'message') {
                    this.addInteraction(data);
                }
            });
            this.setState({
                websocket,
            })
        }
    }

    componentWillUnmount() {
        try {
            console.log('Interaction websocket at unmount')
            console.log(this.state.websocket)
            this.state.websocket.unsubscribe();
        } catch (error) {
            console.log('unable to unsubscribe from interaction socket context')
            console.log(error)
        }
    }

    addInteraction = (interaction) => {
        console.log(interaction.message);
        const { interactions } = this.state;
        interactions.push(interaction.message);
        this.setState({ interactions });
    }

    createInteraction = (message) => {
        const participantId = this.props.participant.sessionParticipantId
        const { topicUrl } = this.props.websocketData;

        const messageBundle = createInteractionMessage(message, participantId);
        this.context.socket.publish(topicUrl, {}, messageBundle);
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
