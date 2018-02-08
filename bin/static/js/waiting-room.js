console.log(localStorage.getItem(TOKEN_ACCESS_NAME));
console.log(localStorage.getItem(USERNAME));
console.log(JSON.stringify(SHIP_DEFINITION));

var userChanelName = "";

var stompClient;

preparePageAndLogic(); 

async function  preparePageAndLogic() {
	if (localStorage.getItem(TOKEN_ACCESS_NAME) == null) {
		window.location.replace(SERVER_ADDRESS);
	}
	else {
		showPage();
		loadShipDefinition();
		await obtainUserId();
		await obtainUserChanelName();
		userChanelName = localStorage.getItem(USER_CHANEL_NAME);
		await connectWithChanels();
		getActualMatchesByRest();
	}
}

async function showPage() {
	
	$( "#clear-local-storage" ).click(async function() {
		localStorage.clear();
	});
	
	$( "#send-ships-to-server" ).click(async function() {
		sendShipDefinitionContainer();
	});
	
	$(" #moving-to-game-room-message ").hide();
	
}


async function loadShipDefinition() {
	if (localStorage.getItem(SHIP_CONTAINER) != null) {
		SHIP_DEFINITION = JSON.parse(localStorage.getItem(SHIP_CONTAINER));
	}
	else {
		localStorage.setItem(SHIP_CONTAINER, JSON.stringify(SHIP_DEFINITION));
	}
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
	
	if (localStorage.getItem(USER_CHANEL_NAME) != null) {
		return;
	}
	
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

async function connectWithChanels() {
	
	let socket = new SockJS(MAIN_ENDPOINT);
	stompClient = Stomp.over(socket);
	
	var headers = {};
	headers["Authorization"] = localStorage.getItem(TOKEN_ACCESS_NAME);
	
	console.log("$" + USER_CHANEL_PREFIX + userChanelName + "$");
	
	stompClient.connect(headers, function (frame) {
		stompClient.subscribe(MAIN_CHANEL_NAME, function (message) {
			changeFirst10View(message);
		});
		stompClient.subscribe(USER_CHANEL_PREFIX + userChanelName, function (message) {
			doProperActionForUserChanel(message);
		});
	}, function () { console.log("nie udało się połączyć z kanałami"); });
	
}

async function getActualMatchesByRest() {
	
	let url = SERVER_ADDRESS + "actual-matches/get/all"
	await $.ajax({
		url:url,
		type:"GET",
		headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
		contentType:"application/json; charset=utf-8"
	})
	.then(function(response) {
		console.log("Udało się pobrać informacje o aktualnych rozgrywkach");
		console.log(response);
		$( "#actual-matches" ).empty();
		
		for (x of response) {
			$( "#actual-matches" ).append("<p>" + x.matchInfo + "<p>");
		}
		
	}
	, function(e) {
		console.log("NIe udało się pobrać informacje o aktualnych rozgrywkach");
	});
	
	
}

async function sendShipDefinitionContainer() {
	
	console.log("Wysyłam statki na serwer.");
	
	localStorage.setItem(SHIP_CONTAINER, JSON.stringify(SHIP_DEFINITION));
	
	SHIP_DEFINITION.userId = localStorage.getItem(USER_ID);
	
	let url = SERVER_ADDRESS + "ships/definition"
	await $.ajax({
		url:url,
		type:"POST",
		data: JSON.stringify(SHIP_DEFINITION),
		headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
		contentType:"application/json; charset=utf-8"
	});
}

function doProperActionForUserChanel(message) {
	let result = JSON.parse(message.body);
	let operationCode = parseInt(result.content);
	
	switch (operationCode) {
		case USER_CHANEL_WR_CODES.MOVE_TO_GAME:
			moveUserToGameRoom();
		break;
	} 
}

function changeFirst10View(response) {
	
	console.log(response);
	let result = JSON.parse(response.body);
	
	let finalResult = JSON.parse(result.content);
	console.log("finalResult: ");
	console.log(finalResult);
	
	$( "#actual-matches" ).empty();
	for (x of finalResult) {
		
		$( "#actual-matches" ).append("<p>" + x.matchInfo + "<p>");
	}
}

function moveUserToGameRoom() {
	stompClient.disconnect();
	$(" #moving-to-game-room-message ").show();
	setTimeout(function() {
		window.location.replace(SERVER_ADDRESS + "game-room");
	}, 2000); 
	
}






