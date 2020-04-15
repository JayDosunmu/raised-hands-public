import React from 'react';

import AuthService from './AuthService';
import { withRouter } from 'react-router';

export default withRouter(({ history }) => (
    AuthService.isAuthenticated()
        ? <button  onClick={() => {AuthService.logout(); history.push('/');}}>Logout</button>
        : <div></div>
));
