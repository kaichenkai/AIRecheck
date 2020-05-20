import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'main',
        component: () => import('../views/Main.vue'),
        redirect: {name: "recheckQuery"},
        children: [
            {
                // 复核查询页面
                path: '/main/recheckQuery',
                name: 'recheckQuery',
                component: () => import('../views/RecheckSearch.vue')
                // component: () => import('../views/Login.vue')
            },
            {
                // 通报统计页面
                path: '/main/reportStatistics',
                name: 'reportStatistics',
                component: () => import('../views/ReportStatistics.vue')
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
