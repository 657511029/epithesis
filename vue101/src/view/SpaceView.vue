<template>
 <div class="space-wrap">
   <div style="background-color: white" class="space-container">

     <el-container style="height: 800px; border: 1px solid #eee;">

       <el-aside width="200px" style="margin-top: 1%" >
         <div style="width: 190px;background-color: white;padding: 5px;margin: auto">
           <img  v-bind:src="user.userPic" width="150px" height="150px" style="border-radius: 300px;margin-left: 8%">
           <br>
           <br>
           <p style="margin:0 auto;text-align: center" >{{user.username}}</p>
         </div>

         <br>
         <br>
         <el-menu :default-openeds="['1','2']" :default-active="activeMenu" @select="handleSelect">
           <el-submenu index="1" >
             <template slot="title"><i class="el-icon-message"></i>用户中心</template>
             <el-menu-item-group >
               <span class="alink" @click="toSpaceInfo"><el-menu-item index="1-1">个人信息</el-menu-item></span>
               <span class="alink" @click="toChangePic"><el-menu-item index="1-2">修改头像</el-menu-item></span>
               <span class="alink" @click="toChangeInfo"><el-menu-item index="1-3">修改信息</el-menu-item></span>
             </el-menu-item-group>

           </el-submenu>
           <el-submenu index="2">
             <template slot="title"><i class="el-icon-menu"></i>课程管理</template>
             <el-menu-item-group>
               <span class="alink" @click="toCourseInfo"><el-menu-item index="2-1">已选课程</el-menu-item></span>
             </el-menu-item-group>
           </el-submenu>
         </el-menu>
       </el-aside>

       <el-container style="background-color: white">
         <el-main>
           <component :is = "componentNext"></component>
           <router-view/>
         </el-main>
       </el-container>

     </el-container>

   </div>
 </div>
</template>

<script>
import SpaceInfo from '../components/Space/SpaceInfo.vue'
import ChangeInfo from '../components/Space/ChangeInfo.vue'
import ChangePic from '../components/Space/ChangePic.vue'
import CourseInfo from '../components/Space/CourseInfo.vue'
export default {
  name: 'SpaceView',
  components: {
    SpaceInfo,
    ChangeInfo,
    ChangePic,
    CourseInfo
  },
  data () {
    return {
      user: {
        userPic: '',
        username: ''
      },
      componentNext: 'SpaceInfo',
      activeMenu: '1-1'
    }
  },
  mounted () {
    this.username = sessionStorage.getItem('username')
    this.getAvatarUrl()
  },
  updated () {
    this.username = sessionStorage.getItem('username')
  },
  methods: {
    toSpaceInfo: function () {
      this.componentNext = 'SpaceInfo'
    },
    toChangeInfo: function () {
      this.componentNext = 'ChangeInfo'
    },
    toChangePic: function () {
      this.componentNext = 'ChangePic'
    },
    toCourseInfo: function () {
      this.componentNext = 'CourseInfo'
    },
    handleSelect (index) {
      this.activeMenu = index
    },
    getAvatarUrl: function () {
      this.$axios.post('http://localhost:8080/api/lookUserInfo', {
        'id': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.user.userPic = resp.data.user.avatarUrl
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取个人信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          }
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response)
            let message = error.response.data.message
            this.$message({
              message: '获取个人信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    }
    // 关闭
  }

}
</script>

<style scoped>
.space-wrap{
  background-color: #f6f6f6;
  height: 100vh;
  padding: 30px 100px;
  width: 1158px;
}
/*.el-header {*/
/*  background-color: #B3C0D1;*/
/*  color: #333;*/
/*  line-height: 60px;*/
/*}*/

/*.el-aside {*/
/*  color: #333;*/
/*}*/
.alink{
  color: white;
}
</style>
