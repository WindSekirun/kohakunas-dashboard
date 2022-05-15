import { AxiosRequestConfig, AxiosResponse } from "axios";
import { Store } from "vuex";
import { RefreshBody } from "../model/login";
import axiosInstance, { LOGIN, TOKEN } from "./api";
import tokenService from "./token-service";

const setup = (store: Store<unknown>) => {
    axiosInstance.interceptors.request.use(
        (config: AxiosRequestConfig) => {
            const token = tokenService.getLocalAccessToken();
            if (token) {
                if (!config.headers) {
                    config.headers = {};
                }
                config.headers["Authorization"] = 'Bearer ' + token;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        (res: AxiosResponse) => {
            return res;
        },
        async (err) => {
            const originalConfig = err.config;

            if (originalConfig.url !== LOGIN && err.response) {
                // Access Token was expired
                if (err.response.status === 401 && !originalConfig._retry) {
                    originalConfig._retry = true;

                    try {
                        const request = new RefreshBody()
                        request.userName = tokenService.getUserName();
                        request.refreshToken = tokenService.getLocalRefreshToken();

                        const response = await axiosInstance.post(TOKEN, request);
                        const { accessToken } = response.data;
                        store.dispatch('auth/refreshToken', accessToken);
                        tokenService.updateLocalAccessToken(accessToken);
                        return axiosInstance(originalConfig);
                    } catch (_error) {
                        return Promise.reject(_error);
                    }
                }
            }

            return Promise.reject(err);
        }
    );
};

export default setup;