<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container row">
      <div class="col-auto">
        <a href="/" title="Home">
          <i class="bi bi-house-door" v-if="theme.mode == 'dark-mode'" width="20" height="20"/>
          <i v-else class="bi bi-house-door-fill" width="20" height="20"></i>
        </a>
      </div>
      <div v-if="getCookie('access_token')" class="col-auto">
        <a href="/train" title="Train a dictionary">
          <i class="bi bi-journal-bookmark-fill"></i>
        </a>
      </div>
      <div v-if="getCookie('access_token')" class="col-auto">
        <a href="/add" title="Add new dictionary">
          <i class="bi bi-plus-circle" v-if="theme.mode == 'dark-mode'" />
          <i class="bi bi-plus-circle-fill" v-else />
        </a>
      </div>
      <div v-else class="col-auto">
        <a v-if="theme.mode == 'dark-mode'" @click="setLightMode">
          <i class="bi bi-brightness-high-fill"></i>
        </a>
        <a v-else @click="setDarkMode">
          <i class="bi bi-moon-fill"></i>
        </a>
      </div>
      <div class="col"></div>
      <div class="col-auto">
        <a v-if="!getCookie('access_token')" @click="openLogin()">
          Login <i class="bi bi-box-arrow-in-right"></i>
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
            <i class="bi bi-person-circle" v-else />
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li>
              <a class="dropdown-item" href="/train" title="Train page">
                Train <i class="bi bi-journal-bookmark-fill icon-right"></i>
              </a>
            </li>
            <li>
              <a
                class="dropdown-item"
                href="/my-dictionarys"
                title="Show your dictionarys"
              >
                Yours <i class="bi bi-book icon-right"></i>
              </a>
            </li>
            <li>
              <a
                class="dropdown-item"
                href="/saved"
                title="Show your marked words"
              >
                Saved <i class="bi bi-bookmark-check icon-right"></i>
              </a>
            </li>
            <li v-if="theme.mode == 'dark-mode'">
              <a class="dropdown-item" @click="setLightMode">
                Lightmode <i class="bi bi-brightness-high-fill icon-right"></i>
              </a>
            </li>
            <li v-else>
              <a class="dropdown-item" @click="setDarkMode">
                Darkmode <i class="bi bi-moon-fill icon-right"></i>
              </a>
            </li>
            <li><hr class="dropdown-divider" /></li>
            <li>
              <a @click="logout()" class="dropdown-item text-danger" href="#">
                Logout <i class="bi bi-box-arrow-right icon-right"></i>
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
li:hover {
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