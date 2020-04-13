import React from 'react';
import { Link, Route } from 'react-router-dom';

import LogoutButton from '../auth/LogoutButton';


export default () => (
    <div>
        <Route path='/sessions/:sessionId/participate' render={(props) => (
            <Link to='/sessions'>Back to Sessions</Link>
        )} />
        Header
        <LogoutButton />
    </div>
);
