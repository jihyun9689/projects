angular.module("sportsStore")
    .constant("dataUrl", "http://127.0.0.1:3000/products")
    .controller("sportsStoreCtrl", function ($scope, $http, dataUrl) {

        $scope.data = {};

        $http.get(dataUrl)
            .success(function (data) {
                console.log(data);
                $scope.data.products = data;
            })
            .error(function (error) {
                $scope.data.error = error;
            });
    });
