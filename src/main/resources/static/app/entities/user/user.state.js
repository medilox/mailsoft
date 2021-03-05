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
        })
        .state('user.delete', {
            parent: 'user',
            url: '/{userId}/delete',
            params : { userId: null },
            data: {
                authorities: ['ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user/user-delete-dialog.html',
                    controller: 'UserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['User',  function(User) {
                             return User.get($stateParams.userId)
                             .then(function(response){
                                return response.data;
                            })
                        }]
                    }
                }).result.then(function() {
                    $state.go('user', null, { reload: 'user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        ;
    }

})();
