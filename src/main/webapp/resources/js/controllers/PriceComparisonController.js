/**
 * Created by Ruslan on 12/29/2016.
 */
angular.module('price_comparison_app', [])
    .controller('price_comparison_controller', function ($scope, $http) {
            $scope.getNomenclatures = function(){
                $http.get('/nomenclature/nomenclatureList.json')
                    .success(function(nomenclatures){
                        $scope.nomenclatures = nomenclatures;
                    });
            };

            $scope.getNomenclaturePrices = function(nomenclatureName){
                $http({
                    method: 'POST',
                    url: '/priceComparison/getNomenclaturePrices',
                    data: {
                        id: 0,
                        name: nomenclatureName
                    }
                })
                    .then(function(response){
                       $scope.distributors = response.data;
                    });
            };

            $scope.getNomenclatures();
        }
    );
