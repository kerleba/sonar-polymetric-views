package main.java.model;

import main.java.framework.api.Database;
import main.java.framework.api.components.ClassComponent;
import main.java.framework.api.components.IComponent;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import org.apache.commons.lang.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class ComplexityViewFacade {

    private String projectId;

    public ComplexityViewFacade(String projectId) {
        this.projectId = projectId;
    }

    // ToDo: will be private
    public Collection<ClassComponent> getClassesForProject(String projectId) {
        Collection<ClassComponent> components = Database.getClassComponents();
        return components.stream().filter(x -> x.getSonarProjectID().equals(projectId)).collect(Collectors.toList());
    }

    public Collection<DefaultTreeForTreeLayout<ClassComponent>> getData() {
        Collection<DefaultTreeForTreeLayout<ClassComponent>> forest = new ArrayList<>();

        Collection<ClassComponent> components = this.getClassesForProject(this.projectId);
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
        for (ClassComponent childClass: parent.getChildren()) {
            addToTree(tree, parent, childClass);
    }
}







}
