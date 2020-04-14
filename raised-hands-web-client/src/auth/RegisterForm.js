
import React, { Component } from 'react';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';

import { AuthService } from '.';

export default class RegisterForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      confirmPassword: '',
      name: '',
      loginErrors: '',
      registrationComplete: false
    };
  }

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    });
  }

  handleSubmit = async (event) => {
    event.preventDefault();
    const {
        email,
        password,
        confirmPassword,
        name,
    } = this.state;
    this.register(email, password, confirmPassword, name);
  }

  register = async (email, password, confirmPassword, name) => {
    const { user, jwt } = await AuthService.register(
        email,
        password,
        confirmPassword,
        name
    )

    AuthService.setAppUser(user, jwt);
    this.setState({
        registrationComplete: true,
    })
  }

  render() {
    if (this.state.registrationComplete) {
      return (
        <Redirect to='/sessions' />
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

          <input
            type='password'
            name='confirmPassword'
            placeholder='Re-enter password'
            value={this.state.confirmPassword}
            onChange={this.handleChange}
            required
          />

          <input
            type='text'
            name='name'
            placeholder='your name'
            value={this.state.name}
            onChange={this.handleChange}
            required
          />

          <button type='submit'>Register</button>
        </form>
        <Link to='/login'><button>login</button></Link>
      </div>
    );
  }
}

