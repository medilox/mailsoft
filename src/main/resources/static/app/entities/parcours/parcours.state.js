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

        });
    }
})();
