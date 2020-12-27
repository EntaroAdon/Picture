package com.ck.creative.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ck.creative.common.MyBatisDao;
import com.ck.creative.sys.bean.ImgType;

@MyBatisDao
public interface ImgTypeDao {
	/**
	 * 查询图片类型
	 * @param ImgType
	 * @return
	 */
	public List<ImgType> findList(ImgType imgType);

	
	/**
	 * 添加新图片类型
	 * @param ImgType
	 * @return
	 */
	public int insert(ImgType imgType);
	
	/**
	 * 修改图片类型
	 * @param ImgType
	 * @return
	 */
	public int update(ImgType imgType);
	
	
	/**
	 * 删除图片类型
	 * @param ids
	 * @return
	 */
	public int delete(@Param("ids") String[] ids);
}
