import React from 'react';

export default ({ interactions = {} }) => (
    <ul>
        {
            Object.entries(interactions).map(([interactionId, interaction]) => (
                <li key={interactionId}>
                    {interaction.message}
                </li>
            ))
        }
    </ul>
);
