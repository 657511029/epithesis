<template>
  <div id="app">
<!--    <img src="./assets/logo.png">-->
    <div v-if="$route.meta.show">
      <header-nav></header-nav>
    </div>
    <router-view/>
  </div>

</template>

<script>
import header from './components/Header.vue'
export default {
  name: 'App',
  data () {
    return {
      headerShow: true
    }
  },
  components: {
    headerNav: header
  },
  // watch: {
  //   $route (to, from) {
  //     if (this.$route.path === '/LoginView' || this.$route.path === '/RegisterView') {
  //       this.headerShow = false
  //     } else {
  //       this.headerShow = true
  //     }
  //   }
  // },
  created () {
  },
  methods: {
  }
}
</script>

<style>
/*#app {*/
/*  font-family: 'Avenir', Helvetica, Arial, sans-serif;*/
/*  -webkit-font-smoothing: antialiased;*/
/*  -moz-osx-font-smoothing: grayscale;*/
/*  text-align: center;*/
/*  color: #2c3e50;*/
/*  margin-top: 60px;*/
/*}*/
page{
  background-color: #f6f6f6;
}

</style>
