package cn.offcn.service.impl;

import cn.offcn.entity.RoleSourcesExample;
import cn.offcn.entity.Sources;
import cn.offcn.entity.SourcesExample;
import cn.offcn.mapper.RoleSourcesMapper;
import cn.offcn.mapper.SourcesMapper;
import cn.offcn.service.SourcesService;
import cn.offcn.utils.JsonUtils;
import cn.offcn.utils.OAResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class SourcesServiceImpl implements SourcesService {

    @Autowired
    private SourcesMapper sourcesMapper;

    @Autowired
    private RoleSourcesMapper roleSourcesMapper;

    @Autowired
    private JedisPool jedisPool;

    public List<Sources> getRootSources(int pid) {

        SourcesExample sourcesExample=new SourcesExample();
        SourcesExample.Criteria criteria = sourcesExample.createCriteria();
        criteria.andPidEqualTo(pid);
        List<Sources> sourcesList = sourcesMapper.selectByExample(sourcesExample);
        for (Sources sources : sourcesList) {
            List<Sources> subSourcesList=getRootSources(sources.getId());
            sources.setChildren(subSourcesList);
        }
        return sourcesList;
    }

    @Override
    public List<Sources> getParentNodes(List<Sources> parentNodeList) {
        SourcesExample sourcesExample=new SourcesExample();
        List<Sources> sourcesList = sourcesMapper.selectByExample(sourcesExample);
        for (Sources sources : sourcesList) {
            if(isParentNode(sources)){
                parentNodeList.add(sources);
            }
        }
        return parentNodeList;
    }

    public boolean isParentNode(Sources sources){
        SourcesExample sourcesExample=new SourcesExample();
        SourcesExample.Criteria criteria = sourcesExample.createCriteria();
        criteria.andPidEqualTo(sources.getId());
        List<Sources> sourcesList = sourcesMapper.selectByExample(sourcesExample);
        if(sourcesList!=null && sourcesList.size()>0){
            return true;
        }
        return false;
    }
    public OAResult addSources(Sources sources){
        int rows=sourcesMapper.insert(sources);
        if(rows==1){
            return OAResult.ok(200,"添加成功");
        }
        return OAResult.ok(400,"添加失败");
    }

    public Sources getParentNodeById(int id){
       return  sourcesMapper.selectByPrimaryKey(id);
    }

    @Override
    public OAResult updateSource(Sources sources) {
        int rows=sourcesMapper.updateByPrimaryKey(sources);
        if(rows==1){
            return OAResult.ok(200,"修改成功");
        }
        return OAResult.ok(400,"修改失败");
    }

    @Override
    public OAResult deleteSourceById(int id) {
        //删除中间表的数据
        RoleSourcesExample roleSourcesExample=new RoleSourcesExample();
        RoleSourcesExample.Criteria roleSourcesExampleCriteria = roleSourcesExample.createCriteria();
        roleSourcesExampleCriteria.andResourcesFkEqualTo(id);
        roleSourcesMapper.deleteByExample(roleSourcesExample);
        //删除资源
        int rows=sourcesMapper.deleteByPrimaryKey(id);
        if(rows==1){
            return OAResult.ok(200,"删除成功");
        }
        return OAResult.ok(400,"删除失败");

    }

    @Override
    public List<Sources> getEmployeeBySources(int eid,String username, int pid) {

        Jedis jedis=null;
        try {
            //先从redis中获取资源
            jedis=jedisPool.getResource();
            String jsonData=jedis.hget("menu",username);
            if(StringUtils.isNotBlank(jsonData)){
                return JsonUtils.jsonToList(jsonData,Sources.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(jedis!=null) jedis.close();
        }

        //查询数据库
        List<Sources> sourcesList=findCurrentEmployeeSources(eid,pid);

        //往redis中放数据

        try {
            jedis=jedisPool.getResource();
            jedis.hset("menu",username, JsonUtils.objectToJson(sourcesList));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(jedis!=null) jedis.close();
        }
        return sourcesList;
    }

    public List<Sources> findCurrentEmployeeSources(int eid, int pid){
        List<Sources> sourcesList= sourcesMapper.getCurrentEmployeeSources(eid,pid);
        for (Sources sources : sourcesList) {
            List<Sources> subSourceList= findCurrentEmployeeSources(eid,sources.getId());
            sources.setChildren(subSourceList);
        }
        return sourcesList;
    }
}
