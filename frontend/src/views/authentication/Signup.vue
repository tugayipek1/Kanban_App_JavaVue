<template>
  <div class="vue-tempalte">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <h5><span class="font-weight">Kayıt Ol</span></h5>
            <form @submit.prevent="register">
              <div class="form-group">
                <span class="font-weight">Ad</span>
                <input
                  type="text"
                  class="form-control"
                  v-model="entity.name"
                  :class="{
                    'is-invalid': submitted && $v.entity.name.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.name.required"
                  class="invalid-feedback"
                >
                  Lütfen isminizi giriniz.
                </div>
              </div>

              <div class="form-group">
                <span class="font-weight">Takma Ad</span>
                <input
                  type="text"
                  class="form-control"
                  v-model="entity.nickName"
                  :class="{
                    'is-invalid': submitted && $v.entity.nickName.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.nickName.required"
                  class="invalid-feedback"
                >
                  Lütfen takma adınızı giriniz.
                </div>
                <div
                  v-if="submitted && !$v.entity.nickName.nickNameValidation"
                  class="invalid-feedback"
                >
                  Kullanıcı adında Türkçe karakter ve özel karakterler
                  kullanılmamalıdır.
                </div>
              </div>

              <div class="form-group">
                <span class="font-weight">E-mail Adresi</span>
                <input
                  type="email"
                  class="form-control "
                  v-model="entity.email"
                  :class="{
                    'is-invalid': submitted && $v.entity.email.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.email.required"
                  class="invalid-feedback"
                >
                  Lütfen email adresinizi giriniz.
                </div>
              </div>

              <div class="form-group">
                <span class="font-weight">Parola</span>
                <input
                  type="password"
                  class="form-control"
                  v-model="entity.password"
                  :class="{
                    'is-invalid': submitted && $v.entity.password.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.password.required"
                  class="invalid-feedback"
                >
                  Lütfen parolanızı giriniz
                </div>
                <div
                  v-if="
                    (submitted && !$v.entity.password.minLength) ||
                      (submitted && !$v.entity.password.maxLength)
                  "
                  class="invalid-feedback"
                >
                  Parolanız {{ $v.entity.password.$params.minLength.min }} -
                  {{ $v.entity.password.$params.maxLength.max }} aralığında
                  karakter içermelidir.
                </div>
              </div>
              <div class="form-group">
                <span class="font-weight">Parola tekrar</span>
                <input
                  type="password"
                  class="form-control"
                  v-model="entity.confirmPassword"
                  :class="{
                    'is-invalid': submitted && $v.entity.confirmPassword.$error,
                  }"
                />
                <div
                  v-if="submitted && !$v.entity.confirmPassword.required"
                  class="invalid-feedback"
                >
                  Lütfen parolanızı tekrar giriniz.
                </div>
                <div
                  class="invalid-feedback"
                  v-if="submitted && !$v.entity.confirmPassword.sameAsPassword"
                >
                  Girilen parolalar birbiri ile uyuşmuyor.
                </div>
              </div>

              <button
                type="submit"
                class="btn mt-3 pull-left createbuttonstyle btn-sm"
              >
                Kayıt ol
              </button>
              <p class="forgot-password pull-right text-right mt-4">
                <i class="font-weight"> Zaten bir üyeliğin var mı? </i>
                <router-link class="font-weight" to="/login"
                  >Giriş yap</router-link
                >
              </p>
            </form>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import AuthClient from "../../client/Auth";
import {
  required,
  email,
  minLength,
  maxLength,
  sameAs,
  numeric,
  url,
} from "vuelidate/lib/validators";
export default {
  data() {
    return {
      entity: {
        password: null,
        email: null,
        name: null,
        nickName: null,
        confirmPassword: null,
      },

      submitted: false,
    };
  },
  validations: {
    entity: {
      name: { required },
      nickName: {
        required,
        minLength: minLength(5),
        maxLength: maxLength(20),
        nickNameValidation: function(value) {
          let validNickNamePattern = new RegExp(
            "^[a-zA0-9-Z]+(?:[-'\s][a-zA-Z]+)*$"
          );
          if (validNickNamePattern.test(value)) {
            return true;
          }
          return false;
        },
      },
      email: { required, email },
      password: { required, minLength: minLength(6), maxLength: maxLength(32) },
      confirmPassword: { required, sameAsPassword: sameAs("password") },
    },
  },
  mounted() {},
  methods: {
    async register(e) {
      this.submitted = true;
      this.$v.$touch();

      if (this.$v.$invalid) {
        return;
      }
      try {
        const data = await AuthClient.register(this.entity);

        this.$toastr.success("kullanıcınız başarıyla oluşturuldu", "Başarılı!");
        this.$router.push("/login");
      } catch (error) {
        this.$toastr.error("Bu kullanıcı adı kullanılmaktadır.", "Başarısız!");
      }
    },
  },
};
</script>
