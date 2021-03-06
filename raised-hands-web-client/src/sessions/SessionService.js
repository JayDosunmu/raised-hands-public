import { http } from '../util';

function formatConnectUrl(websocketUrl) {
    // TODO: this must be configurable
    return `ws://${
        process.env.REACT_APP_API_URL
            ? 'localhost:8080'
            : 'api.raisedhands.io'}${websocketUrl}`
}

async function createSession(name, distractionFree, startDate, endDate) {
    const url = '/session';
    const createSessionResponse = await http.post(url, {
        name,
        distractionFree,
        startDate,
        endDate,
    });
    return createSessionResponse.data;
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

async function joinSession(joinId, passcode) {
    const joinSession = await http.post(`/session/join`, {
        joinId,
        passcode
    });
    return joinSession.data;
}

async function changeSessionActive(sessionId, active) {
    const url = `/session/${sessionId}`;
    http.put(url, {
        active
    });
}

export {
    changeSessionActive,
    createSession,
    getSession,
    getUserSessions,
    joinSession,
};
