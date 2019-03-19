package DataBaseMain.Handlers;

import DataBaseMain.UnitsDAO;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.EagerFormParsingHandler;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.Headers;

public class RemoveTableHandler implements HttpHandler {

    private EagerFormParsingHandler inner = new EagerFormParsingHandler();

    public RemoveTableHandler() {
        inner.setNext(this::handle);
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        inner.handleRequest(httpServerExchange);
    }

    public void handle(HttpServerExchange httpServerExchange) throws Exception {
        FormData formData = httpServerExchange.getAttachment(FormDataParser.FORM_DATA);
        FormData.FormValue idRemove = formData.getFirst("id");

        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        UnitsDAO unitsDAO = new UnitsDAO();

        unitsDAO.RemoveFromTable(Integer.parseInt(idRemove.getValue()));

        httpServerExchange.getResponseSender().send("Removed.");
    }
}
