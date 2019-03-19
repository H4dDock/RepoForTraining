package DataBaseMain;

import DataBaseMain.Handlers.AddToTableHandler;
import DataBaseMain.Handlers.RemoveTableHandler;
import ServerNatty.Server;
import freemarker.template.Configuration;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;


public class Main {
    public static void main(String[] args) throws Exception {

        Configuration cfg = new ResourceProvider().freeMarker();

        Undertow undertowServer = Undertow.builder()
                .addHttpListener(8081,"localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("add", new ResourceHandler(new ClassPathResourceManager(Main.class.getClassLoader(),"html/add.html")))
                        .addPrefixPath("remove", new ResourceHandler(new ClassPathResourceManager(Main.class.getClassLoader(), "html/remove.html")))
                        .addExactPath("added", new AddToTableHandler())
                        .addExactPath("removed", new RemoveTableHandler()))
                .build();

        undertowServer.start();

        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new Server(port).run();
    }

}
