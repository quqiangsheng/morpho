package com.max256.morpho.common.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import com.max256.morpho.common.exception.BusinessException;



/**
 * PDF相关操作工具类
 * 比如转换成图片 提取文字 等
 * 依赖apache pdfbox 2.X以上版本
 * @author fbf
 * @since 2016年11月18日 上午9:19:48
 * @version V1.0
 * 
 */
public class PdfUtils {
		public static String IMAGE_TYPE_JPEG="JPEG";
		public static String IMAGE_TYPE_PNG="PNG";
		//测试用例
		public static void main(String[] args) {
			//将pdf装图片 并且自定义图片得格式大小
			/*File file = new File("D:\\开发\\书\\Git Community Book中文版 .pdf");
			pdfToPng(file, new File("D:\\开发\\书\\java核心技术\\"),null);*/
			/*System.out.println(PdfToText("D:\\开发\\书\\Git Community Book中文版 .pdf"));*/
			
			
		}
		
		/**
		 * pdf转换为图片
		 * @param pdfLocation 源pdf地址
		 * @param targetPath 目标pdf地址必须是目录
		 * @param targetType 目标类型 jpeg或者png
		 * @param targetW 如果缩放 缩放的宽 为空代表不缩放
		 * @param targetH 如果缩放 缩放的高 为空代表不缩放
		 * @param limitPage 只转换前limitPage页 为空代表全部转换
		 */
		public static void pdfToImage(File pdfLocation,File targetPath,String targetType, Integer targetW,  Integer targetH,Integer limitPage){
			if(!targetPath.exists()){
				targetPath.mkdir();
			}
			//参数校验
			if(!pdfLocation.exists()){
				throw new BusinessException("PDF文件不存在",0);
			}
			if(pdfLocation.isDirectory()){
				throw new BusinessException("源地址错误,不能是目录,必须是PDF文件",0);
			}
			if(!pdfLocation.canRead()){
				throw new BusinessException("没有权限读取源文件,可能是操作系统访问限制",0);
			}
			if(!pdfLocation.getName().endsWith(".pdf")){
				throw new BusinessException("源文件错误,不是PDF格式文件",0);
			}
			if(!targetPath.isDirectory()){
				throw new BusinessException("目标地址不是目录,必须是目录",0);
			}
			if(!targetPath.canWrite()){
				throw new BusinessException("没有权限写入目标地址,可能是操作系统访问限制",0);
			}
			if(targetW!=null&&targetH!=null){
				//同时不为空时才进行缩放
			}else{
				//不缩放
				targetW=null;
				targetH=null;
			}
			//初始化DDF文档对象
			PDDocument doc = null;
			try {
				doc = PDDocument.load(pdfLocation);
			} catch (IOException e) {
				throw new BusinessException("加载源文件时出现异常",0);
			}
			//PDF渲染对象
			PDFRenderer renderer = new PDFRenderer(doc);
			//文档页数
			int pageCount = doc.getNumberOfPages();
			//如果只需要前几页 并且前几页小于实际页数 则只输出前几页
			if(limitPage!=null&&limitPage<=pageCount){
				pageCount=limitPage;
			}
			//开始循环
			for(int i=0;i<pageCount;i++){
				//生成的图像
				BufferedImage image;
				try {
					//100DPI比较高的了 一般web显示72即可
					image = renderer.renderImageWithDPI(i, 150);
				} catch (IOException e) {
					throw new BusinessException("渲染图片时出现异常",0);
				} 
				//缩放处理
				BufferedImage srcImage=null;
				if(targetW!=null&&targetH!=null){
					 srcImage = resize(image, targetW, targetH);//产生缩略图
				}else{
					 srcImage=image;//不缩放
				}
				//保存
				try {
					ImageIO.write(srcImage, targetType, 
							new File(targetPath.getAbsolutePath()+"/"
									+pdfLocation.getName().
									substring(0, pdfLocation.getName().length()-4)
									+i+"."+
									StringUtils.lowerCase(targetType)));
				} catch (IOException e) {
					throw new BusinessException("保存转换后的图片文件时出错",0);
				}
			}
		}

		/**pdf转换为jpeg
		 * @param pdfLocation 源pdf地址
		 * @param targetJpegPath 目标pdf地址必须是目录
		 * @param limitPage 只转换前limitPage页 为空代表全部转换
		 */
		public static void pdfToJpeg(File pdfLocation,File targetJpegPath,Integer limitPage){
			pdfToImage(pdfLocation, targetJpegPath, IMAGE_TYPE_JPEG, null, null, limitPage);
		}
		
