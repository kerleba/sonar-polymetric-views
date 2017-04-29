package main.java.model;

import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.NodeExtentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class ClassExtentProvider implements NodeExtentProvider<ClassComponent> {

    private String widthMetric;
    private String heightMetric;

    /** The logger object */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ClassExtentProvider(String widthMetric, String heightMetric) {
        this.widthMetric = widthMetric;
        this.heightMetric = heightMetric;
    }

    @Override
    public double getWidth(ClassComponent classComponent) {
        if (!classComponent.getMeasures().containsKey(this.widthMetric)) {
            this.log.warn("ClassComponent " + classComponent.getFileKey() + " does not have measurement for metric " + this.widthMetric);
        }
        return classComponent.getMeasures().get(this.widthMetric);
    }

    @Override
    public double getHeight(ClassComponent classComponent) {
        if (!classComponent.getMeasures().containsKey(this.heightMetric)) {
            this.log.warn("ClassComponent " + classComponent.getFileKey() + " does not have measurement for metric " + this.heightMetric);
        }
        return classComponent.getMeasures().get(this.heightMetric);
    }
}
