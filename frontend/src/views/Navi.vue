<template>
  <nav class="navbar navbar-expand-lg">
    <div class="container">
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-top navbar-collapse" id="navbarNav">
        <a href="/home"><div class="nav-item-logo"></div></a>
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link" to="/home">Anasayfa</router-link>
          </li>
          <li class="nav-item" v-if="isLoggedIn" @click="goToProjects">
            <router-link class="nav-link" to="/projects">Projeler</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" v-if="isProjectSelected" to="/board"
              >Panel</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" v-if="isProjectSelected" to="/backlog"
              >Backlog</router-link
            >
          </li>
        </ul>
      </div>
      <div
        v-if="!isLoggedIn"
        class="collapse navbar-collapse justify-content-end"
      >
        <ul class="navbar-nav">
          <li class="nav-item navbar-right">
            <router-link class="nav-link pr-3" to="/login"
              >Giriş Yap</router-link
            >
          </li>
          <li class="nav-item navbar-right">
            <router-link class="nav-link" to="/signup">Kayıt ol</router-link>
          </li>
        </ul>
      </div>
      <div v-if="isLoggedIn" class="navbar-collapse justify-content">
        <b-dropdown id="dropdown" class="m-md-3" variant="lite">
          <template #button-content>
            <i class="fa fa-bookmark selecticonstyle"></i>
            <span class="font-weight">
              {{ project ? project.projectName : "Proje Seçiniz" }}</span
            >
          </template>
          <b-dropdown-item
            v-for="p in myProjects"
            :key="p.id"
            @click="selectProject(p)"
            href="/board"
          >
            <span class="font-weight"> {{ p.projectName }}</span>

            <span v-if="project">
              <span v-if="project.id == p.id">
                <font-awesome-icon icon="check" style="color: #212169" />
              </span>
            </span>
          </b-dropdown-item>
          <b-dropdown-item @click="$router.push('/addProject')">
            <span class="font-weight">Proje Oluştur</span>
          </b-dropdown-item>
        </b-dropdown>
      </div>
      <div v-if="isLoggedIn" class="navbar-collapse justify-content-end">
        <avatar :username="account.name" :size="30"> </avatar>
        <b-dropdown
          id="dropdown-1"
          :text="account.name"
          variant="lite"
          class="m-md-2 font-weight"
        >
          <template #button-content>
            <span class="font-weight"> {{ account.name }}</span>
          </template>
          <b-dropdown-item @click="goToProfile"
            ><span class="font-weight">Profil</span></b-dropdown-item
          >
          <b-dropdown-item @click="logout"
            ><span class="font-weight">Çıkış Yap</span></b-dropdown-item
          >
        </b-dropdown>
      </div>
    </div>
  </nav>
</template>

<script>
import Avatar from "vue-avatar";

import { mapGetters } from "vuex";

export default {
  components: { Avatar },
  computed: {
    ...mapGetters("Auth", ["isLoggedIn", "account"]),
    ...mapGetters("Project", ["project", "isProjectSelected", "myProjects"]),
  },
  mounted() {
    this.goToProjects();
  },
  methods: {
    logout() {
      this.$store.dispatch("Auth/logout");
    },
    goToProfile() {
      this.$router.push("/profile");
    },
    selectProject(project) {
      this.$store.commit("Project/setProject", project);
    },
    goToProjects() {
      if (this.isLoggedIn) {
        this.$store.dispatch("Project/removeMyProject");
        this.$store.dispatch("Project/getMyProjects", this.account.id);
      }
    },
  },
};
</script>

<style scoped>
.dropdown {
  background: white;
  border-radius: 25px;
  cursor: pointer !important;
}
nav {
  background: linear-gradient(
    90deg,
    rgb(45 37 88) 0%,
    rgb(113 126 189) 50%,
    rgb(45 37 88) 100%
  );
}
a {
  color: black;
  font-weight: bold;
}
a:hover {
  transition: ease 0.5s;
  color: tomato;
}
a.active {
  color: #e6e6e6;
  border-radius: 4px;
}

.nav-item-logo {
  width: 200px;
  height: 150px;
  margin: 10px;
  margin-top: -30px;
  object-fit: contain;
  position: relative;
  cursor: pointer;
  background-image: url(https://api.freelogodesign.org/files/0e9f68377bca476fbc14071868390205/thumb/logo_200x200.png?v=637689347850000000);
}

.navbar-top {
  max-height: 6rem;
}
.nav-link {
  margin-left: 1rem;
}
.aa {
  color: white;
}
.dropdown-toggle::after {
  color: red !important;
}
</style>
