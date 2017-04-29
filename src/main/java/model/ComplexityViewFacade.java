package main.java.model;

import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.util.Collection;

/**
 * ToDo: create javadoc
 *
 * @author Klara Erlebachova
 */
public class ComplexityViewFacade {

    private String projectId;
    
    private ClassExtentProvider classExtentProvider;
    private  DefaultConfiguration<ClassComponent> configuration;

    public ComplexityViewFacade(String projectId) {
        this.projectId = projectId;
    }

    public String getData() {
        TreeFactory treeFactory = new TreeFactory();
        Collection<DefaultTreeForTreeLayout<ClassComponent>> forest = treeFactory.createForestFor(this.projectId);

        // create the NodeExtentProvider for ClassComponent nodes
        this.classExtentProvider = new ClassExtentProvider("loc_class", "loc_class");

        // setup the tree layout configuration
        double gapBetweenLevels = 50;
        double gapBetweenNodes = 10;
        this.configuration = new DefaultConfiguration<>(gapBetweenLevels, gapBetweenNodes);

        StringBuilder stringBuilder = new StringBuilder(1024);

        for (DefaultTreeForTreeLayout<ClassComponent> tree :
                forest) {
            String string = this.getSvgForTree(tree);
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private String getSvgForTree(DefaultTreeForTreeLayout<ClassComponent> tree) {
       
        // create the layout
        TreeLayout<ClassComponent> treeLayout = new TreeLayout<>(tree,
                this.classExtentProvider, this.configuration);

        // Generate the SVG and write it to System.out
        DiagramGenerator generator = new DiagramGenerator(treeLayout);
        return generator.getSVG();


    }


}
