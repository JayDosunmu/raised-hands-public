import React, { useState } from 'react';

function onSubmit(e, websocketUrl, connectWebsocket) {
    e.preventDefault();
    connectWebsocket(websocketUrl);
}

export default ({ connectWebsocket }) => {
    const [websocketUrl, setWebsocketUrl] = useState('');
    return (
        <div>
            <form onSubmit={e => onSubmit(e, websocketUrl, connectWebsocket)}>
                <input type="text" onChange={e => setWebsocketUrl(e.target.value)}/>
                <input type="submit" />
            </form>
        </div>
    )
};
