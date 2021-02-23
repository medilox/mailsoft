angular
.module('myApp')
.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

  $urlRouterProvider.otherwise('/');

  $stateProvider
  .state('app', {
    abstract: true,
    templateUrl: 'app/layouts/layout.html',
  })
}]);
