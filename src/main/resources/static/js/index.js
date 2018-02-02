async function register() {
	let login = $( "#login" ).val();
	let password = $( "password1" ).val();
	
	let url = SERVER_ADDRESS + "register";
	
	// Co teraz się dzie nie wiem
	
	await $.ajax({
		url:url,
		type:"POST",
		data: {id: "", login: login, password: password },
		contentType:"application/json; charset=utf-8",
		dataType:"json",
		success: function() {
			$( "#registration-section" ).hide();
			$( "#login-section" ).show();
		}
	})
	.catch(e => {
		console.log("Coś nie pykło");
		console.log(e);
		console.log(e.responseJSON.status);
		$( "#registration-message" ).text(e.responseJSON.message);
	}); 
}

$(function () {
	$( "#login-section" ).hide();
	$( "#registration-button" ).click(async function() {
		console.log("rejestracja");
		await register();
	});
});