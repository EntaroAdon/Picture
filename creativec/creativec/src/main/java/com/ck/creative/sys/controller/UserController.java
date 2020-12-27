package com.ck.creative.sys.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ck.creative.common.PoiUtils;
import com.ck.creative.sys.bean.User;
import com.ck.creative.sys.bean.UserPhoto;
import com.ck.creative.sys.service.LoginService;
import com.ck.creative.sys.service.UserService;
import com.ck.creative.util.ConstValues;
import com.ck.creative.util.DateUtils;
import com.ck.creative.util.IdGen;
import com.ck.creative.util.MD5Util;
import com.ck.creative.util.ReturnStatus;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 用户Controller
 * @author 
 * @version 
 */
@Controller
@RequestMapping("/user")
@Api(value = "用户管理的操作类")
public class UserController{
	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;

	/**
	 * 查询用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "分页查询用户列表")
	public Map<String,Object>  findList(User user) {
		System.out.println("开始查询");
		Page<User> page = userService.findList(user);
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagedata", page); 
        map.put("total", page.getTotal());
        return map;
	}
	
	
	/**
	 * 从数据库获取图片的byte数组
	 * @param img
	 * @return
	 */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
	@ApiOperation(value = "获取用户头像字节流")
    public void findById(@RequestParam(value = "id") String id,HttpServletResponse response) {
    	response.setContentType("image/jpeg");
        try{
            byte[] imgbytes= userService.findById(id).getPicture();
            OutputStream  out= response.getOutputStream();
            out.write(imgbytes);
        }catch (Exception e){
            
        }
    }
	 /**
     *  插入用户
     * @param user 用户信息
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "添加用户信息")
    public ReturnStatus insert(User user,
    		@RequestParam(value = "file", required = false) MultipartFile file){
    	ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"添加失败!");  
		UserPhoto userPhoto = new UserPhoto();
		try {
			byte[] data = file.getBytes();
			userPhoto.setPicture(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	List<User> row1=loginService.loginName(user);
		if(row1.size() == 1) {
			ret.setReturnValue(ConstValues.FAILURE);
			ret.setMessage("用户名已存在!");
			return ret;			
		}
		String pass =MD5Util.getMD5(user.getPassWord());
		user.setPassWord(pass);
		user.setCreateTime(DateUtils.getDateTime());
		user.setUpdateTime(DateUtils.getDateTime());

		String pid=IdGen.uuid();
		user.setPid(pid);
		int row=userService.insert(user);
		if(row == 1) {
			User us = userService.findById1(user);
			userPhoto.setId(pid);
			userPhoto.setUid(us.getId());
			int row2=userService.insertPhoto(userPhoto);
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("添加成功!");
		}
		return ret;		
    }
  
    /**
     *  更新用户信息
     * @param user 用户信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "更新用户信息")
    public ReturnStatus update(User user,
    		@RequestParam(value = "file", required = false) MultipartFile file){

    	ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"更新失败!");
    	UserPhoto userPhoto = new UserPhoto();
		if(file != null) {
			if(user.getPid()!=null) {
				userService.deletePhoto(user.getPid());
			}
			String pid=IdGen.uuid();
			user.setPid(pid);
			try {
				byte[] data = file.getBytes();
				userPhoto.setId(pid);
				userPhoto.setUid(user.getId());
				userPhoto.setPicture(data);
				int row2=userService.insertPhoto(userPhoto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	List<User> row1=loginService.loginName(user);
		if(row1.size() > 1) {
			ret.setReturnValue(ConstValues.FAILURE);
			ret.setMessage("用户名已存在!");
			return ret;			
		}
		user.setPassWord(null);
		user.setUpdateTime(DateUtils.getDateTime());
		int row=userService.update(user);
		if(row == 1) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("更新成功!");
		}
		return ret;	
    	
    }
    

	/**
	 * 加入会员
	 * @param user
	 * @return
	 */
    @RequestMapping(value = "/ismember", method = RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "加入会员")
	public ReturnStatus ismember(User user) {
		ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"加入失败!");
		ret.setReturnValue(ConstValues.FAILURE);
    	try {
    		user.setMemberTime(DateUtils.getDateTime());
    		userService.ismember(user);
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("加入成功!");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
    /**
	 * 通过id删除用户信息
	 * @param user
	 */
	@RequestMapping(value = "/delete/{ids}", method=RequestMethod.DELETE)
    @ResponseBody
	@ApiOperation(value = "通过id删除用户信息")
	public ReturnStatus delete(@PathVariable String ids){
		ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"删除失败!");
		boolean boo=userService.delete(ids);
		if(boo) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("删除成功!");
		}
		return ret;	
	}
	

    @RequestMapping(value = "/exportEmp", method = RequestMethod.GET)
	@ApiOperation(value = "导出用户信息")
    public ResponseEntity<byte[]> exportEmp() {
    	List<User> list = userService.findAllList(new User());
        return PoiUtils.exportEmp2Excel(list);
    }
	
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "导入用户信息")
    public ReturnStatus importEmp(MultipartFile file) {
		ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"导入失败!");
        List<User> users = PoiUtils.importEmp2List(file);
		int row=userService.insertUsers(users);
        if (row == users.size()) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("导入成功!");
		}
		return ret;	
    }
}
