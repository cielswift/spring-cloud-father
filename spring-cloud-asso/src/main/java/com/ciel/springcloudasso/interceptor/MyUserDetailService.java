package com.ciel.springcloudasso.interceptor;

import com.ciel.entity.RoleEntity;
import com.ciel.entity.UserEntity;
import com.ciel.springcloudcommon.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity byName = userDao.findByName(userName);

        if(byName !=null) {
            List<RoleEntity> roles = byName.getRoles();
            List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
            roles.forEach(t -> authorities.add(new SimpleGrantedAuthority(t.getName())));

            return new User(userName, byName.getPassword(), authorities);
        }else{
            return null;
        }

    }

}
