import React from 'react';

import { InteractionInput, InteractionList, InteractionService } from '.';
import { SocketContext } from '../util';


export default class InteractionComponent extends React.Component {
    static contextType = SocketContext;

    constructor(props) {
        super(props);
        this.state = {
            interactions: {},
        }
    }

    componentDidUpdate = async (prevProps) => {
        if (prevProps.websocketData !== this.props.websocketData) {
            const { topicUrl } = this.props.websocketData;
            const websocket = await this.context.socket.subscribe(topicUrl, message => {
                const data = JSON.parse(message.body);
                if (data.type === 'interaction') {
                    this.addInteraction(data);
                }
            });
            this.setState({
                websocket,
            })
        }
        if (prevProps.sessionId !== this.props.sessionId) {
            this.getInteractions(this.props.sessionId);
        }
    }

    componentWillUnmount() {
        try {
            this.state.websocket.unsubscribe();
        } catch (error) {
            console.log('unable to unsubscribe from interaction socket context')
            console.log(error)
        }
    }

    addInteraction = (interaction) => {
        const { interactions } = this.state;
        interactions[interaction.interactionId] = interaction;
        this.setState({ interactions });
    }

    createInteraction = (message) => {
        const participantId = this.props.participant.sessionParticipantId
        InteractionService.create(
            this.props.sessionId,
            participantId,
            message
        );
    }

    getInteractions = async (sessionId) => {
        const interactionsData = await InteractionService.getInteractions(sessionId);

        const { interactions } = this.state;
        interactionsData.forEach(interaction => {
            interactions[interaction.interactionId] = interaction;
        });
        this.setState({
            interactions
        });
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
