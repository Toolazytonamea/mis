package cn.offcn.controller;

import cn.offcn.entity.Employee;
import cn.offcn.entity.Sources;
import cn.offcn.service.SourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/main")
public class IndexController {

    @Autowired
    private SourcesService sourcesService;
    @RequestMapping("/{page}")
    public String forwardPage(@PathVariable("page") String page){

        return "pages/"+page;
    }

    @RequestMapping("/index")
    public String forwardIndex(HttpSession session, Model model){

        Employee employee=(Employee)session.getAttribute("activeUser");
        List<Sources> sourcesList= sourcesService.getEmployeeBySources(employee.getEid(),employee.getUsername(),1);
        model.addAttribute("sourcesList",sourcesList);
        return "pages/index";
    }

}
