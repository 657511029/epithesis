import Vue from 'vue'
import Router from 'vue-router'
import LoginView from '../view/LoginView.vue'
import RegisterView from '../view/RegisterView.vue'
import HomeView from '../view/HomeView.vue'
import SpaceView from '../view/SpaceView.vue'
import SpaceInfo from '../components/Space/SpaceInfo.vue'
import ChangeInfo from '../components/Space/ChangeInfo.vue'
import ChangePic from '../components/Space/ChangePic.vue'
import CourseInfo from '../components/Space/CourseInfo.vue'
import SearchView from '../view/SearchView.vue'
import CourseView from '../view/CourseView.vue'
import AddCourseView from '../view/AddCourseView.vue'
import EditCourseView from '../view/EditCourseView.vue'
import EditCourseInfo from '../components/Space/EditCourseInfo.vue'
Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

let router = new Router({
  mode: 'history',
  base: '/',
  routes: [
    {
      path: '/',
      name: 'HomeView',
      component: HomeView,
      meta: {
        requireAuth: false,
        show: true
      }
    },
    {
      path: '/LoginView',
      name: 'LoginView',
      component: LoginView,
      meta: {
        requireAuth: false,
        show: false
      }
    },
    {
      path: '/RegisterView',
      name: 'RegisterView',
      component: RegisterView,
      meta: {
        requireAuth: false,
        show: false
      }
    },
    {
      path: '/SpaceView',
      name: 'SpaceView',
      component: SpaceView,
      meta: {
        requireAuth: true,
        show: true
      },
      children: [
        {
          path: '/SpaceInfo',
          name: 'SpaceInfo',
          component: SpaceInfo
        },
        {
          path: '/ChangeInfo',
          name: 'ChangeInfo',
          component: ChangeInfo
        },
        {
          path: '/ChangePic',
          name: 'ChangePic',
          component: ChangePic
        },
        {
          path: '/CourseInfo',
          name: 'CourseInfo',
          component: CourseInfo
        },
        {
          path: '/EditCourseInfo',
          name: 'EditCourseInfo',
          component: EditCourseInfo
        }
      ]
    },
    {
      path: '/SearchView',
      name: 'SearchView',
      component: SearchView,
      meta: {
        requireAuth: true,
        show: true
      }
    },
    {
      path: '/CourseView',
      name: 'CourseView',
      component: CourseView,
      meta: {
        requireAuth: true,
        show: true
      }
    },
    {
      path: '/AddCourseView',
      name: 'AddCourseView',
      component: AddCourseView,
      meta: {
        requireAuth: true,
        show: true
      }
    },
    {
      path: '/EditCourseView',
      name: 'EditCourseView',
      component: EditCourseView,
      meta: {
        requireAuth: true,
        show: true
      }
    }
  ]
})
export default router
// 还是在router.js中 我们这里就放在 export default router 的后面
// 登录拦截
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
    // 如果需要就执行下面的代码
    var num = sessionStorage.getItem('isLogin')
    // 通过sessionStorage 获取当前的isLogin的值 将我们保存的isLogin的值赋给num,num是顺便取的名称，可以换
    // 我们在登录界面，我们使用请求，请求成功后，我们就使用sessionStorage为‘isLogin’保存一个值  1，如果请求失败，就不保存‘isLogin’的值
    // 如果请求成功，num的值就是1，请求失败就是null，所以下面进行判断
    if (num === '1') {
      // 如果登录了，就跳转到'/index'路径
      next()
    } else {
      next({
        path: '/LoginView'// 返回登录界面
        // query: {redirect: to.fullPath}
      })
    }
  } else {
    // 如果不需要登录权限就直接跳转到该路由
    next()
  }
})
