window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {
       // var zoom = d3.behavior.zoom()
       //     .scaleExtent([1, 10]);



        var imported = document.createElement('script');
        document.head.appendChild(imported);

        var width = 2500;

        var height = 700;

        var frame = d3.select(options.el).append("div")
            .attr("style", "margin: 2em 2em");

        frame.append("h2")
            .text("System complexity view");

        var selectsDiv = frame.append("div");

        function onchange() {
            widthSelect = d3.select("#width").node().value;
            heightSelect = d3.select("#height").node().value;
            colorSelect = d3.select("#color").node().value;

            loadData(widthSelect, heightSelect, colorSelect);
        }


        window.SonarRequest.getJSON('/api/polymetric_views_service/metrics', {}).then(function (response) {
            console.log(response);


            var data_options = response.metrics;

            var data_selects = [ { text: "Width: ", name: "width"}, {text: "Height: ", name: "height"}, { text: "Color: ", name: "color"}];

            var selects = selectsDiv
                .selectAll('select')
                .data(data_selects).enter()
                .append('label')
                .attr("for", function (d) {
                    return d.name;
                })
                .text(function (d) {
                    return d.text;
                })
                .append('select')
                .attr('class', 'select')
                .attr('style', 'margin-right: 2em')
                .attr('name', function (d) {
                    return d.name;
                })
                .attr('id', function (d) {
                    return d.name;
                })
                .on('change', onchange);


            var option = selects
                .selectAll('option')
                .data(data_options).enter()
                .append('option')
                .attr("value", function (d) {
                    return d.key;
                })
                .text(function (d) {
                    return d.name;
                });

            onchange();
        });

        var div = frame.append("div")
            .attr("style", "margin-top: 2em ")
            .attr("width", "100%")
            .attr("height", "100%");

        function loadData(widthMetric, heightMetric, colorMetric) {
            div.select("svg").remove();

            var svg = div.append("svg")
                .attr("width", "5000")
                .attr("height", "2500");

            g = svg.append("g");


            // then, create the zoom behvavior
            var zoom = d3.zoom().on("zoom", function () {
                svg.attr("transform", d3.event.transform)
            });


            //svg.call(zoom);

            window.SonarRequest.getJSON('/api/polymetric_views_service/data', {"widthMetric": widthMetric, "projectId": options.component.key,  "heightMetric": heightMetric, "colorMetric": colorMetric}).then(function (response) {
                    console.log(response);

                    var color=d3.scale.linear().domain([response.colorMin,response.colorMax]).range(["white","black"]);

                    var rect = svg.selectAll("rect")
                        .data(response.classes)
                        .enter()
                        .append("a")
                        .attr("xlink:href", function (d) {
                            return "/dashboard?id=" + d.name;
                        })
                        .attr("target", "_blank")
                        .append("rect")
                        .attr("style", function (d) {
                            var style = "stroke-width:1;stroke:rgb(0,0,0);fill:";
                            style += color(d.color);
                            return style;
                        })
                        .attr("width", function (d) {
                            return d.width;
                        })
                        .attr("height", function (d) {
                            return d.height;
                        })
                        .attr("x", function (d) {
                            return d.x;
                        })
                        .attr("y", function (d) {
                            return d.y;
                        })
                        .attr("class", function (d) {
                            return d.name;
                        })
                        .append("svg:title")
                        .append(function(d){
                            //when the parameter to `append()` is a function
                            //it will be run for each element in the active selection
                            //(in this case, the title elements)
                            //and must return the actual element node to append.

                            //the \n will be converted in Javascript to a line break
                            var newLine = "<br/>\n";

                            var span = document.createElement("span");
                            span.innerHTML = d.name;

                            for (var i = 0; i < d.metrics.length; i++) {
                                span.innerHTML += newLine + d.metrics[i].key.toUpperCase() + ": " + d.metrics[i].value;
                            }

                            return span;
                        });

                    var line = svg.selectAll("line")
                        .data(response.edges)
                        .enter().append("line")
                        .attr("style", "stroke:rgb(30, 30, 29);stroke-width:2")
                        .attr("x1", function (d) {
                            return d.startX;
                        })
                        .attr("x2", function (d) {
                            return d.endX;
                        })
                        .attr("y1", function (d) {
                            return d.startY;
                        })
                        .attr("y2", function (d) {
                            return d.endY;
                        });

                }
            );

        }

    };


    return function () {
    };
});