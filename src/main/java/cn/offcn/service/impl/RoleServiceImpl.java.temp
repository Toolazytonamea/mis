package cn.offcn.service.impl;

import cn.offcn.entity.*;
import cn.offcn.mapper.EmpRoleMapper;
import cn.offcn.mapper.RoleMapper;
import cn.offcn.mapper.RoleSourcesMapper;
import cn.offcn.service.RoleService;
import cn.offcn.utils.LayuiTable;
import cn.offcn.utils.OAResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleSourcesMapper roleSourcesMapper;
    @Autowired
    private EmpRoleMapper empRoleMapper;
    @Override
    public OAResult addRole(Role role, int[] sourecesIds) {

        //保存角色
        int rows=roleMapper.insert(role);
        int count=0;
        if(sourecesIds!=null && sourecesIds.length>0){
            for (int sourecesId : sourecesIds) {
                RoleSources roleSources=new RoleSources();
                roleSources.setRoleFk(role.getRoleid());
                roleSources.setResourcesFk(sourecesId);
                //往中间表中插入记录
                roleSourcesMapper.insert(roleSources);
                count++;
            }
        }
        if(rows==1 && count==sourecesIds.length){
            return OAResult.ok(200,"添加成功");
        }

        return OAResult.ok(400,"添加失败");
    }

    @Override
    public LayuiTable<Role> getAllRoles(int page,int limit) {

        PageHelper.startPage(page,limit);
        RoleExample roleExample=new RoleExample();
        List<Role> roleList=roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo=new PageInfo<>(roleList);
        LayuiTable<Role> layuiTable=new LayuiTable<>();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount(pageInfo.getTotal());
        layuiTable.setData(pageInfo.getList());
        return layuiTable;
    }

    @Override
    public Map<String, Object> getRoleById(int roleid) {

        Role role=roleMapper.selectByPrimaryKey(roleid);
        RoleSourcesExample roleSourcesExample=new RoleSourcesExample();
        RoleSourcesExample.Criteria roleSourcesExampleCriteria = roleSourcesExample.createCriteria();
        roleSourcesExampleCriteria.andRoleFkEqualTo(roleid);
        List<RoleSources> roleSourcesList = roleSourcesMapper.selectByExample(roleSourcesExample);
        Map<String,Object> map=new HashMap<>();
        map.put("role",role);
        map.put("roleSourcesList",roleSourcesList);
        return map;
    }

    @Override
    public OAResult updateRole(Role role, int[] sourecesIds) {

        //1.修改role
        int rows=roleMapper.updateByPrimaryKey(role);
        //2.删除role_sources中间表中的当前角色的数据
        RoleSourcesExample roleSourcesExample=new RoleSourcesExample();
        RoleSourcesExample.Criteria roleSourcesExampleCriteria = roleSourcesExample.createCriteria();
        roleSourcesExampleCriteria.andRoleFkEqualTo(role.getRoleid());
        roleSourcesMapper.deleteByExample(roleSourcesExample);

        //重新往中间表中插入数据
        int count=0;
        if(sourecesIds!=null && sourecesIds.length>0){
            for (int sourecesId : sourecesIds) {
                RoleSources roleSources=new RoleSources();
                roleSources.setRoleFk(role.getRoleid());
                roleSources.setResourcesFk(sourecesId);
                //往中间表中插入记录
                roleSourcesMapper.insert(roleSources);
                count++;
            }
        }
        if(rows==1 && count==sourecesIds.length){
            return OAResult.ok(200,"更新成功");
        }

        return OAResult.ok(400,"更新失败");
    }

    @Override
    public OAResult deleteRoleById(int roleid) {

        //删除emp_role中间表的数据
        EmpRoleExample empRoleExample=new EmpRoleExample();
        EmpRoleExample.Criteria empRoleExampleCriteria = empRoleExample.createCriteria();
        empRoleExampleCriteria.andRoleFkEqualTo(roleid);
        empRoleMapper.deleteByExample(empRoleExample);

        //删除role_sources中间表数据
        RoleSourcesExample roleSourcesExample=new RoleSourcesExample();
        RoleSourcesExample.Criteria roleSourcesExampleCriteria = roleSourcesExample.createCriteria();
        roleSourcesExampleCriteria.andRoleFkEqualTo(roleid);
        roleSourcesMapper.deleteByExample(roleSourcesExample);

        //删除角色
        int rows=roleMapper.deleteByPrimaryKey(roleid);
        if(rows==1){
            return OAResult.ok(200,"删除成功");
        }
        return OAResult.ok(400,"删除失败");
    }

    @Override
    public List<Role> queryAllRoles() {
        return roleMapper.selectByExample(new RoleExample());
    }
}
