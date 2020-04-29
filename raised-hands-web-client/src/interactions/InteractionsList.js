import React from 'react';

import { InteractionCard } from '.';

export default ({ interactions = {} }) => (
    <ul>
        {
            Object.entries(interactions).map(([interactionId, interaction]) => (
                <InteractionCard key={interactionId} interaction={interaction} />
            ))
        }
    </ul>
);
