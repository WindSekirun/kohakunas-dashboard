import { AxiosRequestConfig, AxiosResponse } from "axios";
import { Store } from "vuex";
import axiosInstance from "./api";
import tokenService from "./token-service";

const setup = (store: Store<unknown>) => {
    axiosInstance.interceptors.request.use(
        (config: AxiosRequestConfig) => {
            const token = tokenService.getLocalAccessToken();
            if (token) {
                if (!config.headers) return;
                config.headers["Authorization"] = 'Bearer ' + token;  // for Spring Boot back-end
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

            if (originalConfig.url !== "/api/authenticate" && err.response) {
                // Access Token was expired
                if (err.response.status === 401 && !originalConfig._retry) {
                    originalConfig._retry = true;

                    try {
                        const rs = await axiosInstance.post("/api/token", {
                            userName: tokenService.getUserName(),
                            refreshToken: tokenService.getLocalRefreshToken(),
                        });

                        const { accessToken } = rs.data;

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