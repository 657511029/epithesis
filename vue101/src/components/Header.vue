<template>
  <div>
    <header class="header">
      <div class="header-logo">
        Practical Teaching
      </div>
      <div class="header-search">
        <el-form class="search-container">
          <el-input class="search-container-input" type="text" placeholder="请输入搜索内容" v-model="search" auto-complete="off" :prefix-icon="Search"></el-input>
          <el-button class="search-container-button" type="primary" @click="doSearch">搜索</el-button>
        </el-form>
      </div>
      <ul class="header-list">
        <li>
          <a :class="{ active: '/' === $route.path }" href="/">
              <span>
                首页
              </span>
          </a>
        </li>
        <li>
          <a :class="{ active: '/DiscussView' === $route.path }">
            讨论区
          </a>
        </li>
        <li>
          <a :class="{ active: '/ConcernView' === $route.path }">
            关于
          </a>
        </li>
        <li>
          <el-dropdown v-if="ifLogin" >
            <span class='el-dropdown-link' :class="{ active: '/SpaceView' === $route.path }">{{username}}<i class='el-icon-arrow-down el-icon--right'></i></span>
            <el-dropdown-menu slot='dropdown'>
              <el-dropdown-item icon='el-icon-view' style="width: 80px;">
                <a href="/SpaceView"  style="text-decoration: none; color: #505458;">
                  个人中心
                </a>
              </el-dropdown-item>
              <el-dropdown-item icon='el-icon-search'  style="width: 80px;">
                <span href="#"  @click="loginOut" style="text-decoration: none; color: #505458;">
                  登出
                </span>
              </el-dropdown-item>
              <el-dropdown-item icon='el-icon-delete'  style="width: 80px;">
                <span href="#"  @click="deleteUser" style="text-decoration: none; color: #505458;">
                  注销
                </span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <!--          <a v-if="ifLogin" :class="{ active: '/SpaceView' === $route.path }" href="/SpaceView">-->
          <!--            个人中心-->
          <!--          </a>-->
          <a v-else :class="{ active: '/SpaceView' === $route.path }" href="/SpaceView">
            登录
          </a>
        </li>
      </ul>
    </header>
    <el-dialog
      title="修改个人信息"
      width="400px"
      :visible.sync="deleteVisible"
      :before-close="handleDeleteClose"
    >
      <el-form  >
        <div class="updateInfo" style="width: 100%">
            <el-form-item  prop="message">
              <span>您的账号将会被永久注销，所有信息将不可恢复，请确定注销账号</span>
            </el-form-item>
      <div slot="footer"  class="dialog-footer" style="padding: 0 100px">
    <el-button @click="submit" >确认</el-button>
    <el-button type="primary" @click="handleDeleteClose">取消</el-button>

  </div>
        </div>
      </el-form>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: 'Header',
  data: function () {
    return {
      deleteVisible: false,
      search: '',
      ifLogin: false,
      username: ''
    }
  },
  mounted () {
    this.ifLogin = !!sessionStorage.getItem('isLogin')
    // !!sessionStorage.getItem('token')
    this.username = sessionStorage.getItem('username')
  },
  updated () {
    this.ifLogin = !!sessionStorage.getItem('isLogin')
    // !!sessionStorage.getItem('token')
    this.username = sessionStorage.getItem('username')
  },
  methods: {
    deleteUser: function () {
      this.deleteVisible = true
    },
    handleDeleteClose: function () {
      this.deleteVisible = false
    },
    submit: function () {
      this.$axios.post('http://localhost:8080/api/deleteUser', {
        'userId': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.loginOut()
          } else {
            let message = resp.data.message
            this.$message({
              message: '注销失败! ' + message,
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
              message: '注销失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    doSearch: function () {
      this.$router.push({name: 'SearchView', query: {content: this.search}})
      window.location.reload()
    },
    loginOut: function () {
      sessionStorage.removeItem('username')
      sessionStorage.removeItem('isLogin')
      // if (this.$route.path === '/') {
      //   window.location.reload()
      // } else {
      //   this.$router.push('/')
      // }
      this.$router.push('/')
      window.location.reload()
    }
  }

}
</script>

<style scoped>
.updateInfo {
  overflow: auto;
}
.header{
  display: flex;
  padding: 0;
  height: 50px;
  min-width: 1350px;
  position:relative;
}
.header-logo{
  text-align: left;
  line-height: 50px;
  width: 300px;
  color: #c2c0c0;
}
.header-search{
  padding-top: 5px;
  width: 700px;
}
.search-container{
  display: flex;
}
.search-container-input{
  width: 500px;
  height: 30px;
  border-radius: 10px;
  margin-right: 20px;
  background: #fff;
  border: 1px solid #eaeaea;
}
.search-container-button{
  width: 100px;
  height: 40px;
  border-radius: 10px;
  border: 1px solid #eaeaea;
}
.header-list{
  margin: 0;
  padding: 0;
}
.header-list li{
  display: inline-block;
  padding: 15px 10px;
  list-style: none;
  cursor: pointer;
}
.header-list li a{
  text-decoration: none;
  color: #505458;
}
.active{
  color: #505458;
  text-decoration: none;
  border-bottom: 2px solid #505458;
}
.header-list li a:hover{
  text-decoration: none;
  color: #c2c0c0;
}
.el-dropdown-link{
  font-size: 16px;
}
.el-dropdown-link:hover{
  text-decoration: none;
  color: #c2c0c0;
}
</style>
