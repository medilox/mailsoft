angular
.module('myApp', [
  'ui.router',
  'angular-jwt',
])

.config(function($locationProvider) {
      $locationProvider.html5Mode(true);
})

//Possibly unhandled rejection with Angular 1.5.9 #2889
.config(['$qProvider', function ($qProvider) {
      $qProvider.errorOnUnhandledRejections(false);
}])

.run(function ($rootScope, $state, auth, session, AUTH_EVENTS) {


    $rootScope.$on('$stateChangeStart', function (event, next, nextParams, fromState) {

        if ('data' in next && 'authorities' in next.data) {
          var authorities = next.data.authorities;
          if (authorities.length && !auth.hasRoleInArray(authorities)) {
              console.log('not authorized')
              //$state.go($state.current, {}, {reload: true});
              $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
              event.preventDefault();
          }
        }

        if (!auth.isAuthenticated()) {
          if (next.name !== 'login'
                && next.name !== 'register'
                && next.name !== 'activate'
                && next.name !== 'requestReset'
                && next.name !== 'finishReset'
            ) {

            event.preventDefault();
            $state.go('login');
          }
        }
    });

    $rootScope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
        console.log("not authenticated")
        $state.go('login');
    });

    $rootScope.$on(AUTH_EVENTS.notAuthorized, function(event) {
      $state.go('login');
    });
})

.config(function ($httpProvider) {
  //$httpProvider.interceptors.push('AuthInterceptor');
  $httpProvider.interceptors.push('authExpiredInterceptor');
});
