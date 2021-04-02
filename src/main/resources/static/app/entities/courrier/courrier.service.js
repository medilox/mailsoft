(function() {
      'use strict';
      angular
          .module('myApp')
          .factory('Courrier', Courrier);

      Courrier.$inject = ['$http'];

      function Courrier ($http) {
           return{
                save: function(courrier, onSaveSuccess, onSaveError){
                   return $http.post("/api/courriers", courrier)
                           .then(function(response){
                              onSaveSuccess(response);
                            }, function errorCallback(response) {
                             onSaveError(response);
                           });
               },
                update: function(courrier, onSaveSuccess, onSaveError){
                    return $http.put("/api/courriers", courrier)
                            .then(function(response){
                               onSaveSuccess(response);
                             }, function errorCallback(response) {
                              onSaveError(response);
                            });
                },
                getAll: function(numCourrier, refCourrier, objet, concernes){
                    return $http.get("/api/courriers?numCourrier=" + numCourrier + "&refCourrier=" + refCourrier+ "&objet=" + objet+ "&concernes=" + concernes);
                },
                get: function(id){
                    return $http.get("/api/courriers/" + id);
                },
                delete: function(id){
                    return $http.delete("/api/courriers/" + id);
                }
           };
      }
})();