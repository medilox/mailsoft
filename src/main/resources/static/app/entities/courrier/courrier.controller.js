(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('CourrierController', CourrierController);

    CourrierController.$inject = ['$rootScope', '$state', '$stateParams', 'previousState', 'entity', 'Courrier', 'naturesCourrier', '$filter'];

    function CourrierController($rootScope, $state, $stateParams, previousState, entity, Courrier, naturesCourrier, $filter) {
        var vm = this;
        vm.courrier = entity;
        vm.previousState = previousState.name;

        vm.naturesCourrier = naturesCourrier;

        Courrier.getAll()
        .then(function(response){
            vm.courriers = response.data;
        });


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
            $state.reload();
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.onClickCourrier =  function(courrier){
            if(vm.courrier.id == courrier.id)
            vm.courrier = {};
            else{
                vm.courrier.id = courrier.id;
                vm.courrier.refCourrier = courrier.refCourrier;
                vm.courrier.numCourrier = courrier.numCourrier;
                vm.courrier.initiateur = courrier.initiateur;
                vm.courrier.objet = courrier.objet;
                vm.courrier.natureCourrier =  $filter('filter')(vm.naturesCourrier, {value: courrier.natureCourrier})[0].name;
                vm.dateEnvoi = moment(courrier.dateEnvoi, 'YYYY-MM-DD').toDate();
                vm.dateReception = moment(courrier.dateReception, 'YYYY-MM-DD').toDate();
                vm.courrier.concernes = courrier.concernes;
            }
        }
    }
})();