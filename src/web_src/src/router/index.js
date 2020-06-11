import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'main',
        component: () => import('../views/Main.vue'),
        redirect: {name: "reportStatistics"},
        children: [
            {
                // 复核查询页面
                path: '/main/recheckSearch',
                name: 'recheckQuery',
                component: () => import('../views/RecheckSearch.vue')
            },
            {
                // 通报统计页面
                path: '/main/reportStatistics',
                name: 'reportStatistics',
                component: () => import('../views/ReportStatistics.vue')
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
