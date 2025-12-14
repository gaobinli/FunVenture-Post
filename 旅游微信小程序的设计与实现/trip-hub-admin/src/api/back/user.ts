
import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/user', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/user/' + data,
        method: 'delete',
    })
}

export function add(data: object) {
    return request({
        url: '/user',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/user/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/user/' + id,
        method: 'patch',
        data
    })
}

export function userList() {
    return request({
        url: '/user/list',
        method: 'get',
    })
}