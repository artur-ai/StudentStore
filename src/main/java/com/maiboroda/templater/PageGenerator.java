package com.maiboroda.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

// PageGenerator with Freemarker
public class PageGenerator {
    private static final String HTML_DIR = "templates";
    private static PageGenerator instance;
    private final Configuration cfg;

    public static PageGenerator instance() {
        if (instance == null) {
            instance = new PageGenerator();
        }
        return instance;
    }

    private PageGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_31);
        try {
            cfg.setDirectoryForTemplateLoading(new File("."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer writer = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

}

