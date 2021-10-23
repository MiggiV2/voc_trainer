<template>
  <!--:class="{ 'dark-theme': theme == 'dark' }"-->
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container row">
      <div class="col-auto">
        <a href="/" title="Home">
          <HouseOutline
            width="21"
            height="21"
            v-if="theme.mode == 'dark-mode'"
          />
          <House v-else />
        </a>
      </div>
      <div v-if="getCookie('access_token')" class="col-auto">
        <a href="/train" title="Train a dictionary">
          <Journal />
        </a>
      </div>
      <div v-if="getCookie('access_token')" class="col-auto">
        <a href="/add" title="Add new dictionary">
          <AddOutline width="20" height="20" v-if="theme.mode == 'dark-mode'" />
          <Add v-else />
        </a>
      </div>
      <div v-else class="col-auto">
        <a v-if="theme.mode == 'dark-mode'" @click="setLightMode">
          <Sun />
        </a>
        <a v-else @click="setDarkMode">
          <Moon />
        </a>
      </div>
      <div class="col"></div>
      <div class="col-auto">
        <a v-if="!getCookie('access_token')" @click="openLogin()">
          Login <Login />
        </a>
        <div v-else>
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            title="You, xD"
          >
            <img
              v-if="appUser.avatarurl != null"
              class="avatar"
              :src="appUser.avatarurl"
            />
            <Person v-else />
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li>
              <a class="dropdown-item" href="/train" title="Train page">
                Train <Journal class="icon-right" width="17" height="17"
              /></a>
            </li>
            <li>
              <a
                class="dropdown-item"
                href="/my-dictionarys"
                title="Show your dictionarys"
              >
                Yours <Book class="icon-right" />
              </a>
            </li>
            <li>
              <a
                class="dropdown-item"
                href="/saved"
                title="Show your marked words"
              >
                Saved <BookmarkCheckFill class="icon-right" />
              </a>
            </li>
            <li v-if="theme.mode == 'dark-mode'">
              <a class="dropdown-item" @click="setLightMode">
                Lightmode <Sun class="icon-right" />
              </a>
            </li>
            <li v-else>
              <a class="dropdown-item" @click="setDarkMode">
                Darkmode <Moon class="icon-right" />
              </a>
            </li>
            <li><hr class="dropdown-divider" /></li>
            <li>
              <a @click="logout()" class="dropdown-item text-danger" href="#">
                Logout <Logout class="icon-right" />
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
  <!-- DUMMY -->
  <nav class="dummy"></nav>
</template>

<script setup>
import { getAuthURL } from "../tools/auth";
import { loadUser, getAvatarURL } from "../tools/user";
import { getCookie, setCookie, setCookieSeasson } from "../tools/cookie";
import { v4 as uuidv4 } from "uuid";
import { reactive } from "vue";

import House from "./icons/House.vue";
import HouseOutline from "./icons/HouseOutline.vue";
import Add from "./icons/Add.vue";
import AddOutline from "./icons/AddOutline.vue";
import Journal from "./icons/Journal.vue";
import Login from "./icons/Login.vue";
import Logout from "./icons/Logout.vue";
import Person from "./icons/Person.vue";
import Book from "./icons/Book.vue";
import BookmarkCheckFill from "./icons/BookmarkCheckFill.vue";
import Moon from "./icons/Moon.vue";
import Sun from "./icons/Sun.vue";

var appUser = reactive({
  username: String,
  avatarurl: null,
});

var theme = reactive({
  mode: localStorage.getItem("theme"),
});

if (getCookie("access_token") && !getCookie("season_id")) {
  var token = getCookie("access_token");
  setTimeout(function () {
    loadUser(token, setUser);
  }, 500);
}
if (getCookie("access_token") && getCookie("season_id")) {
  setUser();
}

function setDarkMode() {
  localStorage.setItem("theme", "dark-mode");
  document.documentElement.className = "dark-mode";
  theme.mode = "dark-mode";
  console.log(theme.mode);
}

function setLightMode() {
  localStorage.setItem("theme", "light-mode");
  document.documentElement.className = "light-mode";
  theme.mode = "light-mode";
  console.log(theme.mode);
}

function setUser() {
  appUser.username = localStorage.getItem("username");
  appUser.avatarurl = getAvatarURL();
  setCookieSeasson("season_id", uuidv4());
}

function logout() {
  localStorage.removeItem("username");
  localStorage.removeItem("avatar");
  localStorage.removeItem("id");
  setCookie("access_token", "", -1);
  setCookie("season_id", "", -1);
  window.location = "/";
}

function openLogin() {
  var url = getAuthURL();
  var state = uuidv4();
  localStorage.setItem("state", state);
  setCookie("season_id", "", -1);
  window.location = url + "&state=" + state;
}
</script>

<style scoped>
.container {
  max-width: 95vw;
  margin: auto;
}
.dummy {
  height: 57px;
}
.navbar {
  padding: unset;
  min-height: 55px;
  position: fixed;
  margin: 0 auto;
  min-width: 100vw;
  margin-top: 0;
  padding-top: 0;
  top: 0px;
  right: 0px;
  left: 0px;
  z-index: 100;
  border-bottom: 1px black solid;
  background-color: var(--background-color-secondary) !important;
  color: var(--text-primary-color);
}
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
li:hover{
  color: var(--accent-color-2);
}
.avatar {
  max-height: 40px;
  border-radius: 5px;
}
.dropdown-menu.show {
  position: fixed;
  z-index: 5;
  top: unset;
  left: unset;
  right: 5px;
}
.icon-right {
  right: 1.2rem;
  position: absolute;
}
@media (max-width: 768px) {
  .container {
    max-width: 100vw;
    margin: auto;
    padding: unset;
    padding-left: 10px;
  }
}
</style>