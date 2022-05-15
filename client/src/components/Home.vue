<template>
  <div class="container">
    <snackbar ref="snackbar" />
  </div>
</template>

<script>
import Snackbar from "./Snackbar.vue";

export default {
  name: "Home",
  components: {
    Snackbar,
  },
  data() {
    return {
      categoryList: [],
    };
  },
  async mounted() {
    this.$store
      .dispatch("service/getServiceList")
      .then((result) => {
        this.categoryList = result;
      })
      .catch((error) => {
        this.$refs.snackbar.show(error);
      });
  },
};
</script>