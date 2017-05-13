
package main.java.ws;

import main.java.framework.api.metrics.MetricsRegister;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.RequestHandler;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

import java.util.HashMap;
import java.util.Map;

public class MetricsAction implements RequestHandler {

    private static final String METRICS = "metrics";
    public static final String NAME = "name";
    public static final String KEY = "key";

    @Override
    public void handle(Request request, Response response) throws Exception {


        Map<String, String> metrics = new HashMap<>();
        metrics.put(MetricsRegister.LOC_CLASS.getKey(), "Lines of code");
        metrics.put(MetricsRegister.NOA.getKey(), MetricsRegister.NOA.getDescription());
        metrics.put(MetricsRegister.NOM.getKey(), MetricsRegister.NOM.getDescription());


        try (JsonWriter json = response.newJsonWriter()) {

            json.beginObject();

            json.name(METRICS);
            json.beginArray();
            for (Map.Entry<String, String> metric: metrics.entrySet()) {
                json.beginObject();
                json.prop(KEY, metric.getKey());
                json.prop(NAME, metric.getValue());
                json.endObject();
            }
            json.endArray();

            json.endObject();
        }
    }
}
