(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('CourrierController', CourrierController);

    CourrierController.$inject = ['$rootScope', '$state', '$stateParams', 'previousState', 'entity', 'Courrier', 'naturesCourrier'];

    function CourrierController($rootScope, $state, $stateParams, previousState, entity, Courrier, naturesCourrier) {
        var vm = this;
        vm.courrier = entity;
        vm.previousState = previousState.name;

        vm.naturesCourrier = naturesCourrier;

        vm.save = save;
        function save () {
            if(! vm.isSaving){
                vm.isSaving = true;
                vm.courrier.dateEnvoi = moment(vm.dateEnvoi).format('YYYY-MM-DD');
                vm.courrier.dateReception = moment(vm.dateReception).format('YYYY-MM-DD');
                //console.log(vm.courrier);

                if (vm.courrier.id !== null) {
                    Courrier.update(vm.courrier, onSaveSuccess, onSaveError);
                } else {
                    Courrier.save(vm.courrier, onSaveSuccess, onSaveError);
                }
            }
        }

        function onSaveSuccess (result) {
            console.log(result)
            //$scope.$emit('myApp:newCourrier', result);
            $state.go(vm.previousState);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();