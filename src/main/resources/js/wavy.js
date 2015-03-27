var app = angular.module('wackyWaver', ['angularMoment'])

app.service("wackyService", ["$http", function ($http) {
    var isWacky = function () {
        return $http
            .get("/wacky/switch")
            .then(function (response) {
                return response.data == true;
            });
    };

    return {isWacky: isWacky};
}]);


app.controller('wacky-controller', ["$scope", "wackyService", "$timeout", function ($scope, wackyService, $timeout) {
    function updateWackiness() {
        wackyService.isWacky().then(function (data) {
            console.log(data);
            $scope.isWacky = data;
        });
    }
    var poll = function() {
            $timeout(function() {
                updateWackiness();
                poll();
            }, 900);
        };
    poll();
}]);




