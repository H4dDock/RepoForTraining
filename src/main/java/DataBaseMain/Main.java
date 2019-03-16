package DataBaseMain;

import ServerNatty.Server;
import freemarker.template.Configuration;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.form.EagerFormParsingHandler;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.Headers;

public class Main {
    public static void main(String[] args) throws Exception {

        Undertow undertowServer = Undertow.builder()
                .addHttpListener(8081,"localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("/", new ResourceHandler(new ClassPathResourceManager(Main.class.getClassLoader(),"html")))
                        .addExactPath("form", new EagerFormParsingHandler().setNext(httpServerExchange -> {
                            FormData form = httpServerExchange.getAttachment(FormDataParser.FORM_DATA);
                            FormData.FormValue nameOfNewUnit = form.getFirst("name");
                            FormData.FormValue emailOfNewUnit = form.getFirst("email");
                            FormData.FormValue cashOfNewUnit = form.getFirst("cash");

                            httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
                            UnitsDAO unitsDAO = new UnitsDAO();

                            System.out.println(new Units(nameOfNewUnit.getValue(), emailOfNewUnit.getValue(),Integer.parseInt(cashOfNewUnit.getValue())).toString());

                            unitsDAO.AddToTable(new Units(nameOfNewUnit.getValue(), emailOfNewUnit.getValue(),Integer.parseInt(cashOfNewUnit.getValue())));
                            httpServerExchange.getResponseSender().send("Data accepted");

                        }))).build();

        undertowServer.start();

        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new Server(port).run();
    }
}
