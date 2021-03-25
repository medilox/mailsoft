(function() {
    'use strict';

    var app = angular.module('myApp');

    app.controller('ParcoursController', ParcoursController);

    ParcoursController.$inject = ['$scope', '$state', '$stateParams', 'Etape'];

    function ParcoursController ($scope, $state, $stateParams, Etape) {
        var vm = this;

        vm.findParcours = function(){
            Etape.findAllByCourrierNumOrRef(vm.numCourrier, vm.refCourrier)
            .then(function(response){
                vm.etapes = response.data;
            });
        }

    };
}) ();


