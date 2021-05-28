package cn.offcn.controller;

import cn.offcn.entity.Dept;
import cn.offcn.service.DeptService;
import cn.offcn.utils.LayuiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/{page}")
    public String forwardPage(@PathVariable("page") String page, Model model, Integer deptno){
        model.addAttribute("deptno",deptno);
        return "pages/"+page;
    }

    @ResponseBody
    @RequestMapping("/getAllDepts")
    public LayuiTable<Dept> getAllDepts(int page,int limit){

        return deptService.getAllDepts(page,limit);
    }
    @ResponseBody
    @RequestMapping("/queryAllDepts")
    public List<Dept> queryAllDepts(){
        return deptService.queryAllDepts();
    }
}
