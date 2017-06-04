package com.max256.morpho.common.util;

import java.io.File;
import java.io.BufferedInputStream; 
import java.io.BufferedOutputStream; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.net.URLEncoder; 
import javax.servlet.http.HttpServletResponse; 
import org.apache.commons.lang3.StringUtils; 

/** 
 * 文件下载类 
 */
public class DownloadUtils { 
  /** 
   * 文件下载编码 
   * 该编码告诉浏览器文件名的编码方式，以防下载中文文件名时有乱码 
   */
  private static String encoding = "utf-8"; 
    
  /** 
   * 文件下载 
   * @param response 
   * @param filePath 文件在服务器上的路径，包含文件名 
   */
  public static void download(HttpServletResponse response, String filePath){ 
    File file = new File(filePath.toString()); 
    download(response, file, null, encoding); 
  } 
    
  /** 
   * 文件下载 
   * @param response 
   * @param filePath 文件在服务器上的路径，包括文件名称 
   * @param fileName 文件下载到浏览器的名称，如果不想让浏览器下载的文件名称和服务器上的文件名称一样，请设置该参数 
   */
  public static void download(HttpServletResponse response, String filePath, String fileName){ 
    File file = new File(filePath.toString()); 
    download(response, file, fileName, encoding); 
  } 
    
  /** 
   * 文件下载 
   * @param response 
   * @param filePath 文件在服务器上的路径，包括文件名称 
   * @param fileName 文件下载到浏览器的名称，如果不想让浏览器下载的文件名称和服务器上的文件名称一样，请设置该参数 
   * @param encoding 文件名称编码 
   */
  public static void download(HttpServletResponse response, String filePath, String fileName, String encoding){ 
    File file = new File(filePath.toString()); 
    download(response, file, fileName, encoding); 
  } 
    
  /** 
   * 文件下载 
   * @param response 
   * @param file 文件 
   * @param fileName 文件下载到浏览器的名称，如果不想让浏览器下载的文件名称和服务器上的文件名称一样，请设置该参数 
   */
  public static void download(HttpServletResponse response, File file) { 
    download(response, file, null, encoding); 
  } 
    
  /** 
   * 文件下载 
   * @param response 
   * @param file 文件 
   * @param fileName 文件下载到浏览器的名称，如果不想让浏览器下载的文件名称和服务器上的文件名称一样，请设置该参数 
   */
  public static void download(HttpServletResponse response, File file, String fileName) { 
    download(response, file, fileName, encoding); 
  } 
    
  /** 
   * 文件下载 
   * @param response 
   * @param file 文件 
   * @param fileName 文件下载到浏览器的名称，如果不想让浏览器下载的文件名称和服务器上的文件名称一样，请设置该参数 
   * @param encoding 文件名称编码 
   */
  public static void download(HttpServletResponse response, File file, String fileName, String encoding) { 
    if(file == null || !file.exists() || file.isDirectory()){ 
      return; 
    } 
      
    // 如果不指定文件下载到浏览器的名称，则使用文件的默认名称 
    if (StringUtils.isBlank(fileName)) { 
      fileName = file.getName(); 
    } 
  
    try { 
      InputStream is = new FileInputStream(file); 
      download(response, is, fileName, encoding); 
    } catch (IOException e) { 
      e.printStackTrace(); 
    } 
  } 
    
  /** 
   * 文件下载 
   * @param response 
   * @param is 文件输入流 
   * @param fileName 下载的文件名称 
   * @throws IOException 
   */
  public static void download(HttpServletResponse response, InputStream is, String fileName){ 
    download(response, is, fileName, encoding); 
  } 
    
  /** 
   * 文件下载 
   * @param response 
   * @param is 文件输入流 
   * @param fileName 下载的文件名称 
   * @param encoding 编码格式 
   */
  public static void download(HttpServletResponse response, InputStream is, String fileName, String encoding){ 
    if(is == null || StringUtils.isBlank(fileName)){ 
      return; 
    } 
      
    BufferedInputStream bis = null; 
    OutputStream os = null; 
    BufferedOutputStream bos = null; 
      
    try{ 
      bis = new BufferedInputStream(is); 
      os = response.getOutputStream(); 
      bos = new BufferedOutputStream(os); 
      response.setContentType("application/octet-stream;charset=" + encoding); 
      response.setCharacterEncoding(encoding); 
      response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, encoding)); 
      byte[] buffer = new byte[4096]; 
      int len = bis.read(buffer); 
      while(len != -1){ 
        bos.write(buffer, 0, len); 
        len = bis.read(buffer); 
      } 
        
      bos.flush(); 
    }catch(IOException e){ 
      e.printStackTrace(); 
    }finally{ 
      if(bis != null){ 
        try{ 
          bis.close(); 
        }catch(IOException e){} 
      } 
        
      if(is != null){ 
        try{ 
          is.close(); 
        }catch(IOException e){} 
      } 
    } 
  } 
  
  public static String getEncoding() { 
    return encoding; 
  } 
  
  public static void setEncoding(String encoding) { 
    DownloadUtils.encoding = encoding; 
  } 
}
