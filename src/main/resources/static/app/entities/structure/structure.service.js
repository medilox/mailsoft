(function() {
      'use strict';
      angular
          .module('myApp')
          .factory('Structure', Structure);

      Structure.$inject = ['$http'];

      function Structure ($http) {
           return{
                save: function(structure, onSaveSuccess, onSaveError){
                   return $http.post("/api/structures", structure)
                           .then(function(response){
                              onSaveSuccess(response);
                            }, function errorCallback(response) {
                             onSaveError(response);
                           });
               },
                update: function(structure, onSaveSuccess, onSaveError){
                    return $http.put("/api/structures", structure)
                            .then(function(response){
                               onSaveSuccess(response);
                             }, function errorCallback(response) {
                              onSaveError(response);
                            });
                },
                getAll: function(){
                    return $http.get("/api/structures");
              },
              get: function(id){
                    return $http.get("/api/structures/" + id);
              },
              getByCurrentStructure: function(id){
                  return $http.get("/api/my-structure");
              },
              delete: function(id){
                   return $http.delete("/api/structures/" + id);
              }
           };
      }
})();