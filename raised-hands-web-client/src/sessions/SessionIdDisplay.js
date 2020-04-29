import React from 'react';

export default ({ joinId }) => (
    <span>
        {joinId.substr(0, 3)}-
        {joinId.substr(3, 3)}-
        {joinId.substr(6)}
    </span>
);
