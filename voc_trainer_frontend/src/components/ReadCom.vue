<template>
  <!--data-bs-toggle="modal" data-bs-target="#editModal"-->
  <div class="dictionary-wrapped">
    <div v-if="show.content === null" class="dictionary">
      <div class="row header" v-if="dictionary.content.user != null">
        <div class="col-auto">
          <a :href="'/users?id=' + dictionary.content.user.id">
            <img
              class="avatar"
              v-if="dictionary.content.user.avatarURL != null"
              :src="dictionary.content.user.avatarURL"
              :title="dictionary.content.user.name"
            />
          </a>
        </div>
        <div class="col">
          <h3 class="title">
            {{ dictionary.content.name }}
            <a
              class="desktop"
              :href="'/users?id=' + dictionary.content.user.id"
            >
              by {{ dictionary.content.user.name }}
            </a>
          </h3>
          <a class="mobile" :href="'/users?id=' + dictionary.content.user.id">
            by {{ dictionary.content.user.name }}
          </a>
        </div>
        <div class="col-auto">
          <div class="btn-group" role="group">
            <div
              class="group-item"
              role="button"
              v-if="!show.edit && getCookie('access_token')"
              title="Train this dictionary"
              data-bs-toggle="modal"
              data-bs-target="#trainModal"
            >
              <Journal />
            </div>
            <div class="group-item" v-if="isYours()">
              <div v-if="!show.edit">
                <div
                  id="navbarDropdown"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  title="Options"
                >
                  <Gear />
                </div>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li>
                    <button
                      class="dropdown-item btn btn-danger btn-sm"
                      data-bs-toggle="modal"
                      data-bs-target="#deleteModal"
                      title="Delete"
                    >
                      <Trash />
                    </button>
                  </li>
                  <li>
                    <button
                      class="dropdown-item btn btn-primary btn-sm"
                      @click="edit()"
                      title="Update"
                    >
                      <Pencil />
                    </button>
                  </li>
                </ul>
              </div>
              <div v-else>
                <button
                  class="btn btn-danger btn-sm update-button"
                  @click="cancel()"
                >
                  <Cross />
                </button>
                <button
                  class="btn btn-success btn-sm update-button"
                  @click="startUpdate()"
                  data-bs-toggle="modal"
                  data-bs-target="#updatedModal"
                >
                  <Check />
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="sub-title">
        <a>Englisch &lt;--&gt; German</a>
      </div>
      <div
        v-if="dictionary.content.words.length == 0"
        class="spinner-border"
      ></div>
      <div
        class="words"
        v-for="(item, index) in dictionary.content.words"
        :key="index"
      >
        <div v-if="item.dictionaryID != null">
          <div v-if="show.edit">
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                placeholder="Englisch word"
                v-model="item.eng"
              />
              <input
                type="text"
                class="form-control"
                placeholder="German word"
                v-model="item.ger"
              />
            </div>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                placeholder="(OPTIONAL) description, opposite, etc..."
                v-model="item.op"
              />
              <div
                v-if="isLastItem(item)"
                class="btn btn-outline-secondary"
                @click="addFlied()"
              >
                <Plus />
              </div>
              <div class="btn btn-outline-secondary" @click="removeFiled(item)">
                <Dash />
              </div>
            </div>
          </div>
          <div v-else>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                placeholder="Englisch word"
                v-model="item.eng"
                readonly
              />
              <input
                type="text"
                class="form-control"
                placeholder="German word"
                v-model="item.ger"
                readonly
              />
            </div>
            <div class="input-group">
              <input
                v-if="showItem(item)"
                type="text"
                class="form-control"
                placeholder="(OPTIONAL) description, opposite, etc..."
                v-model="item.op"
                readonly
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <Toast
    v-if="show.content !== null"
    :content="show.content"
    :success="show.success"
  />
  <!--Modal-->
  <div
    class="modal fade"
    id="updatedModal"
    tabindex="-1"
    aria-labelledby="updatedModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="updatedModalLabel">{{ modal.title }}</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">{{ modal.body }}</div>
        <div class="modal-footer">
          <div>
            <button
              v-if="modal.showButton"
              type="button"
              class="btn btn-success"
              data-bs-dismiss="modal"
            >
              {{ modal.button }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--DeleteModal-->
  <div
    class="modal fade"
    id="deleteModal"
    tabindex="-1"
    aria-labelledby="deleteModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="deleteModalLabel">Sure?</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          Are you sure? You you really want to delete this dictionary?
        </div>
        <div class="modal-footer">
          <button
            v-if="modal.showButton"
            type="button"
            class="btn btn-success"
            data-bs-dismiss="modal"
            @click="sendDelete()"
          >
            Yes
          </button>
        </div>
      </div>
    </div>
  </div>
  <!--TrainModal-->
  <div
    class="modal fade"
    id="trainModal"
    tabindex="-1"
    aria-labelledby="trainModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="trainModalLabel">Start training</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          Do you want to start training this dictionary now?
        </div>
        <div class="modal-footer">
          <button
            v-if="modal.showButton"
            type="button"
            class="btn btn-danger"
            data-bs-dismiss="modal"
          >
            No
          </button>
          <button
            v-if="modal.showButton"
            type="button"
            class="btn btn-success"
            data-bs-dismiss="modal"
            @click="openTainer()"
          >
            Yes
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { HOST, sha512 } from "../tools/auth";
import { getAvatarURLFromUser } from "../tools/user";
import { getCookie } from "../tools/cookie";
import Toast from "./SuccessToast.vue";

import Plus from "./icons/Plus.vue";
import Dash from "./icons/Dash.vue";
import Pencil from "./icons/Pencil.vue";
import Trash from "./icons/Trash.vue";
import Check from "./icons/Check.vue";
import Cross from "./icons/X.vue";
import Gear from "./icons/Gear.vue";
import Journal from "./icons/Journal.vue";

var urlParams = new URLSearchParams(window.location.search);
var dictionary = reactive({
  content: {
    name: "...",
    id: urlParams.get("id"),
    user: null,
    words: [],
  },
});
var show = reactive({
  success: false,
  content: null,
  edit: false,
});
var modal = reactive({
  body: "",
  title: "",
  button: "Okay",
  showButton: true,
});
var lastWordHash = "";

if (dictionary.content.id === null || dictionary.content.id.length < 30) {
  console.log("Need ?id=UUID");
} else {
  sendRequest();
}

function sendRequest() {
  fetch(HOST + "api/get/dictionary?id=" + dictionary.content.id, {
    credentails: "same-origin",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (response.ok || response.status === 400) {
        return response.json();
      }
      console.log(response.statusText);
      showMessage(response.statusText, false);
    })
    .then((dictionaryResponse) => {
      if (
        dictionaryResponse !== null &&
        typeof dictionaryResponse !== "undefined"
      ) {
        if (typeof dictionaryResponse.message !== "undefined") {
          showMessage(dictionaryResponse.message), false;
        } else {
          dictionary.content = dictionaryResponse;
          document.title = dictionaryResponse.name + " | Voc-Trainer";
          dictionary.content.user.avatarURL = getAvatarURLFromUser(
            dictionary.content.user
          );
          setWordHash();
        }
      }
    })
    .catch((e) => {
      showMessage(e, false);
      console.log(e);
    });
}

function sendDelete() {
  dictionary.content.words = [];
  fetch(HOST + "api/delete", {
    method: "DELETE",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(dictionary.content),
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + getCookie("access_token"),
    },
  })
    .then((response) => {
      if (response.ok) {
        showMessage("Deleted!", true);
      } else {
        return response.json();
      }
    })
    .then((message) => {
      if (message != null) {
        showMessage(message.content, false);
      }
    })
    .catch((ex) => {
      showMessage("Error " + ex, false);
      console.log(ex);
    });
}

