'use strict';

app
.controller('helloDisplay', function ($scope, helloService) {
	$scope.exemple='Chargement...';
	helloService.sayHello(function(message) {
		$scope.exemple=message.name;
	});
})
;