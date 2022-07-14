angular.module('mailApp')
	.component('signup', {
		templateUrl: '/app/template/account/signup.html',
		controller: function($scope, $timeout, $location, AccountApi) {

			$scope.signup = function() {
				AccountApi.signup($scope.userRequest, function(resp) {
					if (resp.code == 10) {
						$location.path('/login');
					} else {
						$scope.error = true;
						$timeout(function() {
							$scope.error = false;
						}, 3000);
					}
				});
			};
		}
	});