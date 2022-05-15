import api, { SERVICE_LIST } from "./api";

class ServicesService {
    async getServiceList() {
        const response = await api.get(SERVICE_LIST)
        return response.data;
    }
}

export default new ServicesService();