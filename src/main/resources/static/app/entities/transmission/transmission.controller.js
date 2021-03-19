(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('TransmissionController', TransmissionController);

    TransmissionController.$inject = ['$rootScope', '$state', '$stateParams', 'previousState', 'Etape', '$filter'];

    function TransmissionController($rootScope, $state, $stateParams, previousState, Etape, $filter) {
        var vm = this;
        vm.previousState = previousState.name;

        Etape.getReceptionsByCurrentUserStructure()
        .then(function(response){
            vm.receptionsByStructure = response.data;
        });

        Etape.getTransmissionsByCurrentUser()
        .then(function(response){
            vm.transmissionsByUser = response.data;
        });
    }
})();