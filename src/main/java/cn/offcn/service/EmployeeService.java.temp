package cn.offcn.service;

import cn.offcn.entity.Employee;
import cn.offcn.utils.EmployeeVo;
import cn.offcn.utils.LayuiTable;
import cn.offcn.utils.OAResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface EmployeeService {
   public  LayuiTable<EmployeeVo> getEmployees(int page, int limit,int selectType,String keyword);

   public  OAResult saveEmployee(Employee employee, int roleid, MultipartFile picImage);

   public  Employee getEmployeeById(int eid);

    public OAResult updateEmployee(Employee employee, int roleid, MultipartFile picImage);

    public OAResult deleteEmps(int[] eids);

    public OAResult getEmployeeByUsernameAndPassword(String username, String password);

    public OAResult updateLogo(MultipartFile picImage,HttpSession session);

    public OAResult updatePassword(String password, HttpSession session);
}
