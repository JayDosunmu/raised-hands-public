import React from "react";
import { Redirect } from 'react-router-dom';

import { CreateSessionForm, JoinSessionForm, SessionService, SessionList } from '.';

export default class SessionListView extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
          sessionId: null,
          sessions: {},
      };
  }

  componentDidMount() {
    this.getUserSessions();
  }

  addSession = (session) => {
    const { sessions } = this.state;
    sessions[session.sessionId] = session;

    this.setState({
      sessions
    });
  }

  joinSession = (session) => {
    const { sessionId } = session;
    this.setState({
      sessionId
    })
  }

  getUserSessions = async () => {
      const sessions = {};
      try {
        const sessionsResponse = await SessionService.getUserSessions();
        sessionsResponse.forEach((session) => {
            sessions[session.sessionId] = session;
        });
      } catch (error) {
        console.log(error);
      }

      this.setState({
          sessions
      });

  }

  render() {
      return this.state.sessionId
      ? (<Redirect to={`sessions/${this.state.sessionId}/participate`} />)
      : (
          <div className="row">
              <CreateSessionForm addSession={this.addSession} />
              <JoinSessionForm joinSession={this.joinSession} />
              <SessionList sessions={this.state.sessions} />
          </div>
      );
  }
}
