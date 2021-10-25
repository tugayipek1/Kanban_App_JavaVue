import axios from "axios";

const state = () => ({
  project: null,
  myProjects: [],
});

const getters = {
  project: (state, getters) => {
    return state.project;
  },

  isProjectSelected: (state, getters) => {
    return state.project != null;
  },

  myProjects: (state, getters) => {
    return state.myProjects;
  },
}

const actions = {
  async getProject({ commit, state }, data) {
    try {
      const response = await axios.get(`/projects/${data.projectId}`)
      commit("setProject", response.data);
    } catch (error) {
    }
  },
  removeProject({ state, commit }) {
    commit("destroyProject");
  },

  async getMyProjects({ state, commit }, ownerId) {
    try {
      const response = await axios.get(`/projects/my/${ownerId}`);
      commit("setMyProjects", response.data);
    } catch (error) { }
  },

  removeMyProject({ state, commit }) {
    commit("destroyMyProject");
  },

  removeByMember({ state, commit }, payload) {
    return axios.delete(`/projects/project/${payload.projectId}/member/${payload.memberId}`)
  },
}

const mutations = {
  setProject(state, data) {
    state.project = data;
    axios.defaults.headers.common["Project"] = data == null ? null : data.id;
  },

  destroyProject(state) {
    state.project = null;
    axios.defaults.headers.common["Project"] = null;
  },

  setMyProjects(state, data) {
    state.myProjects = data;
  },

  destroyMyProject(state) {
    state.myProjects = null;
  },
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};