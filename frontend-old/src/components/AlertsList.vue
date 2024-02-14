<template>
    <div class="wrapper">
        <b-alert v-for="alert in $store.state.alerts" dismissible show :variant="alert.variant" v-bind:key="alert.id" @dismissed="()=>closeAlert(alert.id)">{{alert.message}}</b-alert>
    </div>
</template>

<script>


export default {
    name: 'AlertsList',
    methods: {
        closeAlert(id){
            this.$store.dispatch('removeAlert', id)
        }
    }
}
</script>

<style scoped>
.wrapper {
    display: flex;
    position: fixed !important;
    left: 0;
    bottom: 0;
    width: 100%;
    flex-direction: column-reverse;
}
</style>