<template>
  <div class="container">
    <div class="row justify-content-md-center">
      <template v-if="editId != null">
        <div class="card mt-5 w-75">
          <div class="card-header bg-white">
            <h3>Proje Güncelle</h3>
          </div>
          <div class="card-body">
            <form v-on:submit.prevent="updateProject">
              <div class="form-group">
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Proje Adı:</label
                >
                <input
                  type="text"
                  class="form-control"
                  :class="{
                    'is-invalid': submitted && $v.projectName.$error,
                  }"
                  v-model="projectName"
                />
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.projectName.required"
                >
                  Bu alanın doldurulması zorunludur
                </div>

                <div
                  class="invalid-feedback"
                  v-else-if="submitted && !$v.projectName.minLength"
                >
                  Proje adı en az
                  {{ $v.projectName.$params.minLength.min }} karakterden
                  oluşmalıdır.
                </div>
                <div
                  class="invalid-feedback"
                  v-else-if="submitted && !$v.projectName.maxLength"
                >
                  Proje adı en fazla
                  {{ $v.projectName.$params.maxLength.max }} karakterden
                  oluşmalıdır.
                </div>
                <br />
                <label
                  style="
                    font-weight: bold;
                    margin-bottom: 10px;
                    margin-top: 10px; ;
                  "
                  >Proje Kodu:</label
                >
                <input
                  type="text"
                  class="form-control"
                  :class="{
                    'is-invalid': submitted && $v.projectCode.$error,
                  }"
                  v-model="projectCode"
                />
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.projectCode.required"
                >
                  Bu alanın doldurulması zorunludur
                </div>

                <div
                  class="invalid-feedback"
                  v-else-if="submitted && !$v.projectCode.maxLength"
                >
                  Proje kodu en fazla
                  {{ $v.projectCode.$params.maxLength.max }} karakterden
                  oluşmalıdır .
                </div>
              </div>
              <br />
              <div
                class="
                  form-group
                  d-grid
                  gap-2
                  d-md-flex
                  justify-content-md-start
                "
              >
                <button type="submit" class="btn createbuttonstyle btn-sm">
                  <font-awesome-icon icon="save" />
                  Kaydet
                </button>
              </div>
              <br />
            </form>
          </div>
        </div>
      </template>

      <template v-else>
        <div class="card mt-3 w-75">
          <div class="card-header bg-white">
            <h5><span class="font-weight">Proje Oluştur</span></h5>
          </div>
          <div class="card-body">
            <form v-on:submit.prevent="addProject">
              <div class="form-group">
                <span class="font-weight">Proje Adı:</span>
                <input
                  type="text"
                  class="form-control"
                  :class="{
                    'is-invalid': submitted && $v.projectName.$error,
                  }"
                  v-model="projectName"
                />
                <div
                  v-if="submitted && !$v.projectName.required"
                  class="invalid-feedback"
                >
                  Bu alanın doldurulması zorunludur
                </div>

                <div
                  class="invalid-feedback"
                  v-else-if="submitted && !$v.projectName.minLength"
                >
                  Proje adı en az
                  {{ $v.projectName.$params.minLength.min }} karakterden
                  oluşmalıdır.
                </div>
                <div
                  class="invalid-feedback"
                  v-else-if="submitted && !$v.projectName.maxLength"
                >
                  Proje adı en fazla
                  {{ $v.projectName.$params.maxLength.max }} karakterden
                  oluşmalıdır.
                </div>
              </div>

              <br />
              <div class="form-group">
                <span class="font-weight">Proje Kodu:</span>
                <input
                  type="text"
                  class="form-control"
                  :class="{
                    'is-invalid': submitted && $v.projectCode.$error,
                  }"
                  v-model="projectCode"
                />
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.projectCode.required"
                >
                  Bu alanın doldurulması zorunludur
                </div>

                <div
                  class="invalid-feedback"
                  v-else-if="submitted && !$v.projectCode.maxLength"
                >
                  Proje kodu en az
                  {{ $v.projectCode.$params.maxLength.max }} karakterden
                  oluşmalıdır .
                </div>
              </div>

              <br />
              <div
                class="
                  form-group
                  d-grid
                  gap-2
                  d-md-flex
                  justify-content-md-start
                "
              >
                <button type="submit" class="btn createbuttonstyle btn-sm">
                  <font-awesome-icon icon="save" />
                  Kaydet
                </button>
              </div>
              <br />
            </form>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
import { required, minLength, maxLength } from "vuelidate/lib/validators";

export default {
  data() {
    return {
      editId: null,
      projectName: null,
      projectCode: null,
      ownerId: null,
      submitted: false,
      projectData: {},
    };
  },
  validations: {
    projectName: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(120),
    },
    projectCode: {
      required,
      maxLength: maxLength(4),
    },
  },
  computed: {
    ...mapGetters("Auth", ["account", "jwt"]),
    ...mapGetters("Project", ["project"]),
  },
  methods: {
    addProject() {
      this.submitted = true;
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.projectData.projectName = this.projectName;
        this.projectData.ownerId = this.ownerId;
        this.projectData.projectCode = this.projectCode;
        axios
          .post("/projects", this.projectData)
          .then((response) => {
            this.$toastr.success("Proje eklendi.", "Başarılı!");
            this.$store.dispatch("Project/getMyProjects", this.ownerId);
            this.$router.push("/projects");
          })
          .catch((error) => {
            this.$toastr.error(error.response.data.message, "Başarısız!");
          });
      } else {
        this.$toastr.warning(
          "Lütfen geçerli bir proje adı ve proje kodu giriniz.",
          "Başarısız!"
        );
      }
    },

    updateProject() {
      this.submitted = true;
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.projectData.projectName = this.projectName;
        this.projectData.projectCode = this.projectCode;
        axios
          .put("/projects", this.projectData)
          .then((response) => {
            this.$toastr.success("Proje güncellendi.", "Başarılı!");

            if (
              this.project != null &&
              this.projectData.id == this.project.id
            ) {
              this.$store.commit("Project/setProject", this.projectData);
            }

            this.$store.dispatch("Project/getMyProjects", this.ownerId);
            this.$router.push("/projects");
          })
          .catch((error) => {
            this.$toastr.error("Hata meydana geldi.", "Başarısız!");
          });
      } else {
        this.$toastr.warning(
          "Lütfen geçerli bir proje adı ve proje kodu giriniz.",
          "Başarısız!"
        );
      }
    },
  },
  mounted() {
    this.ownerId = this.account.id;
    this.editId = this.$route.params.id;
    if (this.editId != null) {
      axios
        .get("/projects/" + this.editId)
        .then((response) => {
          this.projectData = response.data;
          this.projectName = response.data.projectName;
          this.projectCode = response.data.projectCode;
        })
        .catch((error) => {
          this.$toastr.error();
          "Hata meydana geldi.", "Başarısız!";
        });
    } else {
    }
  },
};
</script>

<style scoped></style>
