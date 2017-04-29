window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {

        window.SonarRequest.getJSON('/api/polymetric_views_service/example', {projectName: options.component.key}).then(function (response) {
            console.log(options.component.key);
            console.log(response)
        });

        window.SonarRequest.getJSON('/api/polymetric_views_service/data', {projectId: options.component.key}).then(function (response) {
            console.log(response);
            var div = document.createElement('div');
            div.innerHTML = response.data;
            document.body.appendChild(div);
        });





    }

    return function () {};
});