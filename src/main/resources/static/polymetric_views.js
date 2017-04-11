window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {

        var data = [
            {
                "class": "Classname",
                "metric1": 50,
                "metric2": 70,
                "children": [
                    {
                        "class": "Classname2",
                        "metric1": 25,
                        "metric2": 90,
                        "children": []
                    }
                ]
            },
            {
                "class": "Classname2",
                "metric1": 50,
                "metric2": 90,
                "children": []
            },
            {
                "class": "Classname2",
                "metric1": 50,
                "metric2": 30,
                "children": []
            },
            {
                "class": "Classname2",
                "metric1": 50,
                "metric2": 70,
                "children": [
                    {
                        "class": "Classname2",
                        "metric1": 10,
                        "metric2": 40
                    },
                    {
                        "class": "Classname2",
                        "metric1": 40,
                        "metric2": 90
                    }
                ]

            }
        ];

        var items = [
            {
                "class": "Classname",
                "x": 0,
                "y": 0,
                "metric1": 50,
                "metric2": 70
            },
            {
                "class": "Classname2",
                "x": 12.5,
                "y": 80,
                "metric1": 25,
                "metric2": 90
            },
            {
                "class": "Classname2",
                "x": 60,
                "y": 0,
                "metric1": 50,
                "metric2": 90
            },
            {
                "class": "Classname2",
                "x": 120,
                "y": 0,
                "metric1": 50,
                "metric2": 30
            },
            {
                "class": "Classname2",
                "x": 180,
                "y": 0,
                "metric1": 50,
                "metric2": 70
            },
            {
                "class": "Classname2",
                "x": 180,
                "y": 85,
                "metric1": 10,
                "metric2": 40
            },
            {
                "class": "Classname2",
                "x": 193.5,
                "y": 85,
                "metric1": 40,
                "metric2": 90
            }
        ];

        var data2 = [
            {
                "class": "Classname",
                "width": 50,
                "height": 70,
                "children_width": 0,
                "x" : 0,
                "y" : 0,
                "children": [
                    {
                        "class": "Classname2",
                        "width": 25,
                        "height": 90,
                        "children_width": 0,
                        "x" : 0,
                        "y" : 0,
                        "children": []
                    }
                ]
            },
            {
                "class": "Classname2",
                "width": 50,
                "height": 90,
                "children_width": 0,
                "x" : 0,
                "y" : 0,
                "children": []
            },
            {
                "class": "Classname2",
                "width": 50,
                "height": 30,
                "children_width": 0,
                "x" : 0,
                "y" : 0,
                "children": []
            },
            {
                "class": "Classname2",
                "width": 50,
                "height": 70,
                "children_width": 0,
                "x" : 0,
                "y" : 0,
                "children": [
                    {
                        "class": "Classname2",
                        "width": 10,
                        "height": 40,
                        "children_width": 0,
                        "x" : 0,
                        "y" : 0,
                        "children": []
                    },
                    {
                        "class": "Classname2",
                        "width": 40,
                        "height": 90,
                        "children_width": 0,
                        "x" : 0,
                        "y" : 0,
                        "children": []
                    }
                ]

            }
        ];


        // při traversování dolů předávat potomkům výšku (parent.y + parent.height + LINE_LENGTH)
        // strom procházet do šířky
        // dojít až dolů a spočítat šířku listů
        // iniciálně mají všechny nodes children_width = 0
        // každý potomek udělá parent.children_width += node.width (+spacing)



        function max(var1, var2) {
            return var1 > var2 ? var1 : var2;
        }


        init_y = 1;
        space = 5;

        current_x = 1;
        current_y = init_y;
        var classes = [];

        data2.forEach( function (node) {
            // this is root node
            node.y = current_y;
            node.children.forEach( function (child) {
                // ToDo: go deeper in tree
                child.y = node.y + node.height;
                child.x = current_x + node.children_width;
                node.children_width += child.width + space;
                classes.push(child);

            });
            node.x = current_x;
            current_y = init_y;
            current_x += max(node.children_width, node.width) + space;
            classes.push(node);

        });




        // získat maximální šířku podstromu max_tree_width
        // rodič bude mít x = max_tree_width / 2 - width / 2
        // a y = 0
        //



        // projít strom s už spočítanými pozicemi
        // vygenerovat pole lines

  /**      var lines = [];

        items.forEach( function (item) {
            console.log(item);
            item.children.forEach( function (child) {
                lines.push(
                    {
                        "x1": item.x + (item.metric1 / 2),
                        "y1": item.y + item.metric2,
                        "x2": child.x + (child.metric1 / 2),
                        "y2": child.y + child.metric2
                    }
                )

            })
        });
   **/

        // function getLineBetweenClasses(class1, class2) {
        // x1 = class1.x + (class1.width / 2)
        // y1 = class1.y + class1.height
        // x2 = class2.x + (class2.width / 2)
        // y2 = class2.y + class2.height

        /**var lines = [
            {
                "x1": 25,
                "y1": 70,
                "x2": 25,
                "y2": 80
            },
            {
                "x1": 205,
                "y1": 70,
                "x2": 185,
                "y2": 85
            },
            {
                "x1": 205,
                "y1": 70,
                "x2": 213.5,
                "y2": 85
            }
        ];
         */

        var width = 600;
        var height = 700;

        var frame = d3.select(options.el).append("div")
            .attr("style", "margin: 2em 2em");

        frame.append("h2")
            .text("This is my page with polymetric views!");

        var div = frame.append("div")
            .attr("style", "margin-top: 2em ");

        var svg = div.append("svg")
            .attr("width", width)
            .attr("height", height);

        console.log(classes);

        var rect = svg.selectAll("rect")
            .data(classes)
            .enter().append("rect")
            .attr("style", "fill:rgb(188, 188, 186);stroke-width:1;stroke:rgb(0,0,0)")
            .attr("width", function(d) { return d.width; })
            .attr("height", function(d) { return d.height; })
            .attr("x", function(d) { return d.x; })
            .attr("y", function(d) { return d.y; });

     /**   var line = svg.selectAll("line")
            .data(lines)
            .enter().append("line")
            .attr("style", "stroke:rgb(30, 30, 29);stroke-width:2")
            .attr("x1", function(d) { return d.x1; })
            .attr("x2", function(d) { return d.x2; })
            .attr("y1", function(d) { return d.y1; })
            .attr("y2", function(d) { return d.y2; });
*/

    };


    return function () {};
});