import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/sysRole', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/sysRole/' + data,
        method: 'delete',
    })
}

export function add(data: object) {
    return request({
        url: '/sysRole',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/sysRole/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/sysRole/' + id,
        method: 'patch',
        data
    })
}

export function roleList() {
  return request({
    url: '/sysRole/getAllRoleList',
    method: 'get',
  })
}

export function getUserRoleList(id: number) {
  return request({
    url: '/sysRole/getUserRoleList?userId=' + id,
    method: 'get',
  })
}