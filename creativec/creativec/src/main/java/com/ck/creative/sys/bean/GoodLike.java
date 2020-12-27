package com.ck.creative.sys.bean;

import com.ck.creative.common.BaseBean;

public class GoodLike extends BaseBean{
	private int id; //主键
	private int uid; //用户id
	private int imid; //图片id
	private String createTime; //创建时间
	private String context;
	private String name;
	private String keywords;//关键字
	private String imName;//图片名
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getImid() {
		return imid;
	}
	public void setImid(int imid) {
		this.imid = imid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getImName() {
		return imName;
	}
	public void setImName(String imName) {
		this.imName = imName;
	}
	
	
}
