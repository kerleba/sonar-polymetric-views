package main.java.plugin;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * The widget initialization class
 * @author Klara Erlebachova
 */
@UserRole(UserRole.USER)
public class PolymetricViewsWidget extends AbstractRubyTemplate implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public PolymetricViewsWidget() {
        super();
    }

    /**
     * Get the widget id.
     * @return the widget id
     */
    @Override
    public String getId() {
        return "polymetricviews.widget";
    }

    /**
     * Get the widget title
     * @return the widget title
     */
    @Override
    public String getTitle() {
        return "Polymetric views Widget";
    }

    /* (non-Javadoc)
     * @see org.sonar.api.web.AbstractRubyTemplate#getTemplatePath()
     */
    @Override
    protected String getTemplatePath() {
//        String templatePath = "resources/testWidget.html.erb";
//    	 FIXME use the first line. This is just for debug purposes. Will not work on sonar server!
        String templatePath = "/home/klara/devel/sonar-polymetric-views/src/resources/testWidget.html.erb";
        return templatePath;
    }
}
