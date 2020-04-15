import React from 'react';
import { Link, Route } from 'react-router-dom';

import LogoutButton from '../auth/LogoutButton';


export default () => (
    <nav className="navbar navbar-custom">
        <Route path='/sessions/:sessionId/participate' render={(props) => (
            <Link to='/sessions'>Back to Sessions</Link>
        )} />
        RAISED HANDS
        <LogoutButton />
    </nav>
);
