(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('StructureDeleteController',StructureDeleteController);

    StructureDeleteController.$inject = ['$uibModalInstance', 'entity', 'Structure'];

    function StructureDeleteController($uibModalInstance, entity, Structure) {
        var vm = this;

        vm.structure = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Structure.delete(id)
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