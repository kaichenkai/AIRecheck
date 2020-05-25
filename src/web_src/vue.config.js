// const target = 'https://10.10.4.36.5000';
const target = "http://10.10.19.250:5000";
module.exports = {
  publicPath: "/", // nginx部署，nginx静态资源处理的相当于根目录部署
  // filenameHashing: false,
  devServer: {
    host: "0.0.0.0",
    hotOnly: false,
    https: false,
    // disableHostCheck: true,
    port: 8080,
    proxy: {
      "/api": {
        target, // 接口域名
        changeOrigin: true // 是否跨域
      }
    }
    // proxy: {
    //     '/business': {
    //         target, // 接口域名
    //         changeOrigin: true // 是否跨域
    //     },
    //     '/common': {
    //         target,
    //         changeOrigin: true
    //     },
    //     '/monitor': {
    //         target,
    //         changeOrigin: true
    //     },
    //     '/client': {
    //         target,
    //         changeOrigin: true
    //     },
    //     '/system': {
    //         target,
    //         changeOrigin: true
    //     },
    //     '/test': {
    //         target,
    //         changeOrigin: true
    //     },
    //     '/config': {
    //         target,
    //         changeOrigin: true
    //     },
    //     '/map': {
    //         target,
    //         changeOrigin: true
    //     }
    // },

  }
};

