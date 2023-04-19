<template>
  <div class="course-wrap">
    <div class="background-container" :style="{backgroundImage: 'url(' +this.course.backgroundUrl +')'}">
      <div class="background-title">
        <p>
       <span>
          <strong>{{course.courseName}}</strong>
       </span>
        </p>
        <p>
        <span style="font-size: 20px;margin-top: 40px">
          {{course.courseInstitution}}
        </span>
        </p>
      </div>
    </div>
    <div class="course-intro">
      <div class="course-intro-item">
        <div class="course-intro-item-title">
          开课时间:
        </div>
        <div class="course-intro-item-message">
          {{course.courseTime}}
        </div>
      </div>
      <div class="course-intro-partition"></div>
      <div class="course-intro-item">
        <div class="course-intro-item-title">
          章节数:
        </div>
        <div class="course-intro-item-message">
          {{course.experimentNumber}}
        </div>
      </div>
      <div class="course-intro-partition"></div>
      <div class="course-intro-item">
        <div class="course-intro-item-title">
          选课人数:
        </div>
        <div class="course-intro-item-message">
          {{course.studentNumber}}
        </div>
      </div>
      <div  class="course-join" @click="editCourse">
        编辑课程
      </div>
      <div  class="course-join" @click="deleteCourse">
        删除课程
      </div>
    </div>
    <el-dialog
      title="删除课程"
      width="400px"
      :visible.sync="deleteCourseVisible"
      :before-close="handleDeleteCourseClose"
    >
      <el-form  >
        <div class="updateInfo" style="width: 100%">
          <el-form-item  prop="message">
            <span>删除课程之后，该课程中的所有数据将被清空并永不可恢复</span>
          </el-form-item>
          <div slot="footer"  class="dialog-footer" style="padding: 0 100px">
            <el-button @click="submit" >确认</el-button>
            <el-button type="primary" @click="handleDeleteCourseClose">取消</el-button>

          </div>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog
      title="编辑课程"
      width="1000px"
      :visible.sync="editCourseVisible"
      :before-close="handleEditCourseClose"
    >
      <el-form :model="editCourseForm"
               :rules="editRules"
               ref="editCourseForm"
               class="edit-form-container"
               autocomplete="new-password"
               label-width="100px"
               id="editCourseForm"
      >
        <el-form-item prop="courseName" label="课程名称:">
          <el-input type="text" placeholder="课程名称" v-model="editCourseForm.courseName" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="课程介绍:">
          <el-input type="text" placeholder="课程介绍(描述字数请少于255字)" v-model="editCourseForm.introduction" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="courseInstitution" label="课程单位:">
          <el-input type="text" placeholder="课程单位" v-model="editCourseForm.courseInstitution" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="chooseName" label="课程方向:">
          <el-select v-model="editCourseForm.chooseName"  filterable placeholder="请选择课程方向" clearable="">
            <el-option v-for="choose in chooseList" :label="choose.chooseName" :value="choose.chooseName" :key="choose.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="displayUrl" label="展示图片:">
          <el-button @click="uploadDisplay">
            更改课程展示图
          </el-button>
          <input type="file"  accept="image/*" ref="uploadDisplayFile" enctype="multipart/form-data" class="hiddenInput">
        </el-form-item>
        <el-form-item prop="backgroundUrl" label="背景图片:">
          <el-button @click="uploadBackground">
            更改课程背景图
          </el-button>
          <input type="file" accept="image/*" ref="uploadBackgroundFile" enctype="multipart/form-data" class="hiddenInput">
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submit2">提 交</el-button>
        <el-button @click="handleEditCourseClose">取 消</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="添加教师"
      width="500px"
      :visible.sync="addTeacherVisible"
      :before-close="handleAddTeacherClose"
    >
      <el-form :model="addTeacherForm"
               :rules="addTeacherRules"
               ref="addTeacherForm"
               class="add-form-container"
               autocomplete="new-password"
               label-width="100px"
               id="addTeacherForm"
      >
        <el-form-item prop="chooseName" label="教师名称:">
          <el-select v-model="addTeacherForm.userId"  filterable placeholder="请选择需要添加的教师" clearable="">
            <el-option v-for="user in userList" :label="user.username" :value="user.id" :key="user.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submit3">提 交</el-button>
        <el-button @click="handleAddTeacherClose">取 消</el-button>
  </span>
    </el-dialog>
    <section class="course-content">
      <aside class="course-content-left">
        <div class="course-content-intro">
          <div class="course-content-intro-title">
         <span>
           课程介绍
         </span>
          </div>
          <div class="course-content-intro-message">
            {{course.introduction}}
          </div>
        </div>
        <div class="course-content-router">
          <div v-bind:class="{courseContentRouterActive: this.componentNext === 'Chapter',courseContentRouterNoActive: this.componentNext != 'Chapter'}" @click="toChapter">
            课程章节
          </div>
          <div v-bind:class="{courseContentRouterActive: this.componentNext === 'Rank',courseContentRouterNoActive: this.componentNext != 'Rank'}" @click="toRank">
            排行榜
          </div>
        </div>
        <div class="course-content-router-content" style="background-color: white">
          <component :is = "componentNext" :parentId="courseId" :editIf="editIf"></component>
          <router-view/>
        </div>
        <div  class="course-join" style="margin-left: 20px;margin-top: 30px" @click="editCourse">
          添加章节
        </div>
      </aside>
      <section class="course-content-right">
        <div class="course-content-teacher">
        <span class="course-content-teacher-title">
          教学团队
        </span>
          <div class="course-content-teacher-list">
            <div class="course-content-teacher-item" v-for="(item) in teacherList" :key="item.userId">
              <div class="course-content-teacher-item-container">
              <span>
                <img v-bind:src="item.userPic">
                <div>
                  {{item.username}}
                </div>
              </span>
              </div>
            </div>
          </div>
        </div>
        <div  class="course-join" style="margin-left: 0;margin-top: 30px" @click="addTeacher">
          添加教学团队
        </div>
      </section>
    </section>
  </div>
