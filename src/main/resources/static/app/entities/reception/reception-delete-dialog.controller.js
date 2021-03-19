(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('ReceptionDeleteController',ReceptionDeleteController);

    ReceptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Etape'];

    function ReceptionDeleteController($uibModalInstance, entity, Etape) {
        var vm = this;

        vm.etape = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            vm.showErrorAlert = false;
            Etape.delete(id)
            .then(function(response){
                onSaveSuccess(response);
            }, function(error){
                onSaveError(error);
            });
        }

        function onSaveSuccess (response) {
            $uibModalInstance.close(response);
            vm.isSaving = false;
        }

        function onSaveError (error) {
            vm.isSaving = false;
            vm.errorMessage = error.data.errorMessage;
            vm.showErrorAlert = true;
        }
    }
})();