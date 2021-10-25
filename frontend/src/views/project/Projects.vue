<template>
  <div class="container mt-4">
    <b-row>
      <b-col>
        <b-col style="float: left">
          <router-link
            to="/addProject"
            tag="button"
            class="btn btn-light createbuttonstyle btn-sm"
            id="addProject"
          >
            <span class="font-weight"
              >Proje Oluştur <font-awesome-icon icon="plus"
            /></span>
          </router-link>
        </b-col>
        <div class="justify-content-md-center">
          <div class="bg-light" style="margin-top: 5%">
            <div class="card-body">
              <div>
                <table class="table">
                  <thead>
                    <tr class="row">
                      <th class="col-sm-2">SAHİPLİK</th>
                      <th class="col-sm-3">PROJE ADI</th>
                      <th class="col-sm-5">ÜYELER</th>
                      <th class="col-sm-2">AKSİYONLAR</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      class="row"
                      v-for="(project, index) in myProjects"
                      v-bind:key="project.id"
                    >
                      <td class="col-sm-2">
                        <span
                          class="badge"
                          style="text:bold; color:black; background:#f3d9aa"
                          v-if="project.ownerId != account.id"
                          >Üye</span
                        >
                        <span
                          class="badge"
                          v-if="project.ownerId == account.id"
                          style="text:bold; color:black; background:#d4d3d3"
                          >Yönetici</span
                        >
                      </td>
                      <td class="font-weight col-sm-3">
                        {{ project.projectName }}
                      </td>
                      <td class="col-sm-5">
                        <div
                          class="pull-left"
                          v-for="member in project.members"
                          v-bind:key="member.id"
                        >
                          <avatar
                            data-toggle="tooltip"
                            data-placement="top"
                            :title="member.name + ' - ' + member.nickName"
                            :username="member.name"
                            :size="30"
                          ></avatar>
                        </div>
                      </td>
                      <td class="col-sm-2">
                        <b-dropdown
                          id="dropdown-grouped"
                          text=""
                          variant="lite"
                          style="margin-left: 40px"
                        >
                          <template #button-content>
                            <span
                              class="fa fa-tasks fa-lg selecticonstyle"
                            ></span>
                          </template>

                          <b-dropdown-group id="dropdown-group-1">
                            <div
                              style="font-size: 15px; color: black; background:#d4d3d3"
                              class="dropdown-header text-center font-weight"
                            >
                              Aksiyonlar
                            </div>
                            <b-dropdown-item-button>
                              <button
                                @click="selectProject(project)"
                                type="button"
                                class="btn btn-sm"
                              >
                                <font-awesome-icon
                                  class="selecticonstyle font-weight"
                                  icon="mouse-pointer"
                                />
                                <span class="font-weight ms-1">Proje Seç</span>
                              </button>
                            </b-dropdown-item-button>
                            <b-dropdown-item-button>
                              <router-link
                                :to="'/updateProject/' + project.id"
                                v-if="project.ownerId == account.id"
                              >
                                <button
                                  type="button"
                                  class="btn btn-sm"
                                  style="color: black"
                                >
                                  <font-awesome-icon
                                    icon="edit"
                                    class="updateiconstyle"
                                  />
                                  <span class="font-weight ms-1"
                                    >Proje Güncelle</span
                                  >
                                </button>
                              </router-link>
                            </b-dropdown-item-button>

                            <b-dropdown-item-button>
                              <button
                                v-on:click="onDeleteProject(project.id, index)"
                                type="button"
                                class="btn btn-sm"
                                style="color: black"
                              >
                                <font-awesome-icon
                                  class="deleteiconstyle"
                                  icon="trash"
                                />
                                <span class="font-weight ms-1">Proje Sil</span>
                              </button>
                            </b-dropdown-item-button>
                          </b-dropdown-group>
                          <b-dropdown-group id="dropdown-group-2">
                            <div
                              style="font-size: 15px; color: black; background:#d4d3d3"
                              class="dropdown-header text-center font-weight"
                            >
                              Üye Sil
                            </div>

                            <b-dropdown-item-button>
                              <b-dropdown-item
                                v-for="member in project.members"
                                :key="member.id"
                                @click="onDeleteMember(project.id, member.id)"
                              >
                                <div class="row">
                                  <div class="col-4" style="margin-left: -20px">
                                    <avatar
                                      :username="member.name"
                                      :size="30"
                                    ></avatar>
                                  </div>
                                  <div class="col-8 mt-1 font-weight">
                                    {{ member.name }}
                                    <div
                                      class="fa fa-trash deleteiconstyle"
                                    ></div>
                                  </div>
                                </div>
                              </b-dropdown-item>
                            </b-dropdown-item-button>
                          </b-dropdown-group>
                        </b-dropdown>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Avatar from "vue-avatar";
import { mapGetters } from "vuex";
import axios from "axios";
export default {
  components: { Avatar },
  data() {
    return {
      editId: "",
      ownerId: null,
      projects: [],
    };
  },
  computed: {
    ...mapGetters("Auth", ["account", "jwt"]),
    ...mapGetters("Project", ["project", "myProjects"]),
  },
  mounted() {
    this.ownerId = this.account.id;
    this.getprojects();
  },
  methods: {
    getprojects() {
      this.$store.dispatch("Project/removeMyProject");
      this.$store.dispatch("Project/getMyProjects", this.account.id);
    },
    onDeleteProject(id, index) {
      if (!confirm("Silinsin mi?")) {
        return;
      }
      axios
        .delete(`/projects/${id}`)
        .then((response) => {
          this.$toastr.info("Proje silindi.", "Başarılı!");
          this.myProjects.splice(index, 1);
          if (id == this.project.id) {
            this.$store.commit("Project/setProject", null);
          }
          this.$store.dispatch("Project/getMyProjects", this.account.id);
        })
        .catch((error) => {});
    },
    async onDeleteMember(projectId, memberId) {
      if (!confirm("Silinsin mi?")) {
        return;
      }
      var payload = {
        projectId: projectId,
        memberId: memberId,
      };
      await this.$store.dispatch("Project/removeByMember", payload);
      this.getprojects();
      this.$toastr.info("Üye silindi.", "Başarılı!");
    },
    selectProject(project) {
      this.$store.commit("Project/setProject", project);
      this.getprojects();
      this.$router.push("/board");
    },
  },
};
</script>

<style scoped>
#addProject {
  float: right;
}
.badge {
  margin-right: 10px;
}
.prjname {
  font-weight: bold;
}
.dropdown {
  position: relative;
  display: inline-block;
  text-decoration: none;
}
</style>
