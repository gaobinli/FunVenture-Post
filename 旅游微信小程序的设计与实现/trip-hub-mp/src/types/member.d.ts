/** 通用的用户信息 */
type BaseProfile = {
  /** 用户ID */
  id: number
  /** 头像  */
  avatar: string
  /** 账户名  */
  phone: string
  /** 昵称 */
  nickName?: string
  /** 余额 */
  balance?: number
}

/** 小程序登录 登录用户信息 */
export type LoginResult = BaseProfile & {
  /** 登录凭证 */
  token: string
}

/** 个人信息 用户详情信息 */
export type ProfileDetail = BaseProfile & {
  /** 性别 */
  sex?: number
}