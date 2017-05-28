package main.java.model;

import main.java.framework.api.MeasurementRepository;
import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author Klara Erlebachova
 */
public class TreeFactory {

    private Collection<ClassComponent> getClassesForProject(String projectId) {
        return MeasurementRepository.getTreeOfClassComponents(projectId);
    }

    public Collection<DefaultTreeForTreeLayout<ClassComponent>> createForestFor(String projectId) {
        Collection<DefaultTreeForTreeLayout<ClassComponent>> forest = new ArrayList<>();

        Collection<ClassComponent> components = this.getClassesForProject(projectId);
        for (ClassComponent component :components) {
            DefaultTreeForTreeLayout<ClassComponent> tree = new DefaultTreeForTreeLayout<>(component);
            for (ClassComponent childClass: component.getChildren()) {
                addToTree(tree, component, childClass);
            }
            forest.add(tree);
        }
        return forest;
    }

    private void addToTree(DefaultTreeForTreeLayout<ClassComponent> tree, ClassComponent parent, ClassComponent child) {
        tree.addChild(parent, child);
        parent = child;
        for (ClassComponent childClass : parent.getChildren()) {
            addToTree(tree, parent, childClass);
        }
    }
}
