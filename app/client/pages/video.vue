<!--

Requetes this librery:
https://sdk.amazonaws.com/js/aws-sdk-2.16.0.min.js
More info here:         // https://docs.aws.amazon.com/rekognition/latest/dg/image-bytes-javascript.html

Designed to work with Nuxt / Vue.js / AWS Rekognition and Lambda

-->


<template>
    <div>
        <v-app>
            <v-container>


                <v-layout column>
                    <v-layout align-center justify-center row wrap>
                        <v-btn :disabled="loading" @click="go" v-if="!video_on">Start Video</v-btn>
                        <v-btn :disabled="loading" @click="send_picture">Send the picture</v-btn>
                        <v-btn :disabled="loading" @click="stop_video" v-if="video_on">Stop Video</v-btn>
                        <v-btn :disabled="loading || !video_on" @click="make_photo">Make Photo</v-btn>
                    </v-layout>
                    <v-layout justify-center wrap row>
                        <v-flex xs4 pl-2>

                            <video ref="video" width="100%" :height="video_height" autoplay></video>
                        </v-flex>
                        <v-flex xs4 pl-2 v-if="url">
                            <v-img contain width="100%" :height="video_height" :src="url"></v-img>

                        </v-flex>

                    </v-layout>
                </v-layout>


                <canvas style="display: none" ref="canvas" width="320"
                        :height="video_height"></canvas>
            </v-container>


        </v-app>
    </div>


</template>

<script>

  var the_stream = undefined;
  import axios from "axios";

  async function getImage({
                            canvas,
                            width,
                            height,
                            mime = 'image/jpeg',
                            quality = 0.8,
                          }) {
    return new Promise(resolve => {
      const tmpCanvas = document.createElement('canvas');
      tmpCanvas.width = width;
      tmpCanvas.height = height;

      const ctx = tmpCanvas.getContext('2d');
      ctx.drawImage(
          canvas,
          0,
          0,
          canvas.width,
          canvas.height,
          0,
          0,
          width,
          height,
      );

      tmpCanvas.toBlob(resolve, mime, quality);
    });
  }


  axios.defaults.headers.post = {
    'Access-Control-Allow-Origin': "*",
    'Access-Control-Allow-Credentials': 'true',
    'Content-Type': 'application/json',
  };

  export default {
    name: "Video",

    data: () => ({
      stream: undefined,
      video: undefined,
      url: undefined,
      canvas: undefined,
      loading: false,
      video_width: 320,
      video_on: false,
      video_ratio: 320 / 240,

    }),
    methods: {
      //Playing Video

      stop_video: function () {
        this.video.srcObject.getVideoTracks().forEach(track => track.stop());
        this.video_on = false;
      },
      go: function () {
        if (process.client) {
          console.log('this.$navigator.mediaDevices.getUserMedia:');
          console.log(navigator);
          if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            // Not adding `{ audio: true }` since we only want video now
            navigator.mediaDevices.getUserMedia({
                  video: true
                })
                .then(stream => {
                  //video.src = window.URL.createObjectURL(stream);
                  the_stream = stream;
                  let tracks = stream.getTracks();
                  for (let i = 0; i < tracks.length; i++) {
                    this.video_ratio = tracks[i].getSettings().aspectRatio;
                    console.log(tracks[i].getSettings());
                  }
                  this.video = this.$refs.video;

                  this.video.srcObject = the_stream;
                  this.video.play();
                  this.video_on = true;
                  alert(true);
                })
                .catch(error => {
                  // Handle Errors here.
                  this.loading = false;
                  alert(error.message);
                  var errorCode = error.code;
                  var errorMessage = error.message;
                  console.log(errorCode, errorMessage);
                  // ...
                });
            ;
          }
        }
      },
      // Sends picture to the local server
      send_picture: function (file) {
        let data = new FormData();
        data.append('file', file, file.fileName);
        axios
            .post("http://127.0.0.1:4000/",
                data,
                {
                  headers: {
                    'accept': 'application/json',
                    'Accept-Language': 'en-US,en;q=0.8',
                    'Content-Type': `multipart/form-data; boundary=${data._boundary}`,
                  }
                })
            .then(response => {
              console.log("Axios:", response);
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
      },
      //Making Photo
      make_photo: async function () {

        this.loading = true;

        this.canvas = this.$refs.canvas;
        this.canvas.getContext('2d').drawImage(this.video, 0, 0, this.video_width, this.video_height);
        // Get an image dataURL from the canvas.
        console.log('this.canvas', this.canvas);
        let imageDataURL = this.canvas.toDataURL('image/jpg');
        var can = this.canvas;
        const photo = await getImage({canvas: can, width: this.video_width, height: this.video_height});

        // let imageBlob = this.canvas.toBlob('image/jpg');
        this.url = imageDataURL;
        console.log('imageBlob', photo);
        console.log('The image URL:', this.url);
        this.send_picture(photo);
        this.loading = false;
      },

    },
    computed: {
      device_horisontal: function () {
        return this.$vuetify.breakpoint.width / this.$vuetify.breakpoint.height > 1
      },
      video_height: function () {
        console.log(this.$vuetify.breakpoint.width, this.$vuetify.breakpoint.height);

        console.log(this.video_ratio);
        console.log(this.device_horisontal);
        if (this.device_horisontal) {
          return parseInt(320 / this.video_ratio)

        } else {
          return parseInt(320 * this.video_ratio)

        }
      }

    },
    created() {
      // Get access to the camera!


      // Trigger photo take
      // this.$refs.snap.addEventListener("click", function () {
      //   context.drawImage(video, 0, 0, 640, 480);
      // });

    }
  }
</script>

<style scoped>

</style>