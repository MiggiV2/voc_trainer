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
        <div class="mobile col"></div>
        <div class="mobile col-auto">
          <button
            id="show-button"
            class="btn btn-outline-primary top-buttons"
            data-bs-toggle="modal"
            data-bs-target="#helpWordModal"
          >
            <Eye />
          </button>
          <button
            title="Skip"
            @click="setRandomWord()"
            class="btn btn-danger top-buttons"
          >
            <Arrow_Right_Circle />
          </button>
        </div>
        <div class="col-md">
          <div class="your-word">
            <h5 v-if="anwser.show === 2">
              Your definition / synonym:
              <p>{{ dictionary.currentWord.op }}</p>
            </h5>
            <h5 v-else-if="anwser.show === 1">
              Your words is {{ dictionary.currentWord.ger }}
            </h5>
            <h5 v-else>Your words is {{ dictionary.currentWord.eng }}</h5>
          </div>
        </div>
        <div class="col-auto">
          <div class="desktop">
            <button
              id="show-button"
              class="btn btn-outline-primary top-buttons"
              data-bs-toggle="modal"
              data-bs-target="#helpWordModal"
            >
              <Eye />
            </button>
            <button
              title="Skip"
              @click="setRandomWord()"
              class="btn btn-danger top-buttons"
            >
              <Arrow_Right_Circle />
            </button>
          </div>
        </div>
      </div>
      <form @submit.prevent="check" onsubmit="return false">
        <div class="input-group submit-form">
          <input
            type="text"
            class="form-control"
            placeholder="Englisch word"
            v-model="anwser.input"
          />
          <button type="submit" class="btn btn-success">Answer</button>
        </div>
        <div class="buttons"></div>
      </form>
      <div v-if="anwser.isCorrect != null">
        <div v-if="anwser.isCorrect" class="feedback correct">
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
  <!--HelpModal-->
  <div
    class="modal fade"
    id="helpWordModal"
    tabindex="-1"
    aria-labelledby="helpWordModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="helpWordModalLabel">Word help</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <p>The word is: {{ getAnwser() }}</p>
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

import Bookmark from "./icons/Bookmark.vue";
import Arrow_Right_Circle from "./icons/Arrow_Right_Circle.vue";
import EmojiFrown from "./icons/EmojiFrown.vue";
import EmojiWink from "./icons/EmojiWink.vue";
import Eye from "./icons/Eye.vue";

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
  },
});

var anwser = reactive({
  input: "",
  show: 0,
  isCorrect: null,
  lastWordIndex: null,
});

var startedHideThread = 0;

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
  var correctWord = getAnwser();
  var isCorrect = correctWord === anwser.input;
  if (isCorrect) {
    setTimeout(function () {
      setRandomWord();
    }, 800);
  }
  anwser.isCorrect = isCorrect;
  startedHideThread = new Date();
  var time = startedHideThread;
  setTimeout(function () {
    if (time === startedHideThread) {
      anwser.isCorrect = null;
    } else {
      console.log("Other thread stated!");
    }
  }, 2500);
}

function getAnwser() {
  if (anwser.show === 2) {
    return dictionary.currentWord.eng;
  } else if (anwser.show === 1) {
    return dictionary.currentWord.eng;
  } else {
    return dictionary.currentWord.ger;
  }
}

function setRandomWord() {
  var randomIndex = Math.floor(Math.random() * dictionary.content.words.length);
  if (
    anwser.lastWordIndex !== null &&
    anwser.lastWordIndex === randomIndex &&
    dictionary.content.words.length > 1
  ) {
    console.log("Is same word -> new...");
    setRandomWord();
  } else {
    anwser.lastWordIndex = randomIndex;
    anwser.input = "";
    var maxStatus = dictionary.currentWord.op === null ? 2 : 3;
    dictionary.currentWord = dictionary.content.words[randomIndex];
    anwser.show = Math.floor(Math.random() * maxStatus);
    console.log(anwser.show);
  }
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
  margin-bottom: 1rem;
}
.top-buttons {
  margin-top: -10px;
}
form {
  margin-bottom: 2rem;
  margin-top: 2rem;
}
input:focus {
  outline: none;
  box-shadow: none;
}
.feedback {
  max-width: 10rem;
  padding: 2px;
  margin: auto;
  margin-bottom: 2rem;
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
#show-button {
  margin-right: 0.5rem;
}
.mobile {
  display: none;
}
/* Triggered on boostrap md breakpoint */
@media (max-width: 768px) {
  .mobile {
    display: unset;
    margin-bottom: 2rem;
  }
  .desktop {
    display: none;
  }
  .wrapped-box{
    max-width: 99vw;
  }
  .submit-form {
    min-width: 85vw;
  }
}
</style>