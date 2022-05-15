<template>
  <v-app>
    <v-app-bar color="#FCFAD4">
      <v-toolbar-title>KohakuNAS Dashboard</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-icon class="mr-2">mdi-account-circle</v-icon>
      {{ userName }}
    </v-app-bar>
    <v-main>
      <v-container fluid fill-height>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HelloWorld from "./components/Home.vue";
import { useStore } from "vuex";
import { AuthState } from "./store/auth";

export default defineComponent({
  name: "App",

  components: {
    HelloWorld,
  },
  setup() {},
  data() {
    return {
      drawer: false,
    };
  },
  mounted() {
    this.emitter.on("logout", this.logout());
  },
  beforeUnmount() {
    this.emitter.off("logout");
  },
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },
  },
  computed: {
    userName() {
      const authStore: AuthState = useStore().state.auth;
      return authStore.user?.userName || "Not Signed";
    },
  },
});
</script>
