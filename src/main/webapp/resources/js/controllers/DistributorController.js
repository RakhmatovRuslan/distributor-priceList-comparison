/**
 * Created by Ruslan on 12/29/2016.
 */
angular.module('distributor_app', [])
    .controller('distributor_controller', function ($scope, $http) {
        $scope.fetchDistributorsList = function () {
            $http.get('/distributor/getAllDistributors').success(function (distributors) {
                $scope.distributors = distributors;
            });
        };

        $scope.removeDistributor = function(id){
            $http.post('/distributor/remove/'+id).success(function (){
                $scope.fetchDistributorsList();
                $scope.removed = true;
            });
        };
        $scope.enableUpload = function(state){
            var file = $scope.file;
          $scope.uploaded = state;
        };

        $scope.removeAllDistributors = function(){
            $http.get('/distributor/removeAll').success(function(){
                $scope.fetchDistributorsList();
                $scope.removed = true;
            });
        };

        $scope.fetchDistributorsList();


    });