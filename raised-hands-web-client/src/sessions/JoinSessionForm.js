
import React, { Component } from 'react';

import { SessionService } from '.'

export default class JoinSessionForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      joinId: '',
      passcode: '',
    };
  }

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    });
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const {
        joinId,
        passcode,
    } = this.state;

    this.joinSession(joinId, passcode);
  }

  joinSession = async (joinId, passcode) => {
    const joinSessionResponse = await SessionService.joinSession(
        joinId,
        passcode
    );
    this.props.joinSession(joinSessionResponse)
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <input
            type='text'
            name='joinId'
            placeholder="Session's Join ID"
            value={this.state.name}
            onChange={this.handleChange}
            required
          />

          <input
            type='text'
            name='passcode'
            placeholder='6-digit Passcode'
            onChange={this.handleChange}
          />
          <button type='submit'>Join Session</button>
        </form>
      </div>
    );
  }
}

