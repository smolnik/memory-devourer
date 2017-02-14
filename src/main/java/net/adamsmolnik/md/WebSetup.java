package net.adamsmolnik.md;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import net.adamsmolnik.metrics.aws.AwsCustomMetrics;

@WebListener("mdSetup")
public class WebSetup implements ServletContextListener {

	private final AwsCustomMetrics cm = new AwsCustomMetrics();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		cm.launch();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		cm.close();
	}

}
