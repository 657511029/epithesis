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
                <span href="#"  @click="addCourse" style="text-decoration: none; color: #505458;">
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
    <el-dialog
      title="新建课程"
      width="1000px"
      :visible.sync="addVisible"
      :before-close="handleAddClose"
    >
      <el-form :model="addCourseForm"
               :rules="addRules"
               ref="addCourseForm"
               class="add-form-container"
               autocomplete="new-password"
               label-width="100px"
               id="addCourseForm"
      >
        <el-form-item prop="courseName" label="课程名称:">
          <el-input type="text" placeholder="课程名称" v-model="addCourseForm.courseName" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="课程介绍:">
          <el-input type="text" placeholder="课程介绍(描述字数请少于255字)" v-model="addCourseForm.introduction" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="courseInstitution" label="课程单位:">
          <el-input type="text" placeholder="课程单位" v-model="addCourseForm.courseInstitution" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="chooseName" label="课程方向:">
          <el-select v-model="addCourseForm.chooseName"  filterable placeholder="请选择课程方向" clearable="">
            <el-option v-for="choose in chooseList" :label="choose.chooseName" :value="choose.chooseName" :key="choose.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="displayUrl" label="展示图片:">
          <el-button @click="uploadDisplay">
            上传课程展示图
          </el-button>
          <p>
            本项非必需。如不上传，则使用系统默认图片；如上传，请选择jpg、png格式，大小不超过2M，建议尺寸1920*240像素）
          </p>
         <input type="file"  accept="image/*" ref="uploadDisplayFile" enctype="multipart/form-data" class="hiddenInput">
        </el-form-item>
        <el-form-item prop="backgroundUrl" label="背景图片:">
          <el-button @click="uploadBackground">
            上传课程背景图
          </el-button>
          <p>
            本项非必需。如不上传，则使用系统默认图片；如上传，请选择jpg、png格式，大小不超过2M，建议尺寸1920*240像素）
          </p>
          <input type="file" accept="image/*" ref="uploadBackgroundFile" enctype="multipart/form-data" class="hiddenInput">
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submit2">提 交</el-button>
        <el-button @click="handleAddClose">取 消</el-button>
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
      addCourseId: 0,
      editShow: false,
      deleteVisible: false,
      modifyVisible: false,
      addVisible: false,
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
      },
      addCourseForm: {
        courseName: '',
        introduction: '',
        courseInstitution: '',
        chooseName: '',
        displayUrl: '',
        backgroundUrl: ''
      },
      addRules: {
        courseName: [{required: true, message: '请输入课程名称', trigger: 'blur'}],
        introduction: [{required: true, message: '请输入课程介绍', trigger: 'blur'}],
        courseInstitution: [{required: true, message: '请输入课程单位', trigger: 'blur'}],
        chooseName: [{required: true, message: '请输入课程方向', trigger: 'blur'}]
      },
      chooseList: []
    }
  },
  mounted () {
    this.editShow = (sessionStorage.getItem('authority') === 'teacher')
    this.ifLogin = !!sessionStorage.getItem('isLogin')
    // !!sessionStorage.getItem('token')
    this.username = sessionStorage.getItem('username')
    this.getChooseList()
  },
  updated () {
    this.ifLogin = !!sessionStorage.getItem('isLogin')
    // !!sessionStorage.getItem('token')
    this.username = sessionStorage.getItem('username')
  },
  methods: {
    formatDateTime: function (date) {
      let y = date.getFullYear()
      let m = date.getMonth() + 1
      m = m < 10 ? ('0' + m) : m
      let d = date.getDate()
      d = d < 10 ? ('0' + d) : d
      return y + '-' + m + '-' + d
    },
    uploadDisplay: function () {
      this.$refs.uploadDisplayFile.click()
    },
    uploadBackground: function () {
      this.$refs.uploadBackgroundFile.click()
    },
    deleteUser: function () {
      this.deleteVisible = true
    },
    modifyPassword: function () {
      this.modifyVisible = true
    },
    addCourse: function () {
      this.addVisible = true
    },
    handleDeleteClose: function () {
      this.deleteVisible = false
    },
    handleModifyClose: function () {
      this.modifyVisible = false
    },
    handleAddClose: function () {
      this.addVisible = false
    },
    getChooseList () {
      this.$axios.post('http://localhost:8080/api/lookChooseList', {
        // 'message': this.content
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.chooseList = resp.data.chooseList
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取课程类型列表信息失败! ' + message,
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
              message: '获取课程类型列表信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
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
                  message: '密码修改失败! ' + message,
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
                  message: '密码修改失败! ' + message,
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
    submit2: function () {
      this.addCourseInfo()
      // window.location.reload()
    },
    addCourseInfo: function () {
      this.$refs.addCourseForm.validate((valid) => {
        if (valid) {
          // 只能接受json格式的数据
          this.$axios.post('http://localhost:8080/api/addCourse', {
            'courseName': this.addCourseForm.courseName,
            'introduction': this.addCourseForm.introduction,
            'chooseName': this.addCourseForm.chooseName,
            'courseInstitution': this.addCourseForm.courseInstitution,
            'courseTime': this.formatDateTime(new Date()),
            'userId': sessionStorage.getItem('userId')
          })
            .then(resp => {
              if (resp.status === 200) {
                console.log(resp)
                this.addCourseId = resp.data.course.id
                if (this.$refs.uploadDisplayFile.files.length === 0 && (this.$refs.uploadBackgroundFile.files.length === 0)) {
                  this.addVisible = false
                  window.location.reload()
                } else if (this.$refs.uploadDisplayFile.files.length !== 0) {
                  this.modifyDisplay(resp.data.course.id)
                } else {
                  this.modifyBackground(resp.data.course.id)
                }
                this.$message({
                  message: '新建课程成功',
                  type: 'success',
                  duration: 1500
                })
              } else {
                let message = resp.data.message
                this.$message({
                  message: '新建课程失败 ' + message,
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
                  message: '新建课程失败 ' + message,
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
    modifyDisplay: function (addCourseId) {
      let formData = new FormData()
      // 通过append()方法追加数据
      formData.append('displayFile', this.$refs.uploadDisplayFile.files[0])
      console.log(typeof addCourseId)
      formData.append('courseId', addCourseId + '')
      this.$axios.post('http://localhost:8080/api/modifyDisplayUrl', formData)
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            if (this.$refs.uploadBackgroundFile.files.length !== 0) {
              this.modifyBackground(addCourseId)
            } else {
              this.addVisible = false
              window.location.reload()
            }
          } else {
            let message = resp.data.message
            this.$message({
              message: '发生错误,更新展示图失败! ' + message,
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
              message: 'error,更新展示图失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    modifyBackground: function (addCourseId) {
      let formData = new FormData()
      // 通过append()方法追加数据
      formData.append('backgroundFile', this.$refs.uploadBackgroundFile.files[0])
      formData.append('courseId', addCourseId + '')
      this.$axios.post('http://localhost:8080/api/modifyBackgroundUrl', formData)
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.addVisible = false
            window.location.reload()
          } else {
            let message = resp.data.message
            this.$message({
              message: '更新背景失败! ' + message,
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
              message: '更新背景失败! ' + message,
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
.hiddenInput{
  display: none;
}
</style>
