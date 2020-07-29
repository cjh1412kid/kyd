package com.nuite.manager.controller;

import com.nuite.manager.entity.Menu;
import com.nuite.manager.entity.Role;
import com.nuite.manager.entity.User;
import com.nuite.manager.service.MenuService;
import com.nuite.manager.service.UserService;
import com.nuite.manager.util.Const;
import com.nuite.manager.util.RightsHelper;
import com.nuite.manager.util.StringUtils;
import com.nuite.manager.util.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

    /**
     * 访问登录页
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return "login";
    }

    /**
     * 请求登录，验证用户
     *
     * @param session
     * @param loginname
     * @param password
     * @param code
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(HttpSession session, @RequestParam String loginname, @RequestParam String password, @RequestParam String code) {
        String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE);
        ModelAndView mv = new ModelAndView();
        String errInfo = "";
        System.out.println(loginname + "=============sdfdsf==============" + password);
        if (Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)) {
            String md5RandomKey = (String) session.getAttribute("md5RandomKey");//得到 md5加密随机密码
            System.out.println("后台从session中获取的md5加密随机数：" + md5RandomKey);
            
            User user = userService.getUserByNameAndUser(loginname);//得到 当前登陆 用户密码
            if (user != null) {
                String pwd = user.getPassword();//未加密 密码
                System.out.println("用户实际密码为：" + pwd);
                String real_pwd = StringUtils.getMD5Str(pwd + md5RandomKey, null);//实际 密码 MD5加密后的密码
                System.out.println("后台使用md5加密后的密码为：" + real_pwd);
                if (real_pwd.equals(password)) {
                    user.setLastLogin(new Date());
                    userService.updateLastLogin(user);
                    session.setAttribute(Const.SESSION_USER, user);
                    session.removeAttribute(Const.SESSION_SECURITY_CODE);
                } else {
                    errInfo = "用户名或密码有误！";
                }
            } else {
                errInfo = "用户名或密码有误！";
            }
        } else {
            errInfo = "验证码输入有误！";
        }
        if (Tools.isEmpty(errInfo)) {
            mv.setViewName("redirect:index.html");
        } else {
            mv.addObject("errInfo", errInfo);
            mv.addObject("loginname", loginname);
            mv.addObject("password", "");
            mv.setViewName("login");
        }
        return mv;
    }

    /**
     * 访问系统首页
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(HttpSession session, Model model) {
        User user = (User) session.getAttribute(Const.SESSION_USER);
        user = userService.getUserAndRoleById(user.getUserId());
        Role role = user.getRole();
        String roleRights = role != null ? role.getRights() : "";
        String userRights = user.getRights();
        //避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
        session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); //将角色权限存入session
        session.setAttribute(Const.SESSION_USER_RIGHTS, userRights); //将用户权限存入session

        List<Menu> menuList = menuService.listAllMenu();
        if (Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)) {
            for (Menu menu : menuList) {
                menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMenuId()) || RightsHelper.testRights(roleRights, menu.getMenuId()));
                if (menu.isHasMenu()) {
                    List<Menu> subMenuList = menu.getSubMenu();
                    for (Menu sub : subMenuList) {
                        sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMenuId()) || RightsHelper.testRights(roleRights, sub.getMenuId()));
                    }
                }
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("menuList", menuList);
        return "index";
    }

    /**
     * 进入首页后的默认页面
     *
     * @return
     */
    @RequestMapping(value = "/default")
    public String defaultPage() {
        return "default";
    }

    /**
     * 用户注销
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Const.SESSION_USER);
        session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
        session.removeAttribute(Const.SESSION_USER_RIGHTS);
        return "login";
    }
}
