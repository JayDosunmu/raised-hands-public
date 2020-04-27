import React from 'react';
import { BrowserRouter, Switch} from 'react-router-dom';

import { AuthenticatedRoute, UnauthenticatedRoute } from './common';
import { SessionListView, SessionParticipateView } from './sessions';
import { Header } from './header'
import { HomeView } from './home';
import { LoginView , RegisterView } from './auth';
import { SocketProvider } from './util';

export default () => (
  <div className='app h-100'>
    <SocketProvider>
      <BrowserRouter>
          <Header />
          <div className='container-fluid'>
            <Switch>
                <AuthenticatedRoute path='/sessions/:sessionId/participate' component={SessionParticipateView} />
                <AuthenticatedRoute path='/sessions' component={SessionListView} />
                <UnauthenticatedRoute exact path='/login' component={LoginView} />
                <UnauthenticatedRoute exact path='/register' component={RegisterView} />
                <UnauthenticatedRoute exact path='/' component={HomeView} />
            </Switch>
          </div>
      </BrowserRouter>
    </SocketProvider>
  </div>
);
