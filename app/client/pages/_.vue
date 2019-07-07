<template>
    <v-app>

        <v-layout column pt-2>
            <v-flex>
                <v-layout>
                    <v-flex>
                        <v-text-field
                                v-model="comment"
                                label="Kommentar hier eingeben"
                        ></v-text-field>
                    </v-flex>
                    <v-flex>
                        <v-btn @click="send_text" :disabled="loading">
                            Kommentar senden
                        </v-btn>
                    </v-flex>
                </v-layout>

            </v-flex>
            <v-flex>
                <v-layout py-2 v-for="(val, idx) in comments" :key="idx" column>
                    <v-card>

                        <v-flex class="subheading">
                            {{val.text}}
                        </v-flex>
                        <v-flex>
                            <v-layou row >
                                <v-flex xs6 class="caption">
                                    user: {{val.user}}
                                </v-flex>
                                <v-flex xs6 class="caption">
                                    time: {{val.time}}
                                </v-flex>
                            </v-layou>
                        </v-flex>
                    </v-card>
                </v-layout>

            </v-flex>

        </v-layout>

    </v-app>
</template>

<script>
  import axios from "axios";

  axios.defaults.headers.post = {
    'Access-Control-Allow-Origin': "*",
    'Access-Control-Allow-Credentials': 'true',
    'Content-Type': 'application/json',
  };
  export default {
    name: "Home",
    data: () => ({
      comments: [],
      loading: false,
      comment: null
    }),
    methods: {

      send_text: function () {
        axios
            .post("http://localhost:4000/",
                this.comment,
                {
                  headers: {
                    "Content-Type": "application/json",
                  }
                })
            .then(response => {
              this.comments = response.data;
              console.log("Axios:", this.comments);

              this.loading = false;
            })
            .catch(error => {
              // Handle Errors here.
              this.loading = false;

              var errorCode = error.code;
              var errorMessage = error.message;
              console.log(errorCode, errorMessage);
              // ...
            });
      }
    }
  };
</script>

<style scoped>
</style>