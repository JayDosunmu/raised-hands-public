import React from 'react';
import { Link, Route } from 'react-router-dom';

import LogoutButton from '../auth/LogoutButton';


export default () => (
    <nav className="navbar navbar-custom">
        <Route path='/sessions/:sessionId/participate' render={(props) => (
            <Link className ="LinkIe"to='/sessions'>Back to Sessions </Link>
        )} />
        <h2>RAISED HANDS </h2>
        <LogoutButton />
    </nav>
);
