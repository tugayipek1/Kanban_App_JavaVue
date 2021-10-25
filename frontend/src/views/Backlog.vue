<template>
  <div class="row mt-5">
    <div class="col-1"></div>
    <div class="col-10">
      <update-delete-task
        v-if="selectedTask && project"
        :taskCatch="selectedTask"
        @updated="taskUpdated"
        :project="project"
        modalId="updateDeleteTask"
      />

      <table
        class="table table-striped table-bordered"
        style="background: white;"
      >
        <thead>
          <tr class="table-active" style=" font-size: 1em;">
            <th scope="col">
              Task Kodu
            </th>
            <th scope="col">
              Task Başlığı
            </th>
            <th scope="col">Açıklama</th>
            <th scope="col">Öncelik</th>
            <th scope="col">Atanan</th>
            <th scope="col">Teslim Tarihi</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="task in tasks"
            :key="task.id"
            class="font-weight"
            style=" font-size: 0.9em;"
          >
            <td>{{ task.project.projectCode + "-" + task.id }}</td>
            <td>{{ task.title }}</td>
            <td v-if="task.description">
              {{ task.description }}
            </td>
            <td v-else>-</td>
            <td>
              {{ task.priority | priorityFilter }}
            </td>
            <td v-if="task.assignee">
              {{ task.assignee.name }}
            </td>
            <td style="margin-center; text-align: center " v-else>-</td>
            <td v-if="task.dueDate">
              {{ task.dueDate }}
            </td>
            <td v-else>-</td>
            <td
              style="padding: 0; -webkit-text-emphasis-style: sesame; text-align: center;"
            >
              <button
                class="btn-sm"
                style="border:none; background: transparent;"
                v-b-modal.updateDeleteTask
                @click="selectTask(task)"
              >
                <font-awesome-icon class="updateiconstyle" icon="edit" />
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import UpdateDeleteTask from "../views/task/UpdateDeleteTask.vue";
import { mapGetters } from "vuex";
import axios from "axios";
export default {
  name: "Backlog",
  components: {
    UpdateDeleteTask,
  },
  data() {
    return {
      tasks: [],
      selectedTask: null,
    };
  },
  computed: {
    ...mapGetters("Project", ["project"]),
  },
  mounted() {
    axios.get(`/task/${this.project.id}/status/backlog`).then((r) => {
      this.tasks = r.data;
      if (r.data.length < 1) {
        this.$toastr.info("Backlogta task bulunmamaktadır.");
      }
    });
  },
  methods: {
    selectTask(task) {
      this.selectedTask = { ...task };
    },
    taskUpdated() {
      this.loadTasks();
    },
    loadTasks: function() {
      setInterval(() => {
        if (this.project) {
          axios.get(`/task/${this.project.id}/status/backlog`).then((res) => {
            this.tasks = res.data;
          });
        }
      }, 1500);
    },
  },
};
</script>

<style></style>
