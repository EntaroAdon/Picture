/**
 * 
 */
package com.ck.creative.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 常量集合
 * @author ck901111
 *
 */
public class ConstValues {
	public static final Integer NOTHING_FOUND = -1;
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String SESSION_TEST_STRING = "sdyhlt_bdpt_session.0.0.1";
	public static final Map<String, String> LOG_MAP;
	public static final Map<String, String> TRANS_MAP = new HashMap<String, String>();
	public static int ID_ROLE = 0;
	
	static {
		// TODO Auto-generated constructor stub
		LOG_MAP = new HashMap<String, String>();
		LOG_MAP.put("/user/resign", "用户注册");
		LOG_MAP.put("/user/resetPassWord", "重置密码");
		LOG_MAP.put("/user/findList", "访问用户管理页面");
		LOG_MAP.put("/user/insert", "用户管理--添加用户");
		LOG_MAP.put("/user/update", "用户管理--修改用户信息");
		LOG_MAP.put("/user/delete", "用户管理--删除用户信息");
		LOG_MAP.put("/user/exportEmp", "用户管理--导出用户信息");
		LOG_MAP.put("/user/import", "用户管理--导入用户信息");
		LOG_MAP.put("/img/findList", "访问图片管理页面");
		LOG_MAP.put("/img/findListById", "按id查询图片");
		LOG_MAP.put("/img/insert", "图片管理-添加新图片");
		LOG_MAP.put("/img/update", "图片管理-修改图片");
		LOG_MAP.put("/img/delete", "图片管理-删除图片");
		LOG_MAP.put("/imgType/findList", "访问图片类型页面");
		LOG_MAP.put("/imgType/insert", "图片类型--添加新图片类型");
		LOG_MAP.put("/imgType/update", "图片类型--修改图片类型");
		LOG_MAP.put("/imgType/delete", "图片类型--删除图片类型");
		LOG_MAP.put("/goodlike/insert", "点赞");
		LOG_MAP.put("/goodlike/delete", "取消点赞");
		LOG_MAP.put("/favorite/insert", "收藏");
		LOG_MAP.put("/favorite/delete", "取消收藏");
		LOG_MAP.put("/comment/findList", "查询评论");
		LOG_MAP.put("/comment/insert", "提交评论");
		LOG_MAP.put("/role/findList", "访问角色管理页面");
		LOG_MAP.put("/role/insert", "角色管理--添加角色");
		LOG_MAP.put("/role/update", "角色管理--修改角色");
		LOG_MAP.put("/role/delete", "角色管理--删除角色");
		
		
	}
}
