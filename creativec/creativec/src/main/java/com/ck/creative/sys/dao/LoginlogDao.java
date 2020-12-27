package com.ck.creative.sys.dao;

import java.util.List;

import com.ck.creative.common.MyBatisDao;
import com.ck.creative.sys.bean.Loginlog;

@MyBatisDao
public interface LoginlogDao {
	/**
	 * 查询
	 * @param Oplog
	 * @return
	 */
	public List<Loginlog> findList(Loginlog loginlog);

	
	/**
	 * 添加
	 * @param Oplog
	 * @return
	 */
	public int insert(Loginlog loginlog);
}
