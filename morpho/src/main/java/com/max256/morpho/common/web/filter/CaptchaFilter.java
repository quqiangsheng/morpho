package com.max256.morpho.common.web.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.max256.morpho.common.config.Constants;
import com.max256.morpho.common.util.CaptchaUtils;

/**
 * 生成验证码响应流 返回jpg给请求 用于生成验证码图片的过滤器。 
 * 这个生成验证码图片过滤器需要放到Shiro代理的过滤器链之后
 * 因为Shiro将包装HttpSession 如果不放在它之后,可能造成两次的sesison id 不一样
 * 
 * @author fbf
 */
public class CaptchaFilter extends OncePerRequestFilter {
	// 日志工具
	protected Logger logger = LoggerFactory.getLogger(CaptchaFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 禁止图像缓存。
		response.setDateHeader("Expires", 0L);
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		// 输出流
		ServletOutputStream out = response.getOutputStream();

		String captcha = CaptchaUtils.generateCaptchaCode4();// 生成4位的验证码

		// 存入会话session
		HttpSession session = request.getSession(true);
	
		session.setAttribute(Constants.SESSION_CAPTCHA, captcha.toLowerCase());// 转换为小写
		// 记录生成的验证码日志
		if(logger.isDebugEnabled()){			
			logger.debug("验证码:" + captcha);
		}
		// 生成图片宽高
		int w = 200, h = 80;
		// 生成图片
		BufferedImage image = CaptchaUtils.createImage(w, h, captcha);
		// 返回图片
		ImageIO.write(image, "JPEG", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}

}
