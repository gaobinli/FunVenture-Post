
import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/food', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/food/' + data,
        method: 'delete',
    })
}

export function list() {
    return request({
        url: '/food/list',
        method: 'get',
    })
}

export function add(data: object) {
    return request({
        url: '/food',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/food/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/food/' + id,
        method: 'patch',
        data
    })
}

export function one(id: number) {
    return request({
        url: bulidURL('/food/' + id),
        method: 'get',
    })
}