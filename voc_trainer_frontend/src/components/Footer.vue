<template>
  <div class="footer-wrapped">
    <div class="row footer">
      <div class="col-auto desktop">
        <Discord />
      </div>
      <div class="col-auto desktop">
        <a :href="botURL">Discord-Bot</a>
      </div>
      <div class="col-auto desktop">
        <a :href="serverURL">Discord-Server</a>
        </div>
      <div class="col"></div>
      <div class="col-auto">
        <a :href="ownerURL"><Cup /> Made by Miggi#9895</a>
        </div>
    </div>
  </div>
</template>

<script setup>
import Discord from "./icons/Discord.vue";
import Cup from "./icons/Cup.vue";

var serverURL = "https://discord.gg/aSJJwn9";
var ownerURL = "https://discord.com/channels/@me/309696934174785556";
var botURL =
  "https://discord.com/oauth2/authorize?client_id=732152444787753002&scope=bot&permissions=0";
</script>

<style scoped>
.footer-wrapped {
  min-width: 100vw;
  background-color: #f0f0f0;
  position: fixed;
  bottom: 0;
  height: 35px;
  z-index: 100;
}
.footer {
  padding: 6px;
  max-width: 90vw;
  margin-left: 5vw;
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
@media (max-width: 768px) {
  .desktop {
    display: none;
  }
}
</style>