		/**pdf转换为png
		 * @param pdfLocation 源pdf地址
		 * @param targetPngPath 目标pdf地址必须是目录
		 * @param limitPage 只转换前limitPage页 为空代表全部转换
		 */
		public static void pdfToPng(File pdfLocation,File targetPngPath,Integer limitPage){
			pdfToImage(pdfLocation, targetPngPath, IMAGE_TYPE_PNG, null, null, limitPage);
		}
		
		/**pdf转换为jpeg同时缩放
		 * @param pdfLocation 源pdf地址
		 * @param targetJpegPath 目标pdf地址必须是目录
		 * @param targetW  缩放的宽 
		 * @param targetH  缩放的高 
		 * @param limitPage 只转换前limitPage页 
		 */
		public static void pdfToJpegThumb(File pdfLocation,File targetJpegPath, Integer targetW,  Integer targetH,Integer limitPage){
			pdfToImage(pdfLocation, targetJpegPath, IMAGE_TYPE_JPEG, targetW, targetH, limitPage);
		}
		
		/**pdf转换为png同时缩放
		 * @param pdfLocation 源pdf地址
		 * @param targetPngPath 目标pdf地址必须是目录
		 * @param targetW  缩放的宽 
		 * @param targetH  缩放的高 
		 * @param limitPage 只转换前limitPage页 
		 */
		public static void pdfToPngThumb(File pdfLocation,File targetPngPath, Integer targetW,  Integer targetH,Integer limitPage){
			pdfToImage(pdfLocation, targetPngPath, IMAGE_TYPE_PNG, targetW, targetH, limitPage);
		}
		
		/**
		 * 对图片缩放
		 * @param source 准备处理的源图片
		 * @param targetW 目标宽
		 * @param targetH 目标高
		 * @return
		 */
		private static BufferedImage resize(BufferedImage source, int targetW,  int targetH) {  
	       int type=source.getType();  
	       BufferedImage target=null;  
	       double sx=(double)targetW/source.getWidth();  
	       double sy=(double)targetH/source.getHeight();  
	       if(sx>sy){  
	           sx=sy;  
	           targetW=(int)(sx*source.getWidth());  
	       }else{  
	           sy=sx;  
	           targetH=(int)(sy*source.getHeight());  
	       }  
	       if(type==BufferedImage.TYPE_CUSTOM){  
	           ColorModel cm=source.getColorModel();  
	                WritableRaster raster=cm.createCompatibleWritableRaster(targetW, targetH);  
	                boolean alphaPremultiplied=cm.isAlphaPremultiplied();  
	                target=new BufferedImage(cm,raster,alphaPremultiplied,null);  
	       }else{  
	           target=new BufferedImage(targetW, targetH,type);  
	       }  
	       Graphics2D g=target.createGraphics();  
	       g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  
	       g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));  
	       g.dispose();  
	       return target;  
	   }  
		
		/**
		 * PDF抽取文字
		 * (并不一定都能成功)
		 * @return 文本信息
		 */
		public static String PdfToText(String pdfLocation){
			String result="";
			if(!pdfLocation.endsWith(".pdf")){
				throw new BusinessException("源文件错误,不是PDF格式文件",0);
			}
			File file = new File(pdfLocation);
			//参数校验
			if(!file.exists()){
				throw new BusinessException("PDF文件不存在",0);
			}
			if(file.isDirectory()){
				throw new BusinessException("源地址错误,不能是目录,必须是PDF文件",0);
			}
			if(!file.canRead()){
				throw new BusinessException("没有权限读取源文件,可能是操作系统访问限制",0);
			}
		
				PDDocument document=null;
				try {
					document = PDDocument.load(file);
				} catch (IOException e) {
					throw new BusinessException("读取源PDF文件时发生异常",0);
				}
				PDFTextStripper stripper=null;
				try {
					stripper = new PDFTextStripper();
				} catch (IOException e) {
					throw new BusinessException("解析PDF文件到文本时发生异常",0);
				}
                stripper.setSortByPosition( true );
				try {
					result = stripper.getText(document);
				} catch (IOException e) {
					throw new BusinessException("生成PDF的文本最终结果时发生异常",0);
				}
			
			return result;
			
		}
		
		
}
