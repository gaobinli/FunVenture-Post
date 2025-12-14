import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
  return request({
    url: bulidURL('/sysUser', data),
    method: 'get',
  })
}

export function del(data: string) {
return request({
    url: '/sysUser/' + data,
    method: 'delete',
})
}

export function add(data: object) {
return request({
    url: '/sysUser',
    method: 'post',
    data
})
}

export function update(id: number, data: object) {
return request({
    url: '/sysUser/' + id,
    method: 'patch',
    data
})
}

export function patch(id: number, data: object) {
  return request({
    url: '/sysUser/' + id,
    method: 'patch',
    data
  })
}

export function patchPassword(id: number, data: object) {
  return request({
    url: '/sysUser/updatePasssword/' + id,
    method: 'patch',
    data
  })
}

export function loginApi(data: object) {
  return request({
    url: '/admin/login',
    method: 'post',
    data
  })
}

export function verificationCodeApi() {
  return request({
    url: '/captchaImage',
    method: 'get',
  })
}

export function menuTreeApi() {
  return request({
    url: '/sysMenu/permisson/menuTree',
    method: 'get',
  })
}

export function getInfoApi(data: object) {
  return request({
    url: bulidURL('/sysUser/info'),
    method: 'get',
  })
}

export function loginOutApi() {
  return request({
    url: '/logout',
    method: 'get',
  })
}