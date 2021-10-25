import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "@/assets/css/main.css";
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import Vuelidate from 'vuelidate'
import VueToastr2 from "vue-toastr-2";
import "vue-toastr-2/dist/vue-toastr-2.min.css";

window.toastr = require("toastr");

Vue.use(VueToastr2);

library.add(fas)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(Vuelidate)

Vue.config.productionTip = false;

import { BACKEND_URL } from "./config/index";
import axios from "axios";
axios.defaults.baseURL = BACKEND_URL;
if (store.getters["Auth/isLoggedIn"]) {
  const jwtToken = store.getters["Auth/jwt"];
  axios.defaults.headers.common["Authorization"] = `Bearer ${jwtToken}`;
}

Vue.filter('date', function (value) {
  if (value) {
    const date = new Date(value);
    return `${date.getDay()}.${date.getMonth() + 1}.${date.getFullYear()} ${date.getHours()}:${date.getMinutes()}`
  }
});

Vue.filter('priorityFilter', function (value) {

  if (value == "HIGH") {
    return "YÜKSEK"

  }
  else if (value == "MEDIUM") {
    return "ORTA"

  }
  else {
    return "DÜŞÜK"

  }

}
);

new Vue({
  router,
  store,
  render: function (h) {
    return h(App);
  },
}).$mount("#app");
