var app = angular.module('wackyWaver', ['angularMoment'])

app.service("wackyService", ["$http", function ($http) {
    var isWacky = function () {
        return $http
            .get("/wacky/switch")
            .then(function (response) {
                return response.data == true;
            });
    };

    function makeWacky(wackiness) {
        $http({
            method: "post",
            url: "/wacky/switch",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            params: { 'on': wackiness }
        }).success(function () {});
    }

    return {isWacky: isWacky, makeWacky : makeWacky};
}]);


app.controller('wacky-controller', ["$scope", "wackyService", "$timeout", function ($scope, wackyService, $timeout) {
    function updateWackiness() {
        wackyService.isWacky().then(function (data) {
            $scope.isWacky = data;
        });
    }

    $scope.toggleWackiness = function() {
        wackyService.makeWacky(!$scope.isWacky);
        updateWackiness();
    };

    var poll = function() {
            $timeout(function() {
                updateWackiness();
                poll();
            }, 900);
        };
    poll();
}]);




