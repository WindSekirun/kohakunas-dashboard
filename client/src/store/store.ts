import { createStore } from "vuex";
import { auth } from "./auth";
import { service } from "./services";

const store = createStore({
    modules: {
        auth,
        service
    }
})

export default store;