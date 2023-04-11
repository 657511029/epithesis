<template>
<div class="login-wrap">
  <el-form :model="loginForm"
           :rules="rules"
           ref="loginForm"
           class="login-container"
           autocomplete="new-password">
    <h1 class="title">用户登录</h1>
    <el-form-item prop="username">
      <el-input type="text" placeholder="用户账户" v-model="loginForm.username" autocomplete="new-password"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" placeholder="用户密码" v-model="loginForm.password" autocomplete="new-password"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="doLogin" style="width: 100%">登录</el-button>
    </el-form-item>
    <el-row style="text-align: center;margin-top: -10px;">
      <el-link type="primary" @click="toRegister">用户注册</el-link>
      <el-link type="primary">忘记密码</el-link>
    </el-row>
  </el-form>

</div>
</template>

<script>
export default {
  name: 'LoginView',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      }
    }
  },
  methods: {
    doLogin: function () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 只能接受json格式的数据
          this.$axios.post('http://localhost:8080/api/login', {
            'username': this.loginForm.username,
            'password': this.loginForm.password
          })
            .then(resp => {
              if (resp.status === 200) {
                console.log(resp)
                // 登录成功
                this.$message({
                  message: '登录成功! ',
                  type: 'success',
                  duration: 1500
                })
                sessionStorage.setItem('isLogin', '1')
                sessionStorage.setItem('username', resp.data.user.username)
                sessionStorage.setItem('userId', resp.data.user.id)
                console.log(typeof sessionStorage.getItem('userId'))
                this.$router.push('/')
              } else {
                // 注册出现错误
                let message = resp.data.message
                this.$message({
                  message: '登录失败! ' + message,
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
                  message: '登录失败! ' + message,
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
    toRegister: function () {
      this.$router.push('/RegisterView')
    }
  }
}
</script>

<style scoped>
.login-wrap{
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding-top: 10%;
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
}
.login-container{
  border-radius: 10px;
  margin: 0 auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0,0,0,0.1);
}
.title{
  margin: 0 auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
