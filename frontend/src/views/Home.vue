<template>
  <div class="home mt-5">
    <!-- ============================================-->
    <!-- <section> begin ============================-->
    <section class="pb-6">
      <div class="container">
        <div class="row flex-center">
          <div class="img col-lg-6 col-md-5 order-md-1">
            <img class="img-fluid" src="../assets/kanban-res.png" alt="" />
          </div>
          <div
            class="col-md-7 col-lg-6 mt-5 text-center text-md-start"
            style="color:white"
          >
            <h1 class="fw-medium">
              BöceKanban ile her şey daha sistematik ve daha kolay!
            </h1>
            <p class="mt-3 mb-4">
              Günlük iş hayatınızda ki koşuşturmacaların, projelerin ve
              görevlerin arasında boğulmaya son! Artık projeler BöceKanban ile
              daha sistematik ve daha kolay yönetilebilir hale geldi. Eğer siz
              de BöceKanban'ın kolaylıklarından faydalanmak için
              sabırsızlanıyorsanız hemen üye olun ve projeye başlayın!
            </p>
            <router-link to="/projects">
              <a class="btn btn-lg btn-primary hover-top btn-glow"
                >HEMEN BAŞLA
              </a>
            </router-link>
          </div>
        </div>
      </div>
      <!-- end of .container-->
    </section>
    <!-- <section> close ============================-->
    <!-- ============================================-->

    <!-- ============================================-->
    <!-- <section> begin ============================-->
    <section class="py-4">
      <div class="container">
        <div class="card py-5 border-0 shadow-sm">
          <div class="card-body">
            <div class="row">
              <div class="col-4">
                <div class="border-end d-flex justify-content-md-center">
                  <div class="mx-2 mx-md-0 me-md-5">
                    <div class="badge badge-circle bg-soft-danger">
                      <svg
                        class="fa fa-tasks"
                        xmlns="http://www.w3.org/2000/svg"
                        width="42"
                        height="42"
                        fill="#0275d8"
                        viewBox="0 0 16 16"
                      >
                        <path
                          d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"
                        ></path>
                      </svg>
                    </div>
                  </div>
                  <div>
                    <p class="fw-bolder text-1000 mb-0">{{ countAccount }}</p>
                    <p class="mb-0">Kullanıcı</p>
                  </div>
                </div>
              </div>
              <div class="col-4">
                <div class="border-end d-flex justify-content-md-center">
                  <div class="mx-2 mx-md-0 me-md-5">
                    <div class="badge badge-circle bg-soft-danger">
                      <div style="font-size: 2.5rem; color: Dodgerblue">
                        <i class="fa fa-tasks" aria-hidden="true"></i>
                      </div>
                    </div>
                  </div>
                  <div>
                    <p class="fw-bolder text-1000 mb-0">{{ countTask }}</p>
                    <p class="mb-0">Görev</p>
                  </div>
                </div>
              </div>
              <div class="col-4">
                <div class="d-flex justify-content-md-center">
                  <div class="mx-2 mx-md-0 me-md-5">
                    <div class="badge badge-circle bg-soft-danger">
                      <svg
                        class="bi bi-hdd-stack-fill"
                        xmlns="http://www.w3.org/2000/svg"
                        width="42"
                        height="42"
                        fill="#0275d8"
                        viewBox="0 0 16 16"
                      >
                        <path
                          d="M2 9a2 2 0 0 0-2 2v1a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2v-1a2 2 0 0 0-2-2H2zm.5 3a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1zm2 0a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1zM2 2a2 2 0 0 0-2 2v1a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H2zm.5 3a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1zm2 0a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1z"
                        ></path>
                      </svg>
                    </div>
                  </div>
                  <div>
                    <p class="fw-bolder text-1000 mb-0">{{ countProject }}</p>
                    <p class="mb-0">Proje</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- end of .container-->
    </section>
    <!-- <section> close ============================-->
    <!-- ============================================-->

    <!-- ============================================-->
    <!-- <section> begin ============================-->
    <section class="pt-4 pt-md-6">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-md-5 col-lg-7 text-lg-center">
            <img class="img-fluid mb-5 mb-md-0" src="../assets/2.png" alt="" />
          </div>
          <div
            class="col-md-7 col-lg-5 text-center text-md-start"
            style="color:white"
          >
            <h2>Sizler için bir çok yeni özellik geliştiriyoruz!</h2>
            <br />
            <div class="d-flex">
              <svg
                class="bi bi-check-circle-fill"
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="#2FAB73"
                viewBox="0 0 16 16"
              >
                <path
                  d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"
                ></path>
              </svg>
              <p class="ms-2">Sistemimize dahil olun</p>
            </div>
            <div class="d-flex">
              <svg
                class="bi bi-check-circle-fill"
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="#2FAB73"
                viewBox="0 0 16 16"
              >
                <path
                  d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"
                ></path>
              </svg>
              <p class="ms-2">Yeni bir proje başlatın</p>
            </div>
            <div class="d-flex">
              <svg
                class="bi bi-check-circle-fill"
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="#2FAB73"
                viewBox="0 0 16 16"
              >
                <path
                  d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"
                ></path>
              </svg>
              <p class="ms-2">Projenize yeni ekip arkadaşları davet edin</p>
            </div>
            <div class="d-flex">
              <svg
                class="bi bi-check-circle-fill"
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="#2FAB73"
                viewBox="0 0 16 16"
              >
                <path
                  d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"
                ></path>
              </svg>
              <p class="ms-2">Tasklarınızı oluşturun</p>
            </div>
            <div class="d-flex">
              <svg
                class="bi bi-check-circle-fill"
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="#2FAB73"
                viewBox="0 0 16 16"
              >
                <path
                  d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"
                ></path>
              </svg>
              <p class="ms-2">Artık proje yönetimine hazırsınız.</p>
            </div>
          </div>
        </div>
      </div>
      <!-- end of .container-->
    </section>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import dashboard from "../client/Dashboard";

export default {
  name: "Home",
  data() {
    return {
      countProject: null,
      countTask: null,
      countAccount: null,
    };
  },
  mounted() {
    this.getAccountCounts();
    this.getTaskCounts();
    this.getProjectCounts();
  },
  components: {
    HelloWorld,
  },
  methods: {
    getAccountCounts() {
      dashboard.getAccountCounts().then((r) => {
        this.countAccount = r.data;
      });
    },
    getTaskCounts() {
      dashboard.getTaskCounts().then((r) => {
        this.countTask = r.data;
      });
    },
    getProjectCounts() {
      dashboard.getProjectCounts().then((r) => {
        this.countProject = r.data;
      });
    },
  },
};
</script>
