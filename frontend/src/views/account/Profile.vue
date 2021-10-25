<template>
  <div class="vue-tempalte">
    <b-container class="bv-example-row">
      <b-row class="justify-content-md-center">
        <b-col col lg="6">
          <b-card class="mt-5">
            <span class="font-weight"></span>
            <h4><span class="font-weight">Profilim</span></h4>

            <div class="form-group">
              <div class="font-weight mt-3 ">Adım - Soyadım</div>
              <input
                type="text"
                class="form-control"
                :class="{
                  'is-invalid': submitted && $v.entity.name.$error,
                }"
                v-model="entity.name"
              />
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.name.required"
              >
                Bu alanın doldurulması zorunludur
              </div>
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.name.maxLength"
              >
                Proje adı en az
                {{ $v.entity.name.$params.maxLength.max }} karakterden
                oluşmalıdır.
              </div>
            </div>
            <div class="form-group">
              <div class="font-weight mt-3">Takma Adım</div>
              <input
                type="text"
                class="form-control"
                :class="{
                  'is-invalid': submitted && $v.entity.nickName.$error,
                }"
                v-model="entity.nickName"
              />
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.nickName.required"
              >
                Bu alanın doldurulması zorunludur
              </div>
              <div
                class="invalid-feedback"
                v-if="submitted && !$v.entity.nickName.maxLength"
              >
                Proje adı en az
                {{ $v.entity.nickName.$params.maxLength.max }} karakterden
                oluşmalıdır.
              </div>
            </div>
            <div class="form-group">
              <label
                style="
                  font-weight: bold;
                  margin-bottom: 10px;
                  margin-top: 10px; ;
                "
                >E-Mail Adresim</label
              >
              <div
                class="profile-email"
                v-if="entity.auth && entity.auth.email"
              >
                {{ this.entity.auth.email }}
              </div>
            </div>

            <div
              class="form-group d-grid gap-2 d-md-flex justify-content-md-start"
            >
              <button
                type="submit"
                @click="update"
                class="btn updatebuttonstyle mt-3 btn-sm"
              >
                <font-awesome-icon icon="edit" />
                Güncelle
              </button>
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
import {
  required,
  minLength,
  maxLength,
  url,
  numeric,
} from "vuelidate/lib/validators";

export default {
  data() {
    return {
      entity: {},
      submitted: false,
    };
  },
  validations: {
    entity: {
      name: {
        required,
        maxLength: maxLength(64),
      },
      nickName: {
        required,
        maxLength: maxLength(20),
      },
    },
  },
  computed: {
    ...mapGetters("Auth", ["account", "jwt"]),
  },
  async mounted() {
    this.entity.id = this.account.id;
    const { data } = await axios.get(`/account`);
    this.entity = { ...data };
  },
  methods: {
    async update() {
      this.submitted = true;
      this.$v.$touch();

      if (!this.$v.$invalid) {
        await axios.put(`/account`, this.entity).then((r) => {
          this.$store.commit("Auth/setAccount", this.entity);
        });

        this.$toastr.success("Profil başarıyla güncellendi", "Başarılı!");
      } else {
        this.$toastr.error(
          "Lütfen geçerli profil bilgileri giriniz",
          "Başarısız!"
        );
      }
    },
  },
};
</script>

<style scoped>
.profile-email {
  cursor: not-allowed;
  font-weight: 500;
}
</style>
