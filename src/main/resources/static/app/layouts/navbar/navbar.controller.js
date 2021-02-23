(function() {
'use strict';

angular
.module('myApp')
.controller('NavbarController', NavbarController);

NavbarController.$inject = ['$rootScope', '$scope', '$state', 'session',
                            'JhiLanguageService', '$translate', 'tmhDynamicLocale'];

function NavbarController ($rootScope, $scope, $state, session,
                            JhiLanguageService, $translate, tmhDynamicLocale) {
    var vm = this;
    //vm.login = LoginService.open;

    vm.changeLanguage = changeLanguage;
    vm.languages = null;

    JhiLanguageService.getAll().then(function (languages) {
        vm.languages = languages;
    });

    function changeLanguage (languageKey) {
        $translate.use(languageKey);
        tmhDynamicLocale.set(languageKey);
        session.setLangKey(languageKey);
    }

    JhiLanguageService.getCurrent().then(function(current) {
        var lang = current;

        if(!lang)
        lang = "fr";

        if (lang !== current) {
           $translate.use(lang);
        }
        session.setLangKey(lang);
    });
}
})();