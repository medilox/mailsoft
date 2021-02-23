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
            params: {},
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
        });
    }
})();
