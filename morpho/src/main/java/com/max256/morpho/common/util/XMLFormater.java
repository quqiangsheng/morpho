package com.max256.morpho.common.util;

import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * XMLFormater工具 dom4j解析
 * 
 * @author fbf
 * 
 */
public class XMLFormater {

	public static String XMLFormat(Document document) {
		StringWriter out = new StringWriter();
		OutputFormat formate = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, formate);
		try {
			writer.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return out.toString();
	}

	public static String XMLFormat(String xml) throws DocumentException,
			IOException {
		Document document = DocumentHelper.parseText(xml);
		StringWriter out = new StringWriter();
		OutputFormat formate = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, formate);
		try {
			writer.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return out.toString();
	}

	public XMLFormater() {
	}
}
