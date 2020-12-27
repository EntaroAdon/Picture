package com.ck.creative.sys.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ck.creative.sys.bean.Loginlog;
import com.ck.creative.sys.service.LoginlogService;
import com.ck.creative.util.ConstValues;
import com.ck.creative.util.ReturnStatus;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/loginlog")
@Api(value = "登录日志的操作类")
public class LoginlogController {
	@Autowired
	private LoginlogService loginlogService;
	/**
	 * 查询
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "管理端分页查询登录日志")
	public Map<String,Object>  findList(Loginlog loginlog){
		Page<Loginlog> page = loginlogService.findList(loginlog);
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagedata", page); 
        map.put("total", page.getTotal()); 
        return map;
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "添加登录日志")
	public ReturnStatus insert(Loginlog loginlog){
    	ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"添加失败!");
		int row=loginlogService.insert(loginlog);
		if(row == 1) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("添加成功!");
		}
		return ret;	
	}
}
