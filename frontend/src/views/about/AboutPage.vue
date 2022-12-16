<template>
  <div class="wrapper">
    <!--Header block-->
    <div class="header-block">
      <b-breadcrumb class="header">
        <b-breadcrumb-item href="/">
          <b-icon icon="house-fill" scale="1.25" shift-v="1.25" aria-hidden="true"></b-icon>
          Main
        </b-breadcrumb-item>
        <b-breadcrumb-item active>Projects</b-breadcrumb-item>
      </b-breadcrumb>
    </div>

    <div class="text">
      This is a study project on working with BootStrap + VueJS + SpringBoot
    </div>
  </div>
</template>

<script>
export default {
  name: 'AboutPage',

  data() {
    return {}
  }
}
</script>

<style scoped>

.header-block {
  display: flex;
  justify-content: right;
  align-items: center;
  padding: 2px 0;
  background-color: #E9ECEF;
}
.header-block .header {
  width: 100%;
  margin: 0;
}

.text {
  font-size: 14px;
  padding: 25px;
}
</style>