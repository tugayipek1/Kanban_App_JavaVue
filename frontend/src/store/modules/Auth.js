import AuthClient from "../../client/Auth";

import router from "../../router/index";
import store from "../../store";

const state = () => ({
  account: null,
  jwt: null,
  credentialError: null,
});

const getters = {
  account: (state, getters, rootState) => {
    return state.account;
  },
  jwt: (state, getters) => {
    return state.jwt;
  },
  isLoggedIn: (state, getters) => {
    return !!state.jwt;
  },
  credentialError: (state) => {
    return state.credentialError;
  },
  hasCredentialError: (state, getters) => {
    return !!getters.credentialError;
  },
};

const actions = {
  async login({ commit, state }, credentials) {
    try {
      commit("setCredentialError", null);
      const response = await AuthClient.login(credentials);

      commit("initLogin", response.data);
      store.dispatch("Project/removeProject");
      store.dispatch("Project/removeMyProject");
      window.location.reload();
    } catch (error) {
      commit(
        "setCredentialError",
        "Kullanıcı adı veya parolanız hatalı olabilir"
      );
    }
  },

  logout({ state, commit }) {
    commit("destroyLogin");
    store.dispatch("Project/removeProject");
    store.dispatch("Project/removeMyProject");
    router.push("/login");
  },
};

const mutations = {
  initLogin(state, { token, account }) {
    state.jwt = token;
    state.account = account;
    state.credentialError = null;
  },

  destroyLogin(state) {
    state.jwt = null;
    state.account = null;
    state.credentialError = null;
  },

  setCredentialError(state, err) {

    state.credentialError = err;
  },

  setAccount(state, account) {
    state.account = account;
  }

};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
