package main.java.model;

import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.NodeExtentProvider;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class ClassExtentProvider implements NodeExtentProvider<ClassComponent> {

    private String widthMetric;
    private String heightMetric;

    public ClassExtentProvider(String widthMetric, String heightMetric) {
        this.widthMetric = widthMetric;
        this.heightMetric = heightMetric;
    }

    @Override
    public double getWidth(ClassComponent classComponent) {
        return classComponent.getMeasures().get(this.widthMetric);
    }

    @Override
    public double getHeight(ClassComponent classComponent) {
        return classComponent.getMeasures().get(this.heightMetric);
    }
}
