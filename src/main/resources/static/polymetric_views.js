window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {

        window.SonarRequest.getJSON('/api/polymetric_views_service/example', {projectName: options.component.key}).then(function (response) {
            console.log(options.component.key);
            console.log(response)
        });


        var imported = document.createElement('script');
        document.head.appendChild(imported);

        var data_options = ["Lines of code", "Number of attributes", "Cyclomatic complexity"];

        var data_selects = ["Width: ", "Height: ", "Color: "];

        var selects = d3.select(options.el)
            .selectAll('select')
            .data(data_selects).enter()
            .append('select')
            .attr('class', 'select')
            .on('change', onchange);

        var option = selects
            .selectAll('option')
            .data(data_options).enter()
            .append('option')
            .text(function (d) {
                return d;
            });


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

        window.SonarRequest.getJSON('/api/polymetric_views_service/data', {projectId: options.component.key}).then(function (response) {
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