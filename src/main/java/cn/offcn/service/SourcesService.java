package cn.offcn.service;

import cn.offcn.entity.Sources;
import cn.offcn.utils.OAResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SourcesService {
    public List<Sources> getRootSources(int pid);

    public List<Sources> getParentNodes(List<Sources> parentNodeList);

    public OAResult addSources(Sources sources);

    public Sources getParentNodeById(int id);

    public OAResult updateSource(Sources sources);

    public OAResult deleteSourceById(int id);

    public List<Sources> getEmployeeBySources(int eid,String username,int pid);


}
