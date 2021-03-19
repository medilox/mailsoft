(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('ReceptionDialogController', ReceptionDialogController);

    ReceptionDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity',
                                                'Etape', 'Structure'];

    function ReceptionDialogController ($stateParams, $uibModalInstance, entity,
                                                Etape, Structure) {
        var vm = this;
        vm.etape = entity;
        console.log(vm.etape)
        
        vm.clear = clear;
        vm.save = save;

        function clear () {
             $uibModalInstance.dismiss('cancel');
         }

        function save () {
            vm.isSaving = true;
            vm.showErrorAlert = false;
            console.log(vm.etape)
            Etape.save(vm.etape, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError (error) {
            vm.isSaving = false;
            console.log(error)
            vm.errorMessage = error.data.errorMessage;
            vm.showErrorAlert = true;
        }

    }
})();
