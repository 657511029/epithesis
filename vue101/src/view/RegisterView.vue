<template>
  <div class="register-wrap">
    <el-form :model="registerForm"
             :rules="rules"
             ref="registerForm"
             class="register-container"
             autocomplete="new-password">
      <h1 class="title">用户注册</h1>
      <el-form-item prop="username">
        <el-input type="text" placeholder="用户账户" clearable v-model="registerForm.username" autocomplete="new-password"></el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input type="email" placeholder="用户邮箱" clearable v-model="registerForm.email" autocomplete="new-password"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" placeholder="用户密码" clearable v-model="registerForm.password" autocomplete="new-password"></el-input>
      </el-form-item>
      <el-form-item prop="password1">
        <el-input type="password" placeholder="确认密码" clearable v-model="registerForm.password1" autocomplete="new-password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doRegister" style="width: 45%">注册</el-button>
        <el-button type="primary" @click="toLogin" style="width: 45%">返回登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'RegisterView',
  data () {
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
      } else if (this.registerForm.password !== this.registerForm.password1) {
        return callback(new Error('两次输入密码不一致！'))
      } else {
        callback()
      }
    }
    return {
      registerForm: {
        username: '',
        email: '',
        password: '',
        password1: ''
      },
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        email: [{required: true, message: '请输入用户邮箱', trigger: 'blur'},
          { pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '请输入合法的邮箱地址', trigger: 'blur' }
        ],
        password: [{required: true, message: '请输入密码', trigger: 'blur'},
          {validator: pwdCheck, trigger: 'blur'}],
        password1: [{required: true, message: '请确认密码', trigger: 'blur'},
          {validator: pwdAgainCheck, trigger: 'blur'}]
      }
    }
  },
  methods: {
    doRegister: function () {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          // 只能接受json格式的数据
          this.$axios.post('http://localhost:8080/api/register', {
            'username': this.registerForm.username,
            'email': this.registerForm.email,
            'password': this.registerForm.password,
            'authority': 'user'
          })
            .then(resp => {
              if (resp.status === 200) {
                console.log(resp)
                // 注册成功
                this.$message({
                  message: '注册成功! ',
                  type: 'success',
                  duration: 1500
                })
                this.$router.push('/LoginView')
              } else {
                // 注册出现错误
                let message = resp.data.message
                this.$message({
                  message: '注册失败! ' + message,
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
                  message: '注册失败! ' + message,
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
    toLogin: function () {
      this.$router.push('/LoginView')
    }
  }
}
</script>

<style scoped>
.register-wrap{
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding-top: 10%;
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
}
.register-container{
  border-radius: 10px;
  margin: 0px auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0,0,0,0.1);
}
.title{
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
