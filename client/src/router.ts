import { createWebHistory, createRouter, RouteRecordRaw } from "vue-router";
import Home from "./components/Home.vue";
import tokenService from "./services/token-service";

const routes: RouteRecordRaw[] = [
    {
        path: "/",
        name: "home",
        component: Home
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