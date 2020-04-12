import React from 'react';
import { Route, Redirect } from 'react-router';
import { AuthService } from '../auth';


export default ({ component: Component, ...rest }) => (
    <Route {...rest} render={(props) => (
        AuthService.isAuthenticated() === false
            ? <Component {...props} />
            : <Redirect to='/sessions' />
    )}/>
);