function sendUpdate() {
  modal.title = "Sending...";
  modal.body = "Pls wait";
  modal.showButton = false;
  show.edit = false;
  fetch(HOST + "api/update/dictionary?id=" + dictionary.content.id, {
    method: "PUT",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(dictionary.content.words),
    headers: {
      Authorization: "Bearer " + getCookie("access_token"),
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (response.ok) {
        sendRequest();
        modal.title = "Updated!";
        modal.body = "Your changes have been applied!";
        modal.showButton = true;
      } else {
        return response.json();
      }
    })
    .then((message) => {
      if (message != null) {
        modal.title = "Failed!";
        modal.body = message.content;
        modal.showButton = true;
      }
    })
    .catch((ex) => {
      modal.title = "Error!";
      modal.body = ex;
      modal.showButton = true;
    });
}

function sendBindRequest(funcToRun) {
  modal.title = "Sending...";
  modal.body = "Pls wait";
  modal.showButton = false;
  fetch(HOST + "api/bind?id=" + dictionary.content.id, {
    method: "PUT",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(dictionary.content.words),
    headers: {
      Authorization: "Bearer " + getCookie("access_token"),
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (response.ok) {
        modal.title = "Done!";
        modal.body =
          "You have bond this dictionary! Now you can go the the Train-Tab (Journal-Icon)";
        modal.showButton = true;
        funcToRun();
      } else {
        return response.json();
      }
    })
    .then((message) => {
      if (message != null) {
        modal.title = "Failed!";
        modal.body = message.content;
        modal.showButton = true;
      }
    })
    .catch((ex) => {
      modal.title = "Error!";
      modal.body = ex.message;
      modal.showButton = true;
      console.log(ex);
    });
}

function startUpdate() {
  sha512(JSON.stringify(dictionary.content.words)).then((hash) => {
    if (hash == lastWordHash) {
      modal.title = "Canceled!";
      modal.body = "You have nothing changed!";
      modal.showButton = true;
      show.edit = false;
    } else {
      sendUpdate();
    }
  });
}

function openTainer() {
  if (getCookie("access_token")) {
    sendBindRequest(function () {
      window.location = "/train?id=" + dictionary.content.id;
    });
  } else {
    window.location = "/train?id=" + dictionary.content.id;
  }
}

function removeFiled(word) {
  word.dictionaryID = null;
}

function isLastItem(item) {
  var shownWords = getShownWords();
  return shownWords[shownWords.length - 1] === item;
}

function getShownWords() {
  var shownWords = [];
  for (var i = 0; i < dictionary.content.words.length; i++) {
    var currentWord = dictionary.content.words[i];
    if (currentWord.dictionaryID != null) {
      shownWords.push(currentWord);
    }
  }
  return shownWords;
}

function addFlied() {
  dictionary.content.words.push({
    eng: "",
    ger: "",
    op: "",
    dictionaryID: dictionary.content.id,
  });
}

function setWordHash() {
  sha512(JSON.stringify(dictionary.content.words)).then((hash) => {
    lastWordHash = hash;
  });
}

function showMessage(content, isSuccess) {
  show.content = content;
  show.success = isSuccess;
  setTimeout(function () {
    window.location = "/";
  }, 5000);
}

function showItem(item) {
  return (
    item !== null &&
    item.op !== null &&
    typeof item.op !== "undefined" &&
    item.op != ""
  );
}

function cancel() {
  show.edit = false;
  sendRequest();
}

function edit() {
  show.edit = true;
}

function isYours() {
  return dictionary.content.user.id == localStorage.getItem("id");
}
</script>

<style scoped>
.dictionary-wrapped {
  margin: 0 auto 8rem;
  max-width: 60rem;
}
.dictionary {
  border: solid black 1px;
  border-radius: 10px 5px 10px 5px;
  max-width: 98%;
  margin: 4rem auto;
  text-align: center;
}
.dictionary a {
  font-size: 1rem;
}
.header {
  margin-top: 5px;
}
input:focus {
  outline: none;
  box-shadow: none;
}
.input-group {
  margin: 5px;
  width: unset;
}
.form-control {
  background-color: unset;
}
.words {
  margin-bottom: 1.4rem;
}
.sub-title {
  margin-bottom: 1.4rem;
}
.avatar {
  height: 45px;
  border: solid 0;
  border-radius: 5px;
  margin-left: 5px;
}
.update-button {
  margin-top: 0.5rem;
  margin-left: 0.8rem;
}
.spinner-border {
  margin: auto;
  margin-top: 0.5rem;
  margin-bottom: 3rem;
}
.dropdown-menu {
  min-width: 2rem;
  margin: 2px 0px 0px -16px !important;
}
.group-item {
  margin: 0 auto;
}
.mobile {
  display: none;
  color: unset;
  text-decoration: unset;
}
.desktop {
  color: unset;
  text-decoration: unset;
}
.desktop :hover {
  color: unset;
  text-decoration: unset;
}
.group-item {
  margin-right: 0.5rem;
  margin-left: 0.3rem;
}
/* Triggered on boostrap md breakpoint */
@media (max-width: 768px) {
  .mobile {
    display: unset;
    margin-bottom: 1rem;
  }
  .desktop {
    display: none;
  }
  .dictionary-wrapped {
    max-width: 97vw;
    margin-bottom: 5rem;
  }
  .dictionary {
    margin-top: 2rem;
    margin-bottom: 2rem;
  }
}
</style>