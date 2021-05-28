package cn.offcn.controller;

import cn.offcn.entity.Role;
import cn.offcn.service.RoleService;
import cn.offcn.utils.LayuiTable;
import cn.offcn.utils.OAResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/{page}")
    public String forwardPage(@PathVariable("page") String page, Model model, Integer roleid){
        model.addAttribute("roleid",roleid);
        return "pages/"+page;
    }

    @ResponseBody
    @RequestMapping("/addRole")
    public OAResult addRole(Role role,@RequestParam("sourecesIds[]") int[] sourecesIds){

        return roleService.addRole(role,sourecesIds);
    }


    @ResponseBody
    @RequestMapping("/getAllRoles")
    public LayuiTable<Role> getAllRoles(int page,int limit){


        return roleService.getAllRoles(page,limit);
    }

    @ResponseBody
    @RequestMapping("/getRoleById")
    public Map<String,Object> getRoleById(int roleid){

        return roleService.getRoleById(roleid);
    }

    @ResponseBody
    @RequestMapping("/updateRole")
    public OAResult updateRole(Role role,@RequestParam("sourecesIds[]") int[] sourecesIds){

        return roleService.updateRole(role,sourecesIds);
    }

    @ResponseBody
    @RequestMapping("/deleteRoleById")
    public OAResult deleteRoleById(int roleid){
        return roleService.deleteRoleById(roleid);
    }

    @ResponseBody
    @RequestMapping("/queryAllRoles")
    public List<Role> queryAllRoles(){
        return roleService.queryAllRoles();
    }
}
