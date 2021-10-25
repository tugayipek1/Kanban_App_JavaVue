<template>
  <div class="vue-tempalte">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <h5><span class="font-weight">Şifre Sıfırlama Talebi</span></h5>

            <form @submit.prevent="submit">
              <div class="form-group">
                <div class="font-weight mt-2">E-mail Adresi:</div>
                <input
                  type="email"
                  class="form-control"
                  v-model="email"
                  :class="{
                    'is-invalid': submitted && $v.email.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.email.required"
                  class="invalid-feedback"
                >
                  Lütfen emailinizi giriniz.
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.email.email"
                >
                  Geçerli bir email adresi giriniz
                </div>
              </div>

              <button
                type="submit"
                class="btn createbuttonstyle pull-right mt-3 btn-sm"
              >
                Gönder
              </button>
            </form>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import axios from "axios";
import { required, email } from "vuelidate/lib/validators";

export default {
  submitted: false,

  name: "ForgotPassword",
  data() {
    return {
      submitted: false,
      email: null,
    };
  },
  validations: {
    email: { required, email },
  },

  methods: {
    submit() {
      this.submitted = true;

      this.$v.$touch();

      if (this.$v.$invalid) {
        return;
      }
      axios
        .get(`/auth/reset-password/${this.email}`)
        .then((r) => {
          this.projects = r.data;
          this.$toastr.success(r.data.message, "Başarılı!");
          setTimeout(() => {
            this.$router.push("/");
          }, 1000);
        })
        .catch((e) => {
          this.$toastr.error(e.response.data.message, "Başarısız!");
        });
    },
  },
};
</script>
