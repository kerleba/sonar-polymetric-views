window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {



        const WHITE = 'white';
        const PURPLE = 'purple';
        const GRAY = 'gray';
        const BLACK = 'black';


        var imported = document.createElement('script');
        document.head.appendChild(imported);

        imported.src = 'https://d3js.org/d3.v4.min.js';
        imported.onload = function () {

            var classes = [
                {
                    "class": "Classname",
                    "x": 0,
                    "y": 0,
                    "width": 50,
                    "height": 70
                },
                {
                    "class": "Classname2",
                    "x": 12.5,
                    "y": 80,
                    "width": 25,
                    "height": 90
                },
                {
                    "class": "Classname2",
                    "x": 60,
                    "y": 0,
                    "width": 50,
                    "height": 90
                },
                {
                    "class": "Classname2",
                    "x": 120,
                    "y": 0,
                    "width": 50,
                    "height": 30
                },
                {
                    "class": "Classname2",
                    "x": 180,
                    "y": 0,
                    "width": 50,
                    "height": 70
                },
                {
                    "class": "Classname2",
                    "x": 180,
                    "y": 85,
                    "width": 10,
                    "height": 40
                },
                {
                    "class": "Classname2",
                    "x": 193.5,
                    "y": 85,
                    "width": 40,
                    "height": 90
                }
            ];
            var lines = [
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


            var width = 2500;
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
                .attr("y", function(d) { return d.y; })
                .attr("class", function (d) { return d.class; });

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