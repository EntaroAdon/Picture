package com.ck.creative.sys.dao;

import java.util.List;

import com.ck.creative.common.MyBatisDao;
import com.ck.creative.sys.bean.Oplog;

@MyBatisDao
public interface OplogDao {
	/**
	 * 查询
	 * @param Oplog
	 * @return
	 */
	public List<Oplog> findList(Oplog oplog);

	
	/**
	 * 添加
	 * @param Oplog
	 * @return
	 */
	public int insert(Oplog oplog);
	
}
