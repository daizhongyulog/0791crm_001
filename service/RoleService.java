package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.Role;
import com.yjxxt.crm.mapper.RoleMapper;
import com.yjxxt.crm.query.RoleQuery;
import com.yjxxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {

    @Autowired(required = false)
    private RoleMapper roleMapper;


    /**
     * 查询所有的角色的信息
     * @return
     */
    public List<Map<String,Object>> findRoles(Integer userId){

        return roleMapper.selectRoles(userId);
    }

    /**
     * 角色的条件查询以及分页
     * @param roleQuery
     * @return
     */
    public Map<String,Object> findRoleByParam(RoleQuery roleQuery){
        //实例化对象
        Map<String,Object> map=new HashMap<String, Object>();
        //开启分页单位
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getLimit());
        PageInfo<Role> rlist=new PageInfo<>(selectByParams(roleQuery));
        //准备数据
        map.put("code",0);
        map.put("mag","success");
        map.put("count",rlist.getTotal());
        map.put("data",rlist.getList());


        //放回目标map
        return map;
    }

    /**
     * 一：验证
     * 1.角色名非空
     * 2.角色名唯一
     * 二：默认参数
     *      is_valid=1
     *      createDate
     *      updateDate
     * 三：添加成功与否
     * @param role
     */
    public void addRole(Role role){
        //1.角色名非空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名称");
        //2.角色名唯一
        Role temp = roleMapper.selectRoleByName(role.getRoleName());
        AssertUtil.isTrue(temp!=null,"角色已存在");
        //3.设定默认值
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());


        //4.添加成功与否
        AssertUtil.isTrue(insertHasKey(role)<1,"添加失败了");

    }

    /**
     * 一：验证
     *  id验证
     * 1.角色名非空
     * 2.角色名唯一
     * 二：默认参数
     *      is_valid=1
     *      createDate
     *      updateDate
     * 三：添加成功与否
     * @param role
     */
    public void changeRole(Role role){
        //验证当前对象是否存在
        Role temp = roleMapper.selectByPrimaryKey(role.getId());
        AssertUtil.isTrue(temp==null,"待修改记录不存在");
        //2.角色名唯一
        Role temp2 = roleMapper.selectRoleByName(role.getRoleName());
        AssertUtil.isTrue(temp!=null && !(temp2.getId().equals(role.getId())),"角色已存在");
        //3.设定默认值
        role.setUpdateDate(new Date());
        //修改是否成功
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"修改失败了");
    }
}
