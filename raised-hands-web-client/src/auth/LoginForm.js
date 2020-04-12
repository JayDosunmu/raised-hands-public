
import React, { Component } from "react";
import { Redirect } from "react-router";
import axios from "axios";

export default class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      loginErrors: "",
      redirectToReferrer: false
    };

    const { referrer } = this.props.location && this.props.location.state
                            ? this.props.location.state
                            : { referrer: '/sessions' };
    this.state.referrer = referrer;

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value
    });
  }

  handleSubmit(event) {
    const { email, password } = this.state;

    axios
      .post(
        "/api/auth/login",
        {
          email: email,
          password: password
        }
      )
      .then(response => {
        if (response.data.user) {
          localStorage.setItem("token", response.data.jwt);
        }
        this.setState({
          redirectToReferrer: true
        });
      })
      .catch(error => {
        console.log("login error", error);
      });
    event.preventDefault();
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
            type="email"
            name="email"
            placeholder="Email"
            value={this.state.email}
            onChange={this.handleChange}
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={this.state.password}
            onChange={this.handleChange}
            required
          />

          <button type="submit">Login</button>
        </form>
      </div>
    );
  }
}

