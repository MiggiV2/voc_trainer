<template>
  <div class="wrapped">
    <div class="box" v-if="!getCookie('access_token') && !status.showTainer">
      <h1 class="text-danger">Please login first!</h1>
    </div>
    <div class="box" v-if="!status.showTainer">
      <h2>Lets start!</h2>
      <hr />
      <h4>Select a dictionary</h4>
      <p>
        Status:
        <a v-if="status.bond == null" class="spinner-border"></a>
        <a v-else-if="status.bond"><Check class="text-success" /></a>
        <a
          v-else
          class="trigger"
          data-bs-toggle="modal"
          data-bs-target="#helpModal"
        >
          <Exclamation class="text-danger" />
          No dictionary found -> Help!
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
          <p>In your left corner, click <House /></p>
          <p>Then find one dictionary</p>
          <p>Right, beside the dictionary title click <Journal /></p>
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

import Check from "./icons/Check.vue";
import Exclamation from "./icons/Exclamation.vue";
import House from "./icons/House.vue";
import Journal from "./icons/Journal.vue";

var urlParams = new URLSearchParams(window.location.search);

var status = reactive({
  bond: true,
  dictionaryID: "help",
  showTainer: urlParams.has("id"),
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
        startTimer(2);
        return response.json();
      } else if (response.status === 204) {
        status.bond = false;
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
    if(time <= 0)
    {
      startTainer();
    }
    else{
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
  border: solid black 1px;
  border-radius: 5px 10px 5px 10px;
  max-width: 98%;
  margin: 4rem auto;
  text-align: center;
  padding: 5px;
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