package com.leo.labs.oauth2.provider.web.bak;
//package com.leo.labs.oauth2.provider.web.service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.catalina.authenticator.Constants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.leo.labs.oauth2.provider.web.config.MyUserDetails;
//
//@Service("userInfo")
//public class UserInfoService implements UserDetailsService{
//    
//    @Autowired
//    private UserMapper userMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // TODO Auto-generated method stub
//        Map<String, String> paramMap=new HashMap<String, String>();
//        paramMap.put("loginId", username);
//        User user=userMapper.getUserByloginIds(paramMap);
//        
//        if(user ==null) {
//            throw new BadCredentialsException(Constants.getReturnStr(Constants.USER_NOT_FOUND, Constants.USER_NOT_FOUND_TIPS));
//        }
//        MyUserDetails userDetails = new MyUserDetails();
//        userDetails.setUserName(username);
//        userDetails.setPassword(user.getPassword());
//        userDetails.setUser(user);
//        return userDetails;
//    }
//
//}