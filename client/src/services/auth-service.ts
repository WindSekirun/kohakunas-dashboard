import axios from "axios";
import { LoginCredentials, LoginTokenResponse } from "../model/login";
import { CreateUser } from "../model/user";
import api from "./api";
import tokenService from "./token-service";
const API_URL = import.meta.env.API_URL

class AuthService {
    async login(user: LoginCredentials) {
        const response = await api.post(API_URL + "/api/authenticate", user)
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
        const response = await api.post(API_URL + "/api/user", user)
        const userName = response.data
        return userName
    }
}

export default new AuthService();