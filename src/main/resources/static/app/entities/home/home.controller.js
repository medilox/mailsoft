(function() {
    'use strict';

    var app = angular.module('myApp');

    app.controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state', '$stateParams', 'auth'];

    function HomeController ($scope, $state, $stateParams, auth) {
        var vm = this;
    };
}) ();


