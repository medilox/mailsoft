(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('login', {
          parent: 'app',
          url: '/login',
          templateUrl: 'app/account/login/login.html',
          controller: 'LoginController as vm',
        })
    }

})();
