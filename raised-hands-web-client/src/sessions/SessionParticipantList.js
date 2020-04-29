import React from 'react';

import { SessionParticipantCard } from '.'

export default ({ participants = {} }) => (
    <div>
        <div>
            <h2>Participants</h2>
        </div>
        <ul className="list-unstyled">
            {
                Object.entries(participants)
                    .sort(([idx1, p1], [idx2, p2]) => (p1.sessionParticipantId - p2.sessionParticipantId))
                    .map(([_, participant]) =>
                        <SessionParticipantCard participant={participant} key={participant.sessionParticipantId} />
                    )
            }
        </ul>
    </div>
);
