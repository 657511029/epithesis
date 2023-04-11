<template>
  <div>
    <el-dialog
      title="修改个人信息"
      width="850px"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      >
      <el-form :model="form" :rules="rules" ref="form" label-width="150px" >
        <div class="updateInfo">
          <div class="left">
            <el-form-item label="头像" prop="avatarUrl">
              <img style="width:150px;height:110px" :src="form.avatarUrl">
            </el-form-item>
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input v-model="form.age"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-switch
                v-model="form.sex"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="男"
                inactive-text="女"
                :active-value= '1'
                :inactive-value= '0'
                @change="changeStatus"
              >
              </el-switch>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email"></el-input>
            </el-form-item>

          </div>
          <div class="right" style="padding-right: 50px">
            <el-form-item label="用户编号" prop="id">
              <el-input v-model="form.id" disabled></el-input>
            </el-form-item>
            <el-form-item label="注册时间" prop="registerTime">
              <el-input v-model="form.registerTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="地区" prop="address">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="兴趣爱好" prop="interest">
              <el-input v-model="form.interest"></el-input>
            </el-form-item>
            <el-form-item label="职业" prop="career">
              <el-input v-model="form.career"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phoneNumber">
              <el-input v-model="form.phoneNumber"></el-input>
            </el-form-item>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" @click="submit">提 交</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: 'ChangeInfo',
  data () {
    return {
      sex: '女',
      dialogVisible: true,
      form: {
        id: '',
        avatarUrl: '',
        username: '',
        age: '',
        sex: '0',
        email: '',
        phoneNumber: '',
        address: '',
        registerTime: '',
        career: '',
        interest: ''
      },
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '请输入合法的邮箱地址', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.load()
    this.open()
  },
  methods: {
    changeStatus (callback) {
      if (callback === 1) {
        this.sex = '男'
      } else {
        this.sex = '女'
      }
    },
    open () {
      this.dialogVisible = true
    },
    load () {
      this.$axios.post('http://localhost:8080/api/lookUserInfo', {
        'id': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.form.id = resp.data.user.id
            this.form.avatarUrl = resp.data.user.avatarUrl
            this.form.username = resp.data.user.username
            this.form.age = resp.data.user.age
            this.form.sex = resp.data.user.sex
            this.form.email = resp.data.user.email
            this.form.phoneNumber = resp.data.user.phoneNumber
            this.form.address = resp.data.user.address
            this.form.registerTime = resp.data.user.registerTime
            this.form.career = resp.data.user.career
            this.form.interest = resp.data.user.interest
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
    },
    submit () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8080/api/modifyUserInfo', {
            'id': this.form.id,
            'avatarUrl': this.form.avatarUrl,
            'username': this.form.username,
            'age': this.form.age,
            'sex': this.sex,
            'email': this.form.email,
            'phoneNumber': this.form.phoneNumber,
            'address': this.form.address,
            'career': this.form.career,
            'interest': this.form.interest
          })
            .then(resp => {
              if (resp.status === 200) {
                console.log(resp)
                this.dialogVisible = false
                this.$emit('flesh')
              } else {
                let message = resp.data.message
                this.$message({
                  message: '更新个人信息失败! ' + message,
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
                  message: '更新个人信息失败! ' + message,
                  type: 'warning',
                  duration: 1500
                })
              } else {
                console.log(error)
                this.$message.error('发生错误！')
              }
            })
        }
      })
      window.location.reload()
    },
    handleClose () {
      this.dialogVisible = false
      this.$emit('flesh')
      window.location.reload()
    }
  }
}
</script>

<style scoped>
.updateInfo {
  height: 350px;
  overflow: auto;
}
.left {
  /* width: 330px; */
  float: left;
}
.right {
  overflow: hidden;
}
</style>
