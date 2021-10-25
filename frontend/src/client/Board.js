import axios from "axios";
export default {

    update(data){

        return axios.put("/task",data)
    },

    getTasks(id){


        return axios.get(`/task/${id}`)
    }

  };