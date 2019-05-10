var myApp = angular.module('myApp', []);

myApp.controller('myCtrl', ['$scope', function($scope) {

    $scope.editedItem = { titulo: "", ficha: "", premio: "", ator: "", diretor: "" };

    $scope.list = [];

    $scope.addItem = function(item) {
        $scope.list.push({
            titulo: item.titulo,
            ficha: item.ficha,
            premio: item.premio,
            ator: item.ator,
            diretor: item.diretor
        });
    };

    $scope.edit = function(item) {
        $scope.currentItem = item;
        $scope.editedItem.titulo = item.titulo;
        $scope.editedItem.ficha = item.ficha;
        $scope.editedItem.premio = item.premio;
        $scope.editedItem.ator = item.ator;
        $scope.editedItem.diretor = item.diretor;
    };

    $scope.save = function(item) {
        $scope.currentItem.titulo = item.titulo;
        $scope.currentItem.ficha = item.ficha;
        $scope.currentItem.premio = item.premio;
        $scope.currentItem.ator = item.ator;
        $scope.currentItem.diretor = item.diretor;
    };

    $scope.remove = function(item) {
        var index = $scope.list.indexOf(item);
        $scope.list.splice(index, 1);
    };
}]);

function previewFile() {
    var preview = document.querySelector('#imagem'); //selects the query named img
    var file = document.querySelector('input[type=file]').files[0]; //sames as here
    var reader = new FileReader();

    reader.onloadend = function() {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file); //reads the data as a URL
    } else {
        preview.src = "";
    }
}