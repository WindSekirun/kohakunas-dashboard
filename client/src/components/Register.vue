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
          <p class="font-weight-medium text-h3 mt-5">Register</p>
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

          <v-text-field
            class="ms-5 me-5"
            v-model="registerSecret"
            :rules="[rules.required]"
            label="Register Secret"
            counter
            variant="contained"
          ></v-text-field>

          <v-row class="ms-2 me-2 mb-5">
            <v-col cols="12">
              <v-btn block @click="handleRegister()">Register</v-btn>
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
import { CreateUser } from "../model/user";
import Snackbar from "./Snackbar.vue";

export default {
  name: "Register",
  components: {
    Snackbar,
  },
  data() {
    return {
      profileUrl: kohakuUrl,
      visiblePassword: false,
      username: "",
      password: "",
      registerSecret: "",
      errorText: "",
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
  methods: {
    handleRegister() {
      const user = new CreateUser();
      user.userName = this.username;
      user.password = this.password;
      user.registerSecret = this.registerSecret;
      this.$store
        .dispatch("auth/register", user)
        .then((result) => {
          this.$router.push("/login");
        })
        .catch((error) => {
          this.$refs.snackbar.show(error);
        });
    },
  },
};
</script>