import { LoginCredentials, LoginTokenResponse } from "../model/login";
import { CreateUser } from "../model/user";
import api, { LOGIN, USER } from "./api";
import tokenService from "./token-service";

class AuthService {
    async login(user: LoginCredentials) {
        const response = await api.post(LOGIN, user)
        const token: LoginTokenResponse = response.data
        if (token.credentials.accessToken) {
            tokenService.setUser(token.credentials)
        }
        return token.credentials.userName
    }
    logout() {
        tokenService.removeUser()
    }
    async register(user: CreateUser) {
        const response = await api.post(USER, user)
        const userName = response.data
        return userName
    }
}

export default new AuthService();