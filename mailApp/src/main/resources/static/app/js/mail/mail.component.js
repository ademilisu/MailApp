angular.module('mailApp')
	.component('mail', {
		templateUrl: '/app/template/mail/mail.html',
		controller: function($scope, $location, $routeParams, MailApi) {

			$scope.id = $routeParams.id;

			$scope.trash = function() {
				$scope.actionDto = { boxItems: [] }
				$scope.actionDto.boxItems.push($scope.mailDto.item);
				MailApi.action({ action: 'trash' }, $scope.actionDto, function(resp) {
					if (resp.code == 10) {
						$location.path('/inbox');
					}
				});
			};

			$scope.setReceivers = function(event) {
				if (event.keyCode == 13) {
					$scope.names += ', ';
				}
			};

			$scope.send = function() {
				if ($scope.names != null) {
					$scope.names = $scope.names.split(", ");
					$scope.names.forEach(f => {
						if (f != '') {
							$scope.profile = {};
							$scope.profile.username = f;
							$scope.receivers.push($scope.profile);
						}
					});
					$scope.mailDto.receivers = $scope.receivers;
					if ($scope.mailDto.item.mail.title == '' || $scope.mailDto.item.mail.title == null) {
						$scope.mailDto.item.mail.title = 'New';
					}
					MailApi.send($scope.mailDto, function(resp) {
						if (resp.code == 10) {
							$location.path('/inbox');
						} else {
							toastr.warning('This email address is invalid!');
						}
					});
				} else {
					toastr.warning('Enter a receiver mail');
				}
			};

			$scope.init = function() {
				if ($scope.id != 'new') {
					$scope.pageTitle = 'Mail';
					$scope.mailLabel = 'From';
					MailApi.get({ id: $scope.id }, function(resp) {
						if (resp.code == 10) {
							$scope.mailDto = resp;
							$scope.names = resp.item.sender.username;
							$scope.new = false;
						}
					});
				} else {
					$scope.pageTitle = 'New';
					$scope.mailLabel = 'To';
					$scope.new = true;
					$scope.names = null;
					$scope.mailDto = {
						item: { mail: {} },
						receivers: []
					}
					$scope.receivers = [];
					$scope.profile = {};
				}
			};
			$scope.init();
		}
	});
