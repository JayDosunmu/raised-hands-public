import React from "react";
import { Link, Redirect } from 'react-router-dom';

import { CreateSessionForm, JoinSessionForm, SessionService } from '.';

export default class SessionListView extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
          sessionId: null,
          sessions: {},
      };
      console.log(this.state)
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
          <div>
              Create Session:
              <CreateSessionForm addSession={this.addSession} />
              <br />
              <JoinSessionForm joinSession={this.joinSession} />
              <br />
              <div>Your Sessions</div>
              <ul>
              {
                  Object.entries(this.state.sessions).map(([sessionId, session]) => (
                      <li key={sessionId}>
                        <Link to={`sessions/${sessionId}/participate`}>{session.name}: {session.joinId}[{session.passcode}]</Link>
                      </li>
                  ))
              }
              </ul>
          </div>
      );
  }
}
