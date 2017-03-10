window.registerExtension('sonarPolymetricViews/polymetric_views', function (options) {
    var imported = document.createElement('script');
    document.head.appendChild(imported);

    imported.src = 'https://d3js.org/d3.v4.min.js';
    imported.onload = function () {

        var header = document.createElement('h2');
        header.textContent = 'This is my page with polymetric views!';
        options.el.appendChild(header);

        var p = document.createElement('p');
        options.el.appendChild(p);


    };


    return function () {};
});