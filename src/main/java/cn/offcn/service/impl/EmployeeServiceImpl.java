package cn.offcn.service.impl;

import cn.offcn.entity.*;
import cn.offcn.mapper.DeptMapper;
import cn.offcn.mapper.EmpRoleMapper;
import cn.offcn.mapper.EmployeeMapper;
import cn.offcn.mapper.RoleMapper;
import cn.offcn.service.EmployeeService;
import cn.offcn.utils.EmployeeVo;
import cn.offcn.utils.LayuiTable;
import cn.offcn.utils.OAResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmpRoleMapper empRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Value("${saveDir}")
    private String saveDir;

    @Override
    public LayuiTable<EmployeeVo> getEmployees(int page, int limit,int selectType,String keyword) {

        PageHelper.startPage(page,limit);
        EmployeeExample employeeExample=new EmployeeExample();

        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        PageInfo<Employee> pageInfo=new PageInfo<>(employeeList);
        LayuiTable<EmployeeVo> layuiTable=new LayuiTable<>();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount(pageInfo.getTotal());
        List<EmployeeVo> employeeVoList=new ArrayList<EmployeeVo>();
        for (Employee employee : pageInfo.getList()) {
             EmployeeVo employeeVo=new EmployeeVo(employee);
             EmpRoleExample empRoleExample=new EmpRoleExample();
             EmpRoleExample.Criteria empRoleExampleCriteria = empRoleExample.createCriteria();
             empRoleExampleCriteria.andEmpFkEqualTo(employee.getEid());
             List<EmpRole> empRoleList = empRoleMapper.selectByExample(empRoleExample);
             if(empRoleList!=null&& empRoleList.size()>0){
                int roleid= empRoleList.get(0).getRoleFk();
                 Role role = roleMapper.selectByPrimaryKey(roleid);
                 employeeVo.setRoleName(role.getRolename());
             }
            Dept dept= deptMapper.selectByPrimaryKey(employee.getDfk());
            employeeVo.setDeptName(dept.getDname());
            employeeVoList.add(employeeVo);
        }
        layuiTable.setData(employeeVoList);
        return layuiTable;
    }

    @Override
    public OAResult saveEmployee(Employee employee, int roleid, MultipartFile picImage) {

        try {
            if(picImage!=null && picImage.getSize()>0){
                // 获取上传文件的名子
               String fileName= picImage.getOriginalFilename();
               //文件的扩展名
               String extName=fileName.substring(fileName.lastIndexOf("."));
               int hashCode= fileName.hashCode();
               int d1=hashCode&0xf;
               int d2=d1>>2&0xf;
               String newSaveDir=saveDir+"/"+d1+"/"+d2;
               File newFileDir=new File(newSaveDir);
               if(!newFileDir.exists()) newFileDir.mkdirs();
               String newFileName=UUID.randomUUID().toString()+extName;
               File newFile=new File(newFileDir, newFileName);
               picImage.transferTo(newFile);
               employee.setPic("/pic/images/"+d1+"/"+d2+"/"+newFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return OAResult.ok(401,"文件上传失败");
        }
        //保存员工
        employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));
        int rows1= employeeMapper.insert(employee);
        //保存员工角色信息
        EmpRole empRole=new EmpRole();
        empRole.setEmpFk(employee.getEid());
        empRole.setRoleFk(roleid);
        int rows2=empRoleMapper.insert(empRole);
        if(rows1==1 && rows2==1){
            return OAResult.ok(200,"保存成功");
        }
        return OAResult.ok(400,"保存失败");
    }

    @Override
    public Employee getEmployeeById(int eid) {
        Employee employee = employeeMapper.selectByPrimaryKey(eid);

        EmpRoleExample empRoleExample=new EmpRoleExample();
        EmpRoleExample.Criteria empRoleExampleCriteria = empRoleExample.createCriteria();
        empRoleExampleCriteria.andEmpFkEqualTo(eid);
        List<EmpRole> empRoleList= empRoleMapper.selectByExample(empRoleExample);
        if(empRoleList!=null && empRoleList.size()>0){
            employee.setRoleid(empRoleList.get(0).getRoleFk());
        }
        return employee;
    }

    @Override
    public OAResult updateEmployee(Employee employee, int roleid, MultipartFile picImage) {

        Employee oldEmployee=employeeMapper.selectByPrimaryKey(employee.getEid());
        try {
            if(picImage!=null && picImage.getSize()>0){
                // 获取上传文件的名子
                String fileName= picImage.getOriginalFilename();
                //文件的扩展名
                String extName=fileName.substring(fileName.lastIndexOf("."));
                int hashCode= fileName.hashCode();
                int d1=hashCode&0xf;
                int d2=d1>>2&0xf;
                String newSaveDir=saveDir+"/"+d1+"/"+d2;
                File newFileDir=new File(newSaveDir);
                if(!newFileDir.exists()) newFileDir.mkdirs();
                String newFileName=UUID.randomUUID().toString()+extName;
                File newFile=new File(newFileDir, newFileName);
                picImage.transferTo(newFile);
                employee.setPic("/pic/images/"+d1+"/"+d2+"/"+newFileName);
            }else{
                employee.setPic(oldEmployee.getPic());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return OAResult.ok(401,"文件上传失败");
        }

        if(StringUtils.isNotBlank(employee.getPassword())){
            employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));
        }else{
            employee.setPassword(oldEmployee.getPassword());
        }


        //更新员工信息
        int rows1= employeeMapper.updateByPrimaryKey(employee);
        //删除该员工emp_role中间表中的数据
        EmpRoleExample empRoleExample=new EmpRoleExample();
        EmpRoleExample.Criteria empRoleExampleCriteria = empRoleExample.createCriteria();
        empRoleExampleCriteria.andEmpFkEqualTo(employee.getEid());
        empRoleMapper.deleteByExample(empRoleExample);
        //重新插入数据
        EmpRole empRole=new EmpRole();
        empRole.setEmpFk(employee.getEid());
        empRole.setRoleFk(roleid);
        int rows2=empRoleMapper.insert(empRole);
        if(rows1==1 && rows2==1){
            return OAResult.ok(200,"更新成功");
        }
        return OAResult.ok(400,"更新失败");

    }

    @Override
    public OAResult deleteEmps(int[] eids) {
        int count=0;
        if(eids!=null && eids.length>0){
            for (int eid : eids) {
                //1. 先删除中间表数据
                EmpRoleExample empRoleExample=new EmpRoleExample();
                EmpRoleExample.Criteria empRoleExampleCriteria = empRoleExample.createCriteria();
                empRoleExampleCriteria.andEmpFkEqualTo(eid);
                empRoleMapper.deleteByExample(empRoleExample);
                //2. 删除员工
                employeeMapper.deleteByPrimaryKey(eid);
                count++;
            }
        }
        if(count==eids.length){
            return OAResult.ok(200,"删除成功");
        }
        return OAResult.ok(400,"删除失败");

    }

    @Override
    public OAResult getEmployeeByUsernameAndPassword(String username, String password) {

        EmployeeExample employeeExample=new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        if(employeeList!=null && employeeList.size()>0){
            Employee employee=employeeList.get(0);
            EmpRoleExample empRoleExample=new EmpRoleExample();
            EmpRoleExample.Criteria empRoleExampleCriteria = empRoleExample.createCriteria();
            empRoleExampleCriteria.andEmpFkEqualTo(employee.getEid());
            List<EmpRole> empRoleList=empRoleMapper.selectByExample(empRoleExample);
            if(empRoleList!=null && empRoleList.size()>0){
                Role role=roleMapper.selectByPrimaryKey(empRoleList.get(0).getRoleFk());
                employee.setRole(role);
                return OAResult.build(200,"登陆成功",employee);
            }
            return OAResult.build(200,"登陆成功",null);
        }
        return OAResult.build(400,"登陆失败",null);
    }

    @Override
    public OAResult updateLogo(MultipartFile picImage,HttpSession session) {
        Employee employee=(Employee)session.getAttribute("activeUser");
        try {
            if(picImage!=null && picImage.getSize()>0){
                // 获取上传文件的名子
                String fileName= picImage.getOriginalFilename();
                //文件的扩展名
                String extName=fileName.substring(fileName.lastIndexOf("."));
                int hashCode= fileName.hashCode();
                int d1=hashCode&0xf;
                int d2=d1>>2&0xf;
                String newSaveDir=saveDir+"/"+d1+"/"+d2;
                File newFileDir=new File(newSaveDir);
                if(!newFileDir.exists()) newFileDir.mkdirs();
                String newFileName=UUID.randomUUID().toString()+extName;
                File newFile=new File(newFileDir, newFileName);
                picImage.transferTo(newFile);
                employee.setPic("/pic/images/"+d1+"/"+d2+"/"+newFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return OAResult.ok(401,"文件上传失败");
        }
        return OAResult.build(200,"修改成功",employee.getPic());
    }

    @Override
    public OAResult updatePassword(String password, HttpSession session) {
        Employee employee=(Employee) session.getAttribute("activeUser");
        employee.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        int rows=employeeMapper.updateByPrimaryKey(employee);
        if(rows==1){
            return OAResult.ok(200,"修改成功");
        }
        return OAResult.ok(400,"修改失败");
    }
}
