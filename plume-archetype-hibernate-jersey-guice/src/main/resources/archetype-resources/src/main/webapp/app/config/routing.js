'use strict';

app.config(
	    function ($routeProvider) {
	        $routeProvider
	            .when('/', {
	                controller: 'helloDisplay',
	                templateUrl: 'app/views/hello.html'
	            })
	            .otherwise({
	                redirectTo: '/'
	            });
	    }
	);