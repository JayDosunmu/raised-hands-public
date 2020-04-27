function createInteractionMessage(message, sessionParticipantId) {
    return JSON.stringify({
        message,
        sessionParticipantId,
        type: "message"
    });
}

export {
    createInteractionMessage
};
