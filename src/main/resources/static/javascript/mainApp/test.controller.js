/* global angular, UHGroupingsApp */

(function () {

    /**
     * Controller for checking valid UhUuid upon login. In LDAP, if a person is in the
     * "misc" branch and not in the "people" branch, the UhUuid won't work with
     * UHGroupings and an error modal shows up which prompts them to logout.
     *
     * @param $scope - Binding between controller and HTML page
     * @param $window - A reference to the browser's window object
     * @param $uibModal - Creates modal
     * @param dataProvider - Used for HTTP requests
     * @param BASE_URL - Base url for api calls
     * @param $timeout - AngularJS wrapper for window.setTimeout, used to implement session timeout
     * @param $interval - AngularJS wrapper for window.setInterval, used to implement session timeout
     * @constructor
     */
    function TestJsController($scope, $window, $uibModal, $controller, groupingsService, dataProvider, BASE_URL, $timeout, $interval) {

        angular.extend(this, $controller("GeneralJsController", { $scope }));

        let isModalOpen = false;

        angular.element(function () {

            groupingsService.getCurrentUser((res) => {

                // $scope.currentUser = "help";
                groupingsService.getMemberAttributes($scope.currentUser, function (person) {
                    $scope.user = person;
                    console.log("5: " + $scope.currentUser);
                    console.log("3: " + $scope.user.uhUuid);
                    // to show the modal uncomment line below
                    // $scope.user.uhUuid = null;
                    if (person.uhUuid === null) {
                        $scope.createTestModal();
                    }
                }, function () {
                        console.log("error");
                    $scope.createTestModal();
                    //
                });
                }
            );
            /*$(this).click(function (e) {
                console.log("click");
            });
            $(this).keypress(function (e) {
                $scope.createTestModal();
                console.log("press");
            });*/
        });

        /**
         * Create xxx modal.
         */
        $scope.createTestModal = function () {
            $scope.testModalInstance = $uibModal.open({
                templateUrl: "modal/testModal",
                scope: $scope,
                backdrop: "static",
                keyboard: false
            });
        };
    }

    UHGroupingsApp.controller("TestJsController", TestJsController);
}());
