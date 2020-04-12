/*
    functions:
    - isAuthenticated(): boolean
    - authenticate(email, password): Auth<Object>{ user<Object>, jwt: String }
    - register(email, password, confirmPassword, name): Auth<Object>{ user<Object>, jwt: String }
    - logout():
*/

const AuthService = {
    isAuthenticated: () => localStorage.getItem('token') != null,
    logout: () => { localStorage.removeItem('token'); },
}

export default AuthService;
