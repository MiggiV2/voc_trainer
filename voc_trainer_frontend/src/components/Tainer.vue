<template>
  <div class="wrapped-box" v-if="status.showTainer">
    <div class="box">
      <h3 class="header">Dictionary {{ dictionary.content.name }}!</h3>
      <hr />
      <div class="row">
        <div class="col-auto">
          <button title="Save this word" class="btn btn-primary top-buttons">
            <Bookmark />
          </button>
        </div>
        <div class="col">
          <div class="your-word">
            <h5 v-if="dictionary.currentWord.showEng">
              Your words is {{ dictionary.currentWord.eng }}
            </h5>
            <h5 v-else>Your words is {{ dictionary.currentWord.ger }}</h5>
          </div>
        </div>
        <div class="col-auto">
          <button
            title="Skip"
            @click="setRandomWord()"
            class="btn btn-danger top-buttons"
          >
            <Arrow_Right_Circle />
          </button>
        </div>
      </div>
      <form @submit.prevent="check" onsubmit="return false">
        <div class="input-group submit-form">
          <input
            type="text"
            class="form-control"
            placeholder="Englisch word"
            v-model="dictionary.currentWord.input"
          />
          <button type="submit" class="btn btn-success">Answer</button>
        </div>
        <div class="buttons"></div>
      </form>
      <div v-if="dictionary.currentWord.isCorrect != null">
        <div v-if="dictionary.currentWord.isCorrect" class="feedback correct">
          <h3>
            Correct!
            <EmojiWink />
          </h3>
        </div>
        <div v-else class="feedback wrong">
          <h3>
            Wrong
            <EmojiFrown />
          </h3>
        </div>
      </div>
    </div>
  </div>
  <!--
    <button class="btn btn-outline-primary">
      Help
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="16"
        height="16"
        fill="currentColor"
        class="bi bi-question-circle"
        viewBox="0 0 16 16"
      >
        <path
          d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"
        />
        <path
          d="M5.255 5.786a.237.237 0 0 0 .241.247h.825c.138 0 .248-.113.266-.25.09-.656.54-1.134 1.342-1.134.686 0 1.314.343 1.314 1.168 0 .635-.374.927-.965 1.371-.673.489-1.206 1.06-1.168 1.987l.003.217a.25.25 0 0 0 .25.246h.811a.25.25 0 0 0 .25-.25v-.105c0-.718.273-.927 1.01-1.486.609-.463 1.244-.977 1.244-2.056 0-1.511-1.276-2.241-2.673-2.241-1.267 0-2.655.59-2.75 2.286zm1.557 5.763c0 .533.425.927 1.01.927.609 0 1.028-.394 1.028-.927 0-.552-.42-.94-1.029-.94-.584 0-1.009.388-1.009.94z"
        />
      </svg>
    </button>
  -->
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";

import Bookmark from "./icons/Bookmark.vue";
import Arrow_Right_Circle from "./icons/Arrow_Right_Circle.vue";
import EmojiFrown from "./icons/EmojiFrown.vue";
import EmojiWink from "./icons/EmojiWink.vue";

var urlParams = new URLSearchParams(window.location.search);

var status = reactive({
  showTainer: urlParams.has("id"),
});
var dictionary = reactive({
  content: {
    name: "...",
    id: urlParams.get("id"),
    words: [],
  },
  currentWord: {
    eng: "...",
    ger: "...",
    op: "...",
    input: "",
    showEng: true,
    isCorrect: null,
  },
  lastIndex: null,
});

if (urlParams.has("id")) {
  loadDictionary();
}

function loadDictionary() {
  fetch(HOST + "api/get/dictionary?id=" + urlParams.get("id"))
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        alert(response.statusText);
      }
    })
    .then((response) => {
      if (response != null) {
        dictionary.content = response;
        setRandomWord();
      }
    });
}

function check() {
  var anwser = dictionary.currentWord.showEng
    ? dictionary.currentWord.ger
    : dictionary.currentWord.eng;
  var isCorrect = anwser === dictionary.currentWord.input;
  console.log(isCorrect);
  if (isCorrect) {
    setRandomWord();
  }
  dictionary.currentWord.isCorrect = isCorrect;
  setTimeout(function () {
    dictionary.currentWord.isCorrect = null;
  }, 3000);
}

function setRandomWord() {
  var randomIndex = Math.floor(Math.random() * dictionary.content.words.length);
  if (
    dictionary.lastIndex !== null &&
    dictionary.lastIndex === randomIndex &&
    dictionary.content.words.length > 1
  ) {
    console.log("Is same!");
  }
  dictionary.currentWord.input = "";
  dictionary.currentWord = dictionary.content.words[randomIndex];
  dictionary.currentWord.showEng = Math.floor(Math.random() * 2) === 0;
}
</script>

<style scoped>
.wrapped-box {
  max-width: 64rem;
  margin: auto;
}
.box {
  border: solid black 1px;
  border-radius: 5px 10px 5px 10px;
  max-width: 98%;
  margin: 2rem auto;
  text-align: center;
  padding: 5px;
}
.submit-form {
  max-width: 60%;
  margin: auto;
  margin-bottom: 0.5rem;
}
.header {
  margin-top: 1rem;
  margin-bottom: 1rem;
}
.your-word {
  margin-bottom: 2rem;
}
.top-buttons {
  margin-top: -10px;
}
form {
  margin-bottom: 2rem;
}
input:focus {
  outline:none;
  box-shadow: none;
}
.feedback {
  max-width: 10rem;
  padding: 2px;
  margin: auto;
  border: solid 0 black;
  border-radius: 5px;
}
.correct {
  background-color: #0e9f5c;
  color: white;
}
.wrong {
  background-color: #dc3545;
  color: white;
}
</style>