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
            authorities.add(new SimpleGrantedAuthority("ROLE_BOSS"));
            //List<GrantedAuthority> admin = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");

            return new User(userName, byName.getPassword(), authorities);
        }else{
            throw new UsernameNotFoundException("找不到此用户");
        }

    }
    //默认情况下表单name属性必须为username和password。

//    isAccountNonExpired() 是否过期
//    isAccountNonLocked() 是否锁定
//    isCredentialsNonExpired() 凭据是否过期
//    isEnabled() 是否可用

}
