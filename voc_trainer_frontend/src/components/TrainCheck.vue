<template>
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
        data-bs-target="#deleteModal"
      >
        <Exclamation class="text-danger" />
        No dictionary found -> Help!
      </a>
    </p>
    <div v-if="status.bond">
      <hr />
      <button @click="startTainer()" class="btn btn-success">Start</button>
    </div>
  </div>
  <!--HelpModal-->
  <div
    class="modal fade"
    id="deleteModal"
    tabindex="-1"
    aria-labelledby="deleteModallLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="deleteModalLabel">Help</h5>
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
  bond: null,
  dictionaryID: String,
  showTainer: urlParams.has('id'),
});



if(!status.showTainer)
{
  sendRequest();
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

function startTainer() {
  window.location = "/train?id=" + status.dictionaryID;
}
</script>

<style scoped>
.box {
  border: solid black 1px;
  border-radius: 5px 10px 5px 10px;
  max-width: 44rem;
  margin: 2rem auto;
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