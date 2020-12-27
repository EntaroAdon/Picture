package com.ck.creative.sys.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ck.creative.sys.bean.Img;
import com.ck.creative.sys.service.ImgService;
import com.ck.creative.util.ConstValues;
import com.ck.creative.util.ReturnStatus;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/img")
@Api(value = "图片的操作类")
public class ImgController {

	@Autowired
	private ImgService imgService;
	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "分页查询图片")
	public Map<String,Object>  findList(Img img){
		Page<Img> page = imgService.findList(img);
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagedata", page); 
        map.put("total", page.getTotal()); 
        return map;
	}
	
	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	@RequestMapping(value = "/findUserList", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户分页查询图片")
	public Map<String,Object>  findUserList(Img img){
		Page<Img> page = imgService.findUserList(img);
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagedata", page); 
        map.put("total", page.getTotal()); 
        return map;
	}
	/**
	 * 查询图片
	 * @param Img
	 * @return
	 */
	@RequestMapping(value = "/findListById", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据图片id查询图片信息")
	public List<Img>  findListById(Img img){
		List<Img> page = imgService.findListById(img);
        return page;
	}
	/**
	 * 从数据库获取图片的byte数组
	 * @param img
	 * @return
	 */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
	@ApiOperation(value = "图片面显示字节流")
    public void findById(@RequestParam(value = "imgid") String imgid,HttpServletResponse response) {
    	response.setContentType("image/jpeg");
        try{
            byte[] imgbytes= imgService.findById(imgid).getPicture();
            OutputStream  out= response.getOutputStream();
            out.write(imgbytes);
        }catch (Exception e){
            
        }
    }
	
	/**
	 * 添加新图片
	 * @param Img
	 * @return
	 */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "添加新图片")
	public ReturnStatus insert(Img img,
    		@RequestParam(value = "file", required = false) MultipartFile file){
    	ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"添加失败!");
    	try {
			byte[] data = file.getBytes();
			img.setPicture(data);
			int row=imgService.insert(img);
			if(row == 1) {
				ret.setReturnValue(ConstValues.SUCCESS);
				ret.setMessage("添加成功!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;	
	}
	
	/**
	 * 修改图片
	 * @param Img
	 * @return
	 */
    @RequestMapping(value = "/update", method=RequestMethod.POST)
    @ResponseBody
	@ApiOperation(value = "修改图片")
	public ReturnStatus update(Img img,
    		@RequestParam(value = "file", required = false) MultipartFile file){
    	ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"更新失败!");
    	try {
			if(file != null) {
				byte[] data = file.getBytes();
				img.setPicture(data);				
			}
			int row=imgService.update(img);
			if(row == 1) {
				ret.setReturnValue(ConstValues.SUCCESS);
				ret.setMessage("更新成功!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;	
	}
	
	/**
	 * 删除图片
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete/{ids}", method=RequestMethod.DELETE)
    @ResponseBody
	@ApiOperation(value = "删除图片")
	public ReturnStatus delete(@PathVariable String ids){
		ReturnStatus ret = new ReturnStatus(ConstValues.FAILURE,"删除失败!");
		boolean boo=imgService.delete(ids);
		if(boo) {
			ret.setReturnValue(ConstValues.SUCCESS);
			ret.setMessage("删除成功!");
		}
		return ret;	
	}
}
