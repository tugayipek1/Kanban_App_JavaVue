import axios from "axios";

export default {
  getAccountCounts(data) {
    return axios.get(`/dashboard/countAccount`);
  },
  getTaskCounts(data) {
    return axios.get(`/dashboard/countTask`);
  },
  getProjectCounts(data) {
    return axios.get(`/dashboard/countProject`);
  }
};
