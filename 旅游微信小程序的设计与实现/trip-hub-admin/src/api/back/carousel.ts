
import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/carousel', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/carousel/' + data,
        method: 'delete',
    })
}

export function list() {
    return request({
        url: '/carousel/list',
        method: 'get',
    })
}

export function add(data: object) {
    return request({
        url: '/carousel',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/carousel/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/carousel/' + id,
        method: 'patch',
        data
    })
}