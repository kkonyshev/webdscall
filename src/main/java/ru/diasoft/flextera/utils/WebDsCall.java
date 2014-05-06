package ru.diasoft.flextera.utils;

import static spark.Spark.setPort;
import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import ru.diasoft.flextera.common.util.PrettyPrinter;
import ru.diasoft.utils.XMLUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class WebDsCall implements SparkApplication {
    private Configuration cfg;
    private final String PLATFORM_UTILS_VERSION = "7.02.01-12101201";
    private static Integer portNumber = 8082; 

    public static void main(String[] args) throws IOException {
    	if (args.length != 0) {
        	try {
        		portNumber = Integer.valueOf(args[0]);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }
    	new WebDsCall();
    }

    public WebDsCall() throws IOException {
    	init();
    }


	public void init() {
        cfg = createFreemarkerConfiguration();
        setPort(portNumber);
        try {
			initializeRoutes();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
    
    abstract class FreemarkerBasedRoute extends Route {
        final Template template;

        /**
         * Constructor
         *
         * @param path The route path which is used for matching. (e.g. /hello, users/:name)
         */
        protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
            super(path);
            template = cfg.getTemplate(templateName);
        }

        @Override
        public Object handle(Request request, Response response) {
            StringWriter writer = new StringWriter();
            try {
                doHandle(request, response, writer);
            } catch (Exception e) {
                e.printStackTrace();
                response.redirect("/internal_error");
            }
            return writer;
        }

        protected abstract void doHandle(final Request request, final Response response, final Writer writer)
                throws IOException, TemplateException;

    }

    private void initializeRoutes() throws IOException {
    	 get(new FreemarkerBasedRoute("/bootstrap/3.1.1/css/bootstrap.min.css", "bootstrap/3.1.1/css/bootstrap.min.css") {
             @Override
             public void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
             	SimpleHash root = new SimpleHash();
             	response.type("text/css; charset=UTF-8");
             	template.process(root, writer);
             }
         });
    	 get(new FreemarkerBasedRoute("/bootstrap/3.1.1/css/bootstrap-theme.min.css", "bootstrap/3.1.1/css/bootstrap-theme.min.css") {
             @Override
             public void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
             	SimpleHash root = new SimpleHash();
             	response.type("text/css; charset=UTF-8");
             	template.process(root, writer);
             }
         });
    	 get(new FreemarkerBasedRoute("/bootstrap/3.1.1/js/bootstrap.min.js", "bootstrap/3.1.1/js/bootstrap.min.js") {
             @Override
             public void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
             	SimpleHash root = new SimpleHash();
             	response.type("text/javascript; charset=UTF-8");
             	template.process(root, writer);
             }
         });
    	 get(new FreemarkerBasedRoute("/bootstrap/3.1.1/js/jquery-2.1.1.min.js", "bootstrap/3.1.1/js/jquery-2.1.1.min.js") {
             @Override
             public void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
             	SimpleHash root = new SimpleHash();
             	response.type("text/javascript; charset=UTF-8");
             	template.process(root, writer);
             }
         });
    	 
    	
    	// this is the blog home page
        get(new FreemarkerBasedRoute("/call", "call.ftl") {
            @Override
            public void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
            	SimpleHash root = new SimpleHash();
            	
            	root.put("callStatus", "info");
            	template.process(root, writer);
            }
        });
        
        post(new FreemarkerBasedRoute("/call", "call.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

                String serverName = request.queryParams("serverName");
                String serverContext = request.queryParams("serverContext");
                String serviceName = request.queryParams("serviceName");
                String userName = request.queryParams("userName");
                String userPassword = request.queryParams("userPassword");
                String requestText = request.queryParams("requestText");
                
                String resultText = null;
                String callStatus = "success";
                
                XMLUtil xmlUtil = new XMLUtil(userName, userPassword);
                Map<String, Object> parameters;
                Long startTime = System.nanoTime();
				try {
					parameters = xmlUtil.parse(requestText);
					Map<String, Object> resultMap = xmlUtil.callService(serverName + serverContext, serviceName, parameters);
					resultText = PrettyPrinter.INSTANCE.getFormattedXML(xmlUtil.createXML(resultMap));
				} catch (Exception e) {
					e.printStackTrace();
					resultText = e.getLocalizedMessage();
					callStatus = "danger";
				}
				Long endTime = System.nanoTime();
				Long callTimeMs = (endTime - startTime)/1000000;

				// redisplay page with errors
				HashMap<String, String> root = new HashMap<String, String>();
				root.put("serverName", serverName);
				root.put("serverContext", serverContext);
				root.put("serviceName", serviceName);
				root.put("userName", userName);
				root.put("userPassword", userPassword);
				root.put("requestText", requestText);
				root.put("resultText", resultText);
				root.put("callStatus", callStatus);
				root.put("callTimeMs", callTimeMs.toString());
				root.put("platformUtilsVersion", PLATFORM_UTILS_VERSION);
				
				template.process(root, writer);
            }
        });

        // used to process internal errors
        get(new FreemarkerBasedRoute("/internal_error", "error_template.ftl") {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                SimpleHash root = new SimpleHash();

                root.put("error", "System has encountered an error.");
                template.process(root, writer);
            }
        });
    }

    private Configuration createFreemarkerConfiguration() {
        Configuration retVal = new Configuration();
        retVal.setClassForTemplateLoading(WebDsCall.class, "/freemarker");
        return retVal;
    }
}