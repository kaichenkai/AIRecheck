"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var axios_1 = require("axios");
var router_1 = require("../router");
var tools_1 = require("@/common/tools");
var axiosConfig = {
    timeout: 15000,
    withCredentials: true // 允许携带cookie
    // baseURL: '/',
};
//创建axios实例
var service = axios_1.default.create(axiosConfig);
// 请求拦截器
service.interceptors.request.use(function (config) {
    return config;
}, function (error) {
    return Promise.reject(error);
});
// 响应拦截器
service.interceptors.response.use(function (response) {
    if (response.status !== 200) {
        return {};
    }
    var data = response.data;
    if (data && (parseInt(data.errorCode, 10) === 403)) {
        window.sessionStorage.removeItem('access-token');
        router_1.default.replace({
            path: '/login'
        });
    }
    // if (data && parseInt(data.errorCode, 10) === 409) {
    //     window.sessionStorage.removeItem('access-token');
    //     const params: any = {
    //         change: true
    //     };
    //     router.push({ name: '个人首页', params });
    // }
    return data;
}, function (error) {
    tools_1.message('网络错误,请刷新重试!');
    return Promise.reject(error);
});
/**
 * 封装axios的请求
 * @param url {String} 请求url
 * @param queryParams {Object} 请求参数，参数为对象
 */
function request(url, method, queryParams) {
    if (!url) {
        tools_1.message('url为空');
        return;
    }
    queryParams = queryParams || {};
    var axiosParams = {};
    axiosParams.url = url;
    // @ts-ignore
    axiosParams.method = method || 'get';
    axiosParams.responseType = axiosParams.responseType || 'json';
    // 根据请求类型，将params设置到对应的属性中
    // @ts-ignore
    if (axiosParams.method.toLowerCase() === 'post') {
        axiosParams.data = queryParams;
    }
    else {
        axiosParams.params = queryParams;
    }
    return service(axiosParams).catch(errorHandler);
}
exports.request = request;
function errorHandler(error) {
    if (process.env.NODE_ENV !== 'production') {
        console.info('%c [axios error]:', 'color: yellow', error);
    }
}
