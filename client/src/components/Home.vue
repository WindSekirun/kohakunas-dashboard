<template>
  <div class="container pa-5">
    <div v-for="(item, index) in categoryList" :key="index">
      <p class="font-weight-medium text-h4">{{ item.name }}</p>
      <hr class="mt-2" />
      <v-row class="mt-2">
        <v-col
          sm="5"
          md="6"
          lg="3"
          xl="3"
          v-for="(service, serviceIndex) in item.services"
          :key="serviceIndex"
        >
          <v-card>
            <v-card-header>
              <v-card-header-text>
                <v-card-title>
                  <img :src="service.icon" width="24" class="mr-2" />
                  {{ service.title }}
                </v-card-title>
                <v-card-subtitle class="mt-1">
                  {{ service.desc }}
                </v-card-subtitle>
              </v-card-header-text>
            </v-card-header>
            <v-divider></v-divider>
            <v-card-actions class="justify-end">
              <v-btn color="info" v-bind="props"> Memo </v-btn>
              <v-tooltip bottom>
                <template v-slot:activator="{ props }">
                  <v-btn
                    color="info"
                    variant="text"
                    @click="moveWeb(service)"
                    dark
                    v-bind="props"
                  >
                    Move
                  </v-btn>
                </template>
                <span>{{ service.connectUrl }}</span>
              </v-tooltip>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
      <br />
    </div>
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
      memoDialog: null,
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
  methods: {
    moveWeb(service) {
      window.open(service.connectUrl, "_blank");
    },
  },
};
</script>

<style>
hr {
  color: #fbd8c9;
}
</style>