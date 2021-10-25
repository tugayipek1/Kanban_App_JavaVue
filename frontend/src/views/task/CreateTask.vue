<template>
  <div>
    <div class="container">
      <Search project="project"></Search>
      <b-button
        v-if="project"
        class="create-task pull-right createbuttonstyle btn-sm"
        v-b-modal.modal-1
        variant="light"
      >
        Task Oluştur
        <font-awesome-icon icon="edit" />
      </b-button>
      <div class="clearfix"></div>
    </div>
    <b-modal id="modal-1" hide-footer title="Task Oluştur">
      <div class="font-weight">
        <form @submit.prevent="createTask">
          <div>
            <b-form-group
              label="Task Başlığı"
              label-for="title"
              invalid-feedback="Boş Olamaz!"
            >
              <b-form-input
                class="mt-2 mb-2"
                id="title"
                v-model="taskData.title"
                :type="'text'"
                required
              ></b-form-input>
            </b-form-group>
          </div>

          <div>
            <b-form-group label="Açıklama" label-for="description">
              <b-form-textarea
                class="mt-2 mb-2"
                id="description"
                v-model="taskData.description"
                placeholder="Açıklama yazın..."
                rows="3"
                max-rows="6"
              ></b-form-textarea>
            </b-form-group>
          </div>

          <div>
            <b-form-group
              label="Öncelik"
              label-for="priority"
              invalid-feedback="Boş Olamaz!"
            >
              <b-form-select
                style="width: 100%; padding: 5px"
                class="mt-2 mb-2"
                id="priority"
                v-model="taskData.priority"
                :options="prt_options"
                :type="'text'"
                required
              >
              </b-form-select>
            </b-form-group>
          </div>

          <div>
            <b-form-group
              label="Tip"
              label-for="issueType"
              invalid-feedback="Boş Olamaz!"
            >
              <b-form-select
                style="width: 100%; padding: 5px"
                class="mt-2 mb-2"
                id="issueType"
                v-model="taskData.issueType"
                :options="issueType_options"
                :type="'text'"
                required
              >
              </b-form-select>
            </b-form-group>
          </div>

          <div>
            <b-form-group
              label="Durum"
              label-for="status"
              invalid-feedback="Boş Olamaz!"
            >
              <b-form-select
                style="width: 100%; padding: 5px"
                class="mt-2 mb-2"
                id="status"
                v-model="taskData.status"
                :options="status_options"
                :type="'text'"
              >
              </b-form-select>
            </b-form-group>
          </div>
          <div>
            <b-form-group
              label="Atanan"
              label-for="assignee"
              invalid-feedback="Boş Olamaz!"
            >
              <b-form-select
                style="width: 100%; padding: 5px"
                class="mt-2 mb-2"
                id="assignee"
                v-model="taskData.assignee"
                :options="members"
                :type="'text'"
              >
              </b-form-select>
            </b-form-group>
          </div>

          <div>
            <b-form-group
              label="Teslim Tarihi"
              label-for="dueDate"
              invalid-feedback="Boş Olamaz!"
            >
              <b-form-datepicker
                class="mt-2 mb-2"
                id="dueDate"
                v-model="taskData.dueDate"
                :min="min"
                required
              ></b-form-datepicker>
            </b-form-group>
          </div>

          <br />
          <b-button
            class="createbuttonstyle btn-sm"
            type="submit"
            variant="light"
          >
            <font-awesome-icon icon="save" /> Kaydet</b-button
          >
        </form>
      </div>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
import Search from "../../views/task/Search.vue";
import { mapGetters } from "vuex";
export default {
  components: { Search },
  data() {
    return {
      min: new Date(),
      selected: null,
      members: [],
      taskData: {},
      priority: null,
      issueType: null,
      prt_options: [
        { value: "HIGH", text: "YÜKSEK" },
        { value: "MEDIUM", text: "ORTA" },
        { value: "LOW", text: "DÜŞÜK" },
      ],
      status: null,
      status_options: [
        { value: "BACKLOG", text: "Backlog" },
        { value: "TODO", text: "Todo" },
      ],
      issueType: null,
      issueType_options: [
        { value: "BUG", text: "Bug" },
        { value: "TASK", text: "Task" },
      ],
    };
  },
  props: ["project"],

  mounted() {
    this.getProjectMembers();
    this.resetTask();
  },
  computed: {
    ...mapGetters("Auth", ["account"]),
  },
  methods: {
    createTask() {
      if (this.taskData.title) {
        this.taskData.lastUpdaterPerson = this.account;
        axios.post("/task", this.taskData).then((response) => {
          this.$toastr.success("Task oluşturuldu", "Başarılı!");
          this.$emit("created", response.data);
          this.$bvModal.hide("modal-1");
          this.resetTask();
        });
      } else {
        this.$toastr.error("Başlık boş bırakılamaz.", "Hata!");
      }
    },
    resetTask() {
      this.taskData = {
        project: this.project,
        title: null,
        status: "TODO",
        description: null,
        priority: null,
        assignee: null,
        createdDate: new Date(),
        dueDate: null,
        lastUpdaterPerson: null,
      };
    },
    getProjectMembers() {
      axios
        .get(`/projects/${this.project.id}`)
        .then((res) => {
          this.members = res.data.members.map((e) => {
            return { value: e, text: e.name };
          });
          this.members.push({ text: "Atanmamış", value: null });
        })
        .catch((err) => {
          this.$toastr.error(err.message, "Başarısız!");
        });
    },
  },
};
</script>

<style></style>
