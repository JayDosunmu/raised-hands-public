import { http } from '../util';

function formatConnectUrl(websocketUrl) {
    // TODO: this must be configurable
    return `ws://localhost:8080${websocketUrl}`
}

async function getUserSessions(userId) {
    const url = `/session${ userId ? '/' : ''}${userId || ''}`;
    const getSessionsResponse = await http.get(url);
    return getSessionsResponse.data;
}

async function getSession(sessionId) {
    if (!sessionId) {
        throw new Error('invalid sessionId value used to get session');
    }
    const url = `/session/${sessionId}`;
    const getSessionResponse = await http.get(url);

    const sessionData = getSessionResponse.data
    sessionData.websocketData.connectUrl = formatConnectUrl(
            sessionData.websocketData.connectUrl,
        );

    return getSessionResponse.data;
}

export {
    getSession,
    getUserSessions,
};