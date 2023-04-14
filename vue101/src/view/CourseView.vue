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
    <div v-if="lookIfJoin === 'true'" class="course-join" @click="outCourse">
      退出课程
    </div>
    <div v-else class="course-join" @click="joinCourse">
      加入课程
    </div>
  </div>
  <el-dialog
    title="退出课程"
    width="400px"
    :visible.sync="outVisible"
    :before-close="handleOutClose"
  >
    <el-form  >
      <div class="updateInfo" style="width: 100%">
        <el-form-item  prop="message">
          <span>退出课程之后，你在该课程中的所有数据将被清空并永不可恢复</span>
        </el-form-item>
        <div slot="footer"  class="dialog-footer" style="padding: 0 100px">
          <el-button @click="submit" >确认</el-button>
          <el-button type="primary" @click="handleOutClose">取消</el-button>

        </div>
      </div>
    </el-form>
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
        <component :is = "componentNext" :parentId="courseId"></component>
        <router-view/>
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
    </section>
  </section>
</div>
</template>

<script>
import Chapter from '../components/Course/Chapter.vue'
import Rank from '../components/Course/Rank.vue'
export default {
  name: 'CourseView',
  components: {
    Chapter,
    Rank
  },
  data () {
    return {
      outVisible: false,
      lookIfJoin: '加入课程',
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
      teacherList: []
    }
  },
  created () {
    this.courseId = this.$route.query.courseId
  },
  mounted () {
    this.getCourse()
    this.getTeacherList()
    this.getIfJoin()
  },
  methods: {
    joinCourse: function () {
      this.$axios.post('http://localhost:8080/api/joinCourse', {
        'courseId': this.courseId,
        'userId': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.$message({
              message: '加入课程成功 ',
              type: 'success',
              duration: 1500
            })
            window.location.reload()
          } else {
            let message = resp.data.message
            this.$message({
              message: '加入课程失败! ' + message,
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
              message: '加入课程失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    outCourse: function () {
      this.outVisible = true
    },
    handleOutClose: function () {
      this.outVisible = false
    },
    submit: function () {
      this.$axios.post('http://localhost:8080/api/outCourse', {
        'courseId': this.courseId,
        'userId': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.$message({
              message: '退出课程成功 ',
              type: 'success',
              duration: 1500
            })
            window.location.reload()
          } else {
            let message = resp.data.message
            this.$message({
              message: '退出课程失败! ' + message,
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
              message: '退出课程失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    getIfJoin: function () {
      this.$axios.post('http://localhost:8080/api/lookIfJoinCourse', {
        'courseId': this.courseId,
        'userId': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.lookIfJoin = resp.data.lookIfJoin
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
    toChapter: function () {
      this.componentNext = 'Chapter'
    },
    toRank: function () {
      this.componentNext = 'Rank'
    },
    getCourse: function () {
    //   this.course.backgroundUrl = 'static/backgroundPic/1.png'
    //   this.course.courseName = '大学计算机基础 —— 计算思维'
    //   this.course.courseTime = '2023-4-8'
    //   this.course.studentNumber = 133
    //   this.course.experimentNumber = 45
    //   this.course.introduction = '人要成功融入社会所必备的思维能力，是由其所处时代能够获得的工具决定的。计算机是信息社会的必备工具之一，如何有效利用计算机分析和解决问题，将与阅读、写作和算术一样，成为 21 世纪每个人的基本技能，而不仅仅属于计算机专业人员。计算机正在对人们的生活、工作，甚至思维产生深刻的影响。\n' +
    //     '\n' +
    //     '《大学计算机基础》是大学本科教育的第一门计算机公共基础课程，它的改革越来越受到人们的关注。课程的主要目的是从使用计算机、理解计算机系统和计算思维三个方面培养学生的计算机应用能力。2008 年开始，以“计算思维”的培养为主线开展计算科学通识教育，逐渐成为国内外计算机基础教育界的共识。\n' +
    //     '\n' +
    //     '基于这种认识，国防科技大学《大学计算机基础》教学团队对该课程进行了全面改革，强调计算思维的启蒙和训练。教学的指导思想是兼顾计算机基础知识和计算思维，选择 Python 作为实践语言，将信息表示与处理、计算机系统、网络、数据库、多媒体等知识既作为教学内容，又作为计算思维求解问题的研究对象并加以实践。希望通过这种问题求解的学习和实践过程，培养学生在理解计算机基础知识的同时，能有意识地将计算思维的方法和技能应用在各自专业学习中，能解决具有一定难度的实际问题。'
    //
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
  margin-left: 100px;
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
</style>
