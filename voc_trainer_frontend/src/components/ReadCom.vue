<template>
  <div v-if="show.content === null" class="dictionary">
    <div class="row header" v-if="dictionary.content.user != null">
      <div class="col-1">
        <div v-if="isYours()">
          <button class="btn btn-danger" @click="sendDelete()">
            <Trash />
          </button>
        </div>
      </div>
      <div class="col">
        <h3 class="title">
          {{ dictionary.content.name }}
          <a> by {{ dictionary.content.user.name }}</a>
        </h3>
      </div>
      <div class="col-1">
        <img
          class="avatar"
          v-if="dictionary.content.user.avatarURL != null"
          :src="dictionary.content.user.avatarURL"
          :title="dictionary.content.user.name"
        />
      </div>
    </div>
    <div class="sub-title">
      <a>Englisch &lt;--&gt; German</a>
    </div>
    <div
      class="words"
      v-for="(item, index) in dictionary.content.words"
      :key="index"
    >
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
  <Toast
    v-if="show.content !== null"
    :content="show.content"
    :success="show.success"
  />
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";
import { getAvatarURLFromUser } from "../tools/user";
import Toast from "./SuccessToast.vue";

import Trash from "./icons/Trash.vue";

var urlParams = new URLSearchParams(window.location.search);
var dictionary = reactive({
  content: {
    name: "...",
    id: urlParams.get("id"),
    user: null,
    words: [
      {
        eng: "...",
        ger: "...",
      },
    ],
  },
});
var show = reactive({
  success: false,
  content: null,
});

if (dictionary.content.id === null || dictionary.content.id.length < 30) {
  console.log("Need ?id=UUID");
} else {
  sendRequest();
}

function sendRequest() {
  fetch(HOST + "api/get/dictionary/" + dictionary.content.id, {
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
        }
      }
    })
    .catch((e) => {
      showMessage(e, false);
      console.log(e);
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

function sendDelete() {
  var dictionaryCopy = dictionary.content;
  dictionaryCopy.words = [];
  fetch(HOST + "api/delete", {
    method: "DELETE",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(dictionaryCopy),
    headers: {
      "Content-Type": "application/json",
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
    .catch();
  {
    showMessage("Something went wrong!", false);
  }
}

function isYours() {
  return dictionary.content.user.id == localStorage.getItem("id");
}
</script>

<style scoped>
.dictionary {
  border: solid black 1px;
  border-radius: 10px 5px 10px 5px;
  max-width: 60rem;
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
}
</style>