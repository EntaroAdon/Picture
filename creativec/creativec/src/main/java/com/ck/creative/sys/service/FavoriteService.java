package com.ck.creative.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.GoodLike;
import com.ck.creative.sys.dao.FavoriteDao;

@Service
public class FavoriteService {

	@Autowired
	private FavoriteDao favoriteDao;

	/**
	 * 查询
	 * @param GoodLike
	 * @return
	 */
	public List<GoodLike> findAllList(GoodLike GoodLike){
		List<GoodLike> page = favoriteDao.findList(GoodLike);
		return page;
	}
	/**
	 * 添加新
	 * @param GoodLike
	 * @return
	 */
	public int insert(GoodLike GoodLike){
		return favoriteDao.insert(GoodLike);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public int delete(GoodLike GoodLike){
		int row=favoriteDao.delete(GoodLike);
		return row;
	}
}
