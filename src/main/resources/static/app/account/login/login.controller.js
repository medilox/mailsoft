(function() {
    'use strict';

    var app = angular.module('myApp');

    app.controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$state', '$stateParams', '$window', 'auth'];

    function LoginController ($scope, $state, $stateParams, $window, auth) {
        var vm = this;
        vm.login = login;
        vm.register = register;
        vm.requestResetPassword = requestResetPassword;

        if(auth.isAuthenticated()){
            $state.go('home', {}, {reload: true});
        }

        function login(){
            vm.isSaving = true;
            vm.userNotActivated = false;
            vm.authenticationError = false;

            auth.login(vm.credentials)
            .then(function(response) {
                if(response == "OK"){
                    $state.go('home', {}, {reload: true});
                }
                else{
                    console.log(response)
                    if(response == "Bad credentials")
                        vm.authenticationError = true;
                    else if(response == "User is disabled")
                        vm.userNotActivated = true;
                    else if(response == "User account has expired")
                        vm.accountExpired = true;
                    else
                    vm.authenticationError = true;
                }
                vm.isSaving = false;
            }, function(error){
                console.log(error)
            });
        };

        function register () {
            $state.go('register');
        }

        function requestResetPassword () {
            $state.go('requestReset');
        }
    };
}) ();


