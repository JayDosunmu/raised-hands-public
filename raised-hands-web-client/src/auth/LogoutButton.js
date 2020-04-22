import React from 'react';

import AuthService from './AuthService';
import { withRouter } from 'react-router';

export default withRouter(({ history }) => (
    AuthService.isAuthenticated()
        ? <button class="btn btn-raised btn-success" onClick={() => {AuthService.logout(); history.push('/');}}>Logout</button>
        : <div></div>
));
