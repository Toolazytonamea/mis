package cn.offcn.service.impl;

import cn.offcn.entity.Dept;
import cn.offcn.entity.DeptExample;
import cn.offcn.mapper.DeptMapper;
import cn.offcn.service.DeptService;
import cn.offcn.utils.LayuiTable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Override
    public LayuiTable<Dept> getAllDepts(int page, int limit) {

        PageHelper.startPage(page,limit);
        DeptExample deptExample=new DeptExample();
        List<Dept> deptList = deptMapper.selectByExample(deptExample);
        PageInfo<Dept> pageInfo=new PageInfo<>(deptList);
        LayuiTable<Dept> layuiTable=new LayuiTable<>();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount(pageInfo.getTotal());
        layuiTable.setData(pageInfo.getList());
        return layuiTable;
    }

    @Override
    public List<Dept> queryAllDepts() {
        return deptMapper.selectByExample(new DeptExample());
    }
}
