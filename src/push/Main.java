package push;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Main implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SocketController socketController = new SocketController();
        socketController.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
