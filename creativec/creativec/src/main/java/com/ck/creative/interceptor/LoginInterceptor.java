package com.ck.creative.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ck.creative.sys.bean.Oplog;
import com.ck.creative.sys.bean.User;
import com.ck.creative.sys.service.OplogService;
import com.ck.creative.util.ConstValues;
import com.ck.creative.util.DateUtils;

/**
 * 拦截器
 * @author ck901111
 * @phone 13396412323
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private OplogService oplogService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// 执行完毕，返回前拦截			
	}
 
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// 在处理过程中，执行拦截		
	}
 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 在拦截点执行前拦截，如果返回true则不执行拦截点后的操作（拦截成功）
		// 返回false则不执行拦截

		String uri = request.getRequestURI(); // 获取登录的uri
		System.out.println(uri);
		if(uri.contains("delete")) {
			String[] ss = uri.split("delete");
			uri = ss[0]+"delete";
		}
		if(uri.contains("favicon.ico")) {
			return true;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user != null) {

			System.out.println(user);
			Oplog oplog =new Oplog();
			String key = uri;
			String operate = ConstValues.LOG_MAP.get(key);
			oplog.setOperate(operate);
			oplog.setUid(user.getId());
			oplog.setCreateTime(DateUtils.getDateTime());
			oplogService.insert(oplog);
		}else {
			return false;
		}
		return true;
	}
}