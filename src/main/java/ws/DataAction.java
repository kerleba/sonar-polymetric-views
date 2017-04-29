
package main.java.ws;

import main.java.framework.api.Database;
import main.java.framework.api.components.IComponent;
import main.java.model.ComplexityViewFacade;
import org.sonar.api.ce.measure.Component;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.RequestHandler;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

import java.util.Collection;

public class DataAction implements RequestHandler {

    // ToDo: share constants with JS
    public static final String PROJECT_ID = "projectId";

    @Override
    public void handle(Request request, Response response) throws Exception {

        try (JsonWriter json = response.newJsonWriter()) {
            String projectId = request.param(PROJECT_ID);
            ComplexityViewFacade complexityViewFacade = new ComplexityViewFacade(projectId);
            Collection<IComponent> components = complexityViewFacade.getClassesForProject(projectId);

            json.beginObject();
            if (components.isEmpty()) {
                json.prop("empty", "true");
            } else {
                json.prop("empty", "false");
            }
            if (Database.getComponents().isEmpty()) {
                json.prop("empty_components", "true");
            }

            for (IComponent component :
                    components) {
                json.prop(component.getID(), "test");
            }
            json.endObject();
        }
    }
}
