import { http } from '../util';

async function getInteractions(sessionId) {
    try {
        const url = `/interaction/session/${sessionId}`
        const getInteractionsResponse = await http.get(url);
        return getInteractionsResponse.data;
    } catch(error) {
        return [];
    }
}

async function create(sessionId, sessionParticipantId, message) {
    try {
        const url = '/interaction'
        await http.post(url, {
            sessionId,
            sessionParticipantId,
            message,
        });
    } catch (error) {
        console.log(error);
    }
}

export {
    create,
    getInteractions,
};
