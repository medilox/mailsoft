(function() {
    'use strict';

    angular
        .module('myApp')
        .controller('CourrierFileController', CourrierFileController);

    CourrierFileController.$inject = ['$rootScope', '$scope', '$state', '$uibModalInstance', '$uibModal', 'CourrierFileService', 'entity'];

    function CourrierFileController ($rootScope, $scope, $state, $uibModalInstance, $uibModal, CourrierFileService, entity) {
        var vm = this;
        vm.courrierFile = entity;

        console.log(vm.courrierFile)
        vm.clear = clear;
        vm.save = save;


        function clear () {
           $uibModalInstance.dismiss();
        }

        vm.newFile = newFile;
        function newFile(){
            vm.modalInstance = $uibModal.open({
                templateUrl: 'app/entities/courrier/courrier-file-new-dialog.html',
                scope: $scope,
                backdrop: false,
                size: 'md'
            });
        };

        vm.clear2 = clear2;
        function clear2 () {
            if(vm.modalInstance)
            vm.modalInstance.dismiss();
        }

        vm.loadFiles = loadFiles;
        function loadFiles(){
            CourrierFileService.getByCourrierId(vm.courrierFile.courrierId)
            .then(function(response){
                vm.courrierFiles = response.data;
            });
        }
        vm.loadFiles();

        function save () {
            vm.isSaving = true;
            if(vm.file){
                CourrierFileService.save(vm.courrierFile, vm.file).then(function(response){
                    vm.isSaving = false;
                    vm.courrierFile.name = "";
                    vm.courrierFile.description = "";
                    vm.loadFiles();
                    vm.modalInstance.dismiss();
                });
            }
        }

        vm.deleteFile = deleteFile;
        function deleteFile(fileId){
            vm.selectedFileId = fileId;
            vm.modalInstance = $uibModal.open({
                templateUrl: 'app/entities/courrier/courrier-file-delete-dialog.html',
                scope: $scope,
                backdrop: false,
                size: 'md'
            });
        };

        vm.confirmDelete = confirmDelete;
        function confirmDelete () {
            CourrierFileService.deleteFile(vm.selectedFileId)
            .then(function(response){
                onSaveSuccess(response);
            }, function(){
                onSaveError();
            });
        }

        function onSaveSuccess (response) {
            vm.loadFiles();
            vm.modalInstance.dismiss();
            vm.isSaving = false;

        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.downloadFile = downloadFile;
        function downloadFile (fileId) {
            CourrierFileService.downloadFile(fileId)
            .then(function(response){
                showDownloadPopup(response);
            });
        }

        function showDownloadPopup(response){
           var headers = response.headers();
           var filename = getFileNameFromHttpResponse(response);
           var contentType = headers['content-type'];
           var linkElement = document.createElement('a');
           try {
               var blob = new Blob([response.data], { type: contentType });
               var url = window.URL.createObjectURL(blob);
               linkElement.setAttribute('href', url);
               linkElement.setAttribute("download", filename);
               var clickEvent = new MouseEvent("click", {
                   "view": window,
                   "bubbles": true,
                   "cancelable": false
               });
               linkElement.dispatchEvent(clickEvent);
           } catch (ex) {
               console.log(ex);
           }
        }

        //=====================================
        function getFileNameFromHttpResponse(httpResponse) {
              var contentDispositionHeader = httpResponse.headers('Content-Disposition');
              var result = contentDispositionHeader.split(';')[1].trim().split('=')[1];
              return result.replace(/"/g, '');
        }
    }
})();
