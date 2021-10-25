<template>
  <div class="vue-template">
    <Navi></Navi>

    <!-- Main -->
    <div class="App">
      <div class="vertical-center">
        <div>
          <router-view />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navi from "./views/Navi.vue";
export default {
  name: "App",
  components: { Navi },
};
</script>
