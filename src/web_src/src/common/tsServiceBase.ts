import axios from 'axios';
import router from '../router';
import { message } from '@/common/tools'
import { AxiosRequestConfig } from 'axios';
const axiosConfig: AxiosRequestConfig = {
    timeout: 15000, // 超时设置s
    withCredentials: true // 允许携带cookie
    // baseURL: '/',
};

//创建axios实例
const service = axios.create(axiosConfig);


// 请求拦截器
service.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);
// 响应拦截器
service.interceptors.response.use(
    (response) => {
        if (response.status !== 200) {
            return {};
        }
        let { data } = response;

        if (data && (parseInt(data.errorCode, 10) === 403)) {
            window.sessionStorage.removeItem('access-token');
            router.replace({
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
    },
    (error) => {
        message('网络错误,请刷新重试!');
        return Promise.reject(error);
    }
);
/**
 * 封装axios的请求
 * @param url {String} 请求url
 * @param queryParams {Object} 请求参数，参数为对象
 */
export function request(url: string, method: string, queryParams: any, ) {
    if (!url) {
        message('url为空');
        return;
    }
    queryParams = queryParams || {};
    let axiosParams: AxiosRequestConfig = {};

    axiosParams.url = url;
    // @ts-ignore
    axiosParams.method = method || 'get';
    axiosParams.responseType = axiosParams.responseType || 'json';
    // 根据请求类型，将params设置到对应的属性中
    // @ts-ignore
    if (axiosParams.method.toLowerCase() === 'post') {
        axiosParams.data = queryParams;
    } else {
        axiosParams.params = queryParams;
    }
    return service(axiosParams).catch(errorHandler);
}
function errorHandler(error: any) {
    if (process.env.NODE_ENV !== 'production') {
        console.info('%c [axios error]:', 'color: yellow', error);
    }
}
