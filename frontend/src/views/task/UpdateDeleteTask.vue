<template>
  <!-- Update Task Modal -->

  <div>
    <b-modal
      size="xl"
      :id="modalId"
      hide-footer
      title="Task Güncelle"
      v-if="taskCatch"
      class="font-weight"
    >
      <span class="project-code">
        <font-awesome-icon
          v-if="this.taskData.issueType === 'TASK'"
          class="iconId"
          icon="thumbtack"
          style="color: green"
        />

        <font-awesome-icon
          v-else
          class="iconId"
          icon="bug"
          style="color: #ad1313"
        />
        <span class="card-title"
          >{{ project.projectCode }} - {{ this.taskCatch.id }}</span
        >
      </span>

      <div class="row font-weight">
        <div class="col-6">
          <form @submit.prevent="updateTask">
            <div class="row">
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
                    required
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
                    :min="min"
                    required="required"
                    v-model="taskData.dueDate"
                  ></b-form-datepicker>
                </b-form-group>
              </div>
              <div class="lastUpdaterPerson-top mt-2 mb-2">
                <span class="lastUpdaterPerson-style"
                  >Taskı son güncelleyen:</span
                >
                <span
                  class="lastUpdaterPerson-data-style"
                  v-if="this.taskData.lastUpdaterPerson"
                >
                  {{ this.taskData.lastUpdaterPerson.name }}
                </span>
              </div>
              <br />

              <div class="upd-dlt mt-2">
                <b-button
                  type="submit"
                  variant="light"
                  class="updatebuttonstyle btn-sm"
                  ><font-awesome-icon icon="edit" /> Güncelle</b-button
                >
                <b-button
                  class="dlt deletebuttonstyle btn-sm"
                  @click="deleteTask"
                  variant="light"
                  ><font-awesome-icon icon="trash" /> Sil</b-button
                >
              </div>
            </div>
          </form>
        </div>
        <div class="col-6">
          <form>
            <div class="row comment">
              <div>
                <b-form-group label="Yorum" label-for="description">
                  <b-form-textarea
                    class="mt-2 mb-2"
                    id="description"
                    v-model="commentText"
                    placeholder="Yorum yazın..."
                    rows="3"
                    max-rows="6"
                  ></b-form-textarea>
                </b-form-group>
                <b-button
                  class="mt-2 createbuttonstyle btn-sm"
                  variant="light"
                  @click="commentCreate"
                  ><font-awesome-icon icon="plus" /> Yorum ekle</b-button
                >
              </div>

              <div
                v-for="(comment, index) in taskData.comments"
                :key="comment.id"
              >
                <div class="row mt-3">
                  <div>
                    <b-card bg-variant="light">
                      <b-card-text>
                        <div class="row">
                          <div class="col-1 mt-1">
                            <avatar
                              :username="comment.commentOwner.name"
                              :size="30"
                              :title="
                                comment.commentOwner.name +
                                  ' - ' +
                                  comment.commentOwner.nickName
                              "
                            ></avatar>
                          </div>
                          <div class="col-7 mt-2">
                            <h5>{{ comment.commentOwner.name }}</h5>
                          </div>
                          <div class="col-4 mt-1">
                            {{ comment.at | date }}
                          </div>
                        </div>
                      </b-card-text>

                      <b-card-text>
                        <div
                          style="
                            max-witdh: 160px;
                            word-wrap: break-word;
                            white-space: normal;
                          "
                          v-html="mutateComments[index]"
                        ></div>

                        <div class="mt-1">
                          <div class="delete">
                            <b-button
                              size="sm"
                              class="fa fa-trash btn-sm"
                              variant="danger"
                              @click="commentDelete(comment)"
                            ></b-button>
                          </div>
                        </div>
                      </b-card-text>
                    </b-card>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
import Avatar from "vue-avatar";

