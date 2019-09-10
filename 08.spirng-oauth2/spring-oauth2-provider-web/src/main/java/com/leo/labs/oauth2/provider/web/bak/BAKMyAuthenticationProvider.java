//package com.leo.labs.oauth2.provider.web.bak;
//
//import java.util.Collection;
//
//import javax.annotation.Resource;
//
//import org.springframework.context.support.MessageSourceAccessor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.SpringSecurityMessageSource;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BAKMyAuthenticationProvider extends DaoAuthenticationProvider  {
//
//
//    /** 规则校验 */
//    @Resource(name = "passwordService")
//    private PasswordService passwordService;
//
//    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
//
//    // 构造函数中注入
//    @Inject
//    public BAKMyAuthenticationProvider(UserDetailsService userDetailsService)
//    {
//        this.setUserDetailsService(userDetailsService);
//    }
//
//    /**
//     * 自定义验证方式
//     */
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = (String) authentication.getCredentials();
//        
//        MyUserDetails userDetails = (MyUserDetails) 
//                this.getUserDetailsService().loadUserByUsername(username);
//
//        //按登录规则校验用户
//        passwordService.validateRules(userDetails.getUser(), password);
//
//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(JSON.toJSONString(userDetails,SerializerFeature.WriteMapNullValue), password, authorities); 
//        return authenticationToken;
//        
//    }
//
//    @Override
//    public boolean supports(Class<?> arg0) {
//        return true;
//    }
//
//}
