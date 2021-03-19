(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reception', {
            parent: 'app',
            url: '/reception',
            params: {idCourrier: null },
            templateUrl: 'app/entities/reception/reception.html',
            controller: 'ReceptionController',
            controllerAs: 'vm',
            resolve: {
                entity: function () {
                    return {
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
        .state('reception.new', {
            //url: '/new',
            params: {courrierId: null, structureId: null },
            onEnter: ['$state', '$stateParams', '$uibModal', function($state, $stateParams, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reception/reception-dialog.html',
                    controller: 'ReceptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'false',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                courrierId: $stateParams.courrierId,
                                structureId: $stateParams.structureId,
                                type: "reception"
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reception', null, { reload: 'reception' });
                }, function() {
                    $state.go('reception');
                });
            }]
        })
        .state('reception.delete', {
            parent: 'reception',
            //url: '/{etapeId}/delete',
            params : { etapeId: null },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reception/reception-delete-dialog.html',
                    controller: 'ReceptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Etape',  function(Etape) {
                             return Etape.get($stateParams.etapeId)
                             .then(function(response){
                                return response.data;
                            })
                        }]
                    }
                }).result.then(function() {
                    $state.go('reception', null, { reload: 'reception' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }
})();
