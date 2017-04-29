
package main.java.ws;

import main.java.model.ClassDTO;
import main.java.model.ComplexityViewFacade;
import main.java.model.EdgeDTO;
import org.apache.commons.lang3.tuple.Pair;
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
            Pair<Collection<ClassDTO>, Collection<EdgeDTO>> data = complexityViewFacade.getDataFor();


            json.beginObject();
            json.name("classes");
            json.beginArray();
            for ( ClassDTO classDTO :data.getLeft()) {
                classDTO.toJson(json);
            }
            json.endArray();
            json.name("edges");
            json.beginArray();
            for ( EdgeDTO edgeDTO :data.getRight()) {
                edgeDTO.toJson(json);
            }
            json.endArray();
            json.endObject();
        }
    }
}
