window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {



        var imported = document.createElement('script');
        document.head.appendChild(imported);




        var width = 2500;

        var height = 700;

        var frame = d3.select(options.el).append("div")
            .attr("style", "margin: 2em 2em");

        frame.append("h2")
            .text("This is my page with polymetric views!");

        var selectsDiv = frame.append("div");


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
                .attr('name', function (d) {
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

        });

        var div = frame.append("div")
            .attr("style", "margin-top: 2em ");

        var svg = div.append("svg")
            .attr("width", width)
            .attr("height", height);

       window.SonarRequest.getJSON('/api/polymetric_views_service/data', {"widthMetric": "loc_class", "projectId": options.component.key,  "heightMetric": "noa", "colorMetric": "loc_class"}).then(function (response) {
                console.log(response);

                var rect = svg.selectAll("rect")
                    .data(response.classes)
                    .enter().append("rect")
                    .attr("style", "fill:rgb(188, 188, 186);stroke-width:1;stroke:rgb(0,0,0)")
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



    };


    return function () {
    };
});