package com.ck.creative.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.Loginlog;
import com.ck.creative.sys.dao.LoginlogDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class LoginlogService {
	@Autowired
	private LoginlogDao loginlogDao;

	/**
	 * 查询
	 * @param Oplog
	 * @return
	 */
	public Page<Loginlog> findList(Loginlog loginlog){
		int pageNum = loginlog.getPageNum();
		int pageAmount = loginlog.getPageAmount();
		Page<Loginlog> page = PageHelper.startPage(pageNum, pageAmount);
		loginlogDao.findList(loginlog);
		return page;
	}

	
	/**
	 * 添加
	 * @param Oplog
	 * @return
	 */
	public int insert(Loginlog loginlog) {
		return loginlogDao.insert(loginlog);
	}
}
