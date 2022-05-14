import { createWebHistory, createRouter, RouteRecordRaw } from "vue-router";
import Home from "./components/Home.vue";
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";
import tokenService from "./services/token-service";

const routes: RouteRecordRaw[] = [
    {
        path: "/",
        name: "home",
        component: Home
    },
    {
        path: "/login",
        name: "login",
        component: Login
    },
    {
        path: "/register",
        name: "register",
        component: Register
    }
]


const router = createRouter({
    history: createWebHistory(),
    routes,
});


router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register'];
    const authRequired = !publicPages.includes(to.path);

    if (authRequired && !tokenService.hasToken()) {
        next('/login');
    } else {
        next();
    }
});


export default router;