async function register() {
	let login = $( "#login" ).val();
	let password = $( "#password1" ).val();
	
	let url = SERVER_ADDRESS + "user/register";
	
	// Co teraz się dzie nie wiem
	
	console.log("Wysyłam żadanie pod: " + url);
	console.log("login: " + login);
	console.log("password: " + password);
	
	await $.ajax({
		url:url,
		type:"POST",
		data: JSON.stringify({id: "", login: login, password: password }),
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
	}
	
	function showRegistrationErrors(e) {
		console.log("Coś nie pykło");
		console.log(e);
		if (e.responseJSON != undefined) {
			for (x of e.responseJSON) {
				$( "#registration-message" ).append("<p>" + x + "</p>");
			}
		}
	}
}

$(function () {
	$( "#registration-section" ).hide();
	$( "#registration-button" ).click(async function() {
		console.log("rejestracja");
		await register();
	});
});