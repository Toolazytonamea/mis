package cn.offcn.service;

import cn.offcn.entity.Role;
import cn.offcn.utils.LayuiTable;
import cn.offcn.utils.OAResult;

import java.util.List;
import java.util.Map;

public interface RoleService {
    public OAResult addRole(Role role, int[] sourecesIds);

    public LayuiTable<Role> getAllRoles(int page,int limit);

    public Map<String, Object> getRoleById(int roleid);

    public OAResult updateRole(Role role, int[] sourecesIds);

    public OAResult deleteRoleById(int roleid);

    public List<Role> queryAllRoles();
}
