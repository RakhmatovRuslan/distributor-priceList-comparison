angular.module('main_app',[])
    .controller('main_controller',function($scope, $http){
       $scope.referenceTo = function(request){
          $http.get('/'+request).success(function(){

          });
       } ;
    });