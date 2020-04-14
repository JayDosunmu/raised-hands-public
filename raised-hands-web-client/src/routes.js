import React from 'react';
import { BrowserRouter, Switch} from 'react-router-dom';

import { AuthenticatedRoute, UnauthenticatedRoute } from './common';
import { SessionListView, SessionParticipateView } from './sessions';
import { Header } from './header'
import { HomeView } from './home';
import { LoginView , RegisterView } from './auth';


export default () => (
    <BrowserRouter>
        <Header />
        <Switch>
            <AuthenticatedRoute path='/sessions/:sessionId/participate' component={SessionParticipateView} />
            <AuthenticatedRoute path='/sessions' component={SessionListView} />
            <UnauthenticatedRoute exact path='/login' component={LoginView} />
            <UnauthenticatedRoute exact path='/register' component={RegisterView} />
            <UnauthenticatedRoute exact path='/' component={HomeView} />
        </Switch>
    </BrowserRouter>
);
