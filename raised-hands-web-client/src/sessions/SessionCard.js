import React from 'react';
import { Link } from 'react-router-dom';

import { SessionIdDisplay } from '.';

export default ({ session }) => (
    <li>
        <Link to={`sessions/${session.sessionId}/participate`}>{session.name}: <SessionIdDisplay joinId={session.joinId} />[{session.passcode}]</Link>
    </li>
);
