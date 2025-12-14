import type { LoginResult } from '@/types/member'
import { http } from '@/utils/http'

type LoginParams = {
  username: string
  password: string
}

export const postQuickLoginAPI = (username: string) => {
  return http<LoginResult>({
    method: 'POST',
    url: '/user/quick/login',
    data: {
      username,
    },
  })
}

export const postNormalLoginAPI = (data: LoginParams) => {
  return http<LoginResult>({
    method: 'POST',
    url: '/user/login',
    data
  })
}