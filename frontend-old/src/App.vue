<template>
  <div class="wrapper">
    <!-- Header -->
    <b-navbar type="dark" variant="dark" class="header">
      <b-navbar-brand to="/">
        <img src="/favicon.ico"/>
        <span class="name">CRUD-VUEJS-SPRING-BOOT</span>
      </b-navbar-brand>
    </b-navbar>
    <div class="main">
      <!-- Side Menu -->
      <b-nav vertical class="side-menu bg-light">
        <b-nav-item :active="$route.name === 'projects'" to="/projects"
          active-class="text-secondary">Projects</b-nav-item>
        <b-nav-item :active="$route.name === 'about'" to="/about" active-class="text-secondary">About</b-nav-item>
      </b-nav>
      <!-- Content Aria-->
      <div class="content">
        <router-view />
      </div>
    </div>
    <!-- Alerts List-->
    <AlertsList />
  </div>
</template>

<script>
import AlertsList from "@/components/AlertsList.vue"
export default {
  name: 'App',
  components: {
    AlertsList
  }
}
</script>

<style>
.header {
  height: 50px;
  font-size: 14px;
}
.header img {
  height: 25px;
}
.header .name {
  font-size: 14px;
  margin-left: 10px;
}

.main {
  display: flex;
  height: calc(100vh - 50px);
}

.side-menu {
  width: 180px;
  padding-top: 15px;
  padding-left: 15px;
  align-content: center;
  border-right: 1px solid #DEE2E6;
}

.content {
  width: 100%;
}
</style>
