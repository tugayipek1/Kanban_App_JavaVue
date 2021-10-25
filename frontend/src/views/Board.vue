<template>
  <div>
    <div v-if="project">
      <div class="project-members">
        <ProjectMembers></ProjectMembers>
        <create-task :project="project" @created="taskCreated"></create-task>
      </div>
    </div>
    <div class="project-board">
      <Kanban :project="project" ref="kanban-component" />
    </div>
  </div>
</template>

<script>
import Kanban from "../components/board/KanbanBoard.vue";
import ProjectMembers from "../components/ProjectMembers.vue";
import CreateTask from "./task/CreateTask.vue";
import { mapGetters } from "vuex";
import axios from "axios";

export default {
  name: "Board",
  components: { Kanban, ProjectMembers, CreateTask },
  data() {
    return {};
  },
  created() {
    if (this.checkProjectSelected()) {
      axios
        .get(`/projects/${this.project.id}`)
        .then((r) => this.$store.commit("Project/setProject", r.data));
    }
  },
  computed: {
    ...mapGetters("Project", ["project", "isProjectSelected"]),
  },
  methods: {
    taskCreated() {
      this.$refs["kanban-component"].loadTasks();
    },
    checkProjectSelected() {
      if (!this.isProjectSelected) {
        return false;
      } else {
        return true;
      }
    },
  },
};
</script>
