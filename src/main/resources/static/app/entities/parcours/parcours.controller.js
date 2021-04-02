(function() {
    'use strict';

    var app = angular.module('myApp');

    app.controller('ParcoursController', ParcoursController);

    ParcoursController.$inject = ['$scope', '$state', '$stateParams', 'Courrier'];

    function ParcoursController ($scope, $state, $stateParams, Courrier) {
        var vm = this;

        vm.numCourrier = "";
        vm.refCourrier = "";
        vm.objet = "";
        vm.concernes = "";

        vm.search = function(){
            Courrier.getAll(vm.numCourrier, vm.refCourrier, vm.objet, vm.concernes)
            .then(function(response){
                vm.courriers = response.data;
            });
        }

    };
}) ();


