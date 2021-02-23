(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user', {
            parent: 'app',
            url: '/user',
            params: {},
            templateUrl: 'app/entities/user/user.html',
            controller: 'UserController',
            controllerAs: 'vm',
            resolve: {
                roles: ['$http',  function($http) {
                   return $http.get('/api/roles')
                   .then(function(response){
                      return response.data;
                   })
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'home',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }],
            }
        });
    }

})();
