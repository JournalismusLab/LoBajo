<template>
    <v-app>
        <v-container>
            <v-layout column>
                <v-flex d-flex style="height: 100px" fluid>
                    <v-flex style="height: 100px" fluid>
                        <v-text-field
                                v-model="comment"
                                label="Kommentar hier eingeben"
                        ></v-text-field>
                    </v-flex>
                    <v-flex style="height: 100px">
                        <v-btn @click="send_text" :disabled="loading">
                            Kommentar senden
                        </v-btn>
                    </v-flex>
                </v-flex>
                <v-flex>
                    <v-layout my-2 v-for="(val, idx) in comments" :key="idx" column>
                        <v-card>

                            <v-flex class="title">
                                <p ma-2>
                                    {{val.text}}
                                </p>
                            </v-flex>
                            <v-flex>
                                <v-layout row>
                                    <v-flex xs6 class="caption">
                                        {{val.user}}
                                    </v-flex>
                                    <v-flex xs6 class="caption">
                                        {{val.time}}
                                    </v-flex>
                                </v-layout>
                            </v-flex>
                        </v-card>
                    </v-layout>

                </v-flex>

            </v-layout>
        </v-container>


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
      comment: ""
    }),
    methods: {

      send_text: function () {
        this.loading = true;
        axios
            .post("http://18.185.163.135:4000/",
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
    },
    mounted() {
      this.send_text();
    }
  };
</script>

<style scoped>
</style>