package cn.offcn.controller;

import cn.offcn.entity.Employee;
import cn.offcn.service.EmployeeService;
import cn.offcn.utils.EmployeeVo;
import cn.offcn.utils.LayuiTable;
import cn.offcn.utils.OAResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/{page}")
    public String forwardPage(@PathVariable("page") String page,Model model,Integer eid){

        model.addAttribute("eid",eid);
        return "pages/"+page;
    }

    @ResponseBody
    @RequestMapping("/getEmployees")
    public LayuiTable<EmployeeVo> getEmployees(int page, int limit,
                                               @RequestParam(defaultValue = "0") int selectType,
                                               @RequestParam(defaultValue = "")String keyword){

        return employeeService.getEmployees(page,limit,selectType,keyword);
    }

    @ResponseBody
    @RequestMapping("/saveEmployee")
    public OAResult saveEmployee(Employee employee, int roleid, MultipartFile picImage){
      return   employeeService.saveEmployee(employee,roleid,picImage);
    }
    @ResponseBody
    @RequestMapping("/getEmployeeById")
    public Employee getEmployeeById(int eid){
        return   employeeService.getEmployeeById(eid);
    }

    @ResponseBody
    @RequestMapping("/updateEmployee")
    public OAResult updateEmployee(Employee employee, int roleid, MultipartFile picImage){
        return   employeeService.updateEmployee(employee,roleid,picImage);
    }
    @ResponseBody
    @RequestMapping("/deleteEmps")
    public OAResult deleteEmps(@RequestParam("eids[]") int[] eids){
        return   employeeService.deleteEmps(eids);
    }
    @ResponseBody
    @RequestMapping("/updateLogo")
    public OAResult updateLogo(MultipartFile picImage,HttpSession session){

        return   employeeService.updateLogo(picImage,session);
    }

    @ResponseBody
    @RequestMapping("/checkPassword")
    public OAResult checkPassword(String password,HttpSession session){
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        Employee employee=(Employee) session.getAttribute("activeUser");
        if(password.equals(employee.getPassword())){
            return OAResult.ok(200,"原密码正确");
        }
        return OAResult.ok(400,"原密码不正确");
    }

    @ResponseBody
    @RequestMapping("/updatePassword")
    public OAResult updatePassword(String password,HttpSession session){

        return   employeeService.updatePassword(password,session);
    }
}
