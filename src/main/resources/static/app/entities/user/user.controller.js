(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('UserController', UserController);

    UserController.$inject = ['$scope', '$state', 'session', '$stateParams', 'previousState', 'User', 'roles'];

    function UserController ($scope, $state, session, $stateParams, previousState, User, roles) {
        var vm = this;
        vm.roles = roles;
        vm.previousState = previousState.name;
        vm.save = save;

        function save () {
            if(! vm.isSaving){
                vm.isSaving = true;
                console.log(vm.user);
                if (vm.user.password !== vm.confirmPassword) {
                   vm.doNotMatch = 'ERROR';
                   vm.isSaving = false;
                }
                else{
                    vm.doNotMatch = null;
                    vm.error = null;
                    vm.errorUserExists = null;
                    vm.errorEmailExists = null;

                    vm.user.email = vm.user.login + "@mailsoft.com";
                    User.save(vm.user, onSaveSuccess, onSaveError);
                }
            }
        }

        function onSaveSuccess (result) {
            vm.success = 'OK';
            vm.error = false;
            vm.isSaving = false;
            $state.go(vm.previousState );
        }

        function onSaveError (response) {
            vm.isSaving = false;

            if(response.data && response.data.error == "e-mail address already in use")
            vm.errorEmailExists = true;

            else if(response.data && response.data.error == "login already in use")
            vm.errorUserExists = true;

            else
            vm.error = true;
        }

    }
})();
