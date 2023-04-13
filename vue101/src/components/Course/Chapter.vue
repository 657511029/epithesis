<template>
<div class="chapter-warp">
  <div class="chapter-container">
    <div v-for="(item) in chapterList" :key="item.id" class="chapter-item" >
      <div class="chapter-item-title" style="width: 80%">
        <div class="chapter-item-title-container">
          <span class="chapter-item-title-message">
            {{item.experimentName}}
          </span>
        </div>
      </div>
      <div class="chapter-item-title" style="margin-left: 100px" >
        <div class="chapter-item-title-container">
           <span class="chapter-item-button">
        开始训练
      </span>
        </div>
      </div>

    </div>
  </div>

</div>
</template>

<script>
export default {
  name: 'Chapter',
  props: ['parentId'],
  data () {
    return {
      courseId: 0,
      chapterList: []
    }
  },
  created () {
    this.courseId = this.parentId
  },
  mounted () {
    this.getChapter()
  },
  methods: {
    getChapter: function () {
      // this.chapterList = [
      //   {
      //     chapterName: '1-1 章节一    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 0
      //   },
      //   {
      //     chapterName: '1-2 章节二    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 1
      //   },
      //   {
      //     chapterName: '1-3 章节三    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 2
      //   },
      //   {
      //     chapterName: '1-4 章节四    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 3
      //   },
      //   {
      //     chapterName: '2-1 章节五    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 4
      //   },
      //   {
      //     chapterName: '2-2 章节六    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 5
      //   },
      //   {
      //     chapterName: '2-3 章节七    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 6
      //   },
      //   {
      //     chapterName: '3-1 章节八    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 7
      //   },
      //   {
      //     chapterName: '3-2 章节九    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 8
      //   },
      //   {
      //     chapterName: '3-3 章节十    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 9
      //   },
      //   {
      //     chapterName: '4-1 章节十一    绘制炸弹轨迹——绘制一个坐标点',
      //     chapterId: 10
      //   }
      // ]
      this.$axios.post('http://localhost:8080/api/lookExperimentOfCourse', {
        'courseId': this.courseId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.chapterList = resp.data.experimentList
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
    }
  }
}
</script>

<style scoped>
.chapter-warp{
  background: #fff;
  padding: 50px 20px;
  border-radius: 15px;
  margin-left: 20px;
}
.chapter-container{
  display: flex;
  flex-direction: column;
  padding-top: 20px;
}
.chapter-item{
  display: flex;
  align-items: flex-start;
  padding: 0 0 2px 0;
  outline: none;
  cursor: pointer;
  width: 100%;
}
.chapter-item-title{
  min-height: 40px;
  padding: 0 20px 0 0;
  font-size: 14px !important;
  display: flex;
  align-items: center;
}
.chapter-item-button{
  min-height: 30px;
  width: 100px;
  padding: 0px 20px 0px 20px;
  font-size: 14px !important;
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
  background-color: #4060ff;
  border-radius: 5px;
  color: white;
}
.chapter-item:hover{
  background: transparent;
  background-color: #f5f5f5;
}
.chapter-item-title-container{
  flex: 1 1 0%;
  position: relative;
  max-width: 100%;
  min-height: 1px;
  color: #333;
}
.chapter-item-title-message{
  margin-right: 10px !important;
}
</style>
