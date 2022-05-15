<template>
  <v-snackbar v-model="snackbar" :timeout="5000" top right class="mt-16">
    {{ text }}

    <template v-slot:actions>
      <v-btn color="#FBD8C9" variant="text" @click="snackbar = false">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script>
export default {
  name: "Snackbar",
  data() {
    return {
      snackbar: false,
      text: null,
    };
  },
  computed: {},
  methods: {
    show(text) {
      this.text = text;
      this.snackbar = true;
    },
  },
};
</script>