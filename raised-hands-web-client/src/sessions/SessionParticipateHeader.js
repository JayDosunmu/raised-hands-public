import React from 'react';

import { SessionService, SessionIdDisplay } from '.';

export default ({ session, participant }) => {
    const { joinId = '...loading' , passcode = '...loading', sessionId = -1, active = true } = session || {};
    return (
        <div className="col">
            <h4>
                Session -- Join ID: <SessionIdDisplay joinId={joinId} /> || passcode: {passcode}
            </h4>
            { participant && participant.leader &&
                <button onClick={() => SessionService.changeSessionActive(sessionId, !active)} className="btn btn-primary">
                    {active ? 'End Session' : 'Start Session'}
                </button>
            }
        </div>
    )
};
