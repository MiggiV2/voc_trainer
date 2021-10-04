<template>
  <div class="saved-wrapped">
    <div v-if="getCookie('access_token')">
      <div class="title">
        <h2>Your saved words!</h2>
      </div>
      <div v-if="saved.content.length == 0" class="spinner-border"></div>
      <div class="saved" v-for="(item, index) in saved.content" :key="index">
        <div
          class="saved-item"
          data-bs-toggle="modal"
          data-bs-target="#wordsModal"
          @click="setItem(item)"
        >
          <h2>{{ item.name }}</h2>
          <hr />
          <p>Click me to see your words.</p>
        </div>
      </div>
    </div>
    <div v-else>
      <div class="title">
        <h2>You need to login first!</h2>
      </div>
    </div>
  </div>

  <!--WordsModal-->
  <div
    class="modal fade"
    id="wordsModal"
    tabindex="-1"
    aria-labelledby="wordsLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title" id="wordsLabel">
            {{ saved.current.dictionaryName }}
          </h3>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div
            class="words"
            v-for="(item, index) in saved.current.words"
            :key="index"
          >
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
                v-if="showItem(item)"
                class="form-control"
                placeholder="(OPTIONAL) description, opposite, etc..."
                v-model="item.op"
              />
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-bs-dismiss="modal">
            Close
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

var saved = reactive({
  content: [],
  current: {
    dictionaryName: String,
    words: [],
  },
});

if (getCookie("access_token")) {
  load();
} else {
  setTimeout(function () {
    window.location = "/";
  }, 5000);
}

function load() {
  fetch(HOST + "api/get/special-word", {
    credentails: "same-origin",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + getCookie("access_token"),
    },
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.log(response.statusText);
      }
    })
    .then((savedResponse) => {
      saved.content = savedResponse;
    });
}

function showItem(item) {
  return (
    item !== null &&
    item.op !== null &&
    typeof item.op !== "undefined" &&
    item.op != ""
  );
}

function setItem(item) {
  saved.current.dictionaryName = item.name;
  saved.current.words = item.words;
  console.log(item.words.length);
}
</script>

<style scoped>
a {
  cursor: pointer;
}
a:link {
  color: unset;
  text-decoration: none;
}
a:visited {
  color: unset;
  text-decoration: none;
}
.saved-wrapped {
  margin-bottom: 4rem;
}
.saved {
  max-width: 50rem;
  margin: auto;
}
.title {
  margin: 2rem auto 3.5rem;
  text-align: center;
}
.saved-item {
  border: solid 1px black;
  border-radius: 5px 10px 5px 10px;
  padding: 15px;
  max-width: 98%;
  margin: 2rem auto 2rem;
  cursor: pointer;
}
.avatar {
  max-height: 40px;
  border-radius: 5px;
}
.spinner-border {
  margin-left: 45vw;
  margin-top: 2rem;
}
.words {
  margin-bottom: 1.4rem;
}
@media (max-width: 768px) {
  .title {
    margin-bottom: 2rem;
  }
}
</style>