<template>
  <div class="vue-tempalte">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <h5><span class="font-weight">Giriş Yap</span></h5>

            <form @submit.prevent="login">
              <div class="form-group">
                <span class="font-weight">E-mail Adresi</span>
                <input
                  type="email"
                  class="form-control"
                  v-model="email"
                  @keyup.enter="login"
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
              </div>
              <div class="form-group">
                <span class="font-weight">Parola</span>
                <input
                  type="password"
                  class="form-control"
                  v-model="password"
                  @keyup.enter="login"
                  :class="{
                    'is-invalid': submitted && $v.email.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.password.required"
                  class="invalid-feedback"
                >
                  Lütfen parolanızı giriniz.
                </div>
              </div>

              <div v-if="hasCredentialError" class="mt-3 alert alert-danger">
                {{ credentialError }}
              </div>

              <p class="forgot-password text-right mt-3">
                <router-link class="font-weight" to="/ForgotPassword"
                  >Şifremi unuttum</router-link
                >
              </p>

              <div
                class="
                  form-group
                  d-grid
                  gap-2
                  d-md-flex
                  justify-content-md-start
                "
              >
                <b-button
                  class="createbuttonstyle font-weight btn-sm"
                  type="submit"
                  variant="light"
                >
                  Giriş Yap</b-button
                >
              </div>
            </form>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { required, email } from "vuelidate/lib/validators";

export default {
  data() {
    return {
      submitted: false,
      password: null,
      email: null,
    };
  },
  validations: {
    email: { required, email },
    password: { required },
  },
  computed: {
    ...mapGetters("Auth", [
      "isLoggedIn",
      "account",
      "hasCredentialError",
      "credentialError",
    ]),
  },
  mounted() {
    this.$store.commit("Auth/setCredentialError", null);

    if (this.isLoggedIn) {
      this.$router.push("/");
    }
  },
  methods: {
    login() {
      this.submitted = true;
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.$store.dispatch("Auth/login", {
          email: this.email,
          password: this.password,
        });
      }
    },
  },
};
</script>
