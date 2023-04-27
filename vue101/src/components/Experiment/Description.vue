<template>
<div class="description-wrap">
   <ul class="description-router">
     <li>
       <span @click="toMessage">实验描述</span>
<!--       <a href="#实验描述">实验描述</a>-->
     </li>
     <li>
       <span @click="toExample">实验示例</span>
<!--       <a href="#实验示例">实验示例</a>-->
     </li>
   </ul>
  <hr>
  <h4 class="description-message" id="实验描述" ref="message">实验描述</h4>
  <p>
    {{experiment.description}}
  </p>
  <h4 class="description-example" id="实验示例" ref="example">实验示例</h4>
  <p>
    {{experiment.example}}
  </p>
  <hr>
  <p>开始你的任务吧，祝你成功！</p>
</div>
</template>

<script>
export default {
  name: 'Description',
  props: ['parentId'],
  data () {
    return {
      experimentId: 0,
      experiment: {}
    }
  },
  created () {
    this.experimentId = this.parentId
  },
  mounted () {
    this.getExperiment()
  },
  methods: {
    toMessage: function () {
      this.$refs.message.scrollIntoView({
        behavior: 'smooth', // 平滑过渡
        block: 'start' // 上边框与视窗顶部平齐
      })
    },
    toExample: function () {
      this.$refs.example.scrollIntoView({
        behavior: 'smooth', // 平滑过渡
        block: 'start' // 上边框与视窗顶部平齐
      })
    },
    getExperiment: function () {
      this.$axios.post('http://localhost:8080/api/lookExperiment', {
        'experimentId': this.experimentId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.experiment = resp.data.experiment
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取章节信息失败! ' + message,
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
              message: '获取章节信息失败! ' + message,
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
.description-wrap{
  overflow-y: auto;
  flex: 1 1;
  padding: 16px;
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
.description-router{
  margin-top: 0 !important;
  padding-top: 20px;
}
.description-router li{
  list-style-type: disc;
}
.description-router li span{
  color: #4183c4;
  text-decoration: none;
}
.description-router li span:hover{
  text-decoration: underline;
  cursor: pointer;
}
.description-wrap hr{
  height: 0;
  margin: 15px 0;
  overflow: hidden;
  background: transparent;
  border: 0;
  border-bottom: 1px solid #ddd;
}
.description-wrap h4{
  font-size: 1.25em;
}
.description-wrap p{
  margin: 0 0 16px 0 !important;
  line-height: 28px;
  white-space: pre-wrap;
}
</style>
