
import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/hotel', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/hotel/' + data,
        method: 'delete',
    })
}

export function list() {
    return request({
        url: '/hotel/list',
        method: 'get',
    })
}

export function add(data: object) {
    return request({
        url: '/hotel',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/hotel/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/hotel/' + id,
        method: 'patch',
        data
    })
}

export function one(id: number) {
    return request({
        url: bulidURL('/hotel/' + id),
        method: 'get',
    })
}