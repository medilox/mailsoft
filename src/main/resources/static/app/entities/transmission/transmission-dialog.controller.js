(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('TransmissionDialogController', TransmissionDialogController);

    TransmissionDialogController.$inject = ['$timeout', '$stateParams', '$uibModalInstance', 'entity',
                                                'Etape', 'Structure'];

    function TransmissionDialogController ($timeout, $stateParams, $uibModalInstance, entity,
                                                Etape, Structure) {
        var vm = this;
        vm.etape = entity;

        Structure.getAll()
       .then(function(response){
           vm.structures = response.data;
       });

        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

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
