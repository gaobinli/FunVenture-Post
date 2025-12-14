import { loginApi, getInfoApi, loginOutApi } from '@/api/back/system/user'
import router from '@/router'
import { ActionContext } from 'vuex'
import { ElMessage } from 'element-plus'

export interface userState {
  token: string,
  info: object,
  carNumber: number,
}
const state = (): userState => ({
  token: '', // 登录token
  info: {},  // 用户信息
  carNumber: JSON.parse(localStorage.getItem('shoppingCarts')) == null ? 0 : JSON.parse(localStorage.getItem('shoppingCarts')).length,
})

// getters
const getters = {
  token(state: userState) {
    return state.token
  },
  carNumber(state: userState) {
    return state.carNumber
  },
}

// mutations
const mutations = {
  tokenChange(state: userState, token: string) {
    state.token = token
  },
  infoChange(state: userState, info: object) {
    state.info = info
  },
  addCarNumber(state: userState) {
    state.carNumber++
  },
  decrCarNumber(state: userState) {
    state.carNumber--
  },
}

// actions
const actions = {
  // login by login.vue
  login({ commit, dispatch }: ActionContext<userState, userState>, params: any) {
    return new Promise((resolve, reject) => {
      loginApi(params)
      .then(res => {
        commit('tokenChange', res.data)
        resolve(res.data)
        dispatch('getInfo', { token: res })
        .then(infoRes => {
          resolve(res.data)
        })
      }).catch(err => {
        reject(err)
      })
    })
  },
  // get user info after user logined
  getInfo({ commit }: ActionContext<userState, userState>, params: any) {
    return new Promise((resolve, reject) => {
      getInfoApi(params)
      .then(res => {
        commit('infoChange', res.data)
        resolve(res.data)
      })
    })
  },

  // login out the system after user click the loginOut button
  loginOut({ commit }: ActionContext<userState, userState>) {
    loginOutApi()
    .then(res => {
      
    })
    .catch(error => {

    })
    .finally(() => {
      localStorage.removeItem('tabs')
      localStorage.removeItem('vuex')
      sessionStorage.removeItem('vuex')
      location.reload()
    })
  },
  // login out the system after user click the loginOut button
  toNoAuthPage({ commit }: ActionContext<userState, userState>) {
    location.href = '/#/401'
    ElMessage({
      message: '权限认证失败，即将返回登录界面',
      type: 'error',
      duration: 3 * 1000
    })
    setTimeout(() => {
      localStorage.removeItem('tabs')
      localStorage.removeItem('vuex')
      sessionStorage.removeItem('vuex')
      location.reload()
    }, 3000)
  }
}

export default {
  namespaced: true,
  state,
  actions,
  getters,
  mutations
}
