(function() {
    'use strict';

    angular
        .module('myApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('parcours', {
            parent: 'app',
            url: '/parcours',
            data: {
                authorities: []
            },

            templateUrl: 'app/entities/parcours/parcours.html',
            controller: 'ParcoursController',
            controllerAs: 'vm',

        })
        .state('parcours.etapes', {
            //url: '/new',
            params: {courrierId: null },
            onEnter: ['$state', '$stateParams', '$uibModal', function($state, $stateParams, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parcours/etapes-dialog.html',
                    controller: 'EtapesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'false',
                    size: 'xl',
                }).result.then(function() {
                    $state.go('parcours', null, { reload: 'parcours' });
                }, function() {
                    $state.go('parcours');
                });
            }]
        });
    }
})();
