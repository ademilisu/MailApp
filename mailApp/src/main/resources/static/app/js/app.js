let app = angular.module('mailApp', ['ngRoute', 'ngResource', 'ngFileUpload']);

app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
	$locationProvider.html5Mode({ enabled: true });

	$routeProvider
		.when('/login', {
			template: '<login></login>'
		})
		.when('/signup', {
			template: '<signup></signup>'
		})
		.when('/inbox', {
			template: '<inbox></inbox>'
		})
		.when('/trash', {
			template: '<trash></trash>'
		})
		.when('/outbox', {
			template: '<outbox></outbox>'
		})
		.when('/mail/:id', {
			template: '<mail></mail>'
		})
		.when('/profile', {
			template: '<profile></profile>'
		})
}]);


app.factory('AccountApi', ['$resource', function($resource) {

	let base = "/accounts";

	return $resource(base, {}, {
		signup: {
			method: 'POST',
			url: base + '/signup'
		},
		login: {
			method: 'POST',
			url: base + '/login'
		},
		getProfile: {
			method: 'GET',
			url: base + '/profile'
		},
		logout: {
			method: 'POST',
			url: '/logout'
		}
	});
}]);

app.factory('MailApi', ['$resource', function($resource) {

	let base = '/mails';

	return $resource(base, {}, {
		list: {
			method: 'GET',
			url: base,
			isArray: true
		},
		get: {
			method: 'GET',
			url: base + '/:id'
		},
		action: {
			method: 'POST',
			url: base
		},
		send: {
			method: 'POST',
			url: base + '/send'
		}
	});
}]);

app.controller('MainCtrl', function($scope, $location, AccountApi, AccountService) {

	$scope.logout = function() {
		AccountApi.logout(function() {
			AccountService.setProfile(null);
			$scope.header = false;
			$location.path('/login');
		});
	};

	$scope.getProfile = function() {
		AccountApi.getProfile(function(resp) {
			if (resp.id) {
				AccountService.setProfile(resp);
			}
		});
	};

	$scope.checkProfile = function(prf) {
		if (prf) {
			$scope.header = true;
			$scope.account = prf;
		} else {
			$scope.header = false;
		}
	};

	$scope.init = function() {
		$scope.getProfile();
		AccountService.subscribe($scope.checkProfile);
	};
	$scope.init();
});

app.service('AccountService', function() {

	let observers = [];

	this.subscribe = function(func) {
		observers.push(func);
	};

	this.setProfile = function(p) {
		profile = p;
		observers.forEach(f => f(p));
	};
});

app.directive('boxItem', function($location, MailApi) {

	return {
		restrict: 'E',
		templateUrl: '/app/template/mail/box-item.html',
		transclude: true,
		scope: {
			box: '=',
			items: '=',
			actiondto: '='
		},
		link: function(scope) {

			scope.uncheckAll = function() {
				$("input[type='checkbox']").prop('checked', false);
			};

			scope.action = function(see) {
				let action = null;
				if (scope.box == 'inbox' || scope.box == 'outbox') {
					action = 'trash';
				}
				if (scope.box == 'trash') {
					action = 'delete';
				}
				if (see == 'see') {
					action = 'see';
				}
				MailApi.action({ action: action }, scope.actiondto, function(resp) {
					if (resp.code == 10) {
						scope.actiondto.boxItems.forEach(f => {
							if (action == 'see') {
								f.type = 'SEEN';
							} else {
								_.remove(scope.items, { id: f.id });
							}
						});
						scope.dVisible = false;
						scope.rVisible = false;
						scope.sVisible = false;
						scope.uncheckAll();
					}
				});
			};

			scope.recycle = function() {
				MailApi.action({ action: 'recycle' }, scope.actiondto, function(resp) {
					if (resp.code == 10) {
						scope.actiondto.boxItems.forEach(f => {
							_.remove(scope.items, { id: f.id });
						});
						scope.dVisible = false;
						scope.rVisible = false;
						scope.sVisible = false;
						scope.uncheckAll();
					}
				});
			};

			scope.select = function(item) {
				scope.actiondto.boxItems == [];
				scope.actiondto.boxItems.push(item);
				scope.action('see');
				$location.path('/mail/' + item.id);
			};

			scope.checked = function(item, check) {
				if (check) {
					scope.actiondto.boxItems.push(item);
				} else {
					_.remove(scope.actiondto.boxItems, item);
				}
				scope.arrange();
			};

			scope.arrange = function() {
				if (scope.actiondto.boxItems.length > 0) {
					scope.dVisible = true;
					if (scope.box == 'trash') {
						scope.rVisible = true;
					}
					if (scope.box != 'outbox') {
						scope.sVisible = true;
					}
				} else {
					scope.dVisible = false;
					scope.rVisible = false;
					scope.sVisible = false;
				}
			};
		}
	}
});






