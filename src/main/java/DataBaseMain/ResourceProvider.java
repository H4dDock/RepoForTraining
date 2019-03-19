package DataBaseMain;


import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class ResourceProvider {

    public Configuration freeMarker(){
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassLoaderForTemplateLoading(Main.class.getClassLoader(),"templates");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setObjectWrapper(new BeansWrapper(Configuration.VERSION_2_3_23));
        return cfg;
    }
}
