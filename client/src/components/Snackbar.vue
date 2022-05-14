<template>
  <v-snackbar v-model="snackbar" :timeout="2000">
    {{ text }}

    <template v-slot:actions>
      <v-btn color="blue" variant="text" @click="snackbar = false">
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