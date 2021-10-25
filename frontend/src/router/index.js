import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home";
import Backlog from "../views/Backlog";
import Board from "../views/Board";
import Login from "../views/authentication/Login";
import Signup from "../views/authentication/Signup";
import Profile from "../views/account/Profile";
import ForgotPassword from "../views/authentication/ForgotPassword";
import ResetPassword from "../views/authentication/ResetPassword";
import Projects from '../views/project/Projects.vue';
import AddProject from '../views/project/AddProject.vue';
import CreateTask from '../views/task/CreateTask.vue';
import UpdateDeleteTask from '../views/task/UpdateDeleteTask.vue';
import ApproveInvite from '../views/project/ApproveInvite.vue';
import store from '../store';

import error from "../views/error.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Home,
  },
  {
    path: "/home",
    name: "Home",
    component: Home,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
    beforeEnter

  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    beforeEnter
  },
  {
    path: "/Backlog",
    name: "Backlog",
    component: Backlog,
    beforeEnter
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/forgotpassword",
    name: "ForgotPassword",
    component: ForgotPassword,
  },
  {
    path: '/reset-password/',
    name: 'ResetPassword',
    component: ResetPassword,
    props: route => ({ query: route.query.token }),
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
  },
  {
    path: '/projects',
    name: 'Projects',
    component: Projects,
    beforeEnter
  },
  {
    path: '/updateProject/:id',
    name: 'UpdateProject',
    component: AddProject,
    beforeEnter
  },
  {
    path: '/addProject',
    name: 'AddProject',
    component: AddProject,
    beforeEnter
  },
  {
    path: '/CreateTask',
    name: 'CreateTask',
    component: CreateTask,
    beforeEnter
  },
  {
    path: '/UpdateDeleteTask',
    name: 'UpdateDeleteTask',
    component: UpdateDeleteTask
  },
  {
    path: "/404",
    name: "error",
    component: error,
  },
  { path: "*", redirect: "/404" },
  {
    path: '/invite/approve/invitation-token/',
    name: 'ApproveInvite',
    component: ApproveInvite,
    props: route => ({ query: route.query.token })
  }
]

function beforeEnter(to, from, next) {
  if (store.getters["Auth/isLoggedIn"]) {
    if (to.name == "Board") {
      handeBoardRequests(to, from, next);
    } else if (to.name == "Backlog" && !store.getters["Project/isProjectSelected"]) { next("/projects") }
    else {
      next();
    }
  } else {
    next("/login");
  }
}

function handeBoardRequests(to, from, next) {

  if (to.query.task_id && to.query.project_id) {
    next()
  }
  else if (!store.getters["Project/isProjectSelected"])
    next("/projects")
  else {
    next();
  }

}



const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  linkActiveClass: 'active',
});




export default router;
