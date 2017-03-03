package main.java.plugin;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.Plugin;
import org.sonar.api.SonarPlugin;

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
        context.addExtension(PolymetricViewsWidget.class);
    }
}
