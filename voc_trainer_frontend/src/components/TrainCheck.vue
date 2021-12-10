<template>
  <!--Can't find your last dictionary-->
  <div class="wrapped">
    <div class="box" v-if="!getCookie('access_token') && !status.showTainer">
      <h1 class="text-danger">Please login first!</h1>
    </div>
    <div class="box" v-if="!status.showTainer">
      <h2>Lets start!</h2>
      <hr />
      <h4>{{ status.message }}</h4>
      <p>
        Status:
        <a v-if="status.bond == null" class="spinner-border"></a>
        <a v-else-if="status.bond"
          ><i class="bi bi-check-lg text-success"></i
        ></a>
        <a
          v-else
          class="trigger"
          data-bs-toggle="modal"
          data-bs-target="#helpModal"
        >
          <i class="bi bi-exclamation-diamond-fill text-danger"></i>
          Help!
        </a>
      </p>
      <div v-if="status.bond">
        <hr />
        <button @click="startTainer()" class="btn btn-success">
          {{ button.content }}
        </button>
      </div>
    </div>
  </div>
  <!--HelpModal-->
  <div
    class="modal fade"
    id="helpModal"
    tabindex="-1"
    aria-labelledby="helpModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="helpModalLabel">Help</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <p>In your left corner, click <i class="bi bi-house-door"></i></p>
          <p>Then find one dictionary</p>
          <p>Right, beside the dictionary title click <i class="bi bi-journal-bookmark-fill"></i></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-bs-dismiss="modal">
            Thanks
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";
import { getCookie } from "../tools/cookie";

var urlParams = new URLSearchParams(window.location.search);

var status = reactive({
  bond: null,
  dictionaryID: "help",
  showTainer: urlParams.has("id"),
  message: "Searching your last dictionary...",
});

var button = reactive({
  content: "Start",
  test: String,
});

if (!status.showTainer && getCookie("access_token")) {
  sendRequest();
}
if (!getCookie("access_token")) {
  status.bond = false;
}

function sendRequest() {
  fetch(HOST + "api/get/bonded-dictionary", {
    credentails: "same-origin",
    mode: "cors",
    headers: {
      Authorization: "Bearer " + getCookie("access_token"),
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (response.status === 200) {
        status.bond = true;
        status.message = "Found your last dictionary.";
        startTimer(2);
        return response.json();
      } else if (response.status === 204) {
        status.bond = false;
        status.message = "No last dictionary found.";
      }
    })
    .then((response) => {
      if (response != null) {
        status.dictionaryID = response.dictionaryID;
      }
    });
}

function startTimer(time) {
  setTimeout(function () {
    button.content = "Start in " + time + "!";
    if (time <= 0) {
      startTainer();
    } else {
      startTimer(time - 1);
    }
  }, 1000);
}

function startTainer() {
  window.location = "/train?id=" + status.dictionaryID;
}
</script>

<style scoped>
.wrapped {
  max-width: 44rem;
  margin: 4rem auto;
}
.box {
  text-align: center;
}
.spinner-border {
  max-width: 20px;
  max-height: 20px;
}
.trigger {
  cursor: pointer;
  text-decoration: none;
  color: unset;
}
</style>