(function() {
    'use strict';

    angular
        .module('myApp')
        .factory('CourrierFileService', CourrierFileService);

    CourrierFileService.$inject = ['$uibModal', '$rootScope', '$http', 'Upload'];

    function CourrierFileService ($uibModal, $rootScope, $http, Upload) {
        var service = {
            open: open,
            save: save,
            getByCourrierId: getByCourrierId,
            downloadFile: downloadFile,
            deleteFile: deleteFile
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };
        return service;

        function open (courrierId) {
            if (modalInstance !== null) return;

            modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/entities/courrier/courrier-file-dialog.html',
                controller: 'CourrierFileController',
                controllerAs: 'vm',
                backdrop: 'false',
                size: 'lg',
                resolve: {
                  entity: function () {
                      return {
                          courrierId: courrierId
                      };
                  }
                }
            });

            modalInstance.result.then(
                resetModal,
                resetModal
            );
        }

        function save (courrierFile, file) {
             return  Upload.upload({
                 url: '/api/courrier-files',
                 data: {file: file, 'courrierFile': Upload.jsonBlob(courrierFile) }
             });
        }

        function getByCourrierId (courrierId) {
             return $http.get("/api/courrier-files-by-courrier/" + courrierId);
        }

        function deleteFile (id) {
             return $http.delete("/api/courrier-files/" + id);
        }

        function downloadFile (id) {
            return $http.get("/api/download/" + id, {responseType: 'arraybuffer'})
        }
    }
})();
