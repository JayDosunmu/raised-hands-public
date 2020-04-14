
import React, { Component } from 'react';

import { SessionService } from '.'

export default class CreateSessionForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      setTime: false,
      startDate: '',
      endDate: '',
    };
  }

  handleChange = (event) => {
    const { target } = event;
    const value = target.name === 'setTime' ? target.checked : target.value;
    this.setState({
      [event.target.name]: value
    });
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const {
        name,
        startDate,
        endDate,
    } = this.state;

    this.createSession(name, startDate, endDate);
  }

  createSession = async (name, startDate, endDate) => {
    const createSessionResponse = await SessionService.createSession(
        name,
        false,
        startDate,
        endDate
    );
    this.props.addSession(createSessionResponse)
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <input
            type='text'
            name='name'
            placeholder='Session name'
            value={this.state.name}
            onChange={this.handleChange}
            required
          />

          <input
            type='checkbox'
            name='setTime'
            checked={this.state.setTime}
            onChange={this.handleChange}
          />
          <label htmlFor='setTime'>Set start and end times</label>
          {
            this.state.setTime &&
            (
              <div>
                <input
                  type='datetime-local'
                  name='startDate'
                  value={this.state.startDate}
                  onChange={this.handleChange}
                  />
                <input
                  type='datetime-local'
                  name='endDate'
                  value={this.state.endDate}
                  onChange={this.handleChange}
                  />
                </div>
              )
          }
          <button type='submit'>Create Session</button>
        </form>
      </div>
    );
  }
}

