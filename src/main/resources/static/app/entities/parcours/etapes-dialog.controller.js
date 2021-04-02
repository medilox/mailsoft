(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('EtapesDialogController', EtapesDialogController);

    EtapesDialogController.$inject = ['$stateParams', '$uibModalInstance', 'Etape'];

    function EtapesDialogController ($stateParams, $uibModalInstance, Etape) {
        var vm = this;
        vm.clear = clear;

        function clear () {
             $uibModalInstance.dismiss('cancel');
        }

        Etape.findAllByCourrierId($stateParams.courrierId)
        .then(function(response){
            vm.etapes = response.data;
        });
    }
})();
