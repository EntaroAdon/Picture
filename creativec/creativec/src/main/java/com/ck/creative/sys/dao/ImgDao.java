package com.ck.creative.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ck.creative.common.MyBatisDao;
import com.ck.creative.sys.bean.Img;

@MyBatisDao
public interface ImgDao {
	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	public List<Img> findList(Img img);
	
	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	public List<Img> findUserList(Img img);
	
	/**
	 * 从数据库获取图片的byte数组
	 * @param img
	 * @return
	 */
    public Img findById(@Param("id") String imgid);
	
	/**
	 * 添加新图片
	 * @param Img
	 * @return
	 */
	public int insert(Img img);
	
	/**
	 * 修改图片
	 * @param Img
	 * @return
	 */
	public int update(Img img);
	
	
	/**
	 * 删除图片
	 * @param ids
	 * @return
	 */
	public int delete(@Param("ids") String[] ids);
}
