import { CredentialsResponse, LoginCredentials } from "../model/login";
import { CreateUser } from "../model/user";
import authService from "../services/auth-service";
import { CommitFunction } from "../utils/commit-function";

const user: CredentialsResponse = JSON.parse(localStorage.getItem('user') || "{}");

export interface AuthState {
    status: Status
    user: CredentialsResponse | null
}

export interface Status {
    loggedIn: boolean
}

const initialState: AuthState = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };

export const auth = {
    namespaced: true,
    state: initialState,
    actions: {
        login({ commit }: CommitFunction, user: LoginCredentials) {
            console.log(user)
            return authService.login(user).then(
                user => {
                    commit('loginSuccess', user);
                    return Promise.resolve(user);
                },
                error => {
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        logout({ commit }: CommitFunction) {
            authService.logout();
            commit('logout');
        },
        register({ commit }: CommitFunction, user: CreateUser) {
            return authService.register(user).then(
                response => {
                    commit('registerSuccess');
                    return Promise.resolve(response.data);
                },
                error => {
                    console.log(error)
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        },
        refreshToken({ commit }: CommitFunction, accessToken: String) {
            commit('refreshToken', accessToken);
        }
    },
    mutations: {
        loginSuccess(state: AuthState, user: CredentialsResponse) {
            state.status.loggedIn = true;
            state.user = user;
        },
        loginFailure(state: AuthState) {
            state.status.loggedIn = false;
            state.user = null;
        },
        logout(state: AuthState) {
            state.status.loggedIn = false;
            state.user = null;
        },
        registerSuccess(state: AuthState) {
            state.status.loggedIn = false;
        },
        registerFailure(state: AuthState) {
            state.status.loggedIn = false;
        },
        refreshToken(state: AuthState, accessToken: string) {
            state.status.loggedIn = true;
            if (state.user) {
                state.user.accessToken = accessToken
            }
        }
    }
}