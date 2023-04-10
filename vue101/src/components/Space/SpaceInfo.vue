<template>
  <div>
    <div class="show">
      <p  style="margin-left:6%;margin-top: 5%">昵称:<span style="margin-left: 3%;line-height:40px">{{username}}</span></p>
      <p style="margin-left:6%;margin-top: 5%">性别:<span style="margin-left: 3%;line-height:40px">{{userSex}}</span></p>
      <p  style="margin-left:6%;margin-top: 5%">邮箱:<span style="margin-left: 3%;line-height:40px">{{userEmail}}</span></p>
      <p  style="margin-left:6%;margin-top: 5%">简介:<span style="margin-left: 3%;line-height:40px">{{userIntro}}</span></p>
    </div>
  </div>
</template>

<script>
export default {

  name: 'SpaceInfo',
  data () {
    return {
      username: '',
      userSex: '',
      userEmail: '',
      userIntro: ''
    }
  }
}
</script>

<style scoped>
.show{
  margin: 100px auto;
  width: 80%;
  height: 450px;
  border: 5px solid lightcyan;
  transition: all 0.9s;
  border-radius: 10px;

}

.show:hover{
  box-shadow: 0px 15px 30px rgba(0, 0, 0, 0.4);
  margin-top: 90px;
}
</style>
