import { CredentialsResponse } from "../model/login";

class TokenService {
    hasToken() {
        const user: CredentialsResponse = JSON.parse(localStorage.getItem("user") || "")
        return user
    }

    getUserName() {
        const user: CredentialsResponse = JSON.parse(localStorage.getItem("user") || "");
        return user?.userName;
    }

    getLocalRefreshToken() {
        const user: CredentialsResponse = JSON.parse(localStorage.getItem("user") || "");
        return user?.refreshToken;
    }

    getLocalAccessToken() {
        const user: CredentialsResponse = JSON.parse(localStorage.getItem("user") || "");
        return user?.accessToken;
    }

    updateLocalAccessToken(token: CredentialsResponse) {
        let user = JSON.parse(localStorage.getItem("user") || "");
        user.accessToken = token;
        localStorage.setItem("user", JSON.stringify(user));
    }

    getUser() {
        return JSON.parse(localStorage.getItem("user") || "");
    }

    setUser(user: CredentialsResponse) {
        console.log(JSON.stringify(user));
        localStorage.setItem("user", JSON.stringify(user));
    }

    removeUser() {
        localStorage.removeItem("user");
    }
}

export default new TokenService();