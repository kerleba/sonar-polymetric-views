package main.java.plugin;

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
    }



    /* (non-Javadoc)
         * @see org.sonar.api.Plugin#define(org.sonar.api.Plugin.Context)
         */
    @Override
    public void define(Context context) {
        context.addExtension(PolymetricViewsPageDefinition.class);
    }
}
