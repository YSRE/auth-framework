package com.shy.auth;

/**
 * 业务方需要在api模块里提供feignClient实现
 * 账号服务里引入该feignClient,获取用户的业务信息
 * @author shy
 * @date 2023/3/29
 */
public interface AuthClient {

    /**
     * 远程调用的服务名
     * @return
     */
    String serviceName();

    /**
     * 获取用户在业务线里的信息
     * @param userId
     * @return
     */
    AuthUser userInfo(Long userId);
}
