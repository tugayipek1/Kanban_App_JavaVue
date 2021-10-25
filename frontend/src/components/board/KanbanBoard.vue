<template>
  <div class="container">
    <updateDeleteTask
      v-if="project && this.selectedTask"
      modalId="updateDeleteTask"
      :taskCatch="selectedTask"
      @updated="taskUpdated"
      :project="project"
    />
    <div class="row">
      <div
        v-for="column in columns"
        :key="column.title"
        class="p-3 col rounded"
        style="
          background-color: rgb(244, 245, 247);
          margin-right: 10px;
          min-height: 650px;
        "
      >
        <p class="columnTitle">
          {{ column.title }}
          <span
            class="badge"
            style="background:#d4d3d3; color:black; padding: .35em .3em; border-radius: 1rem; font-size: .75em;"
            v-if="column.tasks.length != 0"
          >
            {{ column.tasks.length }}</span
          >
        </p>
        <draggable
          :list="column.tasks"
          :animation="200"
          @change="onChange($event, column)"
          ghost-class="ghost-card"
          group="tasks"
          style="height: 100%"
        >
          <div
            v-for="task in column.tasks"
            :key="task.id"
            @click="selectTask(task)"
            v-b-modal.updateDeleteTask
          >
            <task-card :task="task" class="mt-3 cursor-move"></task-card>
          </div>
        </draggable>
      </div>
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import TaskCard from "./TaskCard.vue";
import Board from "../../client/Board.js";
import UpdateDeleteTask from "../../views/task/UpdateDeleteTask.vue";
import axios from "axios";

export default {
  name: "App",
  components: {
    TaskCard,
    draggable,
    UpdateDeleteTask,
  },
  props: ["project"],
  methods: {
    taskUpdated() {
      this.loadTasks();
    },
    loadTasks: function() {
      setInterval(() => {
        if (this.project) {
          Board.getTasks(this.project.id).then((res) => {
            this.columns.map((e) => {
              e.tasks = res.data.filter((task) => e.id == task.status);
            });
          });
        }
      }, 1500);
    },
    async onChange(e, column) {
      if (e.added) {
        const task = e.added.element;
        task.status = column.id;
        Board.update(task);
      }
    },
    selectTask(task) {
      this.selectedTask = { ...task };
    },
  },
  mounted() {
    this.loadTasks();
    Board.getTasks(this.project.id).then((res) => {
      this.columns.map((e) => {
        e.tasks = res.data.filter((task) => e.id == task.status);
      });
    });
  },
  data() {
    return {
      projectId: null,
      taskId: null,
      myProjects: [],
      selectedTask: null,
      columns: [
        {
          title: "To Do",
          id: "TODO",

          tasks: [],
        },
        {
          title: "In Progress",
          id: "IN_PROGRESS",

          tasks: [],
        },
        {
          title: "Review",
          id: "REVIEW",

          tasks: [],
        },
        {
          title: "Test",
          id: "TEST",

          tasks: [],
        },
        {
          title: "Done",
          id: "DONE",

          tasks: [],
        },
      ],
    };
  },

  created() {
    this.myProjects = this.$store.getters["Project/myProjects"];
    this.projectId = this.$route.query.project_id;
    this.taskId = this.$route.query.task_id;

    if (this.projectId && this.taskId) {
      axios
        .get(`/task/id/${this.taskId}`)
        .then((r) => (this.selectedTask = r.data))
        .catch(() => this.$router.push("/404"));
    }
    this.loadTasks();
  },
  mounted() {
    const interval = setInterval(() => {
      if (this.$refs.updateDeleteTask) {
        this.$refs["updateDeleteTask"].show();
        clearInterval(interval);
      }
    }, 50);
  },
  watch: {
    selectedTask(value) {
      if (this.projectId && this.taskId) {
        if (value.project.id == this.projectId) {
          var project = this.myProjects.find((p) => p.id == this.projectId);
          if (!project) {
            this.$router.push("/404");
          }
          this.$store.commit("Project/setProject", project);
        } else {
          this.$router.push("/404");
        }
      }
    },
  },
  methods: {
    taskUpdated() {
      this.loadTasks();
    },
    loadTasks() {
      setInterval(() => {
        if (this.project) {
          Board.getTasks(this.project.id).then((res) => {
            this.columns.map((e) => {
              e.tasks = res.data.filter((task) => e.id == task.status);
            });
          });
        }
      }, 1500);
    },
    async onChange(e, column) {
      if (e.added) {
        const task = e.added.element;
        task.status = column.id;
        Board.update(task);
      }
    },
    selectTask(task) {
      this.selectedTask = { ...task };
    },
  },
};
</script>

<style scoped>
.ghost-card {
  opacity: 0.5;
  background: #f7fafc;
  border: 1px solid #4299e1;
}
.info {
  text-align: center;
  font-size: 2rem;
  font-family: monospace;
  background-color: #f8f9fa;
}
.columnTitle {
  font-size: 1.1em;
  font-weight: 600;
}
</style>
