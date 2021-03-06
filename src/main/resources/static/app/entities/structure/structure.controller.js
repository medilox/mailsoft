(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('StructureController', StructureController);

    StructureController.$inject = ['$rootScope', '$state', '$stateParams', 'previousState', 'entity', 'Structure'];

    function StructureController($rootScope, $state, $stateParams, previousState, entity, Structure) {
        var vm = this;
        vm.structure = entity;
        vm.previousState = previousState.name;

        vm.save = save;

        Structure.getAll()
        .then(function(response){
            vm.structures = response.data;
        });

        function save () {
            if(! vm.isSaving){
                vm.isSaving = true;
                console.log(vm.structure);
                if (vm.structure.id !== null) {
                    Structure.update(vm.structure, onSaveSuccess, onSaveError);
                } else {
                    Structure.save(vm.structure, onSaveSuccess, onSaveError);
                }
            }
        }

        function onSaveSuccess (result) {
            //$state.go(vm.previousState );
            $state.reload();
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.onClickStructure =  function(structure){
            if(vm.structure.id == structure.id)
            vm.structure = {};
            else{
                vm.structure.id = structure.id;
                vm.structure.name = structure.name;
                vm.structure.sigle = structure.sigle;
                vm.structure.parentId = structure.parentId;
            }
        }

    }
})();