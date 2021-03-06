package main.java.ws;

import main.java.framework.api.metrics.MetricsRegister;
import org.sonar.api.server.ws.WebService;

/**
 * @author Klara Erlebachova
 */
public class MetricsWebService implements WebService {


    @Override
    public void define(Context context) {
        NewController controller = context.createController("api/polymetric_views_service")
            .setDescription("Api for polymetric views visualisation")
            .setSince("0.1");
        defineDataAction(controller);
        defineMetricsAction(controller);
        controller.done();
    }

    private static void defineDataAction(NewController controller) {
        WebService.NewAction action = controller.createAction("data")
            .setDescription("Get data for system complexity view")
            .setSince("0.1")
            .setHandler(new DataAction());
        action
            .createParam(DataAction.PROJECT_ID)
            .setDescription("Project id")
            .setRequired(true)
            .setExampleValue("my_project");
        action
            .createParam(DataAction.WIDTH_METRIC)
            .setDescription("Width metric ")
            .setRequired(true)
            .setExampleValue(MetricsRegister.LOC_CLASS.getKey());
        action
            .createParam(DataAction.HEIGHT_METRIC)
            .setDescription("Height metric ")
            .setRequired(true)
            .setExampleValue(MetricsRegister.LOC_CLASS.getKey());
        action
            .createParam(DataAction.COLOR_METRIC)
            .setDescription("Color metric ")
            .setRequired(true)
            .setExampleValue(MetricsRegister.LOC_CLASS.getKey());
    }

    private static void defineMetricsAction(NewController controller) {
       controller.createAction("metrics")
            .setDescription("Get possible metrics")
            .setSince("0.1")
            .setHandler(new MetricsAction());
    }
}
