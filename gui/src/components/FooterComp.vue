<template>
  <!--:class="{ 'dark-theme': theme == 'dark' }"-->
  <div class="footer-wrapped desktop">
    <div class="row align-items-center footer">
      <div class="col-auto">
        <i class="bi bi-discord"></i>
      </div>
      <div class="col-auto">
        <a :href="botURL">Discord-Bot</a>
      </div>
      <div class="col-auto">
        <a :href="serverURL">Discord-Server</a>
      </div>
      <div class="col-auto">
        <a :href="gitURL"><i class="bi bi-github"></i></a>
      </div>
      <div class="col"></div>
      <div class="col-auto">
        <a :href="ownerURL"><i class="bi bi-cup-fill"></i> Made by Miggi#9895</a>
      </div>
    </div>
  </div>
</template>

<script setup>
var serverURL = "https://discord.gg/aSJJwn9";
var ownerURL = "https://discord.com/channels/@me/309696934174785556";
var gitURL = "https://github.com/MiggiV2/voc_trainer";
var botURL =
  "https://discord.com/oauth2/authorize?client_id=732152444787753002&permissions=0&scope=bot%20applications.commands";
</script>

<style scoped>
.footer-wrapped {
  background-color: var(--background-color-secondary) !important;
  color: var(--text-primary-color);
  border-top: 1px var(--accent-color-2) solid;
  min-width: 100vw;  
  position: fixed;
  bottom: 0;
  height: 35px;
  z-index: 100;  
}
.footer {
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