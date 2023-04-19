<template>
  <div class="courseInfo-wrap">
    <div class="course-list">
      <div class="course-item" v-for="(item) in courseList.slice(start,end)" :key="item.id">
        <router-link :to="{path:'/EditCourseView',query:{courseId:item.id}}">
          <div class="course-img">
            <img :src="item.displayUrl" alt="">
          </div>
          <div class="course-title">
            <span>{{item.courseName}}</span>
          </div>
          <div class="course-teacher">
            <span class="prev el-icon-traffic-school"></span>
            <span>{{item.courseInstitution}}</span>
          </div>
          <div class="course-info">
          <span class="experimentNumber">
            章节数:{{item.experimentNumber}}</span>
            <span class="studentNumber">选课人数:{{item.studentNumber}}</span>
          </div>
        </router-link>
      </div>
    </div>
    <div class="pageBox">
      <div class="page">

        <div  v-bind:class="{left: this.currentPage != 1,noLeft: this.currentPage === 1}"  @click="prevPage()">
          <span class="prev" >
            上一页
        </span>
        </div>

        <span v-html="'\u00a0\u00a0\u00a0'"></span>
        <div  v-bind:class="{right: this.currentPage != this.totalPage,noRight: this.currentPage === this.totalPage}" @click="nextPage()">
            <span class="next" >
              下一页
        </span>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EditCourseInfo',
  data () {
    return {
      totalPage: 1,
      currentPage: 1, // 当前页数 ，默认为1
      pageSize: 9, // 每页显示数量
      courseNumber: 0,
      courseList: []

    }
  },
  mounted () {
    // this.courseNumber = 10
    // this.totalPage = Math.ceil(this.courseNumber / this.pageSize)
    // this.courseList = [
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 0
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 1
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 2
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 3
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 4
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 5
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 6
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 7
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 8
    //   },
    //   {
    //     url: 'static/userPic/userPic.jpg',
    //     courseName: '计算机基础',
    //     courseTeacher: '山水城市',
    //     experimentNumber: 7,
    //     studentNumber: 110,
    //     courseId: 9
    //   }
    // ]
    this.getCourseList()
  },
  computed: {
    // 监听当前页curPage的变化，求得当前页的起始行start和末行end，自定更新至v-for
    start: function () {
      return this.currentPage * this.pageSize - this.pageSize
    },
    end: function () {
      return this.currentPage * this.pageSize
    }
  },
  methods: {
    getCourseList () {
      this.$axios.post('http://localhost:8080/api/lookCourseListOfTeacher', {
        'userId': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.courseList = resp.data.courseList
            this.courseNumber = this.courseList.length
            this.totalPage = Math.ceil(this.courseNumber / this.pageSize)
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取课程列表信息失败! ' + message,
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
              message: '获取课程列表信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    prevPage () {
      console.log(this.currentPage)
      if (this.currentPage === 1) {
        return false
      } else {
        this.currentPage = this.currentPage - 1
      }
    },
    // 下一页
    nextPage () {
      if (this.currentPage === this.totalPage) {
        return false
      } else {
        this.currentPage = this.currentPage + 1
      }
    }
  }
}
</script>

<style scoped>
.course-list{
  height: 800px;
  padding: 100px 100px 20px 100px;
  display: flex;
  flex-wrap: wrap;
}
.course-item{
  width: 180px;
  margin-right: 18px;
  margin-bottom: 18px;
  height: 210px;
  background: #ffffff;
  box-shadow: 0px 2px 4px 0px #d6dae1;
  border-radius: 7px;
  position: relative;
}

a{
  color: #1890ff;
  background-color: transparent;
  touch-action: manipulation;
  text-decoration: none;
  outline: none;
  cursor: pointer;
  transition: color 0.3s;
}

.course-img{
  overflow: hidden;
  height: 117px;
  border-radius: 7px 7px 0 0;
  position: relative;
}
.course-img img{
  width: 100%;
  transition: all 0.5s;
  border-style: none;
  vertical-align: middle;
  overflow-clip-margin: content-box;
  overflow: clip;
}
.course-img img:hover{
  transform: scale(1.1);
}
.course-title{
  height: 35px;
  font-size: 15px;
  font-weight: 600;
  color: #000000;
  padding: 10px 8px 0px 8px;
  width: 100%;
  display: flex;
  align-items: center;
}
.course-title span{
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 100%;
}
.course-teacher{
  font-size: 12px;
  color: #000f37;
  padding: 0px 8px;
  margin-top: 5px;
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 100%;
}
.course-info{
  height: 24px;
  display: flex;
  align-items: center;
  color: #000f37;
  padding: 0px 8px;
}
.course-info span{
  margin-right: 10px;
  font-size: 12px;
}
.pageBox{
  text-align: center;
  padding-right: 100px;
  font-size: 20px;
}
.left{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
}
.right{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
}
.left:hover{
  color: #40a9ff;
  cursor: pointer;
}
.right:hover{
  color: #40a9ff;
  cursor: pointer;
}
.noLeft{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}
.noRight{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}

</style>
