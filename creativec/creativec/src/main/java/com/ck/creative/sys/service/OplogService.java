package com.ck.creative.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.Oplog;
import com.ck.creative.sys.dao.OplogDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class OplogService {
	@Autowired
	private OplogDao oplogDao;

	/**
	 * 查询
	 * @param Oplog
	 * @return
	 */
	public Page<Oplog> findList(Oplog oplog){
		int pageNum = oplog.getPageNum();
		int pageAmount = oplog.getPageAmount();
		Page<Oplog> page = PageHelper.startPage(pageNum, pageAmount);
		oplogDao.findList(oplog);
		return page;
	}

	
	/**
	 * 添加
	 * @param Oplog
	 * @return
	 */
	public int insert(Oplog oplog) {
		return oplogDao.insert(oplog);
	}
}
