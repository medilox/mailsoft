(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('CourrierDeleteController',CourrierDeleteController);

    CourrierDeleteController.$inject = ['$uibModalInstance', 'entity', 'Courrier'];

    function CourrierDeleteController($uibModalInstance, entity, Courrier) {
        var vm = this;

        vm.courrier = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Courrier.delete(id)
            .then(function(response){
                onSaveSuccess(response);
            }, function(){
                onSaveError();
            });
        }

        function onSaveSuccess (response) {
            $uibModalInstance.close(response);
            vm.isSaving = false;

        }

        function onSaveError () {
            vm.isSaving = false;

        }
    }
})();