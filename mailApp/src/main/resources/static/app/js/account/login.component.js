angular.module('mailApp')
	.component('login', {
		templateUrl: '/app/template/account/login.html',
		controller: function($scope, $location, $timeout, AccountApi, AccountService) {

			$scope.login = function() {
				AccountApi.login($scope.userRequest, function(resp) {
					if (resp.code == 10) {
						AccountService.setProfile(resp.profile);
						$location.path('/inbox');
					} else {
						$scope.error = true;
						$timeout(function() {
							$scope.error = false;
						}, 3000)
					}
				});
			};
		}
	});