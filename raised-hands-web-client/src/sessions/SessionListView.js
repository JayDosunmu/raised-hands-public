import React from "react";
import { Link } from 'react-router-dom';

import { SessionService } from '.';

export default class SessionListView extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
          sessions: {}
      };
  }

  componentDidMount() {
    this.getUserSessions();
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
      return (
          <div>
              <div>Your Sessions</div>
              <ul>
              {
                  Object.entries(this.state.sessions).map(([sessionId, session]) => (
                      <li key={sessionId}>
                        <Link to={`sessions/${sessionId}/participate`}>{session.name}</Link>
                      </li>
                  ))
              }
              </ul>
          </div>
      );
  }
}
