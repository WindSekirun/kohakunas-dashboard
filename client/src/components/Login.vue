<template>
  <v-row align="center" justify="center" style="height: 80vh" dense>
    <v-col
      cols="12"
      class="fill-height d-flex flex-column justify-center align-center"
    >
      <v-card width="400">
        <div align="center" justify="center" class="text-xs-center">
          <v-avatar size="125" class="mt-8">
            <v-img :src="profileUrl"></v-img>
          </v-avatar>
          <br />
          <p class="font-weight-medium text-h3 mt-5">KohakuNAS</p>
          <p class="font-weight-regular text-h3 mt-1">Dashboard</p>
          <br />

          <v-text-field
            class="ms-5 me-5"
            v-model="username"
            :rules="[rules.required]"
            label="Username"
            counter
            variant="contained"
          ></v-text-field>

          <v-text-field
            class="ms-5 me-5"
            v-model="password"
            :append-icon="visiblePassword ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.min]"
            :type="visiblePassword ? 'text' : 'password'"
            label="Password"
            hint="At least 8 characters"
            counter
            variant="contained"
            @click:append="visiblePassword = !visiblePassword"
          ></v-text-field>

          <v-row class="ms-2 me-2 mb-5">
            <v-col cols="6">
              <v-btn block @click="handleRegister()">Register</v-btn>
            </v-col>
            <v-col cols="6">
              <v-btn block color="#FBD8C9" @click="handleLogin()">Login</v-btn>
            </v-col>
          </v-row>
        </div>
      </v-card>
    </v-col>
    <snackbar ref="snackbar" />
  </v-row>
</template>

<script>
import kohakuUrl from "../assets/kohaku.png";
import { LoginCredentials } from "../model/login";
import Snackbar from "./Snackbar.vue";

export default {
  name: "Login",
  components: {
    Snackbar,
  },
  data() {
    return {
      profileUrl: kohakuUrl,
      visiblePassword: false,
      username: "",
      password: "",
      rules: {
        required: (value) => !!value || "Required.",
        min: (v) => v.length >= 8 || "Min 8 characters",
      },
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/");
    }
  },
  methods: {
    async handleLogin() {
      const user = new LoginCredentials();
      user.userName = this.username;
      user.password = this.password;
      this.$store
        .dispatch("auth/login", user)
        .then((result) => {
          this.$router.push("/");
        })
        .catch((error) => {
          this.$refs.snackbar.show(error);
        });
    },

    handleRegister() {
      this.$router.push("/register");
    },
  },
};
</script>