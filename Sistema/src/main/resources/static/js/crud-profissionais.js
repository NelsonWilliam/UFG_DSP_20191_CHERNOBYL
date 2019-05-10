var myApp = angular.module('myApp', []);

myApp.controller('myCtrl', ['$scope', function($scope) {

    $scope.editedItem = { nome: "", cargo: "" };

    $scope.list = [];

    $scope.addItem = function(item) {
        $scope.list.push({
            nome: item.nome,
            cargo: item.cargo
        });
    };

    $scope.edit = function(item) {
        $scope.currentItem = item;
        $scope.editedItem.nome = item.nome;
        $scope.editedItem.cargo = item.cargo;
    };

    $scope.save = function(item) {
        $scope.currentItem.nome = item.nome;
        $scope.currentItem.cargo = item.cargo;
    };

    $scope.remove = function(item) {
        var index = $scope.list.indexOf(item);
        $scope.list.splice(index, 1);
    };
}]);