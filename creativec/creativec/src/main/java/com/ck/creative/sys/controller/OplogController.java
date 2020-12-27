package com.ck.creative.sys.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ck.creative.sys.bean.Oplog;
import com.ck.creative.sys.service.OplogService;
import com.ck.creative.util.ConstValues;
import com.ck.creative.util.ReturnStatus;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/oplog")
@Api(value = "操作日志的操作类")
public class OplogController {
	
	@Autowired
	private OplogService oplogService;
	/**
	 * 查询
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "管理端分页查询操作日志")
	public Map<String,Object>  findList(Oplog oplog){
		Page<Oplog> page = oplogService.findList(oplog);
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
	@ApiOperation(value = "添加操作日志")
	public ReturnStatus insert(Oplog oplog){
    	ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"添加失败!");
		int row=oplogService.insert(oplog);
		if(row == 1) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("添加成功!");
		}
		return ret;	
	}
}
