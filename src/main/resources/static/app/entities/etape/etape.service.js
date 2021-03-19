(function() {
      'use strict';
      angular
          .module('myApp')
          .factory('Etape', Etape);

      Etape.$inject = ['$http'];

      function Etape ($http) {
           return{
                save: function(etape, onSaveSuccess, onSaveError){
                   return $http.post("/api/etapes", etape)
                           .then(function(response){
                              onSaveSuccess(response);
                            }, function errorCallback(response) {
                             onSaveError(response);
                           });
               },
                update: function(etape, onSaveSuccess, onSaveError){
                    return $http.put("/api/etapes", etape)
                            .then(function(response){
                               onSaveSuccess(response);
                             }, function errorCallback(response) {
                              onSaveError(response);
                            });
                },

              getTransmissionsByCurrentUserStructure: function(){
                return $http.get("/api/etapes-transmissions-by-current-user-structure");
              },
              getReceptionsByCurrentUserStructure: function(){
                return $http.get("/api/etapes-receptions-by-current-user-structure");
              },

              getTransmissionsByCurrentUser: function(){
                return $http.get("/api/etapes-transmissions-by-current-user");
              },
              getReceptionsByCurrentUser: function(){
                return $http.get("/api/etapes-receptions-by-current-user");
              },
              get: function(id){
                   return $http.get("/api/etapes/" + id);
              },
              delete: function(id){
                   return $http.delete("/api/etapes/" + id);
              }
           };
      }
})();