package com.ck.creative.sys.bean;

import com.ck.creative.common.BaseBean;


public class User extends BaseBean{

	private int id; //主键
	private String name; //姓名
	private String sex; //性别
	private String phone; //手机号
	private String loginName; //登录名
	private String passWord; //密码
	private String pid; //头像
	private String createTime;// 用户注册时间
	private String updateTime;// 用户信息更新时间
	private String keepAlive;// 用户封禁
	private String memberTime;// 入会时间
	private String roleType;//角色类型
	private String endTime;// 到期时间
	private byte[] picture; //头像
	
	public User() {
		// TODO Auto-generated constructor stub
		this.keepAlive = "否";
		this.passWord = "12345";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMemberTime() {
		return memberTime;
	}
	public void setMemberTime(String memberTime) {
		this.memberTime = memberTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
