console.log(localStorage.getItem(TOKEN_ACCESS_NAME));
console.log(localStorage.getItem(USERNAME));

var shipDefinition;

var userChanelName = "";

var stompClient;

var isUserTurn = 0;

preparePageAndLogic(); 

async function  preparePageAndLogic() {
	if (localStorage.getItem(TOKEN_ACCESS_NAME) == null) {
		window.location.replace(SERVER_ADDRESS);
	}
	else {
		showPage();
		userChanelName = localStorage.getItem(USER_CHANEL_NAME);
		connectWithChanels();
	}
}

async function showPage() {
	await loadShipDefinition();	
	await loadOwnShipSection();
	putShipsToOwnShipSection();
	loadEnemyShipSection();
}


async function loadShipDefinition() {
	if (localStorage.getItem(SHIP_CONTAINER) != null) {
		shipDefinition = JSON.parse(localStorage.getItem(SHIP_CONTAINER));
	}
	else {
		console.log("Nie ma statków");
	}
}


async function connectWithChanels() {
	
	let socket = new SockJS(MAIN_ENDPOINT);
	stompClient = Stomp.over(socket);
	
	var headers = {};
	headers["Authorization"] = localStorage.getItem(TOKEN_ACCESS_NAME);
	
	console.log("$" + USER_CHANEL_PREFIX + userChanelName + "$");
	
	stompClient.connect(headers, function (frame) {
		stompClient.subscribe(USER_CHANEL_PREFIX + userChanelName, function (message) {
			doProperActionForUserChanel(message);
		});
	}, function () { console.log("nie udało się połączyć z kanałami"); });
	
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

async function loadEnemyShipSection() {
	
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
	$("#enemy-ship-section-table").append(row);
	
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
				span.setAttribute('id', 'enemy-pos' + i + j);
				span.setAttribute('title', "pos: " + i + j);
				span.style.outline = "1px solid black";
				$(span).click(function() {
					shotEnemyShip($(this).attr('id'));
				});
			}
			
			row.appendChild(span);
		}
		$("#enemy-ship-section-table").append(row);
	}
	
}


async function shotEnemyShip(fieldId) {
	if (isUserTurn == 1) {
		let pos = fieldId.replace("enemy-pos", "");
		console.log("Wysłana pozycja: " + pos);
		
	}
}

var points = [];

async function putShipsToOwnShipSection() {
	
	prepareOwnShipsLocalization();
	addOwnShipsLocalizationToView();
	
	console.log(points);
}

function prepareOwnShipsLocalization() {
	for (x in shipDefinition) {
		if (shipDefinition[x] instanceof Array || shipDefinition[x] instanceof Object) {
			findFieldLocalization(shipDefinition[x], x) 
		}
		else {
			if (x.includes("field")) {
				points.push(shipDefinition[x]);
			}
		}
	}
}

function addOwnShipsLocalizationToView() {
	for (x of points) {
		let fieldId = "own-pos" + x;
		console.log(fieldId);
		$("#" + "own-pos" + x).get(0).style.backgroundColor = "green";
	}
}

function findFieldLocalization(field, fieldName) {
	
	for (x in field) {
		if (field[x] instanceof Array || field[x] instanceof Object) {
			findFieldLocalization(field[x], x);
		}
		else {
			if (x.includes("field")) {
				console.log(field[x]);
				points.push(field[x]);
			}
		}
	}
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


function moveUserToGameRoom() {
	stompClient.disconnect();
	$(" #moving-to-game-room-message ").show();
	setTimeout(function() {
		window.location.replace(SERVER_ADDRESS + "game-room");
	}, 2000); 
	
}

