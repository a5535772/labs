/oauth/authorize：授权端点
/oauth/token：令牌端点
/oauth/confirm_access：用户确认授权提交端点
/oauth/error：授权服务错误信息端点
/oauth/check_token：用于资源服务访问的令牌解析端点
/oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话

http://127.0.0.1:8080/oauth/authorize
 ?response_type=code
 &client_id=leo-lab-client-id
 &redirect_uri=http://www.baidu.com
 &scope=all
 
 
 重要的类
★★★★★ 
类：org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint 
作用:管理 /oauth/authorize 流程

★★★★★ 
类：org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
作用:管理 /oauth/token 流程

★★
类：org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint
作用:管理 /oauth/confirm_access 流程
 
类：org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices
类：org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices