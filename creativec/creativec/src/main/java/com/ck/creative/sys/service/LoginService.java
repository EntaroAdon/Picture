package com.ck.creative.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.creative.sys.bean.User;
import com.ck.creative.sys.dao.LoginDao;

/**
 * 用户登录
 * @author ck
 * @version 2019-12-7
 */
@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login (User user) {
		return loginDao.login(user);
	}
	

	/**
	 * 用户名验证 
	 * @param user
	 * @return
	 */
	public List<User> loginName (User user){
		return loginDao.loginName(user);
	}
	
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public int resign (User user) {
		return loginDao.resign(user);
	}
	
	/**
	 * 重置密码
	 * @param user
	 * @return
	 */
	public int resetPassWord (User user) {
		return loginDao.resetPassWord(user);		
	}
}
