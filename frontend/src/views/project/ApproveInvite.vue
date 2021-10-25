<template>
  <div class="jumbotron text-center">
    <h1 class="display-3">Merhaba!</h1>
    <p class="lead">
      Lütfen daveti
      <strong> kabul etmek</strong>
      için tıkla ⬇
    </p>
    <button @click="approveInvite()" class="btn btn-success btn-sm">
      Daveti Kabul Et
    </button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "ApproveInvite",
  data() {
    return {
      token: null,
    };
  },
  created() {
    this.token = this.$route.query.token;
  },
  methods: {
    approveInvite() {
      axios
        .get(`/invite/approve/invitation-token/${this.token}`)
        .then((r) => {
          this.$toastr.success(r.data.message, "Başarılı!");
          setTimeout(() => {
            this.$router.push("/home");
          }, 2000);
        })
        .catch((e) => {
          this.$toastr.error();
          e.response.data.message, "Başarısız!";
        });
    },
  },
};
</script>

<style>
</style>