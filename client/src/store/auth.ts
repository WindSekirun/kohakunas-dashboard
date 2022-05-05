import { CredentialsResponse, LoginCredentials } from "../model/login";
import { CreateUser } from "../model/user";
import authService from "../services/auth-service";
import { Commit } from 'vuex'

const user: CredentialsResponse = JSON.parse(localStorage.getItem('user') || "");


export interface CommitFunction {
    commit: Commit;
}

export interface CommitStateFunction<T> extends CommitFunction {
    state: T;
}

export interface StoreState {
    status: Status
    user: CredentialsResponse | null
}

export interface Status {
    loggedIn: boolean
}


const initialState: StoreState = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };

export const auth = {
    namespaced: true,
    state: initialState,
    actions: {
        login({ commit }: CommitFunction, user: LoginCredentials) {
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
        loginSuccess(state: StoreState, user: CredentialsResponse) {
            state.status.loggedIn = true;
            state.user = user;
        },
        loginFailure(state: StoreState) {
            state.status.loggedIn = false;
            state.user = null;
        },
        logout(state: StoreState) {
            state.status.loggedIn = false;
            state.user = null;
        },
        registerSuccess(state: StoreState) {
            state.status.loggedIn = false;
        },
        registerFailure(state: StoreState) {
            state.status.loggedIn = false;
        },
        refreshToken(state: StoreState, accessToken: string) {
            state.status.loggedIn = true;
            if (state.user) {
                state.user.accessToken = accessToken
            }
        }
    }
}