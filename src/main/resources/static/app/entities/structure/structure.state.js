(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('structure', {
            parent: 'app',
            url: '/structure',
            params: {},
            templateUrl: 'app/entities/structure/structure.html',
            controller: 'StructureController',
            controllerAs: 'vm',
            resolve: {
                entity: function () {
                    return {
                        name: null,
                        sigle: null,
                        id: null
                    };
                },
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
        .state('structure.edit', {
            parent: 'structure',
            url: '/{structureId}/edit',
            params : { structureId: null },
            data: {
                authorities: ['ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/structure/structure-dialog.html',
                    controller: 'StructureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Structure',  function(Structure) {
                             return Structure.get($stateParams.structureId)
                             .then(function(response){
                                return response.data;
                             })
                        }]
                    }
                }).result.then(function() {
                    $state.go('structure', null, { reload: 'structure' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('structure.delete', {
            parent: 'structure',
            url: '/{structureId}/delete',
            params : { structureId: null },
            data: {
                authorities: ['ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/structure/structure-delete-dialog.html',
                    controller: 'StructureDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Structure',  function(Structure) {
                             return Structure.get($stateParams.structureId)
                             .then(function(response){
                                return response.data;
                            })
                        }]
                    }
                }).result.then(function() {
                    $state.go('structure', null, { reload: 'structure' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
