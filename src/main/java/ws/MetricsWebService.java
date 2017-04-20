package main.java.ws;

import org.sonar.api.server.ws.WebService;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class
MetricsWebService implements WebService {


    @Override
    public void define(Context context) {
        NewController controller = context.createController("api/polymetric_views_service")
                .setDescription("Api for polymetric views visualisation")
                .setSince("6.3");
        defineExampleAction(controller);
        controller.done();
    }

    private static void defineExampleAction(NewController controller) {
        controller.createAction("example")
                .setDescription("Example action")
                .setSince("6.3")
                .setHandler(new ExampleAction());
    }
}
