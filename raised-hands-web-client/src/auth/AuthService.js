import { http } from '../util';
/*
    functions:
    - isAuthenticated(): boolean
    - authenticate(email, password): Auth<Object>{ user<Object>, jwt: String }
    - register(email, password, confirmPassword, name): Auth<Object>{ user<Object>, jwt: String }
    - logout():
*/

async function authenticate(email, password) {
    const loginResponse = await http
        .post(
            "/auth/login",
            {
                email,
                password,
            }
        )
    const { user, jwt } = loginResponse.data;
    setAppUser(user, jwt);
    return loginResponse.data;
}

function isAuthenticated() {
    return localStorage.getItem('token') != null;
}

function logout() {
    localStorage.removeItem('token');
}

async function register(email, password, confirmPassword, name) {
    const registerResponse = await http
        .post(
            "/auth/register",
            {
                email,
                password,
                confirmPassword,
                name,
            }
        )
    return registerResponse.data;
}

function setAppUser(user, jwt) {
    localStorage.setItem('token', jwt);
    localStorage.setItem('user', JSON.stringify(user));
}

export default {
    authenticate,
    isAuthenticated,
    logout,
    register,
    setAppUser,
};
