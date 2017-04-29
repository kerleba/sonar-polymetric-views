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
        defineDataAction(controller);
        controller.done();
    }

    private static void defineExampleAction(NewController controller) {
        controller.createAction("example")
                .setDescription("Example action")
                .setSince("6.3")
                .setHandler(new ExampleAction());
    }

    private static void defineDataAction(NewController controller) {
        WebService.NewAction action = controller.createAction("data")
                .setDescription("Get data for system complexity view")
                .setSince("6.3")
                .setHandler(new DataAction());

        action
            .createParam(DataAction.PROJECT_ID)
            .setDescription("Project id")
            .setExampleValue("my_project");
    }
}