export default {
  props: ["taskCatch", "project", "modalId"],
  data() {
    return {
      members: [],
      min: new Date(),
      commentText: null,
      taskData: {
        project: null,
        title: null,
        status: "TODO",
        description: null,
        priority: null,
        assignee: null,
        issueType: null,
        createdDate: new Date(),
        dueDate: null,
        lastUpdaterPerson: null,
      },
      priority: null,
      prt_options: [
        { value: "HIGH", text: "YÜKSEK" },
        { value: "MEDIUM", text: "ORTA" },
        { value: "LOW", text: "DÜŞÜK" },
      ],
      issueType: null,
      issueType_options: [
        { value: "BUG", text: "Bug" },
        { value: "TASK", text: "Task" },
      ],
      status: null,
      status_options: [
        { value: "BACKLOG", text: "Backlog" },
        { value: "TODO", text: "Todo" },
      ],
    };
  },
  mounted() {
    this.getProjectMembers();
    this.taskData.project = this.project;
    this.taskData = this.taskCatch;
  },
  watch: {
    taskCatch() {
      this.taskData = this.taskCatch;
    },
  },

  components: {
    Avatar,
  },
  computed: {
    ...mapGetters("Auth", ["account"]),
    mutateComments() {
      var expression = /(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})/gi;
      var regex = new RegExp(expression);
      return this.taskData.comments.map((c) =>
        c.comment.replace(
          regex,
          (x) =>
            '<a href="' +
            x +
            '" style="text-decoration:none;" target=_blank>' +
            x +
            "<a/>"
        )
      );
    },
  },
  methods: {
    show() {
      this.$bvModal.show("updateDeleteTask");
    },
    commentUpdate() {
      const comment = {
        commentOwner: {
          id: this.account.id,
        },
        comment: this.commentText,
        task: {
          id: this.taskCatch.id,
        },
      };
      axios.put(`/comment/`, comment).then((r) => {
        this.commentText = null;
        this.commentDataUpdate();
      });
    },
    commentDelete(comment) {
      if (!confirm("Silinsin mi?")) {
        return;
      }
      axios.delete(`/comment/${comment.id}`).then((r) => {
        this.commentDataUpdate();
      });
    },
    commentCreate() {
      const comment = {
        commentOwner: {
          id: this.account.id,
        },
        comment: this.commentText,
        task: {
          id: this.taskCatch.id,
        },
      };
      axios.post(`/comment/`, comment).then((r) => {
        this.commentText = null;
        this.commentDataUpdate();
      });
    },
    commentDataUpdate() {
      axios.get(`/task/id/${this.taskCatch.id}`).then((r) => {
        this.taskData = r.data;
      });
    },
    updateTask() {
      this.taskData.lastUpdaterPerson = this.account;
      axios.put("/task", this.taskData).then((response) => {
        this.$toastr.success("Task güncellendi.", "Başarılı!");
        this.$emit("updated", response.data);
        this.$bvModal.hide("updateDeleteTask");
      });
    },
    deleteTask() {
      if (!confirm("Silinsin mi?")) {
        return;
      }
      axios.delete(`/task/${this.taskCatch.id}`).then((response) => {
        this.$toastr.info("Task silindi.", "Başarılı!");
        this.$emit("updated", response.data);
        this.$bvModal.hide("updateDeleteTask");
      });
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

<style scope>
.upd-dlt {
  float: left;
}
.dlt {
  margin-left: 10px;
}
.comment {
  word-wrap: break-word;
  max-height: 500px;
  white-space: normal;
  overflow-y: auto;
}
.delete {
  max-width: 50px;
  float: right;
}
.card-title {
  margin-left: 5px;
  font-size: 16px;
  font-weight: 600;
}
.project-code {
  position: absolute;
  margin-top: -3.7rem;
  margin-left: 8.5rem;
}
html {
  font-size: 14px;
}
.lastUpdaterPerson-top {
  padding-top: 5px;
  outline: 1px solid rgba(28, 110, 164, 0.28);
  outline-offset: 0px;
  cursor: not-allowed;
  width: 39rem !important;
  margin-left: 10px;
  padding: 5px;
}
.lastUpdaterPerson-style {
  color: gray;
}
.lastUpdaterPerson-data-style {
  font-weight: 900;
}
</style>
