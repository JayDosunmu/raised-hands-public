import React from 'react';
import Stomp from 'stompjs';

function formatConnectUrl(websocketUrl) {
    // TODO: this must be configurable
    return `ws://${
        process.env.REACT_APP_API_URL
            ? 'localhost:8080'
            : 'api.raisedhands.io'}${websocketUrl}`
}

export const SocketContext = React.createContext('socket');

export const useSocket = () => React.useContext(SocketContext);

export class SocketProvider extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            socket: null,
            subscribeUrl: null,
            publishUrl: null
        }
    }

    componentWillUnmount = () => {
        try {
            const { socket } = this.state;
            socket !== null && socket.disconnect();
        } catch (error) {
            // socket may not be connected
        }
    }

    componentDidMount = async () => {
        await this.connectWebsocket();
    }

    subscribe = async (subscribeUrl, callbackFn) => {
        return this.socket.subscribe(subscribeUrl, callbackFn);
    }

    publish = async (publishUrl, options, data) => {
        return this.socket.send(publishUrl, options, data);
    }

    connectWebsocket = async () => {
        try {
            const connectUrl = formatConnectUrl('/connect');
            const websocket = await Stomp.client(connectUrl);
            await websocket.connect({}, () => {
                console.log("connected to websocket: " + connectUrl);
            });
            this.socket = websocket;
        } catch (error) {
            console.log("unable to connect to websocket: " + error.message);
        }
    }

    render() {
        return (
            <SocketContext.Provider 
                value={{
                    socket:{
                        subscribe: this.subscribe,
                        publish: this.publish,
                    },
                }}>
                {this.props.children}
            </SocketContext.Provider>
        )
    }
}

export default SocketProvider;
