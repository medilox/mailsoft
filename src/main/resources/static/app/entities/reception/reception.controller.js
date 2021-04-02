(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('ReceptionController', ReceptionController);

    ReceptionController.$inject = ['$rootScope', '$state', '$stateParams', 'previousState', 'Etape', 'CourrierFileService'];

    function ReceptionController($rootScope, $state, $stateParams, previousState, Etape, CourrierFileService) {
        var vm = this;
        vm.previousState = previousState.name;

        vm.openFilesDialog = CourrierFileService.open;

        Etape.getTransmissionsByCurrentUserStructure()
        .then(function(response){
            vm.transmissionsByStructure = response.data;
        });

        Etape.getReceptionsByCurrentUser()
        .then(function(response){
            vm.receptionsByUser = response.data;
        });
    }
})();