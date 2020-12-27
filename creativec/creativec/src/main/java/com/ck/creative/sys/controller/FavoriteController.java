package com.ck.creative.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ck.creative.sys.bean.GoodLike;
import com.ck.creative.sys.service.FavoriteService;
import com.ck.creative.util.ConstValues;
import com.ck.creative.util.ReturnStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/favorite")
@Api(value = "收藏的操作类")
public class FavoriteController {
	@Autowired
	private FavoriteService favoriteService;
	/**
	 * 查询
	 * @param GoodLike
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/findAllList", method = RequestMethod.POST)
	 * 
	 * @ResponseBody
	 * 
	 * @ApiOperation(value = "查询") public List<GoodLike> findAllList(){
	 * List<GoodLike> list = favoriteService.findAllList(new GoodLike()); return
	 * list; }
	 */
	/**
	 * 添加新
	 * @param GoodLike
	 * @return
	 */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "添加收藏")
	public ReturnStatus insert(GoodLike GoodLike){
    	ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"添加失败!");
		int row=favoriteService.insert(GoodLike);
		if(row == 1) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("添加成功!");
		}
		return ret;	
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "取消收藏")
	public ReturnStatus delete(GoodLike GoodLike){
		ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"删除失败!");
		int row = favoriteService.delete(GoodLike);
		if(row == 1) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("删除成功!");
		}
		return ret;	
	}
}
