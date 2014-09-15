package ru.diasoft.flextera.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GenRoutes {

	private static final String BASE_PATH = "./src/main/resources/freemarker/";

	private static Configuration cfg = new Configuration();

	public static void main(String[] args) throws IOException, TemplateException {
		initTemplate();
		File start = new File(BASE_PATH + "bootstrap/3.1.1/");
		printFiles(start);
	}

	private static void initTemplate() throws IOException {
		//cfg.getTemplate("./src/main/resources/freemarker/route.ftl");
		cfg.setClassForTemplateLoading(GenRoutes.class, "/freemarker");
	}
	
	public static void printFiles(File start) throws IOException, TemplateException {
		for (File item: start.listFiles()) {
			if (item.isDirectory()) {
				printFiles(item);
			} else {
				processFile(item);
			}
		}
	}

	private static void processFile(File item) throws IOException, TemplateException {

		
		String fileName = item.getPath();
		fileName = fileName.replace('\\', '/');
		fileName = fileName.replaceFirst(BASE_PATH, "");
		boolean isCss = fileName.endsWith(".css");
		boolean isJs = fileName.endsWith(".js");
		if (isCss || isJs) {
			
			Template template = cfg.getTemplate("route.ftl");
			HashMap<String, String> rootMap = new HashMap<String, String>();

			//System.out.print("spark.Spark.get(new FreemarkerBasedRoute(\"");
			//System.out.print("/" + fileName);
			//System.out.print("\", \"");
			//System.out.print(fileName);
			//System.out.println("\") {");
			
			//spark.Spark.get(new FreemarkerBasedRoute("/bootstrap/3.1.1/js/bootstrap.min.js", "bootstrap/3.1.1/js/bootstrap.min.js") {
			
			rootMap.put("fileName", fileName);
			
			if (isCss) {
				rootMap.put("type", "text/css; charset=UTF-8");
			} else if (isJs) {
				rootMap.put("type", "text/javascript; charset=UTF-8");
			}
			
			Writer writer = new StringWriter();
			template.process(rootMap, writer);
			
			System.out.println(writer);
		}
		
	}
}
