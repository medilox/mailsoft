angular
.module('myApp')
.directive('a', preventClickDirective)
.directive('customOnChange', customOnChange)


.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}])
.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
})

.directive('stopPropagation', function () {
    return {
        restrict: 'A',
        link: function (scope, element) {
            element.bind('click', function (event) {
                event.stopPropagation();
            });
        }
    };
})

.directive('loading', function () {
    return {
      template: '<div><div ng-show="loading" class="loading-container"></div><div ng-hide="loading" ng-transclude></div></div>',
      restrict: 'A',
      transclude: true,
      replace: true,
      scope:{
          loading: "=loading"
      },
      compile: function compile(element, attrs, transclude){
        var spinner = new Spinner().spin();
        var loadingContainer = element.find(".loading-container")[0];
        loadingContainer.appendChild(spinner.el);
        }
    };
  })

//input file on change
function customOnChange() {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {
      var onChangeHandler = scope.$eval(attrs.customOnChange);
      element.bind('change', onChangeHandler);
    }
  };
}


//Prevent click if href="#"
function preventClickDirective() {
  var directive = {
    restrict: 'E',
    link: link
  }
  return directive;

  function link(scope, element, attrs) {
    if (attrs.href === '#'){
      element.on('click', function(event){
        event.preventDefault();
      });
    }
  }
}





