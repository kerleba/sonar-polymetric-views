window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {



        const WHITE = 'white';
        const GRAY = 'gray';
        const BLACK = 'black';


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

            var data2 = [
                {
                    "class": "Classname",
                    "width": 50,
                    "height": 70,
                    "children_width": 0,
                    "x" : 0,
                    "y" : 0,
                    "color" : WHITE,
                    "children": [
                        {
                            "class": "Classname2",
                            "width": 25,
                            "height": 90,
                            "children_width": 0,
                            "x" : 0,
                            "y" : 0,
                            "color" : WHITE,
                            "children": []
                        }
                    ]
                },
                {
                    "class": "Classname3",
                    "width": 50,
                    "height": 90,
                    "children_width": 0,
                    "x" : 0,
                    "y" : 0,
                    "color" : WHITE,
                    "children": []
                },
                {
                    "class": "Classname4",
                    "width": 50,
                    "height": 70,
                    "children_width": 0,
                    "x" : 0,
                    "y" : 0,
                    "color" : WHITE,
                    "children": [
                        {
                            "class": "Classname5",
                            "width": 10,
                            "height": 40,
                            "children_width": 0,
                            "x" : 0,
                            "y" : 0,
                            "color" : WHITE,
                            "children": []
                        },
                        {
                            "class": "Classname6",
                            "width": 60,
                            "height": 90,
                            "children_width": 0,
                            "x" : 0,
                            "y" : 0,
                            "color" : WHITE,
                            "children": []
                        }
                    ]

                },
                {
                    "class": "Classname7",
                    "width": 50,
                    "height": 30,
                    "children_width": 0,
                    "x" : 0,
                    "y" : 0,
                    "color" : WHITE,
                    "children": []
                }
            ];

            init_y = 1;
            space = 10;
            LINE_LENGTH = 15;

            current_x = 1;
            current_y = init_y;


            data = data2;

            var classes = [];
            var lines = [];

            data.forEach( function (root) {
                var stack = [];

                root.parent = null;
                stack.push(root);

                while (stack.length > 0) {
                    current = stack.pop();

                    if (current.color == WHITE && current.children.length > 0) {
                        stack.push(current);
                        current.color = GRAY;
                        current.y = current_y;

                        // add all of his children to stack
                        current.children.forEach( function (node) {
                            node.parent = current;

                            stack.push(node)
                        })
                    }
                    else if (current.color == WHITE && current.children.length == 0) {
                        // it is leaf

                        if (current.parent == null) {
                            // it is also root
                            current.x = current_x;
                            current.y = current_y;
                            current_y = init_y;
                            current_x += current.width + space;
                        } else {
                            parrent = current.parent;

                            current.y = parrent.y + parrent.height + LINE_LENGTH;
                            current.x = current_x + parrent.children_width;
                            parrent.children_width += current.width + space;
                        }


                        classes.push(current);
                        current.color = BLACK;

                    }
                    else if (current.color == GRAY) {
                        current.x = current_x + Math.max(current.children_width, current.width) / 2 - current.width / 2;
                        current_y = init_y;
                        current_x += Math.max(current.children_width, current.width) + space;
                        classes.push(current);
                        current.color = BLACK;
                    }
                }
            });


            console.log(classes);


            function getLineBetweenClasses(parent, child) {
                return {
                    "x1": parent.x + (parent.width / 2),
                    "y1": parent.y + parent.height,
                    "x2": child.x + (child.width / 2),
                    "y2": child.y
                };
            }

            classes.forEach( function (item) {
                if (!!item.parent) {
                    lines.push(getLineBetweenClasses(item.parent, item))
                }
            });

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

            var rect = svg.selectAll("rect")
                .data(classes)
                .enter().append("rect")
                .attr("style", "fill:rgb(188, 188, 186);stroke-width:1;stroke:rgb(0,0,0)")
                .attr("width", function(d) { return d.width; })
                .attr("height", function(d) { return d.height; })
                .attr("x", function(d) { return d.x; })
                .attr("y", function(d) { return d.y; });

            var line = svg.selectAll("line")
                .data(lines)
                .enter().append("line")
                .attr("style", "stroke:rgb(30, 30, 29);stroke-width:2")
                .attr("x1", function(d) { return d.x1; })
                .attr("x2", function(d) { return d.x2; })
                .attr("y1", function(d) { return d.y1; })
                .attr("y2", function(d) { return d.y2; });

        };

    };


    return function () {};
});