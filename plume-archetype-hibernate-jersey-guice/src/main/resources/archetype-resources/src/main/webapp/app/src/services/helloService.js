'use strict';

app.service('helloService', function ($resource) {
	
	this.Message = $resource('/api/example/test/:name', {name:'@name'}),
	
    this.sayHello = function (onSuccess) {
    	var message = this.Message.get({name:"Hello"}, function() {
    		onSuccess(message);
    	})
    }
});