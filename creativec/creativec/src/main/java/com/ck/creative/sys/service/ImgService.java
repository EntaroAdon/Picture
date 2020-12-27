package com.ck.creative.sys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.Img;
import com.ck.creative.sys.bean.User;
import com.ck.creative.sys.dao.ImgDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ImgService {
	

	@Autowired
	private ImgDao imgDao;
	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	public Page<Img> findList(Img img){
		int pageNum = img.getPageNum();
		int pageAmount = img.getPageAmount();
		Page<Img> page = PageHelper.startPage(pageNum, pageAmount);
		imgDao.findList(img);
		return page;
	}
	

	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	public Page<Img> findUserList(Img img){
		int pageNum = img.getPageNum();
		int pageAmount = img.getPageAmount();
		Page<Img> page = PageHelper.startPage(pageNum, pageAmount);
		imgDao.findUserList(img);
		return page;
	}
	

	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	public List<Img> findListById(Img img){
		List<Img> list = imgDao.findUserList(img);
		return list;
	}
	/**
	 * 从数据库获取图片的byte数组
	 * @param img
	 * @return
	 */
    public Img findById(String imgid) {
    	return imgDao.findById(imgid);
    }
	
	/**
	 * 添加新图片
	 * @param Img
	 * @return
	 */
	public int insert(Img img){
		return imgDao.insert(img);
	}
	
	/**
	 * 修改图片
	 * @param Img
	 * @return
	 */
	public int update(Img img){
		return imgDao.update(img);
	}
	
	/**
	 * 删除图片
	 * @param ids
	 * @return
	 */
	public boolean delete(String ids){
		String[] arr = ids.split(",");
		int row=imgDao.delete(arr);
		boolean tru = false;
		if(ids.length() == row) {
			tru = true;
		}
		return tru;
	}
}
