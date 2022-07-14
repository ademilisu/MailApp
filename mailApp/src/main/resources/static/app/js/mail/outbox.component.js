angular.module('mailApp')
	.component('outbox', {
		templateUrl: '/app/template/mail/outbox.html',
		controller: function($scope, MailApi) {

			$scope.init = function() {
				$scope.box = 'outbox'
				$scope.ActionDto = {
					boxItems: []
				};
				MailApi.list({ box: 'OUTBOX' }, function(resp) {
					$scope.items = resp;
				});
			};
			$scope.init();
		}
	});