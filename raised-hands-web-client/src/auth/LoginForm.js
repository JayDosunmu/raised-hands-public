
import React, { Component } from 'react';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';

import { AuthService } from '.'

export default class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      redirectToReferrer: false
    };

    const { referrer } = this.props.location && this.props.location.state
                            ? this.props.location.state
                            : { referrer: '/sessions' };
    this.state.referrer = referrer;
  }

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    });
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { email, password } = this.state;

    this.login(email, password);
  }

  login = async (email, password) => {
    const { user, jwt } = await AuthService.authenticate(email, password);

    AuthService.setAppUser(user, jwt);
    this.setState({
      redirectToReferrer: true,
    })
  }

  render() {
    if (this.state.redirectToReferrer) {
      return (
        <Redirect to={this.state.referrer} />
      )
    }

    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <input
            type='email'
            name='email'
            placeholder='Email'
            value={this.state.email}
            onChange={this.handleChange}
            required
          />

          <input
            type='password'
            name='password'
            placeholder='Password'
            value={this.state.password}
            onChange={this.handleChange}
            required
          />

          <button type='submit'>Login</button>
        </form>
        <Link to='/register'><button>Register</button></Link>
      </div>
    );
  }
}

