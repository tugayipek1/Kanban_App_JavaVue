import Vue from "vue";
import Vuex from "vuex";
import VuexPersistence from "vuex-persist";

import Auth from "./modules/Auth";
import Project from "./modules/Project";

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

Vue.use(Vuex);

export default new Vuex.Store({
  modules: { Auth, Project},
  plugins: [vuexLocal.plugin],
});
