import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'main',
        component: () => import('../views/Main.vue'),
        redirect: {name: "recheck"},
        children: [
            {
                // 复核页面
                path: '/main/recheck',
                name: 'recheck',
                component: () => import('../views/RecheckSearch.vue')
                // component: () => import('../views/Login.vue')
            }
        ]
    }
    // {
    //   path: "/login",
    //   name: "login",
    //   component: () => import("../views/Login.vue")
    // }
];

const router = new VueRouter({
    routes
});

export default router;
