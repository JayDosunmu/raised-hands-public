import React from "react";
import { BrowserRouter, Switch} from "react-router-dom";

import { AuthenticatedRoute, UnauthenticatedRoute } from "./common";
import { SessionListView } from "./sessions";
import { Header } from './common'
import { HomeView } from "./home";
import { LoginView } from "./auth";


export default () => (
    <BrowserRouter>
        <Header />
        <Switch>
            <AuthenticatedRoute path='/sessions' component={SessionListView} />
            <UnauthenticatedRoute exact path="/login" component={LoginView} />
            <UnauthenticatedRoute exact path="/" component={HomeView} />
        </Switch>
    </BrowserRouter>
);
