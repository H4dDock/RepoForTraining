package ServletTestServer;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;

import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.servlet;

public class ServletServer {
    public static void main(String[] args) throws ServletException {

        DeploymentInfo myApp = new DeploymentInfo()
                .setClassLoader(ServletServer.class.getClassLoader())
                .setContextPath("/myapp")
                .setDeploymentName("test.war")
                .addServlets(
                        servlet("MessageServlet", MessageServlet.class)
                                .addInitParam("message", "Hello World")
                                .addMapping("/*"),
                        servlet("MyServlet", MessageServlet.class)
                                .addInitParam("message", "MyServlet")
                                .addMapping("/myservlet"));

        ServletContainer container = defaultContainer();
        DeploymentManager manager = container.addDeployment(myApp);
        manager.deploy();

        HttpHandler servletHandler = manager.start();
        PathHandler path = Handlers.path(Handlers.redirect(myApp.getContextPath()))
                .addPrefixPath(myApp.getContextPath(), servletHandler);
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path)
                .build();
        server.start();
    }
}
