package com.ck.creative.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ck.creative.common.MyBatisDao;
import com.ck.creative.sys.bean.GoodLike;

@MyBatisDao
public interface CommentDao {

	/**
	 * 查询
	 * @param GoodLike
	 * @return
	 */
	public List<GoodLike> findList(GoodLike imgType);

	
	/**
	 * 添加
	 * @param GoodLike
	 * @return
	 */
	public int insert(GoodLike imgType);
	
	
	
	/**
	 * 删除
	 * @param GoodLike
	 * @return
	 */
	public int delete(@Param("ids") String[] ids);
}
