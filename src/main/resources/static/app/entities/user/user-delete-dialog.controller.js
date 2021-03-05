(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('UserDeleteController',UserDeleteController);

    UserDeleteController.$inject = ['$uibModalInstance', 'entity', 'User'];

    function UserDeleteController($uibModalInstance, entity, User) {
        var vm = this;

        vm.user = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            User.delete(id)
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