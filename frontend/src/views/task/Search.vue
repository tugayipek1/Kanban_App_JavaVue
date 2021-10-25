<template>
  <div class="container">
    <UpdateDeleteTask
      v-if="selectedTask && project"
      :taskCatch="selectedTask"
      @updated="taskUpdated"
      :project="project"
      modalId="updateDeleteTask1"
    />
    <div class="col-3 mt-5">
      <div>
        <b-input-group>
          <div class="input-group-prepend">
            <span
              class="input-group-text fa fa-search"
              id="basic-addon1"
              style="background: rgb(212 211 211); color: black;"
            ></span>
          </div>
          <b-form-input
            id="form1"
            class="form-control"
            type="search"
            style="height: 27px; cursor: text;"
            v-model="search"
            placeholder="Ara"
          >
          </b-form-input>
        </b-input-group>

        <b-card
          id="card"
          style="position:absolute; width: 318px; height:auto;  word-wrap: break-word;
  max-height: 500px;
  white-space: normal;
  overflow-y: auto;"
          v-if="!!search"
        >
          <div
            class="form-label"
            for="form1"
            :key="index"
            v-for="(task, index) in filteredList"
          >
            <b-card
              :title="`${task.project.projectCode}-${task.id}`"
              :sub-title="task.title"
              @click="selectTask(task)"
              v-b-modal.updateDeleteTask1
            >
            </b-card>
          </div>
        </b-card>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import axios from "axios";
import UpdateDeleteTask from "../../views/task/UpdateDeleteTask.vue";
export default {
  name: "Search",
  components: { UpdateDeleteTask },
  data() {
    return {
      tasks: [],
      search: null,
      selectedTask: null,
    };
  },
  watch: {
    search(value) {
      if (!!value) {
        $("#card").show();
      }
    },
  },
  computed: {
    ...mapGetters("Project", ["project"]),
    filteredList() {
      return this.tasks.filter((i) =>
        i.title.toLowerCase().includes(this.search.toLowerCase())
      );
    },
  },
  mounted() {
    const interval = setInterval(() => {
      if (this.$refs.updateDeleteTask1) {
        this.$refs["updateDeleteTask1"].show();
        clearInterval(interval);
      }
    }, 50);
    axios.get(`/task/${this.project.id}`).then((r) => {
      this.tasks = r.data;
    });
    const self = this;
    $(document).ready(function() {
      $(this).click(() => {
        $("#card").hide();
        $("#form1").val("");
      });
    });
  },
  methods: {
    selectTask(task) {
      this.selectedTask = task;
    },
    taskUpdated() {
      this.loadTasks();
    },
    loadTasks: function() {
      setInterval(() => {
        if (this.project) {
          axios.get(`/task/${this.project.id}`).then((res) => {
            this.tasks = res.data;
          });
        }
      }, 1500);
    },
  },
};
</script>

<style scope>
.card {
  overflow-y: auto;
}
</style>
