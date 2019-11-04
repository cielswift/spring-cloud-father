package com.ciel.springcloudasso.controller;

import com.ccc.outside.Xia;
import com.ciel.entity.UserEntity;
import com.ciel.service.UserService;
import com.ciel.springcloudasso.service.GetCussLoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@SessionAttributes("/user")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private GetCussLoginUser getCussLoginUser;

    @Autowired
    private Xia xia;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public ModelAndView index(HttpServletRequest request){
        String path = request.getServletPath();
        logger.warn(path);
        ModelAndView ma = new ModelAndView();
        ma.setViewName("fram/login");
        return ma;
    }

    @RequestMapping("/log")
    public String log(UserEntity userEntity){

        UserEntity byName = userService.findByName(userEntity.getName());
        if(byName != null){
            return "ok";
        }else{
            return "defeat";
        }

    }

    @RequestMapping("/defeat")
    public ModelAndView defeat(){
        ModelAndView ma = new ModelAndView();
        ma.addObject("msg","用户名或密码错误");
        ma.setViewName("fram/login");
        return ma;
    }

    @RequestMapping("/regis")
    public ModelAndView regis(HttpServletRequest request){
        String path = request.getServletPath();
        logger.warn(path);
        ModelAndView ma = new ModelAndView();
        ma.setViewName("fram/regis");
        return ma;
    }


    @RequestMapping("/reg")
    public String reg(UserEntity userEntity){
        UserEntity byName = userService.findByName(userEntity.getName());
        if(byName==null){
            UserEntity save = userService.save(userEntity);
            return "ok";
        }else{
           return "defeat";
        }
    }

    @RequestMapping("/love")
    public String test3(){
        return "i lovo you";
    }

    @RequestMapping("/hello")
    public ModelAndView hello() {
       return new ModelAndView("fram/hello");
    }

    @Secured("ROLE_BOSS")  //基于注解的访问控制,参数可以以ROLE_开头。
    @RequestMapping("/like")
    public String test4(){
        return "i like you";
    }


    @PreAuthorize("@myServiceImpl.hasPermission(#request,#authentication)")
    @RequestMapping("/test1")
    //表示访问方法或类在执行之前先判断权限,参数和access()方法参数取值相同，都是权限表达式。
    //自定义判断必须返回true或false; 在注解上需要加#获取请求参数, 配置中和页面上不用;

    //比如@PreAuthorize("principal.username.equals(#username)") 限制只能查询自己的信息

//    使用@PreFilter和@PostFilter可以对集合类型的(参数)或(返回值)进行过滤。使用@PreFilter和@PostFilter时，
//    Spring Security将移除使对应表达式的结果为false的元素。

    //@PostFilter("filterObject.id%2==0"） //过滤返回结果,返回结果是一个集合 ;filterObject表示集合的当前对象
    //@PreFilter(filterTarget="ids", value="filterObject%2==0") //对参数进行过滤,参数是一个集合,参数名叫ids;

    public String test1(HttpServletRequest request,Authentication authentication) {
        return "test1";
    }




    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/test2")
    public String test2(Principal principal, Authentication authentication,HttpServletRequest request) {
//获取当前登录用户信息; 的三种方式
        String name = principal.getName();
        String name1 = authentication.getName();
        Principal userPrincipal = request.getUserPrincipal();


        User o = getCussLoginUser.getloginUser(); //获取当前登录用户

        getCussLoginUser.getloginAuthentication();

        return "test2";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping("/test6")
    public String test6() {
        return "test6";
    }

    @PreAuthorize("hasRole('ADMIN')") //使用hasRole去设置权限，那么必须是以ROLE_开头的角色。这里省略ROLE_即可
    @RequestMapping("/test7")
    public String test7() {
        return "test7";
    }

    @PostAuthorize("hasAuthority('USER')")
    //表示方法或类执行结束后判断权限，此注解很少被使用到。
    @RequestMapping("/test5")
    public String aa(){
        return "aaaa";
    }


    @PreAuthorize("hasIpAddress('127.0.0.1')")
    @RequestMapping("/test8")
    public String test8() {
        return "test8";
    }

    @RequestMapping("/boot")
    public ModelAndView boot(){
        return new ModelAndView("fram/boot");
    }

}
