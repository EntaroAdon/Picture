package com.ck.creative.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.GoodLike;
import com.ck.creative.sys.dao.GoodLikeDao;

@Service
public class GoodLikeService {

	@Autowired
	private GoodLikeDao goodLikeDao;

	/**
	 * 查询
	 * @param GoodLike
	 * @return
	 */
	public List<GoodLike> findAllList(GoodLike GoodLike){
		List<GoodLike> page = goodLikeDao.findList(GoodLike);
		return page;
	}
	/**
	 * 添加新
	 * @param GoodLike
	 * @return
	 */
	public int insert(GoodLike GoodLike){
		return goodLikeDao.insert(GoodLike);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public int delete(GoodLike GoodLike){
		int row=goodLikeDao.delete(GoodLike);
		return row;
	}
}
