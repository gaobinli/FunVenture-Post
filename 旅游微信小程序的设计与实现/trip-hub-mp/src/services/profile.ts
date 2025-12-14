import type { ProfileDetail, BaseProfile } from '@/types/member'
import { http } from '@/utils/http'


/**
 * 获取个人信息
 * @returns 
 */
export const getMemberProfileAPI = () => {
    return http<BaseProfile>({
        method: 'GET',
        url: '/user/getUserInfo'
    })
}

/**
 * 修改个人信息
 * @returns 
 */
export const patchMemberProfileAPI = (data: ProfileDetail) => {
    return http<ProfileDetail>({
        method: 'PATCH',
        url: '/user/' + data.id,
        data
    })
}

export const postMemberProfileAPI = (data: ProfileDetail) => {
    return http<ProfileDetail>({
        method: 'POST',
        url: '/user/register',
        data
    })
}

/**
 * 更新用户余额
 * @param userId 用户ID
 * @param balance 新余额
 * @returns
 */
export const patchUserBalanceAPI = (userId: string, balance: number) => {
    return http<ProfileDetail>({
        method: 'PATCH',
        url: '/user/' + userId,
        data: {
            balance
        }
    })
}