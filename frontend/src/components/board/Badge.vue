<template>
  <div>
      <small :class="`badge badge-pill badge-${color}`">
      <slot></slot>
    </small>
  </div>
</template>
<script>
export default {
  props: {
    color: {
      type: String,
      default: "primary"
    }
  }
};
</script>
<style scoped>
small{
  font-size: 11px;
}
</style>
