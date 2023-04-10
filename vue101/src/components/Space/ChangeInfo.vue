<!--<template>-->
<!--<div>-->
<!--  这是更改个人信息-->
<!--</div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  name: 'ChangeInfo'-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->

<!--</style>-->
<template>
  <div class="game">
    <div class="game_item" v-for="(item,index) in gameList" :class="generateClassName(index)" :key="index">
      {{item}}
    </div>
  </div>
</template>

<script>
export default {
  name: 'game',
  data () {
    return {
      gameList: Array(40).fill(1)
    }
  },
  methods: {
    generateClassName (index) {
      // 调用方法，动态生成index
      return `game_item-${index}`
    }
  }
}
</script>
