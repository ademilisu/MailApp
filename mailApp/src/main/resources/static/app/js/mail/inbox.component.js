angular.module('mailApp')
	.component('inbox', {
		templateUrl: '/app/template/mail/inbox.html',
		controller: function($scope, MailApi) {

			$scope.init = function() {
				$scope.box = 'inbox';
				$scope.ActionDto = {
					boxItems: []
				}
				MailApi.list({ box: 'INBOX' }, function(resp) {
					$scope.items = resp;
				});
			};
			$scope.init();
		}
	});