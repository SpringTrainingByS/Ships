console.log(localStorage.getItem(TOKEN_ACCESS_NAME));
if (localStorage.getItem(TOKEN_ACCESS_NAME) == null) {
	window.location.replace(SERVER_ADDRESS);
}
else {
	showPage();
}

async function showPage() {
	
	$( "#send-req" ).click(async function() {
		let url = SERVER_ADDRESS + "/hello";
		await $.ajax({
			url:url,
			type:"POST",
			headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
			contentType:"application/json; charset=utf-8"
		})
		.then(function() {
			console.log("udało się");
		}
		, function(e) {
			console.log("nie udało się");
		});
		
	
	});
	
}


