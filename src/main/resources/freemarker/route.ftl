		spark.Spark.get(new FreemarkerBasedRoute("/${fileName!""}", "${fileName!""}") {
			@Override
			public void doHandle(Request request, Response response,Writer writer) throws IOException, TemplateException {
				SimpleHash root = new SimpleHash();
				response.type("${type!""}");
				template.process(root, writer);
			}
		});