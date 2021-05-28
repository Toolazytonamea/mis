package cn.offcn.controller;

import cn.offcn.entity.Sources;
import cn.offcn.service.SourcesService;
import cn.offcn.utils.OAResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private SourcesService sourcesService;

    @RequestMapping("/{page}")
    public String forwardPage(@PathVariable("page") String page, Model model, Integer id){
        model.addAttribute("id",id);
        return "pages/"+page;
    }

    @ResponseBody
    @RequestMapping("/getRootSources")
    public List<Sources> getRootSources(@RequestParam(defaultValue = "0") int pid){

        return sourcesService.getRootSources(pid);
    }

    @ResponseBody
    @RequestMapping("/getParentNodes")
    public List<Sources> getParentNodes(){

        List<Sources> parentNodeList=new ArrayList<Sources>();
        return sourcesService.getParentNodes(parentNodeList);
    }


    @ResponseBody
    @RequestMapping("/addSources")
    public OAResult addSources(Sources sources){

        return sourcesService.addSources(sources);
    }

    @ResponseBody
    @RequestMapping("/getParentNodeById")
    public Sources getParentNodeById(int id){
        return sourcesService.getParentNodeById(id);
    }
    @ResponseBody
    @RequestMapping("/updateSource")
    public OAResult updateSource(Sources sources){
        return sourcesService.updateSource(sources);
    }

    @ResponseBody
    @RequestMapping("/deleteSourceById")
    public OAResult deleteSourceById(int id){

        return sourcesService.deleteSourceById(id);
    }


}
