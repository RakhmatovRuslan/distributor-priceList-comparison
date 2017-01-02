/**
 * Created by Ruslan on 12/28/2016.
 */
angular.module('nomenclature_app', [])
    .controller('nomenclature_controller', ['$scope', '$http',
            function ($scope, $http) {

                $scope.fetchNomenclatureList = function () {
                    $http.get('nomenclature/nomenclatureList.json').success(function (nomenclatures) {
                        $scope.nomenclatures = nomenclatures;
                    });
                };

                $scope.addNomenclature = function (nomenclatureName) {
                    $http({
                        method: 'POST',
                        url: 'nomenclature/addNomenclature',
                        data: {
                            id: 0,
                            name: nomenclatureName
                        }
                    }).then(function (response) {
                        $scope.fetchNomenclatureList();
                        $scope.nomenclatureName = '';
                        $scope.message = '';
                    },function (response) {
                            $scope.message = response.data;
                    });
                };

                $scope.removeAllNomenclatures = function () {
                    $http.get('nomenclature/removeAllNomenclatures').success(function () {
                        $scope.fetchNomenclatureList();
                    });
                };

                $scope.removeNomenclature = function (nomenclature) {
                    $http({
                        method: 'POST',
                        url: 'nomenclature/removeNomenclature',
                        data: nomenclature
                    }).then(function () {
                        $scope.fetchNomenclatureList();
                    });
                };

                $scope.fetchNomenclatureList();
            }
        ]
    );