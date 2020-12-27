package com.ck.creative.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.Role;
import com.ck.creative.sys.dao.RoleDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 查询列表
	 * @param role
	 * @return 
	 */
	public Page<Role> findList(Role role){
		int pageNum = role.getPageNum();
		int pageAmount = role.getPageAmount();
		Page<Role> page = PageHelper.startPage(pageNum, pageAmount);
		roleDao.findList(role);
		return page;
	}

	/**
	 * 不分页查询列表
	 * @param role
	 * @return 
	 */
	public List<Role> findAllList(Role role){
		List<Role> page = roleDao.findList(role);
		return page;
	}
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public int insert(Role role) {
		return roleDao.insert(role);
	}
	
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	public int update(Role role) {
		return roleDao.update(role);
	}
	
	/**
	 * 删除
	 */
	public boolean delete(String ids){
		String[] arr = ids.split(",");
		int row=roleDao.delete(arr);
		boolean tru = false;
		if(ids.length() == row) {
			tru = true;
		}
		return tru;
	}
	
}
