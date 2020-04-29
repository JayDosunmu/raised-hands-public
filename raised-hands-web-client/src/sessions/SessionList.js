import React from 'react';
import { SessionCard } from '.';

export default ({ sessions }) => (
    <div>
        <div>Your Sessions</div>
        <ul>
        {
            Object.entries(sessions).map(([_, session]) => (
                <SessionCard
                    key={session.sessionId}
                    session={session} />
            ))
        }
        </ul>
    </div>
);
