
import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/order', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/order/' + data,
        method: 'delete',
    })
}

export function list() {
    return request({
        url: '/order/list',
        method: 'get',
    })
}

export function add(data: object) {
    return request({
        url: '/order',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/order/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/order/' + id,
        method: 'patch',
        data
    })
}