</template>

<script>
import Chapter from '../components/Course/Chapter.vue'
import Rank from '../components/Course/Rank.vue'
export default {
  name: 'EditCourseView',
  components: {
    Chapter,
    Rank
  },
  data () {
    return {
      editIf: 'true',
      editCourseVisible: false,
      deleteCourseVisible: false,
      addTeacherVisible: false,
      addExperimentVisible: false,
      courseId: '',
      componentNext: 'Chapter',
      course: {
        backgroundUrl: '',
        courseName: '',
        courseTime: '',
        experimentNumber: 0,
        studentNumber: 0,
        courseId: 0,
        introduction: '',
        courseInstitution: ''
      },
      editCourseForm: {},
      editRules: {
        courseName: [{required: true, message: '请输入课程名称', trigger: 'blur'}],
        introduction: [{required: true, message: '请输入课程介绍', trigger: 'blur'}],
        courseInstitution: [{required: true, message: '请输入课程单位', trigger: 'blur'}],
        chooseName: [{required: true, message: '请输入课程方向', trigger: 'blur'}]
      },
      addTeacherForm: {},
      addTeacherRules: {
        username: [{required: true, message: '请输入用户名称名称', trigger: 'blur'}]
      },
      teacherList: [],
      chooseList: [],
      userList: []
    }
  },
  created () {
    this.courseId = this.$route.query.courseId
  },
  mounted () {
    this.getCourse()
    this.getTeacherList()
    this.getChooseList()
    this.getUserList()
  },
  methods: {
    editCourse: function () {
      this.editCourseVisible = true
    },
    deleteCourse: function () {
      this.deleteCourseVisible = true
    },
    addTeacher: function () {
      this.addTeacherVisible = true
    },
    handleEditCourseClose: function () {
      this.editCourseVisible = false
    },
    handleDeleteCourseClose: function () {
      this.deleteCourseVisible = false
    },
    handleAddTeacherClose: function () {
      this.addTeacherVisible = false
    },
    uploadDisplay: function () {
      this.$refs.uploadDisplayFile.click()
    },
    uploadBackground: function () {
      this.$refs.uploadBackgroundFile.click()
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
    getUserList () {
      this.$axios.post('http://localhost:8080/api/lookUserListByAuthority', {
        'authority': 'teacher'
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.userList = resp.data.userList
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取用户列表信息失败! ' + message,
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
              message: '获取用户列表信息失败! ' + message,
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
      this.$axios.post('http://localhost:8080/api/deleteCourse', {
        'courseId': this.courseId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.$message({
              message: '删除课程成功 ',
              type: 'success',
              duration: 1500
            })
            this.$router.push('/')
          } else {
            let message = resp.data.message
            this.$message({
              message: '删除课程失败! ' + message,
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
              message: '删除课程失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    toChapter: function () {
      this.componentNext = 'Chapter'
    },
    toRank: function () {
      this.componentNext = 'Rank'
    },
    getCourse: function () {
      this.$axios.post('http://localhost:8080/api/lookCourseInfo', {
        'courseId': this.courseId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.course.backgroundUrl = resp.data.course.backgroundUrl
            this.course.courseName = resp.data.course.courseName
            this.course.courseTime = resp.data.course.courseTime
            this.course.studentNumber = resp.data.course.studentNumber
            this.course.experimentNumber = resp.data.course.experimentNumber
            this.course.introduction = resp.data.course.introduction
            this.course.courseInstitution = resp.data.course.courseInstitution
            this.editCourseForm = resp.data.course
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取课程信息失败! ' + message,
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
              message: '获取课程信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    getTeacherList: function () {
      // this.teacherList = [
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客1',
      //     userId: 1
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客2',
      //     userId: 2
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客3',
      //     userId: 3
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客4',
      //     userId: 4
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客5',
      //     userId: 5
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客6',
      //     userId: 6
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客7',
      //     userId: 7
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客8',
      //     userId: 8
      //   },
      //   {
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '游客9',
      //     userId: 9
      //   }
      // ]
      this.$axios.post('http://localhost:8080/api/lookTeacherOfCourse', {
        'courseId': this.courseId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.teacherList = resp.data.teacherList
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取教学团队信息失败! ' + message,
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
              message: '获取教学团队信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    submit2: function () {
      this.editCourseInfo()
      // window.location.reload()
    },
    editCourseInfo: function () {
      this.$refs.editCourseForm.validate((valid) => {
        if (valid) {
          // 只能接受json格式的数据
          this.$axios.post('http://localhost:8080/api/modifyCourse', {
            'courseName': this.editCourseForm.courseName,
            'introduction': this.editCourseForm.introduction,
            'chooseName': this.editCourseForm.chooseName,
            'courseInstitution': this.editCourseForm.courseInstitution,
            'courseId': this.courseId
          })
            .then(resp => {
              if (resp.status === 200) {
                console.log(resp)
                if (this.$refs.uploadDisplayFile.files.length === 0 && (this.$refs.uploadBackgroundFile.files.length === 0)) {
                  this.addVisible = false
                  window.location.reload()
                } else if (this.$refs.uploadDisplayFile.files.length !== 0) {
                  this.modifyDisplay(this.courseId)
                } else {
                  this.modifyBackground(this.courseId)
                }
                this.$message({
                  message: '编辑课程成功',
                  type: 'success',
                  duration: 1500
                })
              } else {
                let message = resp.data.message
                this.$message({
                  message: '编辑课程失败 ' + message,
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
                  message: '编辑课程失败 ' + message,
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
    submit3: function () {
      this.addTeacherTeam()
      // window.location.reload()
    },
    addTeacherTeam: function () {
      this.$refs.addTeacherForm.validate((valid) => {
        if (valid) {
          // 只能接受json格式的数据
          this.$axios.post('http://localhost:8080/api/addTeacher', {
            'courseId': this.courseId,
            'userId': this.addTeacherForm.userId + ''
          })
            .then(resp => {
              if (resp.status === 200) {
                console.log(resp)
                window.location.reload()
                this.$message({
                  message: '添加教师成功',
                  type: 'success',
                  duration: 1500
                })
              } else {
                let message = resp.data.message
                this.$message({
                  message: '添加教师失败 ' + message,
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
                  message: '添加教师失败 ' + message,
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
    modifyDisplay: function (editCourseId) {
      let formData = new FormData()
      // 通过append()方法追加数据
      formData.append('displayFile', this.$refs.uploadDisplayFile.files[0])
      formData.append('courseId', editCourseId + '')
      this.$axios.post('http://localhost:8080/api/modifyDisplayUrl', formData)
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            if (this.$refs.uploadBackgroundFile.files.length !== 0) {
              this.modifyBackground(editCourseId)
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
    modifyBackground: function (editCourseId) {
      let formData = new FormData()
      // 通过append()方法追加数据
      formData.append('backgroundFile', this.$refs.uploadBackgroundFile.files[0])
      formData.append('courseId', editCourseId + '')
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
    }
  }
}
</script>

<style scoped>
.course-wrap{
  background-color: #f6f6f6;
  width:1355px;
  padding-bottom: 100px;
}
.background-container{
  height: 240px;
  width:1355px;
}
.background-title{
  width: 1200px;
  margin: 0 auto;
  padding-top: 100px;
}
.background-title p span{
  font-size: 28px;
  color: #fff;
  margin-right: 8px;
}
.course-intro{
  padding-left: 100px;
  display: flex;
  flex-direction: row;
  align-items: center;
  background-color: white;
  height: 80px;
}
.course-intro-item{
  margin-left: 70px;
  margin-right: 70px;
}
.course-intro-item-title{
  color: #9b9b9b;
  font-size: 12px;
}
.course-intro-item-message{
  color: #05101a;
  font-size: 14px;
}
.course-intro-partition{
  border-right: 1px solid #cdcdcd;
  height: 42px;
  margin-left: 10px;
}
.course-join{
  background: #0152d9;
  color: #fff;
  padding: 0 10px;
  height: 40px;
  width: 250px;
  line-height: 44px;
  cursor: pointer;
  display: block;
  text-align: center;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 18px;
  margin-left: 50px;
}
.course-content{
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}
.course-content-left{
  width: 60%;
}
.course-content-right{
  width: 400px;
  top: 180px;
  margin-right: 50px;
}
.course-content-intro{
  padding: 40px 20px;
  background-color: #fff;
  border-radius: 15px;
  margin-left: 20px;
}
.course-content-intro-title{
  margin-bottom: 20px;
}
.course-content-intro-title span{
  display: flex;
  align-items: center;
  font-weight: bold;
}
.course-content-intro-message{
  -webkit-text-size-adjust: 100%;
  color: #333;
  overflow: hidden;
  font-family: "Microsoft YaHei", Helvetica, "Meiryo UI", "Malgun Gothic", "Segoe UI", "Trebuchet MS", "Monaco", monospace, Tahoma, STXihei, "华文细黑", STHeiti, "Helvetica Neue", "Droid Sans", "wenquanyi micro hei", FreeSans, Arimo, Arial, SimSun, "宋体", Heiti, "黑体", sans-serif;
  word-break: break-word;
  font-size: 16px;
  line-height: 1.6;
  white-space: normal;
  word-wrap: break-word;
  position: relative;
}
.course-content-router{
  transform: translate(0px, 0px);
  position: relative;
  display: flex;
  transition: transform 0.3s;
  height: 76px;
  align-items: center;
  border-radius: 15px;
  margin-left: 20px;
}
.course-content-router div{
  height: 76px;
  background: transparent;
  position: relative;
  display: inline-flex;
  align-items: center;
  font-size: 16px;
  outline: none;
  cursor: pointer;
  margin-right: 32px;
  margin-left: 20px;
}
.course-content-router div:hover{
  color: #2572e6;
}
.courseContentRouterActive{
  color: #0152d9;
  text-shadow: 0 0 0.25px currentcolor;
  border-bottom: 2px solid #0152d9;
}
.course-content-teacher{
  padding: 20px 25px;
  background-color: #ffffff;
  margin-bottom: 10px;
  border-radius: 15px;
}
.course-content-teacher-title{
  font-size: 16px;
  display: flex;
  align-items: center;
}
.course-content-teacher-list{
  width: 100%;
}
.course-content-teacher-item{
  display: inline-block;
  width: 30%;
  padding: 5px;
  background: rgb(255, 255, 255);
  margin-top: 10px;
}
.course-content-teacher-item-container{
  position: relative;
  text-align: center;
}
.course-content-teacher-item-container span img{
  width: 70px;
  height: 70px;
  border-radius: 50%;
  margin-bottom: 10px;
  border: 1px solid #eeeeee;
}
.course-content-router-content{
  border-radius: 15px;
  margin-left: 20px;
}
.hiddenInput{
  display: none;
}
</style>
