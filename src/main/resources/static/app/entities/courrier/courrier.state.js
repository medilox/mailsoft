(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('courrier', {
            parent: 'app',
            url: '/courrier',
            params: {idCourrier: null },
            templateUrl: 'app/entities/courrier/courrier.html',
            controller: 'CourrierController',
            controllerAs: 'vm',
            resolve: {
                entity: function () {
                    return {
                        id: null
                    };
                },
                naturesCourrier : ['$http', function ($http) {
                    return $http.get("/api/natures-courrier")
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
        .state('courrier.delete', {
            parent: 'courrier',
            url: '/{courrierId}/delete',
            params : { courrierId: null },
            data: {
                //authorities: ['ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/courrier/courrier-delete-dialog.html',
                    controller: 'CourrierDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Courrier',  function(Courrier) {
                             return Courrier.get($stateParams.courrierId)
                             .then(function(response){
                                return response.data;
                            })
                        }]
                    }
                }).result.then(function() {
                    $state.go('courrier', null, { reload: 'courrier' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }
})();
