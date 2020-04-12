import React, { useState } from 'react';
import axios from 'axios';

async function onSubmit(e, sessionId, jwt, connectWebsocket) {
    e.preventDefault();
    const { data: sessionData } = await axios.get(
        `/api/session/${sessionId}`,
        {
            headers: {
                Authorization: `Bearer ${jwt}`
            }
        }
    )
    console.log(sessionData);

    const connectUrl = sessionData.websocketData.connectUrl;
    sessionData.websocketData.connectUrl = `ws://localhost:8080${connectUrl}`

    connectWebsocket(sessionData.websocketData);
}

export default ({ connectWebsocket }) => {
    const [sessionId, setSessionId] = useState('');
    const [jwt, setJwt] = useState('');
    return (
        <div>
            <form onSubmit={e => onSubmit(e, sessionId, jwt, connectWebsocket)}>
                <div>
                    <label>session id:</label>
                    <input type="text" onChange={e => setSessionId(e.target.value)}/>
                </div>
                <div>
                    <label>jwt:</label>
                    <input type="text" onChange={e => setJwt(e.target.value)}/>
                </div>
                <input type="submit" />
            </form>
        </div>
    )
};
