/*
 * [The "BSD license"]
 * Copyright (c) 2011, abego Software GmbH, Germany (http://www.abego.org)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the abego Software GmbH nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package main.java.model;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import main.java.framework.api.components.ClassComponent;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import static main.java.model.SvgUtils.text;
import static main.java.model.SvgUtils.doc;
import static main.java.model.SvgUtils.line;
import static main.java.model.SvgUtils.rect;
import static main.java.model.SvgUtils.svg;

/**
 * Generates SVG for a given {@link TreeLayout} of {@link ClassComponent} nodes.
 * <p>
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class DiagramGenerator {
    private final TreeLayout<ClassComponent> treeLayout;
    private String svgText;

    private TreeForTreeLayout<ClassComponent> getTree() {
        return treeLayout.getTree();
    }

    private Iterable<ClassComponent> getChildren(ClassComponent parent) {
        return getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(ClassComponent node) {
        return treeLayout.getNodeBounds().get(node);
    }

    /**
     * @param treeLayout the {@link TreeLayout} to be rendered as SVG
     */
    public DiagramGenerator(TreeLayout<ClassComponent> treeLayout) {
        this.treeLayout = treeLayout;
    }

    // -------------------------------------------------------------------
    // generating

    private void generateEdges(StringBuilder result, ClassComponent parent) {
        if (!getTree().isLeaf(parent)) {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (ClassComponent child : getChildren(parent)) {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                result.append(line(x1, y1, b2.getCenterX(), b2.getCenterY(),
                        "stroke:black; stroke-width:2px;"));

                generateEdges(result, child);
            }
        }
    }

    private void generateBox(StringBuilder result, ClassComponent ClassComponent) {
        // draw the box in the background
        Rectangle2D.Double box = getBoundsOfNode(ClassComponent);
        result.append(rect(box.x + 1, box.y + 1, box.width - 2, box.height - 2,
                "fill:orange; stroke:rgb(0,0,0);", "rx=\"10\""));
    }

    private String generateDiagram() {
        StringBuilder result = new StringBuilder();

        // generate the edges and boxes (with text)
        generateEdges(result, getTree().getRoot());
        for (ClassComponent ClassComponent : treeLayout.getNodeBounds().keySet()) {
            generateBox(result, ClassComponent);
        }

        // generate the svg containing the diagram items (edges and boxes)
        Dimension size = treeLayout.getBounds().getBounds().getSize();
        return svg(size.width, size.height, result.toString());
    }

    /**
     * @return the rendered tree, described in SVG format.
     */
    public String getSVG() {
        if (svgText == null) {
            svgText = doc(generateDiagram());
        }
        return svgText;
    }
}