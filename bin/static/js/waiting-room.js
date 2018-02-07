console.log(localStorage.getItem(TOKEN_ACCESS_NAME));
console.log(localStorage.getItem(USERNAME));

var userChanelName = "";

preparePageAndLogic(); 

async function  preparePageAndLogic() {
	if (localStorage.getItem(TOKEN_ACCESS_NAME) == null) {
		window.location.replace(SERVER_ADDRESS);
	}
	else {
		showPage();
		await obtainUserId();
		await obtainUserChanelName();
		userChanelName = localStorage.getItem(USER_CHANEL_NAME);
		await connectWithChanels();
		putUserToWaitingList();
	}
}

async function showPage() {
	
	$( "#clear-local-storage" ).click(async function() {
		localStorage.clear();
	});
	
}

async function connectWithChanels() {
	
	let socket = new SockJS(MAIN_ENDPOINT);
	stompClient = Stomp.over(socket);
	
	var headers = {};
	headers["Authorization"] = localStorage.getItem(TOKEN_ACCESS_NAME);
	
	stompClient.connect(headers, function (frame) {
		stompClient.subscribe(USER_CHANEL_PREFIX + userChanelName, function (message) {
			console.log("Z kanału użytkownika" + message);
		});
		stompClient.subscribe(MAIN_CHANEL_NAME, function (message) {
			console.log("Z kanału głównego" + message);
		});
	}, function () { console.log("nie udało się połączyć z kanałami"); });
	
}

async function putUserToWaitingList() {
	let url = SERVER_ADDRESS + "waiting-for-match"
	await $.ajax({
		url:url,
		type:"GET",
		data: { user_id : localStorage.getItem(USER_ID)},
		headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
		contentType:"application/json; charset=utf-8"
	})
	.then(function(response) {
		console.log("udało się dodać użytkowników do listy oczekujących");
	}
	, function(e) {
		console.log("nie udało się dodać użytkownika do listy oczekujących");
	});
}

async function obtainUserId() {
	
	if (localStorage.getItem(USER_ID) != null) {
		return;
	}
	
	let url = SERVER_ADDRESS + "/user"
	await $.ajax({
		url:url,
		type:"GET",
		data: { username: localStorage.getItem(USERNAME)},
		headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
		contentType:"application/json; charset=utf-8"
	})
	.then(function(response) {
		console.log("pobrałem id usera");
		localStorage.setItem(USER_ID, response);
	}
	, function(e) {
		console.log("nie udało się pobrać id dla użytkownika");
	});
}

async function obtainUserChanelName() {
	
	if (localStorage.getItem(USER_ID) == null) {
		return;
	}
	
	let url = SERVER_ADDRESS + "/chanel"
	await $.ajax({
		url:url,
		type:"GET",
		data: { user_id: localStorage.getItem(USER_ID)},
		headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
		contentType:"application/json; charset=utf-8"
	})
	.then(function(response) {
		console.log("pobrałem username chanel");
		console.log(response);
		localStorage.setItem(USER_CHANEL_NAME, response);
		userChanelName = response;
	}
	, function(e) {
		console.log("nie udało się pobrać informacji o kanale specjalnym dla użytkownika.");
	});
}






