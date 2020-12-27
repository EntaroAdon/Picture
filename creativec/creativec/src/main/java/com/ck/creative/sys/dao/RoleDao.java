package com.ck.creative.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ck.creative.common.MyBatisDao;
import com.ck.creative.sys.bean.Role;

@MyBatisDao
public interface RoleDao {
	/**
	 * 查询
	 * @param role
	 * @return
	 */
	public List<Role> findList(Role role);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public int insert(Role role);
	
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	public int update(Role role);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public int delete(@Param("ids") String[] ids);
}
