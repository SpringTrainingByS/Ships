$(function () {
	$( "#registration-section" ).hide();
	$("#go-to-login").hide();
	
	$( "#registration-button" ).click(async function() {
		console.log("rejestracja");
		await register();
	});
	
	$("#login-button").bind("click", async function() {
		await login();
	});
	
	$("#go-to-login").bind("click", function() {
		$( "#registration-section" ).hide();
		$( "#login-section" ).show();
		$("#go-to-login").hide();
		$("#go-to-registration").show();
	});
	
	$("#go-to-registration").bind("click", function() {
		$( "#registration-section" ).show();
		$( "#login-section" ).hide();
		$("#go-to-login").show();
		$("#go-to-registration").hide();
	});
});

async function register() {
	let login = $( "#login" ).val();
	let password = $( "#password1" ).val();
	
	let url = SERVER_ADDRESS + "user/register";

	
	console.log("Wysyłam żadanie pod: " + url);
	console.log("username: " + login);
	console.log("password: " + password);
	
	await $.ajax({
		url:url,
		type:"POST",
		data: JSON.stringify({id: "", username: login, password: password }),
		contentType:"application/json; charset=utf-8"
	})
	.then(function() {
		showProperSectionAfterRegistration();
	}
	, function(e) {
		showRegistrationErrors(e);
	}); 
	
	function showProperSectionAfterRegistration() {
		console.log("udało się");
		$( "#registration-section" ).hide();
		$( "#login-section" ).show();
		$( "#login-message" ).append("<p>" + "Rejestracja zakończona sukcesem. Teraz możesz się zalogować." + "</p>");
		$("#go-to-login").hide();
		$("#go-to-registration").show();
	}
	
	function showRegistrationErrors(e) {
		console.log("Coś nie pykło");
		console.log(e);
		$( "#registration-message" ).empty();
		if (e.responseJSON != undefined) {
			for (x of e.responseJSON) {
				$( "#registration-message" ).append("<p>" + x + "</p>");
			}
		}
	}
}

async function login() {
	
	let username = $("#login-to-login").val();
	let password = $("#password-to-login").val();
	let url = SERVER_ADDRESS + "login";
	
	console.log ("username: " + username + ", password: " + password);
	console.log (JSON.stringify({id: "", username: username, password: password }));
	
	await $.ajax({
		url:url,
		type:"POST",
		data: JSON.stringify({id: "", username: username, password: password }),
		contentType:"application/json; charset=utf-8"
	})
	.then(function(data, status, xhr) {
		console.log("udało się zalogować");
		loginSuccess(true, xhr);
	}
	, function(e) {
		console.log("zaraz się uda zalogować");
		loginSuccess(false, e);
	});
	
	function loginSuccess(isSuccess, response) {
		let message = "";
		if (isSuccess) {
			message = "Logowanie zakończone powodzeniem";
			let token = response.getResponseHeader("authorization");
			console.log(token);
			localStorage.setItem(TOKEN_ACCESS_NAME, token);
			window.location.replace(SERVER_ADDRESS + "waiting-room");
		}
		else {
			message = "Logowanie zakończone niepowodzeniem";
			$("#password-to-login").val("");
		}
		
		$( "#login-message" ).empty();
		$( "#login-message" ).append("<p>" + message + "</p>");
	}
}

