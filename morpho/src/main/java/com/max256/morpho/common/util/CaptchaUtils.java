package com.max256.morpho.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.max256.morpho.common.config.Constants;

/**
 * 验证码工具类
 * 
 * @author fbf
 * 
 */
public class CaptchaUtils {

	// Algerian或黑体，去掉0,o,1,i,j,l,I,9,g,q,6,b,p等容易混淆的字符
	public static final String VERIFY_CODES = "234578acdefhkmnrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

	private static Random random = new Random();

	/**
	 * 输出指定验证码图片流
	 * 
	 * @param w
	 * @param h
	 * @param os
	 * @param code
	 * @throws IOException
	 */
	public static BufferedImage createImage(int w, int h, String code)
			throws IOException {
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color c = getRandColor(200, 250);
		g2.setColor(c);// 设置背景色
		g2.fillRect(0, 0, w, h);

		// 添加噪点
		float yawpRate = 0.03f;// 噪声率

		int area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}

		// 绘制干扰线
		for (int i = 0; i < 5; i++) {
			g2.setColor(getRandColor(130, 190));
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int xl = random.nextInt(w / 2) + w / 7;
			int yl = random.nextInt(h / 2) + h / 7;
			g2.setStroke(new BasicStroke(random.nextInt(h / 22) + h / 22));
			g2.drawLine(x, y, x + xl, y + yl);
		}

		// int fontSize = (int) (h * 0.86);
		// //Font font = new Font("宋体", Font.ITALIC, fontSize);
		// g2.setFont(font);

		char[] chars = code.toCharArray();
		int verifySize = code.length();

		for (int i = 0; i < verifySize; i++) {
			int fontSize = (int) (h - random.nextInt(h / 4) * 0.88);
			g2.setFont(new Font("黑体", random.nextBoolean() ? Font.BOLD
					: Font.ITALIC, fontSize));

			if (random.nextBoolean()) {
				g2.setColor(getRandColor(100, 180));
			}
			// AffineTransform affine = new AffineTransform();
			// affine.setToRotation(Math.PI / 4 * random.nextDouble() *
			// (random.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize
			// / 2, h / 2);
			// g2.setTransform(affine);
			// g2.drawChars(chars, i, 1, ((w + 5 - random.nextInt(20)) /
			// verifySize) * i - w / 15 * (i - verifySize / 2), h / 2 + fontSize
			// / 2 - h / 8);

			int xx = ((w - random.nextInt(20)) / verifySize) * i - w / 20
					* (i - verifySize / 2);
			int yy = h / 2 + fontSize / 2 - h / 10;
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(
					Math.PI / 4 * random.nextDouble()
							* (random.nextBoolean() ? 1 : -1), xx + fontSize
							/ 2, yy - fontSize / 2);
			g2.setTransform(affine);
			g2.drawChars(chars, i, 1, xx, yy);

			// 绘制干扰线
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int xl = random.nextInt(w / 2) + w / 8;
			int yl = random.nextInt(h / 2) + h / 8;
			g2.setStroke(new BasicStroke(random.nextInt(h / 25) + h / 25));
			g2.drawLine(x, y, x - xl, y + yl);

		}

		g2.dispose();

		g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// //绘制干扰线
		// for (int i = 0; i < 5; i++) {
		// g2.setColor(getRandColor(130, 190));
		// int x = random.nextInt(w);
		// int y = random.nextInt(h);
		// int xl = random.nextInt(10 + w / 2) + 8;
		// int yl = random.nextInt(5 + h / 2) + 4;
		// g2.setStroke(new BasicStroke(random.nextInt(h / 35) + h / 30));
		// g2.drawLine(x, y, x - xl, y + yl);
		// }

