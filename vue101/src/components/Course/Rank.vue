<template>
  <div class="rank-wrap">
    <div class="rank-container">
      <div class="rank-title">
        <span>
          排名
        </span>
        <span>
          姓名
        </span>
        <span>
          通过关卡数
        </span>
      </div>
      <div class="rank-own">
        <span>
          {{isActive}}
        </span>
        <span>
          {{username}}
        </span>
        <span>
          {{rankNumber}}
        </span>
      </div>
      <div class="rank-split">
      </div>
      <div class="rank-item" v-for="(item,index) in rankList.slice(0,9)" :key="index">
         <span>
          {{index + 1}}
        </span>
        <span>
          {{item.username}}
        </span>
        <span>
          {{item.passNumber}}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Rank',
  props: ['parentId'],
  data () {
    return {
      username: sessionStorage.getItem('username'),
      courseId: 0,
      rankNumber: 0,
      isActive: '未上榜',
      rankList: []
    }
  },
  created () {
    this.courseId = this.parentId
  },
  mounted () {
    this.getRankList()
    this.getRank()
    this.getIsActive()
  },
  methods: {
    getRankList: function () {
      // this.rankList = [
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   },
      //   {
      //     username: '钱钱钱',
      //     rankNumber: 100
      //   }
      // ]
      this.$axios.post('http://localhost:8080/api/lookCourseStudentList', {
        'courseId': this.courseId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.rankList = resp.data.courseStudentList
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
    getRank: function () {
      this.$axios.post('http://localhost:8080/api/lookCourseStudent', {
        'courseId': this.courseId,
        'userId': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            if (resp.data.courseStudent === null) {
              this.rankNumber = 0
            } else {
              this.rankNumber = resp.data.courseStudent.passNumber
            }
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
    getIsActive: function () {
      this.isActive = '未上榜'
    }
  }
}
</script>

<style scoped>
.rank-wrap{
  background: rgb(255, 255, 255);
  padding: 50px 20px;
  border-radius: 15px;
  margin-left: 20px;
}
.rank-container{
  position: relative;
  transition: opacity 0.3s;

}
.rank-title{
  height: 42px;
  background: rgb(250, 250, 250);
  display: flex;
  padding: 0px 5px;
  align-items: center;
}
.rank-title span{
  flex: 1 1 0%;
}
.rank-own{
  height: 42px;
  display: flex;
  padding: 0px 5px;
  align-items: center;
}
.rank-own span{
  flex: 1 1 0%;
}
.rank-split{
  color: rgba(0, 0, 0, 0.85);
  border-top: 1px solid rgba(0, 0, 0, 0.06);
  display: flex;
  clear: both;
  width: 100%;
  min-width: 100%;
  margin: 24px 0;
  box-sizing: border-box;
  padding: 0;
  font-size: 12px;
  font-variant: tabular-nums;
  line-height: 1.66667;
  list-style: none;
}
.rank-item{
  height: 42px;
  display: flex;
  padding: 0px 5px;
  align-items: center;
}
.rank-item span{
  flex: 1 1 0%;
}
span{
  font-size: 14px;
}
</style>
