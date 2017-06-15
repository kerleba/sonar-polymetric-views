package main.java.plugin;

import main.java.framework.db.Configuration;
import main.java.framework.db.DataSourceProvider;
import main.java.ws.MetricsWebService;
import org.sonar.api.Plugin;

/**
 * PolymetricViews plugin definition.
 *
 * @author kerleba
 * @version 0.1
 */
public class PolymetricViewsPlugin implements Plugin {

    /**
     * Default constructor.
     */
    public PolymetricViewsPlugin() {
        super();

        Configuration configuration = Configuration.INSTANCE;
        DataSourceProvider.setConfiguration(configuration);
    }


    /**
     * (non-Javadoc)
     *
     * @see org.sonar.api.Plugin#define(org.sonar.api.Plugin.Context)
     */
    @Override
    public void define(Context context) {
        context.addExtension(PolymetricViewsPageDefinition.class);
        context.addExtension(MetricsWebService.class);
    }
}
