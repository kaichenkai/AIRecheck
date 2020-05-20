import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
// common.less
import './common/common.less';
// fonts
import './assets/fonts/iconfont.css';

// element-ui
import ElementUI from "element-ui";
// import "element-ui/lib/theme-chalk/index.css";
import './assets/theme/index.css';
Vue.use(ElementUI);
// element-ui custom
import './common/elementReset.less';

// common CSS
import "@/assets/css/base.css"

// axios (old method)
// import axios from "axios"
// Vue.prototype.$http = axios;
// axios (new method)
// import HttpServer from "./plugins/http.js";
// Vue.use(HttpServer);

// moment
import moment from 'moment'
Vue.prototype.moment = moment;

// 自定义 tools ,_services, requestCtrl
import tools from './common/tools';
import services from './common/http_request/service.js';
// import requestCtrl from './common/http_request/requestCtrl';
Vue.prototype.tools = tools;
Vue.prototype._services = services;
// Vue.prototype.requestCtrl = requestCtrl;

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
