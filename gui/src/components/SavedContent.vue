<template>
  <div class="saved-wrapped">
    <div v-if="getCookie('access_token')">
      <div v-if="saved.hasContent">
        <h2 class="title">{{ title.content }}</h2>
      </div>
      <div class="box-wrapped" v-else>
        <div class="box">
          <h2>{{ title.content }}</h2>
          <div>
            <hr />
            <p>You have to add some words first!</p>
          </div>
        </div>
      </div>
      <div
        v-if="saved.content.length == 0 && saved.hasContent"
        class="spinner-border"
      ></div>
      <div
        class="box-wrapped box-xl"
        v-for="(item, index) in saved.content"
        :key="index"
      >
        <div
          class="box"
          data-bs-toggle="modal"
          data-bs-target="#wordsModal"
          @click="setItem(item)"
        >
          <h2>{{ item.name }}</h2>
          <hr />
          <p>Click me to see your words.</p>
        </div>
      </div>
      <div class="box-wrapped" v-if="!saved.hasContent">
        <div class="box">
          <h3>How to save words?</h3>
          <hr />
          <p>
            Go to <a href="/train">train</a> and click
            <i class="bi bi-bookmark"></i>
          </p>
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
            <div v-if="item.specialWord">
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
                <div
                  class="btn btn-outline-danger"
                  @click="removeSpecialWord(item)"
                >
                  <i class="bi bi-trash-fill"></i>
                </div>
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

var title = reactive({
  content: "Your saved words...",
});

var saved = reactive({
  content: [],
  current: {
    dictionaryName: String,
    words: [],
  },
  hasContent: true,
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
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 204) {
        saved.hasContent = false;
        title.content = "No words saved!";
      } else {
        console.log(response.statusText);
      }
    })
    .then((savedResponse) => {
      if (savedResponse != null) {
        saved.content = savedResponse;
        title.content = "Your saved words!";
      }
    });
}

function removeSpecialWord(word) {
  word.specialWord = false;
  var data = {
    wordID: word.id,
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
    if (!response.ok) {
      alert(response.statusText);
      load();
    }
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
.text {
  text-align: center;
  font-size: 1.2rem;
}
.title {
  margin: 2rem auto 3.5rem;
  text-align: center;
  color: var(--text-primary-color);
}
.form-control {
  background-color: unset;
  color: var(--text-primary-color);
}
.title h2 {
  margin: 1rem;
}
.box h2 {
  text-align: unset;
}
.avatar {
  max-height: 40px;
  border-radius: 5px;
}
.spinner-border {
  margin-left: 49vw;
  margin-top: 2rem;
}
.box a:link {
  color: #0a58ca;
}
.box-wrapped {
  text-align: center;
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