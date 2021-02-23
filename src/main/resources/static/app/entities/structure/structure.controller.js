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

        vm.structures = [];
        vm.loadStructures = loadStructures;
        function loadStructures(){
            Structure.getAll()
            .then(function(response){
                vm.structures = response.data;
            });
        }
        vm.loadStructures();

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
            //$scope.$emit('myApp:newStructure', result);
            $state.go(vm.previousState );
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();