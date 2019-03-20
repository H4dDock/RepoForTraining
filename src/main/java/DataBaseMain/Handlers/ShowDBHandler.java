package DataBaseMain.Handlers;

import DataBaseMain.Units;
import DataBaseMain.UnitsDAO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowDBHandler implements HttpHandler {
    private final Template template;
    private final UnitsDAO unitsDAO;

    public ShowDBHandler(Configuration cfg, UnitsDAO unitsDAO) throws IOException {
        this.template = cfg.getTemplate("ShowDB.html");
        this.unitsDAO = unitsDAO;
    }


    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE,"text/html");

        StringWriter stringWriter = new StringWriter();

        List<Units> out = unitsDAO.GetAllUnits();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put( "Units", out );

        template.process(root,stringWriter);

        httpServerExchange.getResponseSender().send(stringWriter.toString());
    }
}
