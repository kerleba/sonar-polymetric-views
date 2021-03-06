package main.java.plugin;

import org.sonar.api.web.page.Context;
import org.sonar.api.web.page.Page;
import org.sonar.api.web.page.PageDefinition;

/**
 * The page initialization class
 * @author Klara Erlebachova
 */
public class PolymetricViewsPageDefinition implements PageDefinition {

    @Override
    public void define(Context context) {
        context.addPage(
            Page.builder("sonarPolymetricViews/polymetric_views")
                .setName("Polymetric views")
                .setScope(Page.Scope.COMPONENT)
                .setComponentQualifiers(Page.Qualifier.PROJECT)
                .build());
    }
}