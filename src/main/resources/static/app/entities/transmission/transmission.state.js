(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transmission', {
            parent: 'app',
            url: '/transmission',
            params: {idCourrier: null },
            templateUrl: 'app/entities/transmission/transmission.html',
            controller: 'TransmissionController',
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
        .state('transmission.new', {
            //url: '/new',
            params: {courrierId: null },
            onEnter: ['$state', '$stateParams', '$uibModal', function($state, $stateParams, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transmission/transmission-dialog.html',
                    controller: 'TransmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'false',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                courrierId: $stateParams.courrierId,
                                type: "transmission"
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transmission', null, { reload: 'transmission' });
                }, function() {
                    $state.go('transmission');
                });
            }]
        })
        .state('transmission.delete', {
            parent: 'transmission',
            //url: '/{etapeId}/delete',
            params : { etapeId: null },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transmission/transmission-delete-dialog.html',
                    controller: 'TransmissionDeleteController',
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
                    $state.go('transmission', null, { reload: 'transmission' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }
})();
