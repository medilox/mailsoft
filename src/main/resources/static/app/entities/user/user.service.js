(function() {
      'use strict';
      angular
          .module('myApp')
          .factory('User', User);

      User.$inject = ['$http'];

      function User ($http) {
           return{
                save: function(user, onSaveSuccess, onSaveError){
                    return $http.post("/api/users", user)
                            .then(function(response){
                               onSaveSuccess(response);
                             }, function errorCallback(response) {
                              onSaveError(response);
                            });
                },
                update: function(user, onSaveSuccess, onSaveError){
                    return $http.put("/api/users", user)
                            .then(function(response){
                               onSaveSuccess(response);
                             }, function errorCallback(response) {
                              onSaveError(response);
                            });
                },
                getAll: function(page, size, sortBy, direction, login, email, roles){
                    return $http.get("/api/users"
                                    +"?page=" + page
                                    + "&size=" + size
                                    + "&sortBy=" + sortBy
                                    + "&direction=" + direction
                                    + "&login=" + login
                                    + "&email=" + email
                                    + "&roles=" + roles);
                },
                search: function(mc){
                    return $http.get("/api/users-search?mc=" + mc);
                },
                get: function(id){
                    return $http.get("/api/users/" + id);
                },
                sendCredentials: function(id, mail, onSuccess, onError){
                    return $http.post("/api/account/send-credentials/" + id + "?mail=" + mail)
                        .then(function(response){
                           onSuccess(response);
                         }, function errorCallback(response) {
                          onError(response);
                        });
                },
                delete: function(id){
                   return $http.delete("/api/users/" + id);
                }
           };
      }
})();