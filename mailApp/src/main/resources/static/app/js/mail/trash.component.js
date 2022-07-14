angular.module('mailApp')
	.component('trash', {
		templateUrl: '/app/template/mail/trash.html',
		controller: function($scope, MailApi) {

			$scope.init = function() {
				$scope.box = 'trash';
				$scope.ActionDto = {
					boxItems: []
				}
				MailApi.list({ box: 'TRASH' }, function(resp) {
					$scope.items = resp;
				});
			};
			$scope.init();
		}
	});