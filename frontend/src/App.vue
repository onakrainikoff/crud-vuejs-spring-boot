<template>
  <div class="wrapper">
    <!-- Header --> 
    <CNavbar color-scheme="light" class="bg-light header">
      <CContainer fluid>
        <CNavbarBrand href="#">
          <img src="/vite.svg" alt="" />
          <span class="name">CRUD-VUEJS-SPRING-BOOT</span>        
        </CNavbarBrand>
      </CContainer>
    </CNavbar>
    <div class="main">
      <!-- Side Menu -->
      <CNav class="flex-column side-menu">
        <CNavItem>
          <CNavLink href="#">
            Strored Objects
          </CNavLink>
        </CNavItem>
      </CNav>
      <!-- Content Aria-->
      <div class="content">
        <StoredObjectsPage/>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue'
  import StoredObjectsPage  from "./views/StoredObjectsPage.vue"

  export default defineComponent({
    name: 'App',
    components: {
      StoredObjectsPage
    }
  })

</script>

<style scoped>
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