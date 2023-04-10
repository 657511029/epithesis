<template>
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
</template>

<script>
export default {
  name: 'Header',
  data: function () {
    return {
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
    doSearch: function () {
      this.$router.push({name: 'SearchView', query: {content: this.search}})
      window.location.reload()
    },
    loginOut: function () {
      sessionStorage.removeItem('username')
      sessionStorage.removeItem('isLogin')
      window.location.reload()
      this.$router.push('/')
    }
  }

}
</script>

<style scoped>
.header{
  display: flex;
  padding: 0;
  height: 50px;
  min-width: 1300px;
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
