import React from 'react';
import { Route, Redirect } from 'react-router';
import { AuthService } from '../auth';


export default ({ component: Component, ...rest }) => (
    <Route {...rest} render={(props) => (
        AuthService.isAuthenticated() === true
            ? <Component {...props} />
            : <Redirect to={{
                pathname: '/login',
                state: { from: props.location }
              }} />
    )}/>
);
