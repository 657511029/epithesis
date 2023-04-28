<template>
<div class="answer-wrap">
  {{ experiment.answer }}
</div>
</template>

<script>
export default {
  name: 'Answer',
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
.answer-wrap{
  padding: 100px 50px;
}
</style>
