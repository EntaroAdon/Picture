import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'//管理员登录页
import sys from '@/components/Home2'//用户登录主页
import EmpBasic from '@/components/emp/EmpBasic'//管理员---用户管理
import Img from '@/components/emp/Img'//管理员---图片管理
import ImgType from '@/components/emp/ImgType'//管理员---图片类型管理
import Role from '@/components/emp/Role'//管理员---角色管理
import Oplog from '@/components/emp/Oplog'//管理员---操作日志
import Loginlog from '@/components/emp/Loginlog'//管理员---登录日志
import CommentBasic from '@/components/emp/CommentBasic'//管理员---评论管理
import ImgHost from '@/components/system/imgHost'//用户---图片详情
import UserHome from '@/components/system/userHome'//用户---个人主页
import UserHost from '@/components/system/userHost'//用户---个人主页--图片管理
import UserBasic from '@/components/system/UserBasic'//用户---个人主页--个人信息
import Comment from '@/components/system/comment'//用户---个人主页--我的评论


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      hidden: true
    }, {
      path: '/home',
      name: '管理员主页',
      component: Home,
      hidden: true,
      meta: {
        requireAuth: true
      },
      children: [
      ]
    }, {
      path: '/UserHost',
      name: '个人中心',
      component: UserHome,
      hidden: true,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/UserHost',
          name: '图片管理',
          component: UserHost,
          meta: {
            keepAlive: false
          }
        },{
          path: '/comment',
          name: '我的评论',
          component: Comment,
          meta: {
            keepAlive: false
          }
        },{
          path: '/UserBasic',
          name: '个人信息',
          component: UserBasic,
          meta: {
            keepAlive: false
          }
        },
      ]
    }, {
      path: '/sys',
      name: '用户主页',
      component: sys,
      hidden: true,
      meta: {
        requireAuth: true
      }
    },{
      path: '/imghost',
      name: '',
      component: ImgHost,
      hidden: true,
      meta: {
        keepAlive: false
      }
    },
    {
      path: '/home',
      name: '系统管理',
      component: Home,
      children: [
        {
          path: '/EmpBasic',
          name: '用户管理',
          component: EmpBasic,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/img',
          name: '图片管理',
          component: Img,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/imgType',
          name: '图片类型',
          component: ImgType,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/role',
          name: '角色管理',
          component: Role,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/log',
          name: '操作日志',
          component: Oplog,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/loginLog',
          name: '登录日志',
          component: Loginlog,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/CommentBasic',
          name: '评论管理',
          component: CommentBasic,
          meta: {
            keepAlive: false
          }
        },
      ]
    },

  ]
})
