package com.ciel.springcloudasso.controller;

import com.ccc.outside.Xia;
import com.ciel.entity.UserEntity;
import com.ciel.service.UserService;
import com.ciel.springcloudasso.service.GetCussLoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "主页管理")
@RestController
@SessionAttributes("/user")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private GetCussLoginUser getCussLoginUser;

    @Autowired
    private Xia xia;

    @Autowired
    private RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/rest")
    public Map rest(@ModelAttribute("md") Map map2, Model model,@RequestParam(required=false) String hha){

        Map<String, Object> stringObjectMap = model.asMap();

        Map map = new HashMap();
        map.put("name","xiapeixin");
        return map;
    }

    @ApiOperation(value = "登录页面",notes = "必须要登录")
    @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "用户编号", required = true, example = "1")
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
    public Map test2(Principal principal, Authentication authentication,HttpServletRequest request) {
        //获取当前登录用户信息; 的三种方式
        String name = principal.getName();
        String name1 = authentication.getName();
        Principal userPrincipal = request.getUserPrincipal();

        User o = getCussLoginUser.getloginUser(); //获取当前登录用户

        getCussLoginUser.getloginAuthentication();

        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(new HashMap<>());
        objectObjectMap.put("name","xia_pei_xin");
        return objectObjectMap;
    }

//    @RolesAllowed({"USER","ADMIN"})该方法只要具有“USER","ADMIN"任意一种权限就可以访问。" +
//            "这里可以省略前缀ROLE_，实际的权限可能是ROLE_ADMIN
//    @PermitAll允许所有访问
//   @DenyAll 拒绝所有访问

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


//    hasRole([role])	当前账户有指定角色时返回true, 默认情况下，角色都是以ROLE_开头，当然也可以在修改DefaultWebSecurityExpressionHandler中修改defaultRolePrefix自定义角色前缀
//* hasAnyRole([role1,role2])	当前账户有指定角色中的任意一个时返回true, 默认情况下，角色都是以ROLE_开头，当然也可以在修改DefaultWebSecurityExpressionHandler中修改defaultRolePrefix自定义角色前缀
//    hasAuthority([authority])	当前账户有指定权限时返回true
//    hasAnyAuthority([authority1,authority2])	当前账户有指定权限中任何一个时返回true
//    principal	允许当前用户直接访问的对象主体
//    authentication	允许直接访问从SecurityContext获得的当前身份验证对象
//    permitAll	允许所有
//    denyAll	拒绝所有
//    isAnonymous()	是否匿名用户
//    isRememberMe()	当前是否被记住
//* isAuthenticated()	是否已经登录
//    isFullyAuthenticated()	是否已经登录 或 被记住
//* hasPermission(Object target, Object permission)	Returns true if the user has access to the provided target for the given permission. For example, hasPermission(domainObject, ‘read’)
//            * hasPermission(Object targetId, String targetType, Object permission)	Returns true if the user has access to the provided target for the given permission. For example, hasPermission(1, ‘com.example.domain.Message’, ‘read’)
//    hasIpAddress([ip address])	IP地址是否是？？？


    @RequestMapping("/boot")
    public ModelAndView boot(){
        return new ModelAndView("fram/boot");
    }

}
