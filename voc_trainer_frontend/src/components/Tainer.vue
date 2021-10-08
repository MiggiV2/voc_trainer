<template>
  <div class="wrapped-box" v-if="status.showTainer">
    <div class="box">
      <h3 class="header">Dictionary {{ dictionary.content.name }}!</h3>
      <hr />
      <div class="row">
        <div class="col-auto">
          <button
            title="Save this word"
            class="btn btn-primary top-buttons"
            @click="addSpecialWord()"
            v-if="
              !dictionary.currentWord.specialWord && getCookie('access_token')
            "
          >
            <Bookmark />
          </button>
          <button
            title="Save this word"
            class="btn btn-primary top-buttons"
            @click="removeSpecialWord()"
            v-else-if="getCookie('access_token')"
          >
            <BookmarkCheckFill />
          </button>
          <button
            class="btn btn-outline-primary top-buttons show-button"
            data-bs-toggle="modal"
            data-bs-target="#helpWordModal"
          >
            <Eye />
          </button>
        </div>
        <div class="mobile col"></div>
        <div class="mobile col-auto">
          <button
            class="btn btn-primary top-buttons settings-button"
            data-bs-toggle="modal"
            data-bs-target="#settingsdModal"
          >
            <Gear />
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
              class="btn btn-primary top-buttons settings-button"
              data-bs-toggle="modal"
              data-bs-target="#settingsdModal"
              @click="setCheck()"
            >
              <Gear />
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
            autofocus
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
  <!--Settings-Modal-->
  <div
    class="modal fade"
    id="settingsdModal"
    tabindex="-1"
    aria-labelledby="settingsdModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="settingsdModalLabel">Settings</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <p>Ask Settings:</p>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="flexRadioDefault"
              id="flexRadioDefault2"
              @click="updateAskSettings(0)"
            />
            <label class="form-check-label" for="flexRadioDefault2">
              Only german words
            </label>
          </div>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="flexRadioDefault"
              id="flexRadioDefault1"
              @click="updateAskSettings(1)"
            />
            <label class="form-check-label" for="flexRadioDefault1">
              Only englisch words
            </label>
          </div>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="flexRadioDefault"
              id="flexRadioDefault3"
              @click="updateAskSettings(2)"
            />
            <label class="form-check-label" for="flexRadioDefault3">
              Ask both &amp; definition / synonym
            </label>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-bs-dismiss="modal">
            Save
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

import Bookmark from "./icons/Bookmark.vue";
import BookmarkCheckFill from "./icons/BookmarkCheckFill.vue";
import Arrow_Right_Circle from "./icons/Arrow_Right_Circle.vue";
import EmojiFrown from "./icons/EmojiFrown.vue";
import EmojiWink from "./icons/EmojiWink.vue";
import Eye from "./icons/Eye.vue";
import Gear from "./icons/Gear.vue";

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
    specialWord: false,
  },
});

var anwser = reactive({
  input: "",
  show: 0,
  isCorrect: null,
  lastWordIndex: null,
});

var settings = reactive({
  ask: 2,
});

var startedHideThread = 0;

if (localStorage.getItem("settings.ask")) {
  settings.ask = localStorage.getItem("settings.ask");
} else {
  localStorage.setItem("settings.ask", 2);
}
if (urlParams.has("id")) {
  loadDictionary();
}

function updateAskSettings(askStatus) {
  settings.ask = askStatus;
  localStorage.setItem("settings.ask", askStatus);
}

function setCheck() {
  if (settings.ask == 0) {
    document.getElementById("flexRadioDefault1").setAttribute("checked", true);
  } else if (settings.ask == 1) {
    document.getElementById("flexRadioDefault2").setAttribute("checked", true);
  } else {
    document.getElementById("flexRadioDefault3").setAttribute("checked", true);
  }
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
        setRandomWord(0);
      }
    });
}

function addSpecialWord() {
  var data = {
    wordID: dictionary.currentWord.id,
  };
  fetch(HOST + "api/add/special-word", {
    method: "PUT",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(data),
    headers: {
      Authorization: "Bearer " + getCookie("access_token"),
      "Content-Type": "application/json",
    },
  }).then((response) => {
    if (response.ok) {
      dictionary.currentWord.specialWord = true;
      var index = dictionary.currentWord.index;
      dictionary.content.words[index].specialWord = true;
    } else {
      alert(response.statusText);
    }
  });
}

function removeSpecialWord() {
  var data = {
    wordID: dictionary.currentWord.id,
  };
  fetch(HOST + "api/remove/special-word", {
    method: "PUT",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(data),
    headers: {
      Authorization: "Bearer " + getCookie("access_token"),
      "Content-Type": "application/json",
    },
  }).then((response) => {
    if (response.ok) {
      dictionary.currentWord.specialWord = false;
      var index = dictionary.currentWord.index;
      dictionary.content.words[index].specialWord = false;
    } else {
      alert(response.statusText);
    }
  });
}

function check() {
  var correctWord = getAnwser();
  var isCorrect = correctWord === anwser.input;
  if (isCorrect) {
    setTimeout(function () {
      setRandomWord(0);
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

function setRandomWord(counter) {
  var randomIndex = Math.floor(Math.random() * dictionary.content.words.length);
  if (
    anwser.lastWordIndex !== null &&
    anwser.lastWordIndex === randomIndex &&
    dictionary.content.words.length > 1 &&
    counter < 100
  ) {
    console.log("Is same word -> new...");
    setRandomWord(counter++);
  } else {
    anwser.lastWordIndex = randomIndex;
    anwser.input = "";
    dictionary.currentWord = dictionary.content.words[randomIndex];
    dictionary.currentWord.index = randomIndex;
    setShownStatus();
  }
}

function setShownStatus() {
  if (settings.ask == 0) {
    anwser.show = 0;
  } else if (settings.ask == 1) {
    anwser.show = 1;
  } else {
    var isOpEmpty =
      dictionary.currentWord.op === null ||
      dictionary.currentWord.op.length == 0;
    var maxStatus = isOpEmpty ? 2 : 3;
    anwser.show = Math.floor(Math.random() * maxStatus);
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
.settings-button {
  margin-right: 0.5rem;
}
.show-button {
  margin-left: 0.5rem;
}
.mobile {
  display: none;
}
/* Triggered on boostrap md breakpoint */
@media (max-width: 768px) {
  .mobile {
    display: unset;
    margin-bottom: 1.5rem;
  }
  .desktop {
    display: none;
  }
  .wrapped-box {
    max-width: 99vw;
  }
  .submit-form {
    min-width: 85vw;
  }
}
</style>