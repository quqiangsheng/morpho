package com.max256.morpho.common.security.esapi;

import java.io.IOException;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.PreparedString;
import org.owasp.esapi.codecs.CSSCodec;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.DB2Codec;
import org.owasp.esapi.codecs.HTMLEntityCodec;
import org.owasp.esapi.codecs.JavaScriptCodec;
import org.owasp.esapi.codecs.MySQLCodec;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.codecs.PercentCodec;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.codecs.VBScriptCodec;
import org.owasp.esapi.codecs.WindowsCodec;
import org.owasp.esapi.codecs.XMLEntityCodec;
import org.owasp.esapi.errors.EncodingException;

/**
 * ESAPI 编解码API
 * @author fbf
 * @since 2016年10月11日 上午10:38:55
 * @version V1.0
 * 
 */
public class ESAPIEncoder
{
  private static final Encoder encoder = ESAPI.encoder();
  @SuppressWarnings("unused")
  private static final char HTML_PLACEHODER = '?';

  public String sqlEncode(String inputString, DatabaseCodec dbcodec)
  {
    return encoder.encodeForSQL(dbcodec.codec(), inputString);
  }

  public String sqlPreparedString(String sqlTemplate, String[] paras, DatabaseCodec dbcodec)
  {
    PreparedString sqlPreparedString = new PreparedString(sqlTemplate, dbcodec.codec());
    for (int i = 0; i < paras.length; i++) {
      sqlPreparedString.set(i + 1, paras[i]);
    }
    return sqlPreparedString.toString();
  }

  public String htmlEncode(String inputString)
  {
    return encoder.encodeForHTML(inputString);
  }

  public String htmlAttributeEncode(String inputString)
  {
    return encoder.encodeForHTMLAttribute(inputString);
  }

  public String cssEncode(String inputString)
  {
    return encoder.encodeForCSS(inputString);
  }

  public String javaScriptEncode(String inputString)
  {
    return encoder.encodeForJavaScript(inputString);
  }

  public String urlEncode(String inputString) throws Exception
  {
    try
    {
      return encoder.encodeForURL(inputString);
    }
    catch (EncodingException e) {
    	throw new Exception(e);
    }
  }

  public String urlDecode(String url) throws Exception
  {
    try
    {
      return encoder.decodeFromURL(url); } 
    	catch (EncodingException e) {
    	  throw new Exception(e);
    }
  }

  public String xmlEncode(String inputString)
  {
    return encoder.encodeForXML(inputString);
  }

  public String xmlAttributeEncode(String inputString)
  {
    return encoder.encodeForXMLAttribute(inputString);
  }

  public String webPreparedString(String strTemplate, String[] paras, Codec[] codecs, char placeholder)
  {
    PreparedString clientSidePreparedString = new PreparedString(strTemplate, placeholder, TextCodec.HTML.codec());
    for (int i = 0; i < paras.length; i++) {
      clientSidePreparedString.set(i + 1, paras[i], codecs[i]);
    }
    return clientSidePreparedString.toString();
  }

  public String webPreparedString(String strTemplate, String[] paras, Codec[] codecs)
  {
    return webPreparedString(strTemplate, paras, codecs, '?');
  }

  public String webPreparedString(String strTemplate, String[] paras, TextCodec[] codecs, char placeholder)
  {
    PreparedString clientSidePreparedString = new PreparedString(strTemplate, placeholder, TextCodec.HTML.codec());
    for (int i = 0; i < paras.length; i++) {
      clientSidePreparedString.set(i + 1, paras[i], codecs[i].codec());
    }
    return clientSidePreparedString.toString();
  }

  public String webPreparedString(String strTemplate, String[] paras, TextCodec[] codecs)
  {
    return webPreparedString(strTemplate, paras, codecs, '?');
  }

  public String webPreparedString(String strTemplate, String param, TextCodec codec)
  {
    return webPreparedString(strTemplate, new String[] { param }, new TextCodec[] { codec }, '?');
  }

  public String encodeForBase64(byte[] data)
  {
    return encoder.encodeForBase64(data, false);
  }

  public byte[] decodeFromBase64(String text)
    throws IOException
  {
    return encoder.decodeFromBase64(text);
  }

  public static enum TextCodec
  {
    CSS(new CSSCodec()), 
    HTML(new HTMLEntityCodec()), 
    JS(new JavaScriptCodec()), 
    PERCENT(new PercentCodec()), 
    XML(new XMLEntityCodec()), 
    UNIX(new UnixCodec()), 
    WINDOWS(new WindowsCodec()), 
    VB(new VBScriptCodec());

    private Codec codec;

    private TextCodec(Codec codec) { this.codec = codec; }

    public Codec codec()
    {
      return this.codec;
    }
  }

  public static enum DatabaseCodec
  {
    ORACLE(new OracleCodec()), 
    MYSQL_ANSI(new MySQLCodec(MySQLCodec.Mode.ANSI)), 
    MYSQL_STANDARD(new MySQLCodec(MySQLCodec.Mode.STANDARD)), 
    DB2(new DB2Codec());

    private Codec codec;

    private DatabaseCodec(Codec codec) { this.codec = codec; }

    public Codec codec()
    {
      return this.codec;
    }
  }
}