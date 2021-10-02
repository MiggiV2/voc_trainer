<template>
  <div class="wrapped">
    <div v-if="!show.success && !show.error" class="container box">
      <form @submit.prevent="save" onsubmit="return false">
        <h3 v-if="isEmpty(dictionary.name)" id="title">Add a new dictionary</h3>
        <h3 v-else id="title">Add {{ dictionary.name }}</h3>
        <div class="form-group">
          <input
            type="text"
            class="form-control name"
            placeholder="Enter a dictionary name"
            v-model="dictionary.name"
          />
        </div>
        <div v-for="(word, index) in dictionary.words" :key="index">
          <div class="word" v-if="isUndefined(word)">
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                placeholder="Englisch word"
                v-model="word.eng"
              />
              <input
                type="text"
                class="form-control"
                placeholder="German word"
                v-model="word.ger"
              />
            </div>
            <div class="input-group">
              <input
                type="text"
                class="form-control name"
                placeholder="(OPTIONAL) description, opposite, etc..."
                v-model="word.op"
              />
              <div
                v-if="index == dictionary.words.length - 1"
                class="btn btn-primary"
                @click="addFlied()"
              >
                <Plus />
              </div>
              <div v-else class="btn btn-danger" @click="removeFiled(index)">
                <Dash />
              </div>
            </div>
          </div>
        </div>
        <button
          v-if="isEmpty(dictionary.name)"
          type="submit"
          class="btn btn-primary submit disabled"
        >
          Save
          <SdCard />
        </button>
        <button v-else type="submit" class="btn btn-primary submit">
          {{ button.content }}
          <SdCard />
        </button>
      </form>
    </div>
  </div>
  <Toast
    v-if="show.success || show.error"
    :content="show.content"
    :success="show.success"
  />
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";
import { getCookie } from "../tools/cookie";
import Toast from "./SuccessToast.vue";
import Plus from "./icons/Plus.vue";
import Dash from "./icons/Dash.vue";
import SdCard from "./icons/SdCard.vue";

var dictionary = reactive({
  name: null,
  words: [],
});

var button = reactive({
  content: "Save",
});

var show = reactive({
  success: false,
  error: false,
  content: "",
});

addFlied();

function shouldAddField() {
  if (dictionary.words.length === 0) {
    return true;
  }
  var lastWord = dictionary.words[dictionary.words.length - 1];
  if (!isEmpty(lastWord.eng) && !isEmpty(lastWord.ger)) {
    return true;
  }
}

function isUndefined(word) {
  return typeof word !== "undefined";
}

function removeFiled(index) {
  delete dictionary.words[index];
}

function addFlied() {
  if (!shouldAddField()) {
    return;
  }
  dictionary.words.push({
    eng: "",
    ger: "",
    op: "",
  });
}

function save() {
  button.content = "...";
  fetch(HOST + "api/save", {
    method: "PUT",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(dictionary),
    headers: {
      Authorization: "Bearer " + getCookie("access_token"),
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (response.ok) {
        show.content = "Successfully saved!";
        show.success = true;
        hideHint(5000, true);
      } else {
        show.content = "Something went wrong! " + response.statusText;
        show.error = true;
        hideHint(5000, true);
      }
    })
    .catch((e) => {
      show.content = "Something went wrong! " + e;
      show.error = true;
      hideHint(5000, false);
    });
}

function hideHint(time, redirect) {
  setTimeout(function () {
    if (redirect) {
      window.location = "/";
    }
    show.error = false;
    show.success = false;
  }, time);
}

function isEmpty(_string1) {
  return _string1 == null || _string1.length === 0 || !_string1.trim();
}
</script>

<style scoped>
#title {
  margin-bottom: 1.5rem;
}
.word {
  margin-bottom: 1rem;
}
.submit {
  margin-top: 1rem;
}
.form-group {
  max-width: 50rem;
  margin: auto;
}
.input-group {
  max-width: 50rem;
  margin: auto;
}
.form-group {
  margin-bottom: 1rem;
}
.wrapped {
  max-width: 60rem;
  margin: 4rem auto 8rem;
}
.container {
  text-align: center;
  margin: auto;
  max-width: 98%;
}
input:focus {
  outline: none;
  box-shadow: none;
}
.box {
  border: solid 1px black;
  border-radius: 5px 10px 5px 10px;
  padding: 15px;
}
</style>