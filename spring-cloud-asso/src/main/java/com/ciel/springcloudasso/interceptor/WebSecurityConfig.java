package com.ciel.springcloudasso.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity //使配置生效
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true) //认证失败报500
//prePostEnabled 开启SpringSecurity访问控制的注解 ; securedEnabled
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider authenticationProvider; //自定义认证


    //当进行登录时会执行 UsernamePasswordAuthenticationFilter 过滤器。

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler; //自定义403页面

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public PasswordEncoder myPasswordEncoder(){
        return new MyPasswordEncoder();
    } //加密

    @Autowired
    private PersistentTokenRepository repository; //记住我

    @Autowired
    private UserDetailsService userDetailsService; //自定义的登录逻辑


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider);

        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //表单认证
        http.formLogin()
                .loginProcessingUrl("/log")			//设置登录请求url,需要执行UserDetailsServiceImpl
                .loginPage("/login")				//设置自定义登录页面，使用绝对路径

//                .successForwardUrl("/hello")	//设置登陆成功后跳转的请求路径
//                .failureForwardUrl("/defeat")   //认证失败后跳转的url,

       // 将登陆成功/ 失败跳转到对应的 url 的方法修改成登陆成功/ 失败后跳转到对应的 处理器类 的方法
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new  MyFailHandler("/defeat"))

                .usernameParameter("username")         //自定义设置认证表单中用户名的name属性
                .passwordParameter("password");        //自定义设置认证表单中密码的password属性

        //拦截: 相当于授权的过程
        http.authorizeRequests()
                .antMatchers("/login","/regis","/reg").permitAll()
                //请求登录页面的请求不需要认证。有人都可以访问登录页面,一定要配置/login,不然无线重定向;

                .antMatchers("/**/*.jpg").permitAll()   //放行所有为jpg格式的图片
                .antMatchers(HttpMethod.GET,"/css/**","/js/**","/images/**").permitAll()

//                ? 匹配一个字符
//                * 匹配0 个或多个字符
//                ** 匹配0 个或多个目

                .regexMatchers(HttpMethod.POST,".+[.]jpg").permitAll() //使用正则表达式进行匹配,必须是post请求

                .mvcMatchers("/demo").servletPath("/asso").permitAll()
        //servletPath()中配置了servletPath 后，mvcMatchers()直接写SpringMVC 中@RequestMapping()中设置的路径即可。


                //permitAll()表示不需要认证，随意访问。
                //denyAll()表示所匹配的URL 都不允许被访问。
                //anonymous()表示可以匿名访问匹配的URL。和permitAll()效果类似，只是设置为anonymous()的url 会执行filter 链中
                //authenticated()表示所匹配的URL 都需要被认证才能访问
                //fullyAuthenticated(),如果用户不是被remember me(记住我) 的，才可以访问。
                //rememberMe() 被“remember me”的用户允许访问

 /*------------------------------------------------------------------------------------------------------**/
   // 权限
                .antMatchers("/10.jpg").hasAuthority("USER") //设置具有USER 权限时才能访问。

                .antMatchers("/main1.html").hasAnyAuthority("ADMIN","USER") //如果用户具备给定权限中某一个，就允许访问

                .antMatchers("/main2.html").hasRole("abc") //如果用户具备给定角色就允许访问。否则出现403

//        在给用户赋予角色时角色需要以：ROLE_ 开头，后面添加角色名称。return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_abc"));
//        例如：ROLE_abc 其中abc 是角色名，ROLE_是固定的字符开头。
//        使用hasRole()时参数也只写abc 即可。否则启动报错

                .antMatchers("/main.html").hasAnyRole("abC","abc","ABC")  //判断用户是否用户具备给定角色的任意一个，是就允许被访问

                .antMatchers("/main.html").hasIpAddress("127.0.0.1")   //如果请求是指定的IP 就运行访问。

        /*------------------------------------------------------------------------------------------------------**/
        // acc表达式

        //.antMatchers("/login.html").permitAll()   //登陆页面不需要被认证
            .antMatchers("/login.html").access("permitAll")  //使用access实现拦截

         //.antMatchers("/main1.html").hasRole("abc")	//判断用户是否具备给定角色,是就允许访问。
             .antMatchers("/main1.html").access("hasRole(\"abc\")") //使用access实现角色权限验证

            .antMatchers("/love").access("@myServiceImpl.hasPermission(request,authentication)")
                //在access 中通过@bean 的id 名.方法(参数)的形式进行调用 ,配置类中修改如下


             .anyRequest().authenticated();
               //其他所有的请求都必须被认证。必须登录后才能访问。

//        在所有匹配规则中取所有规则的交集。配置顺序影响了之后授权效果，
//        越是具体的应该放在前面，越是笼统的应该放到后面。


        //自定义异常处理页面-403
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);


        //关闭csrf防护，类似于防火墙，不关闭上面的设置不会真正生效。 //关闭认证,让eureka连接
        http.csrf().disable();


        http.rememberMe()
                //.rememberMeParameter("rememberMe")     //修改rememberMe前端复选框的name属性值, 默认为remember-me
                //.tokenValiditySeconds(60)            	 //设置tokenValiditySeconds生效时间, 默认是2周,单位是秒
                .userDetailsService(userDetailsService)		 //登录逻辑交给哪个对象
                .tokenRepository(repository);

        http.logout()
                .logoutSuccessUrl("/login");	   //用户退出成功后 ,跳转的页面
        //.logoutUrl("/logout")                //修改用户退出时,超链接的name属性值,一般不推荐修改
        //会自动清除RememberMe中在数据库中持久化的口令 ,如果需要的话需要重新登陆并勾选

    }

}