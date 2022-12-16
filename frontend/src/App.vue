<template>
  <div class="wrapper">
    <!-- Header -->
    <b-navbar toggleable="lg" type="dark" variant="dark" class="header">
      <b-navbar-brand to="/">
        <img src="/favicon.ico" class="d-inline-block align-top" />
        CRUD-VUEJS-SPRING-BOOT
      </b-navbar-brand>
    </b-navbar>
    <div class="main">
      <!-- Side Menu -->
      <b-nav vertical class="side-menu bg-light">
        <b-nav-item :active="$route.name === 'projects'" to="/" active-class="text-secondary">Projects</b-nav-item>
        <b-nav-item :active="$route.name === 'tasks'" to="/tasks" active-class="text-secondary">Tasks</b-nav-item>
      </b-nav>
      <!-- Content Aria-->
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style>
.header {
  height: 50px;
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
}

.content {
  width: 100%;
}
</style>
