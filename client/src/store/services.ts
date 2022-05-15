import { ServiceCategory } from "../model/services";
import servicesService from "../services/services-service";
import { CommitFunction } from "../utils/commit-function";

export interface ServiceState {
    services: ServiceCategory[];
}

const initialState: ServiceState = {
    services: [],
}

export const service = {
    namespaced: true,
    state: initialState,
    actions: {
        getServiceList({ commit }: CommitFunction) {
            return servicesService.getServiceList().then(
                list => {
                    commit('getServicesSuccess', list)
                    return Promise.resolve(list)
                },
                error => {
                    return Promise.reject(error.response.data)
                }
            );
        },
    },
    mutations: {
        getServicesSuccess(state: ServiceState, list: ServiceCategory[]) {
            state.services = list;
        }
    }
}