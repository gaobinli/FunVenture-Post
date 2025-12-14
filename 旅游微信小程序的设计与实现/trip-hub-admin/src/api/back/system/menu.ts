import request from '@/utils/system/request'
import bulidURL from '@/utils/system/url'

export function dataList(data: object) {
    return request({
        url: bulidURL('/sysMenu', data),
        method: 'get',
    })
}

export function del(data: string) {
    return request({
        url: '/sysMenu/' + data,
        method: 'delete',
    })
}

export function add(data: object) {
    return request({
        url: '/sysMenu',
        method: 'post',
        data
    })
}

export function update(id: number, data: object) {
    return request({
        url: '/sysMenu/' + id,
        method: 'patch',
        data
    })
}

export function patch(id: number, data: object) {
    return request({
        url: '/sysMenu/' + id,
        method: 'patch',
        data
    })
}

export function getRoleMenuList(roleCode: string) {
    return request({
        url: '/sysMenu/getSysRoleMenuList?roleCode=' + roleCode,
        method: 'get',
    })
}

export function getAllMenuList() {
    return request({
        url: '/sysMenu/getParentList',
        method: 'get',
    })
}

