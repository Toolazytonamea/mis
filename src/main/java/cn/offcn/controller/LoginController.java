package cn.offcn.controller;

import cn.offcn.service.EmployeeService;
import cn.offcn.utils.OAResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/{page}")
    public String forwardPage(@PathVariable("page") String page){

        return "pages/"+page;
    }

    @ResponseBody
    @RequestMapping("/checkLogin")
    public OAResult checkLogin(String username, String password, HttpSession session){
        OAResult oaResult=employeeService.getEmployeeByUsernameAndPassword(username,password);
        if(oaResult.getStatus()==200){
            //往session中放键值对
            session.setAttribute("activeUser",oaResult.getData());
        }
        return oaResult;
    }

    @RequestMapping("/exitSystem")
    public String exitSystem( HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

}
