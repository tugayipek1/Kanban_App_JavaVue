<template>
  <div class="project-members mt-4">
    <div class="d-flex flex-row justify-content-center">
      <div class="container">
        <template v-if="project">
          <div
            class="member rounded"
            v-for="member in project.members"
            :key="member.id"
          >
            <div class="member-icon">
              <span
                data-toggle="tooltip"
                data-placement="bottom"
                :title="member.name + ' - ' + member.nickName"
              >
                <avatar
                  style="font: inherit;"
                  :username="member.name"
                  :size="30"
                ></avatar>
              </span>
            </div>
          </div>
        </template>
        <div class="member m-1">
          <span
            v-b-modal.modal-prevent-closing
            data-toggle="tooltip"
            data-placement="bottom"
            :title="'Üye Ekle'"
          >
            <div style="font-size: 1.2rem;">
              <font-awesome-icon
                v-if="project"
                class="iconId"
                style="color:#d4d3d3"
                icon="user-plus"
              />
            </div>
          </span>
        </div>
      </div>
    </div>
    <b-modal
      id="modal-prevent-closing"
      ref="modal"
      title="Projeye Davet Et"
      hide-header-close
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group
          class="inviteLabel"
          label="Davet edilecek kişinin email adresini girin"
          label-for="email-input"
        >
          <b-form-input
            class="mt-3"
            id="name-input"
            :class="{
              'is-invalid': submitted && $v.email.$error,
            }"
            v-model="email"
          ></b-form-input>
          <div class="invalid-feedback" v-if="submitted && !$v.email.required">
            Bu alanın doldurulması zorunludur
          </div>
          <div class="invalid-feedback" v-if="submitted && !$v.email.email">
            Geçerli bir email adresi giriniz
          </div>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
import Avatar from "vue-avatar";
import axios from "axios";
import { mapGetters, mapMutations } from "vuex";

import { required, email } from "vuelidate/lib/validators";
export default {
  components: {
    Avatar,
  },
  data() {
    return {
      project: this.$store.getters["Project/project"],
      email: "",
      submitted: false,
    };
  },
  validations: {
    email: {
      required,
      email,
    },
  },

  computed: {
    ...mapGetters("Auth", ["account"]),
  },
  methods: {
    sendInvite() {
      var inviteRequest = {
        invitedUserEmail: this.email,
        project: {
          id: this.project.id,
        },
        account: {
          id: this.account.id,
          name: this.account.name,
        },
      };

      axios
        .post("/invite/send-invite", inviteRequest)
        .then((r) => {
          this.$toastr.success(r.data.message, "Başarılı!");
        })
        .catch((e) => {
          this.$toastr.error(e.response.data.message, "Başarısız!");
        });
    },

    resetModal() {
      this.email = "";
      this.submitted = false;
    },
    handleOk(bvModalEvt) {
      bvModalEvt.preventDefault();
      this.handleSubmit();
    },
    handleSubmit() {
      this.submitted = true;
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      this.sendInvite();

      this.$nextTick(() => {
        this.$bvModal.hide("modal-prevent-closing");
      });
    },
  },
};
</script>

<style>
.column-width {
  min-width: 320px;
  width: 320px;
}
.project-members {
  margin-right: 10px;
  margin-top: 5px;
  margin-bottom: 5px;
}
.member {
  display: inline-block;
}
.member-icon {
  margin-right: 2px;
}
.inviteLabel {
  font-weight: bold;
}
</style>
