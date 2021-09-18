<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container row">
      <div class="col-auto">
        <a href="/">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-house-door-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5z"
            />
          </svg>
        </a>
      </div>
      <div v-if="appUser.avatarurl != null" class="col-auto">
        <a href="/add">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-plus-circle-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z"
            />
          </svg>
        </a>
      </div>
      <div class="col-auto">
        <a href="/search">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-search"
            viewBox="0 0 16 16"
          >
            <path
              d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"
            />
          </svg>
        </a>
      </div>
      <div class="col"></div>
      <div class="col-auto">
        <a v-if="appUser.avatarurl == null" @click="openLogin()">
          LOGIN
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-box-arrow-in-right"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M6 3.5a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-2a.5.5 0 0 0-1 0v2A1.5 1.5 0 0 0 6.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-8A1.5 1.5 0 0 0 5 3.5v2a.5.5 0 0 0 1 0v-2z"
            />
            <path
              fill-rule="evenodd"
              d="M11.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H1.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"
            />
          </svg>
        </a>
        <div v-else>
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <img class="avatar" :src="appUser.avatarurl" />
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="#">Comming soon!</a></li>
            <li><a class="dropdown-item" href="#">Comming soon!</a></li>
            <li><hr class="dropdown-divider" /></li>
            <li>
              <a @click="logout()" class="dropdown-item text-danger" href="#">
                Logout
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-box-arrow-right"
                  viewBox="0 0 16 16"
                >
                  <path
                    fill-rule="evenodd"
                    d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"
                  />
                  <path
                    fill-rule="evenodd"
                    d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"
                  />
                </svg>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
  <!-- DUMMY -->
  <nav class="dummy">
  </nav>
</template>

<script setup>
import { getAuthURL } from "../tools/auth";
import { loadUser, getAvatarURL } from "../tools/user";
import { getCookie, setCookie } from "../tools/cookie";
import { v4 as uuidv4 } from "uuid";
import { reactive } from "vue";

var appUser = reactive({
  username: String,
  avatarurl: null,
});

if (getCookie("access_token") && localStorage.getItem("avatar") === null) {
  var token = getCookie("access_token");
  loadUser(token, setUser);
}
if (getCookie("access_token") && localStorage.getItem("avatar") !== null) {
  setUser();
}

function setUser() {
  appUser.username = localStorage.getItem("username");
  appUser.avatarurl = getAvatarURL();
}

function logout() {
  localStorage.removeItem("username");
  localStorage.removeItem("avatar");
  localStorage.removeItem("id");
  setCookie("access_token", "", -1);
  window.location = "/";
}

function openLogin() {
  var url = getAuthURL();
  var state = uuidv4();
  localStorage.setItem("state", state);
  window.location = url + "&state=" + state;
}
</script>

<style scoped>
.container {
  max-width: 95vw;
  padding: 5px 20px 5px 20px;
  margin: auto;
}
.dummy{
  height: 80px;
}
.navbar {
  padding: unset;
  min-height: 80px;
  position: fixed;
  margin: 0 auto;
  min-width: 100vw;
  margin-top: 0;
  padding-top: 0;
  top: 0px;
  right: 0px;
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
div {
  font-family: "Courier New", Courier, monospace;
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
}
</style>