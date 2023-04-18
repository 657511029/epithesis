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
              <el-dropdown-item icon='el-icon-edit'  style="width: 80px;">
                <span href="#"  @click="modifyPassword" style="text-decoration: none; color: #505458;">
                  修改密码
                </span>
              </el-dropdown-item>
              <el-dropdown-item icon='el-icon-delete'  style="width: 80px;">
                <span href="#"  @click="deleteUser" style="text-decoration: none; color: #505458;">
                  注销
                </span>
              </el-dropdown-item>
              <el-dropdown-item v-show="editShow" icon='el-icon-plus'  style="width: 80px;">
                <span href="#"  @click="toAddCourse" style="text-decoration: none; color: #505458;">
                  新建课程
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
      title="删除账户"
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
    <el-dialog
      title="修改密码"
      width="500px"
      :visible.sync="modifyVisible"
      :before-close="handleModifyClose"
    >
      <el-form :model="modifyPasswordForm" :rules="rules" ref="modifyPasswordForm" label-width="100px" >
        <div class="updateInfo">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input type="password" placeholder="旧密码" clearable v-model="modifyPasswordForm.oldPassword" autocomplete="new-password"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input type="password" placeholder="新密码" clearable v-model="modifyPasswordForm.newPassword" autocomplete="new-password"></el-input>
            </el-form-item>
            <el-form-item label="确认新密码" prop="newPassword1">
              <el-input type="password" placeholder="确认新密码" clearable v-model="modifyPasswordForm.newPassword1" autocomplete="new-password"></el-input>
            </el-form-item>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submit1">提 交</el-button>
        <el-button @click="handleModifyClose">取 消</el-button>
  </span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: 'Header',
  data: function () {
    const pwdCheck = async (rule, value, callback) => {
      if (value.length < 6) {
        return callback(new Error('密码不能少于6位！'))
      } else if (value.length > 16) {
        return callback(new Error('密码最长不能超过16位！'))
      } else {
        callback()
      }
    }
    const pwdAgainCheck = async (rule, value, callback) => {
      if (value.length < 1) {
        return callback(new Error('重复密码不能为空！'))
      } else if (this.modifyPasswordForm.newPassword !== this.modifyPasswordForm.newPassword1) {
        return callback(new Error('两次输入密码不一致！'))
      } else {
        callback()
      }
    }
    return {
      editShow: false,
      deleteVisible: false,
      modifyVisible: false,
      search: '',
      ifLogin: false,
      username: '',
      modifyPasswordForm: {
        oldPassword: '',
        newPassword: '',
        newPassword1: ''
      },
      rules: {
        oldPassword: [{required: true, message: '请输入旧密码', trigger: 'blur'},
          {validator: pwdCheck, trigger: 'blur'}],
        newPassword: [{required: true, message: '请输入新密码', trigger: 'blur'},
          {validator: pwdCheck, trigger: 'blur'}],
        newPassword1: [{required: true, message: '请确认新密码', trigger: 'blur'},
          {validator: pwdAgainCheck, trigger: 'blur'}]
      }
    }
  },
  mounted () {
    this.editShow = (sessionStorage.getItem('authority') === 'teacher')
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
    modifyPassword: function () {
      this.modifyVisible = true
    },
    handleDeleteClose: function () {
      this.deleteVisible = false
    },
    handleModifyClose: function () {
      this.modifyVisible = false
    },
    toAddCourse: function () {
      this.$router.push('/AddCourseView')
    },
    submit: function () {
      this.$axios.post('http://localhost:8080/api/deleteUser', {
        'userId': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.$message({
              message: '注销账户成功 ',
              type: 'success',
              duration: 1500
            })
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
    submit1: function () {
      this.$refs.modifyPasswordForm.validate((valid) => {
        if (valid) {
          // 只能接受json格式的数据
          this.$axios.post('http://localhost:8080/api/modifyPassword', {
            'userId': sessionStorage.getItem('userId'),
            'oldPassword': this.modifyPasswordForm.oldPassword,
            'newPassword': this.modifyPasswordForm.newPassword
          })
            .then(resp => {
              if (resp.status === 200) {
                console.log(resp)
                this.$message({
                  message: '密码修改成功,请重新登录 ',
                  type: 'success',
                  duration: 1500
                })
                this.loginOut()
              } else {
                let message = resp.data.message
                this.$message({
                  message: '密码修改失败失败! ' + message,
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
                  message: '密码修改失败失败! ' + message,
                  type: 'warning',
                  duration: 1500
                })
              } else {
                console.log(error)
                this.$message.error('发生错误！')
              }
            })
        } else {
          return false
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
