<template>
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
        <div class="word" v-if="typeof word !== 'undefined'">
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
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="22"
                height="22"
                fill="currentColor"
                class="bi bi-plus"
                viewBox="0 0 16 16"
              >
                <path
                  d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"
                />
              </svg>
            </div>
            <div v-else class="btn btn-danger" @click="removeFiled(index)">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="14"
                height="14"
                fill="currentColor"
                class="bi bi-dash-lg"
                viewBox="0 0 16 16"
              >
                <path
                  d="M0 8a1 1 0 0 1 1-1h14a1 1 0 1 1 0 2H1a1 1 0 0 1-1-1z"
                />
              </svg>
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
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          fill="currentColor"
          class="bi bi-sd-card-fill"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M12.5 0H5.914a1.5 1.5 0 0 0-1.06.44L2.439 2.853A1.5 1.5 0 0 0 2 3.914V14.5A1.5 1.5 0 0 0 3.5 16h9a1.5 1.5 0 0 0 1.5-1.5v-13A1.5 1.5 0 0 0 12.5 0zm-7 2.75a.75.75 0 0 1 .75.75v2a.75.75 0 0 1-1.5 0v-2a.75.75 0 0 1 .75-.75zm2 0a.75.75 0 0 1 .75.75v2a.75.75 0 0 1-1.5 0v-2a.75.75 0 0 1 .75-.75zm2.75.75a.75.75 0 0 0-1.5 0v2a.75.75 0 0 0 1.5 0v-2zm1.25-.75a.75.75 0 0 1 .75.75v2a.75.75 0 0 1-1.5 0v-2a.75.75 0 0 1 .75-.75z"
          />
        </svg>
      </button>
      <button v-else type="submit" class="btn btn-primary submit">
        Save
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          fill="currentColor"
          class="bi bi-sd-card-fill"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M12.5 0H5.914a1.5 1.5 0 0 0-1.06.44L2.439 2.853A1.5 1.5 0 0 0 2 3.914V14.5A1.5 1.5 0 0 0 3.5 16h9a1.5 1.5 0 0 0 1.5-1.5v-13A1.5 1.5 0 0 0 12.5 0zm-7 2.75a.75.75 0 0 1 .75.75v2a.75.75 0 0 1-1.5 0v-2a.75.75 0 0 1 .75-.75zm2 0a.75.75 0 0 1 .75.75v2a.75.75 0 0 1-1.5 0v-2a.75.75 0 0 1 .75-.75zm2.75.75a.75.75 0 0 0-1.5 0v2a.75.75 0 0 0 1.5 0v-2zm1.25-.75a.75.75 0 0 1 .75.75v2a.75.75 0 0 1-1.5 0v-2a.75.75 0 0 1 .75-.75z"
          />
        </svg>
      </button>
    </form>
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

var dictionary = reactive({
  name: null,
  words: [],
});

var show = reactive({
  success: false,
  error: false,
  content: "Test",
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

function removeFiled(index) {
  console.log(dictionary.words);
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
  fetch(HOST + "api/save", {
    method: "PUT",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify(dictionary),
    headers: {
      Authorization: "Bearer " + getCookie("access_token"),
      "Content-Type": "application/json",
    },
  }).then((response) => {
    if (response.ok) {
      show.content = "Successfully saved!";
      show.success = true;
      hideHint(5000,true);
    } else {
      show.content = "Something went wrong! " + response.statusText;
      show.error = true;
      hideHint(5000,true);
    }
  })
  .catch(e => {
    show.content = "Something went wrong! " + e;
      show.error = true;
      hideHint(5000,false);
  });
}

function hideHint(time, redirect) {
  setTimeout(function () {
    if(redirect)
    {
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
.container {
  margin-top: 4rem;
  text-align: center;
  max-width: 60rem;
}
input:focus {
  outline: none;
  box-shadow: none;
}
.box {
  border: solid 1px rgb(0, 113, 243);
  border-radius: 5px 10px 5px 10px;
  padding: 15px;
}
</style>