		// 添加噪点
		yawpRate = 0.01f;// 噪声率
		area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}

		shear(g2, w, h, c);// 使图片扭曲

		g2.dispose();

		return image;
		// ImageIO.write(image, "jpg", os);
	}

	/**
	 * 使用系统默认字符源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @return
	 */
	public static String generateCaptchaCode(int verifySize) {
		return generateCaptchaCode(verifySize, VERIFY_CODES);
	}

	/**
	 * 使用指定源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @param sources
	 *            验证码字符源
	 * @return
	 */
	public static String generateCaptchaCode(int verifySize, String sources) {
		if (sources == null || sources.length() == 0) {
			sources = VERIFY_CODES;// 选择生成验证码源
		}
		int codesLen = sources.length();// 字符源的个数
		Random rand = new Random(System.currentTimeMillis());// 生成一个随机数
		StringBuilder verifyCode = new StringBuilder(verifySize);// 拼凑最终的验证码
																	// 指定StringBuilder长度为生成字符的最终长度
		for (int i = 0; i < verifySize; i++) {
			verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
		}
		return verifyCode.toString();
	}

	/**
	 * 使用系统默认字符源生成4位长度验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @return
	 */
	public static String generateCaptchaCode4() {
		// 4位长度
		return generateCaptchaCode(4, VERIFY_CODES);
	}

	/**
	 * 得到随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static Random getRandom() {
		return random;
	}

	private static int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private static int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	/**
	 * 扭曲图像
	 * 
	 * @param g
	 * @param w1
	 * @param h1
	 * @param color
	 */
	private static void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	/**
	 * 扭曲图像 x轴
	 * 
	 * @param g
	 * @param w1
	 * @param h1
	 * @param color
	 */
	private static void shearX(Graphics g, int w1, int h1, Color color) {
		boolean borderGap = true;
		/*
		 * 正弦型函数解析式：y=Asin（ωx+φ）+h 各常数值对函数图像的影响： φ（初相位）：决定波形与X轴位置关系或横向移动距离（左加右减）
		 * ω：决定周期（最小正周期T=2π/|ω|） A：决定峰值（即纵向拉伸压缩的倍数） h：表示波形在Y轴的位置关系或纵向移动距离（上加下减）
		 * 
		 * 一周的弧度数为2πr/r=2π，360°角=2π弧度，因此，1弧度约为57.3°，
		 * 即57°17'44.806''，1°为π/180弧度，近似值为0.01745弧度
		 */
		double period = (random.nextInt(4) + 1) * h1 / 2; // 周期：像素
		double amplitude = random.nextInt(h1 / 25) + h1 / 50 + 1;// 振幅：像素
		double phase = random.nextInt(360);// 相位：角度
		for (int i = 0; i < h1; i++) {
			double d = amplitude
					* Math.sin(2 * Math.PI * i / period + Math.PI * phase / 180);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(0, i, (int) d, i);
				g.drawLine(w1 + (int) d, i, w1, i);
			}
		}

	}

	/**
	 * 扭曲图像 y轴
	 * 
	 * @param g
	 * @param w1
	 * @param h1
	 * @param color
	 */
	private static void shearY(Graphics g, int w1, int h1, Color color) {
		boolean borderGap = true;

		double period = (random.nextInt(3) + 1.5) * w1 / 3; // 周期：像素
		double amplitude = random.nextInt(w1 / 60) + w1 / 50 + 1;// 振幅：像素
		double phase = random.nextInt(360);// 相位：角度

		for (int i = 0; i < w1; i++) {
			double d = amplitude
					* Math.sin(2 * Math.PI * i / period + Math.PI * phase / 180);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}

	/**
	 * 判断请求输入的验证码是否正确
	 * 
	 * @param request
	 * @param userCaptchaResponse
	 * @return
	 */
	public static boolean validateResponse(HttpServletRequest request,
			String userCaptchaResponse) {
		// 先得到session
		HttpSession session = request.getSession(false);
		// 先判断当前的request有没有绑定到session中 如果session为空 则非法请求 就不用继续判断其他的了
		if (session == null)
			return false;
		boolean validated = false; // 默认的结果不正确
		// 得到session中的验证码
		String sessionFrom = StringUtils.trimToEmpty(
				(String) session.getAttribute(Constants.SESSION_CAPTCHA))
				.toLowerCase();
		// request里输入的验证码
		String requestFrom = StringUtils.trimToEmpty(
				request.getParameter(Constants.REQUEST_CAPTCHA)).toLowerCase();

		// 如果session中或者request参数中任何一个的验证码为空，则判断失败
		if (sessionFrom.equals("") || requestFrom.equals("")) {
			// 返回结果 失败
			return validated;
		}
		// 如果session和request的验证码一样说明验证通过 返回true
		if (sessionFrom.equals(requestFrom)) {
			// 并且session清空验证码属性
			session.removeAttribute(Constants.SESSION_CAPTCHA);
			validated = true;// 验证通过
		}
		// 验证通过返回结果
		return validated;
	}

}
