package com.zhang.yong.demothree.constant;

/**
 * created by CaiBaoHong at 2018/4/18 11:07<br>
 *     响应的code
 */
public interface Codes {
    /** 非法参数 */
    int ILLEGAL = 4400;

    /** 未登录 */
    int UNAUTHEN = 4401;

    /** 未授权，拒绝访问 */
    int UNAUTHZ = 4403;

    /** shiro相关的错误 */
    int SHIRO_ERR = 4444;

    /** 服务端异常 */
    int SERVER_ERR = 5500;

    /** 操作成功*/
    int OK = 1;

    /** 操作失败*/
    int fail = -1;

}
