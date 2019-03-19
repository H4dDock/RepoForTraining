package DataBaseMain.Handlers;

import DataBaseMain.Units;
import DataBaseMain.UnitsDAO;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.EagerFormParsingHandler;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.Headers;

public class AddToTableHandler implements HttpHandler {

    EagerFormParsingHandler inner = new EagerFormParsingHandler();

    public AddToTableHandler(){
        inner.setNext(this::handle);
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        inner.handleRequest(httpServerExchange);
    }

    public void handle(HttpServerExchange httpServerExchange) throws Exception {
        FormData form = httpServerExchange.getAttachment(FormDataParser.FORM_DATA);
        FormData.FormValue nameOfNewUnit = form.getFirst("name");
        FormData.FormValue emailOfNewUnit = form.getFirst("email");
        FormData.FormValue cashOfNewUnit = form.getFirst("cash");

        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        UnitsDAO unitsDAO = new UnitsDAO();

        System.out.println(new Units(nameOfNewUnit.getValue(), emailOfNewUnit.getValue(), Integer.parseInt(cashOfNewUnit.getValue())).toString());

        unitsDAO.AddToTable(new Units(nameOfNewUnit.getValue(), emailOfNewUnit.getValue(), Integer.parseInt(cashOfNewUnit.getValue())));
        httpServerExchange.getResponseSender().send("Data accepted");

    }
}
