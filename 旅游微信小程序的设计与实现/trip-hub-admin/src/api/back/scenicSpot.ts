
import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/scenicSpot', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/scenicSpot/' + data,
        method: 'delete',
    })
}

export function scenicSpotList() {
    return request({
        url: '/scenicSpot/list',
        method: 'get',
    })
}

export function add(data: object) {
    return request({
        url: '/scenicSpot',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/scenicSpot/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/scenicSpot/' + id,
        method: 'patch',
        data
    })
}

export function one(id: number) {
    return request({
        url: bulidURL('/scenicSpot/' + id),
        method: 'get',
    })
}