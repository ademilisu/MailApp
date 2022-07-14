angular.module('mailApp')
	.component('profile', {
		templateUrl: '/app/template/account/profile.html',
		controller: function($scope, $location, Upload, AccountService) {

			$scope.delete = function() {
				image.src = '/app/img/profile.png';
				$scope.defaultImage = 'defaultImage';
				$scope.image = null;
			};

			$scope.select = function(file) {
				if (file != null) {
					image.src = URL.createObjectURL(file);
					$scope.defaultImage = 'null';
					$scope.image = file;
				}
			};

			$scope.save = function() {
				Upload.upload({
					url: '/accounts/profile',
					method: 'PUT',
					data: { file: $scope.image },
					params: { 'defaultImage': $scope.defaultImage }
				}).then(function onSuccess(response) {
					AccountService.setProfile(response.data);
					$location.path('/inbox');
				}).catch(function onError(response) {
					console.log(response);
				});
			};

			$scope.init = function() {
				$scope.image = null;
				$scope.account = profile;
			};
			$scope.init();
		}
	});