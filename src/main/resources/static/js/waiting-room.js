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
		loadOwnShipSection();
		randomShips();
		showShipsOnPanel();
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
	$("#alone-message").hide();
	
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
	})
	.then(function(response) {
		console.log(response);
		if (response == true) {
			console.log("Ustawiam widok na widoczny");
			$("#alone-message").show();
		}
	}
	, function(e) {
		console.log("NIe udało się wysłać statków na serwer");
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
	$("#moving-to-game-room-message").show();
	setTimeout(function() {
		window.location.replace(SERVER_ADDRESS + "game-room");
	}, 2000); 
	
}

async function loadOwnShipSection() {
	
	let row = document.createElement("div"); 
	let span = document.createElement('span');
	row.setAttribute('class', 'row');
	span.setAttribute('class', 'col-lp');
	row.appendChild(span);
	
	for (i = 1; i <= 10; i++) {
		let span = document.createElement('span');
		span.setAttribute('class', 'col-lp');
		span.innerHTML = i;
		row.appendChild(span);
	}
	$("#own-ship-section-table").append(row);
	
	for (i = 1; i <= 10; i++) {
		let row = document.createElement("div");
		row.setAttribute('class', 'row');
		for (j = 0; j <= 10; j++) {
			let span = document.createElement('span');
			
			if (j == 0) {
				span.setAttribute('class', 'col-lp');
				span.innerHTML = i;
			}
			else {
				span.setAttribute('class', 'col-ship');
				span.innerHTML = " ";
				span.setAttribute('id', 'own-pos' + i + j);
				span.setAttribute('title', "pos: " + i + j);
				span.style.outline = "1px solid black";
			}
			
			row.appendChild(span);
		}
		$("#own-ship-section-table").append(row);
	}
}

var ALLOWED_FIELDS = [];
var TAKEN_FIELDS = [];
var FORBIDDEN_FIELDS = [];
var SHIPS = [];

async function randomShips() {
	prepareFieldsInfo();
	findFieldsForAllShips();
}

function prepareFieldsInfo() {
	let val = "";
	for (i = 1; i <= 10; i++) {
		for (j = 1; j <= 10; j++) {
			val = i + "" + j;
			ALLOWED_FIELDS.push(val);	
		}
	}
}

var SHIP_DIRECTION = {
		UP: 0,
		DOWN: 1,
		RIGHT: 2,
		LEFT: 3
	};


var SHIP_SIZES = [2, 3, 3, 4, 5];

function findFieldsForAllShips() {
	for (size of SHIP_SIZES) {
		console.log("Losuję pozycję dla statku o rozmiarze: " + size);
		findFieldsForShip(size); 
	}
}


function findFieldsForShip(size) {

	let firstFieldInfo = randomFirstField();
	
	if ( checkFieldsUp(firstFieldInfo, size) ) {
		console.log("Statek o rozmiarze: " + size + " tworzy pola w górę");
		removeFieldsFromAllowedToUp(firstFieldInfo, size);
		addShipLocationsToUp(firstFieldInfo, size) 
	}
	else if ( checkFieldsRight(firstFieldInfo, size) ) {
		console.log("Statek o rozmiarze: " + size + " tworzy pola w w prawo");
		removeFieldsFromAllowedToRight(firstFieldInfo, size);
		addShipLocationsToRight(firstFieldInfo, size); 
	}
	else if ( checkFieldsDown(firstFieldInfo, size) ) {
		console.log("Statek o rozmiarze: " + size + " tworzy pola w dół");
		removeFieldsFromAllowedToDown(firstFieldInfo, size);
		addShipLocationsToDown(firstFieldInfo, size); 
	}
	else if ( checkFieldsLeft(firstFieldInfo, size) ) {
		console.log("Statek o rozmiarze: " + size + " tworzy pola w lewo");
		removeFieldsFromAllowedToLeft(firstFieldInfo, size);
		addShipLocationsToLeft(firstFieldInfo, size); 
	} 
	
	console.log("Statki: ");
	console.log(SHIPS);
	
}

function randomFirstField() {
	
	let isAllowedField = false;
	
	let x = "";
	let y = "";
	let xy = "";
	
	do {
		
		x = Math.floor((Math.random() * 10) + 1);
		y = Math.floor((Math.random() * 10) + 1);
		xy = x + "" + y;
		
		if (ALLOWED_FIELDS.indexOf(xy) != -1) {
			isAllowedField = true;
		}
		
	} while (!isAllowedField);
	
	console.log("Wylosowane pole: " + xy);
	
	return {
		"x": x,
		"y": y,
		"xy": xy
	};
	
}

function checkFieldsUp(firstFieldInfo, size) {
	
	console.log("sprawdzanie dla pola " + firstFieldInfo.xy);
	
	let upDirectionWright = true;
	
	let x = firstFieldInfo.x;
	let y = firstFieldInfo.y;
	
	x--;
	size--;
	
	for (i = x; i > 0 && size > 0; i--, size--) {
		let loc = i + "" + y;
		console.log("Badana pozycja: " + loc);
		
		if (TAKEN_FIELDS.indexOf(loc) != -1) {
		console.log("Pole jest już zajęte przez inny statek");
			break;
		}
		else if (FORBIDDEN_FIELDS.indexOf(loc) != -1) {
			console.log("Pole jest już zajęte w obrębie restrykcyjnym.");
			break;
		}
 	} 
	
	if (size > 0) {
		console.log("Rozmiar nie został w pełni zmniejszony.");
		upDirectionWright = false;
	}
	
	return upDirectionWright;
	
} 

function checkFieldsRight(firstFieldInfo, size) {
	
	console.log("sprawdzanie dla pola pozycji w prawo " + firstFieldInfo.xy);
	
	let rightDirectionWright = true;
	
	let x = firstFieldInfo.x;
	let y = firstFieldInfo.y;
	
	y++;
	size--;
	
	for (i = y; i <= 10 && size > 0; i++, size--) {
		let loc = x + "" + i;
		console.log("Badana pozycja: " + loc);
		
		if (TAKEN_FIELDS.indexOf(loc) != -1) {
		console.log("Pole jest już zajęte przez inny statek");
			break;
		}
		else if (FORBIDDEN_FIELDS.indexOf(loc) != -1) {
			console.log("Pole jest już zajęte w obrębie restrykcyjnym.");
			break;
		}
 	} 
	
	if (size > 0) {
		console.log("Rozmiar nie został w pełni zmniejszony.");
		rightDirectionWright = false;
	}
	
	return rightDirectionWright;
	
}
  
function checkFieldsDown(firstFieldInfo, size) {
	
	console.log("sprawdzanie dla pola pozycji w dół " + firstFieldInfo.xy);
	
	let downDirectionWright = true;
	
	let x = firstFieldInfo.x;
	let y = firstFieldInfo.y;
	
	x++;
	size--;
	
	for (i = x; i <= 10 && size > 0; i++, size--) {
		let loc = i + "" + y;
		console.log("Badana pozycja: " + loc);
		
		if (TAKEN_FIELDS.indexOf(loc) != -1) {
			console.log("Pole jest już zajęte przez inny statek");
			break;
		}
		else if (FORBIDDEN_FIELDS.indexOf(loc) != -1) {
			console.log("Pole jest już zajęte w obrębie restrykcyjnym.");
			break;
		}
 	} 
	
	if (size > 0) {
		console.log("Rozmiar nie został w pełni zmniejszony.");
		downDirectionWright = false;
	}
	
	return downDirectionWright;
	
}

function checkFieldsLeft(firstFieldInfo, size) {
	
	console.log("sprawdzanie dla pola pozycji w dół " + firstFieldInfo.xy);
	
	let leftDirectionWright = true;
	
	let x = firstFieldInfo.x;
	let y = firstFieldInfo.y;
	
	y--;
	size--;
	
	for (i = y; i > 0 && size > 0; i--, size--) {
		let loc = x + "" + i;
		console.log("Badana pozycja: " + loc);
		
		if (TAKEN_FIELDS.indexOf(loc) != -1) {
			console.log("Pole jest już zajęte przez inny statek");
			break;
		}
		else if (FORBIDDEN_FIELDS.indexOf(loc) != -1) {
			console.log("Pole jest już zajęte w obrębie restrykcyjnym.");
			break;
		}
 	} 
	
	if (size > 0) {
		console.log("Rozmiar nie został w pełni zmniejszony.");
		leftDirectionWright = false;
	}
	
	return leftDirectionWright;
	
}

function removeFieldsFromAllowedToDown(firstFieldInfo, size) {
	firstFieldInfo.x = firstFieldInfo.x + size - 1;
	removeFieldsFromAllowedToUp(firstFieldInfo, size);
}

function removeFieldsFromAllowedToLeft(firstFieldInfo, size) {
	firstFieldInfo.y = firstFieldInfo.y - size + 1;
	removeFieldsFromAllowedToRight(firstFieldInfo, size);
}

function removeFieldsFromAllowedToUp(firstFieldInfo, size) {
	
	console.log("Wyjmuję pola z allowed idąc do góry.");
	
	let initX =  firstFieldInfo.x + 1;
	let initY = firstFieldInfo.y - 1;
	
	console.log("Sprawdzenie 1 sekcji.");
	
	for (i = 0, y = initY; i < 3; i++, y++) {
		if ((initX > 0 && initX < 11) && (y > 0 && y < 11)) {
			let loc = initX + "" + y;
			addFieldToForbiden(loc); 
			removeFromAllowedFields(loc);
		}
	}
	
	console.log("Sprawdzenie 2 sekcji.");
	
	for (i = 0, x = initX - 1; i < size; i++, x--) {
		for (j = 0, y = initY; j < 2; j++, y += 2) {
			if ((x > 0 && x < 11) && (y > 0 && y < 11)) {
				let loc = x + "" + y;
				addFieldToForbiden(loc);
				removeFromAllowedFields(loc);
			}
		}
	}
	
	console.log("Sprawdzenie 3 sekcji.");
	
	for (i = 0, y = initY, x = firstFieldInfo.x - size; i < 3; i++, y++) {
		if ((x > 0 && x < 11) && (y > 0 && y < 11)) {
			let loc = x + "" + y;
			addFieldToForbiden(loc);
			removeFromAllowedFields(loc);
		}
	}
	
	console.log("Doddaie pól dla taken idąc do góry");

	
	for (i = 0, x = firstFieldInfo.x; i < size; i++, x--) {
		let loc = x + "" + firstFieldInfo.y; 
		console.log("Dodaję pole do odjętych: " + loc);
		TAKEN_FIELDS.push(loc);
		removeFromAllowedFields(loc)
	}
}

function removeFieldsFromAllowedToRight(firstFieldInfo, size) {

	console.log("Wyjmuję pola z allowed idąc w prawo.");
	
	let initX =  firstFieldInfo.x - 1;
	let initY = firstFieldInfo.y - 1;
	
	console.log("Sprawdzenie 1 sekcji.");
	
	for (i = 0, x = initX; i < 3; i++, x++) {
		if ((x > 0 && x < 11) && (initY > 0 && initY < 11)) {
			let loc = x + "" + initY;
			addFieldToForbiden(loc);
			removeFromAllowedFields(loc)
		}
	}
	
	console.log("Sprawdzenie 2 sekcji.");
	
	for (i = 0, y = initY + 1; i < size; i++, y++) {
		for (j = 0, x = initX; j < 2; j++, x += 2) {
			if ((x > 0 && x < 11) && (y > 0 && y < 11)) {
				let loc = x + "" + y;
				addFieldToForbiden(loc);
				removeFromAllowedFields(loc);
			}
		}
	}
	
	console.log("Sprawdzenie 3 sekcji.");
	
	for (i = 0, y = firstFieldInfo.y + size, x = firstFieldInfo.x - 1; i < 3; i++, x++) {
		if ((x > 0 && x < 11) && (y > 0 && y < 11)) {
			let loc = x + "" + y;
			addFieldToForbiden(loc);
			removeFromAllowedFields(loc);
		}
	}
	
	console.log("Doddaie pól dla taken idąc do góry");

	
	for (i = 0, y = firstFieldInfo.y; i < size; i++, y++) {
		let loc = firstFieldInfo.x + "" + y; 
		console.log("Dodaję pole do odjętych: " + loc);
		TAKEN_FIELDS.push(loc);
		removeFromAllowedFields(loc);
	}
	
}


function addShipLocationsToLeft(fieldsLocationInfo, size) {
	fieldsLocationInfo.y = fieldsLocationInfo.y - size + 1
	addShipLocationsToRight(fieldsLocationInfo, size);
}

function addShipLocationsToDown(fieldsLocationInfo, size) {
	fieldsLocationInfo.x = fieldsLocationInfo.x + size - 1;  
	addShipLocationsToUp(fieldsLocationInfo, size);
}

function addShipLocationsToUp(fieldsLocationInfo, size) {
	console.log("Dodaje statek dla pól wyszyukiwanych w górę.");
	
	ship = [];

	for (i = 0, x = fieldsLocationInfo.x; i < size; i++, x--) {
		let loc = x + "" + fieldsLocationInfo.y;
		console.log("Dodaję statek o pozycji: " + loc);
		ship.push(loc);
	}
	
	SHIPS.push(ship);
}

function addShipLocationsToRight(fieldsLocationInfo, size) {
	console.log("Dodaje statek dla pól wyszyukiwanych w prawo.");
	
	ship = [];

	for (i = 0, y = fieldsLocationInfo.y; i < size; i++, y++) {
		let loc = fieldsLocationInfo.x + "" + y;
		console.log("Dodaję statek o pozycji: " + loc);
		ship.push(loc);
	}
	
	SHIPS.push(ship);
}


function addFieldToForbiden(loc) {
	if (FORBIDDEN_FIELDS.indexOf(loc) == -1) {
		console.log("Dodaję pole do odjętych: " + loc);
		FORBIDDEN_FIELDS.push(loc);
	}
}

function removeFromAllowedFields(loc) {
	let index = ALLOWED_FIELDS.indexOf(loc);
	ALLOWED_FIELDS.splice(index, 1);
}

function showShipsOnPanel() {
	
}
