import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

// element-ui
import ElementUI from "element-ui";
// import "element-ui/lib/theme-chalk/index.css";
import './assets/theme/index.css';
Vue.use(ElementUI);

// common CSS
import "@/assets/css/base.css"

// axios (old method)
// import axios from "axios"
// Vue.prototype.$http = axios;
// axios (new method)
import HttpServer from "./plugins/http.js";
Vue.use(HttpServer);

// moment
import moment from 'moment'
Vue.prototype.moment = moment;

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
