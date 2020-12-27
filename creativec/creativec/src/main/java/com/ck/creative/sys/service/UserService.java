package com.ck.creative.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.User;
import com.ck.creative.sys.bean.UserPhoto;
import com.ck.creative.sys.dao.UserDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 查询用户列表
	 * @param user
	 * @return 
	 */
	public Page<User> findList(User user){
		int pageNum = user.getPageNum();
		int pageAmount = user.getPageAmount();
		Page<User> page = PageHelper.startPage(pageNum, pageAmount);
		userDao.findList(user);
		return page;
	}

	/**
	 * 不分页查询用户列表
	 * @param user
	 * @return 
	 */
	public List<User> findAllList(User user){
		List<User> page = userDao.findList(user);
		return page;
	}
	/**
	 * 按id查找用户信息
	 * @param user
	 * @return
	 */
	public User findById1(User user){
		return userDao.findById1(user);
	}


	/**
	 * 从数据库获取图片的byte数组
	 * @param img
	 * @return
	 */
    public User findById(String id) {
    	return userDao.findById(id);
    }
    
	/**
	 * 添加新用户
	 * @param user
	 * @return
	 */
	public int insert(User user) {
		return userDao.insert(user);
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int update(User user) {
		return userDao.update(user);
	}

	/**
	 * 加入会员
	 * @param user
	 * @return
	 */
	public int ismember(User user) {
		return userDao.ismember(user);
	}
	 /**
     * 批量导入用户
     * @return
     */
    public int insertUsers(List<User> users){
    	return userDao.insertUsers(users);
    }
	
	/**
	 * 删除用户
	 */
	public boolean delete(String ids){
		String[] arr = ids.split(",");
		int row=userDao.delete(arr);
		boolean tru = false;
		if(ids.length() == row) {
			tru = true;
		}
		return tru;
	}
	
	 /**
	  * 导入用户头像
	  * @param users
	  * @return
	  */
  public int insertPhoto(UserPhoto userPhoto) {
		return userDao.insertPhoto(userPhoto);
  }
	
	/**
	 * 删除用户头像
	 * @param ids
	 * @return
	 */
	public int deletePhoto(String id) {
		return userDao.deletePhoto(id);
	}
}
