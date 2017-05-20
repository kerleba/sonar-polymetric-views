
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

    public static final String PROJECT_ID = "projectId";
    public static final String CLASSES = "classes";
    public static final String EDGES = "edges";
    public static final String WIDTH_METRIC = "widthMetric";
    public static final String HEIGHT_METRIC = "heightMetric";
    public static final String COLOR_METRIC = "colorMetric";

    @Override
    public void handle(Request request, Response response) throws Exception {

        try (JsonWriter json = response.newJsonWriter()) {
            String projectId = request.param(PROJECT_ID);
            String widthMetric = request.param(WIDTH_METRIC);
            String heightMetric = request.param(HEIGHT_METRIC);
            String colorMetric = request.param(COLOR_METRIC);
            ComplexityViewFacade complexityViewFacade = new ComplexityViewFacade(projectId);
            Pair<Collection<ClassDTO>, Collection<EdgeDTO>> data = complexityViewFacade.getDataFor(widthMetric, heightMetric);
            Pair<Integer, Integer> colorBoundaries = complexityViewFacade.getBoundariesFor(colorMetric);


            json.beginObject();

            json.prop("colorMin", colorBoundaries.getLeft());
            json.prop("colorMax", colorBoundaries.getRight());

            json.name(CLASSES);
            json.beginArray();
            for (ClassDTO classDTO: data.getLeft()) {
                classDTO.toJson(json, colorMetric);
            }
            json.endArray();

            json.name(EDGES);
            json.beginArray();
            for (EdgeDTO edgeDTO: data.getRight()) {
                edgeDTO.toJson(json);
            }
            json.endArray();

            json.endObject();
        }
    }
}
