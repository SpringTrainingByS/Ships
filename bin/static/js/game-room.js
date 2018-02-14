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
		await connectWithChanels();
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
	
	await stompClient.connect(headers, function (frame) {
		stompClient.subscribe(USER_CHANEL_PREFIX + userChanelName, function (message) {
			doProperActionForUserChanel(message);
		});
		sendReadyToGameSignal();
	}, function () { console.log("nie udało się połączyć z kanałami"); });
	
}

async function sendReadyToGameSignal() {
	
	let url = SERVER_ADDRESS + "game/readiness";
	
	await $.ajax({
		url:url,
		type:"GET",
		data: { user_id: localStorage.getItem(USER_ID)},
		headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
		contentType:"application/json; charset=utf-8"
	})
	.then(function(response) {
		console.log("Wysłano informację o gotowości na rozgrywkę");
	}
	, function(e) {
		console.log("Nie udało się wysłać informacji o gotowości na rozgrywkę");
	});
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
					console.log("Strzelam");
					shotEnemyShip($(this).attr('id'));
				});
			}
			
			row.appendChild(span);
		}
		$("#enemy-ship-section-table").append(row);
	}
	
}

async function shotEnemyShip(fieldId) {
	console.log("Chcę strzelać.");
	if (isUserTurn == 1) {
		console.log("Pozwolona na strzelanie.");
		let pos = fieldId.replace("enemy-pos", "");
		console.log("Wysłana pozycja: " + pos);
		
		let url = SERVER_ADDRESS + "game/shot";
		
		await $.ajax({
			url:url,
			type:"GET",
			data: { user_id: localStorage.getItem(USER_ID), location: pos},
			headers: {"Authorization" : localStorage.getItem(TOKEN_ACCESS_NAME)},
			contentType:"application/json; charset=utf-8"
		})
		.then(function(response) {
			console.log("Wysyłam strzał na serwer.");
		}
		, function(e) {
			console.log("Nie udało się wysłać statku na serwer.");
		});
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
	console.log("Odebrałem wiadomość");
	console.log(message);
	let result = JSON.parse(message.body);
	let content = JSON.parse(result.content);
	console.log("Content" + content);
	let operationCode = parseInt(content.operationCode);
	console.log("Kod operacji: " + operationCode);

	
	switch (operationCode) {
	
		case USER_CHANEL_GR_CODES.FIRST_WAIT:
			prepareToWait();
			break;
		
		case USER_CHANEL_GR_CODES.FIRST_SHOOT:
			prepareToFirstShot();
			break;
		
		case USER_CHANEL_GR_CODES.ENEMY_SHOT_SUCCESS: 
			prepareAfterEnemyShotSucces(content);
			break;
		
		case USER_CHANEL_GR_CODES.YOUR_SHOT_SUCCESS: 
			prepareAfterYouShoutSuccess(content);
			break;
		
		case USER_CHANEL_GR_CODES.ENEMY_SHOT_FAILURE: 
			prepareAfterEnemyShotFailure(content);
			break;
			
		case USER_CHANEL_GR_CODES.YOUR_SHOT_FAILURE:
			prepareAfterYourShotFailure(content);
			break;
		
		case USER_CHANEL_GR_CODES.YOUR_SHIP_DESTROYED: 
			prepareAfterYourShipDestroyed(content);
			break;
			
		case USER_CHANEL_GR_CODES.ENEMY_SHIP_DESTROYED:
			prepareAfterEnemyShipDestroyed(content);
			break;
			
		case USER_CHANEL_GR_CODES.LOSS_OF_OWN_TIME: 
			prepareAfterLossOfOwnTime(content);
			break;
			
		case USER_CHANEL_GR_CODES.LOSS_OF_ENEMY_TIME: 
			prepareAfterLossOfEnemyTime(content);
			break;
			
		case USER_CHANEL_GR_CODES.VICTORY_RESULT: 
			prepareAfterVictoryResult(content);
			break;
			
		case USER_CHANEL_GR_CODES.LOSS_MATCH: 
			prepareAfterLossMatch(content);
			break;
		
		default: 
			console.log("zły kod operacji");
			break;
	} 
	
	console.log("isUserTurn: " + isUserTurn);
}

// przygotowania

async function prepareToFirstShot() {
	console.log("Przygotowuję użytkownika do pierwszego strzału");
	$("#fast-message").text("Oddajesz strzał jako pierwszy.");
	isUserTurn = 1;
}

async function prepareToWait() {
	console.log("User przygotowywany do poczekania");
	$("#fast-message").text("Czekasz.");
	isUserTurn = 0;
}

// po przygotowaniach

async function prepareAfterEnemyShotSucces(content) {
	$("#game-messages").append("<p>" + content.message + "</p>");
	changeCellColor("yellow", "own-pos", content.localization);
	$("#fast-message").text("Twój statek został trafiony. Czekasz.");
} 

async function prepareAfterYouShoutSuccess(content) {
	$("#game-messages").append("<p>" + content.message + "</p>");
	changeCellColor("yellow", "enemy-pos", content.localization);
	$("#fast-message").text("Trafiłeś statek wroga. Twoja kolej.");
	$("#enemy-pos" + content.localization).unbind( "click" );
}

async function prepareAfterEnemyShotFailure(content) {
	console.log("Wykonuję prepareAfterEnemyShotFailure ");
	isUserTurn = 1;
	$("#game-messages").append("<p>" + content.message + "</p>");
	changeCellColor("black", "own-pos", content.localization);
	$("#fast-message").text("Twoja kolej.");
}

async function prepareAfterYourShotFailure(content) {
	console.log("Wykonuję prepareAfterYourShotFailure");
	isUserTurn = 0;
	$("#game-messages").append("<p>" + content.message + "</p>");
	changeCellColor("black", "enemy-pos", content.localization);
	$("#fast-message").text("Pudło. Czekasz.");
	$("#enemy-pos" + content.localization).unbind( "click" );
}

async function prepareAfterYourShipDestroyed(content) {
	$("#game-messages").append("<p>" + content.message + "</p>");
	changeCellColor("red", "own-pos", content.localization);
	$("#fast-message").text("Twój statek został zniszczony. Czekasz.");
}

async function prepareAfterEnemyShipDestroyed(content) {
	$("#game-messages").append("<p>" + content.message + "</p>");
	changeCellColor("red", "enemy-pos", content.localization);
	$("#fast-message").text("Zniszczyłeś statek wroga. Twoja kolej.");
}

async function prepareAfterLossOfOwnTime(content) {
	isUserTurn = 0;
	$("#game-messages").append("<p>" + content.message + "</p>");
	$("#fast-message").text("Straciłeś czas. Czekasz.");
}

async function prepareAfterLossOfEnemyTime(content) {
	isUserTurn = 1;
	$("#game-messages").append("<p>" + content.message + "</p>");
	$("#fast-message").text("Twój wróg stracił czas. Twoja kolej.");
}

async function prepareAfterVictoryResult(content) {
	isUserTurn = 0;
	changeCellColor("red", "enemy-pos", content.localization);
	$("#game-messages").append("<p>" + content.message + "</p>");
	$("#fast-message").text("WYGRAŁEŚ!!!!!!!!!!!!!!!!!");
}

async function prepareAfterLossMatch(content) {
	isUserTurn = 0;
	changeCellColor("red", "own-pos", content.localization);
	$("#game-messages").append("<p>" + content.message + "</p>");
	$("#fast-message").text("PRZEGRAŁEŚ!!!!!!!!!!!!!!!!");
}

function changeCellColor(color, prefixId, posId) {
	$("#" + prefixId + posId).get(0).style.backgroundColor = color;
}

function removeClickListenerFromField(loc) {
	$("#enemy-pos" + localization).unbind( "click" );
}