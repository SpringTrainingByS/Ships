console.log(localStorage.getItem(TOKEN_ACCESS_NAME));
console.log(localStorage.getItem(USERNAME));

var shipDefinition;

var userChanelName = "";

var stompClient;

preparePageAndLogic(); 

async function  preparePageAndLogic() {
	if (localStorage.getItem(TOKEN_ACCESS_NAME) == null) {
		window.location.replace(SERVER_ADDRESS);
	}
	else {
		showPage();
		userChanelName = localStorage.getItem(USER_CHANEL_NAME);
		connectWithChanels();
		await loadShipDefinition();	
		loadOwnShipSection();
		loadEnemyShipSection();
	}
}

async function showPage() {
	
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
		stompClient.subscribe(MAIN_CHANEL_NAME, function (message) {
			changeFirst10View(message);
		});
		stompClient.subscribe(USER_CHANEL_PREFIX + userChanelName, function (message) {
			doProperActionForUserChanel(message);
		});
	}, function () { console.log("nie udało się połączyć z kanałami"); });
	
}

function loadOwnShipSection() {
	
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
				span.innerHTML = "P";
				span.setAttribute('id', 'own-pos' + i + j);
				span.setAttribute('title', "pos: " + i + j);
				span.style.outline = "1px solid black";
			}
			
			row.appendChild(span);
		}
		$("#own-ship-section-table").append(row);
	}
}

function loadEnemyShipSection() {
	
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
				span.innerHTML = "P";
				span.setAttribute('id', 'own-pos' + i + j);
				span.setAttribute('title', "pos: " + i + j);
				span.style.outline = "1px solid black";
			}
			
			row.appendChild(span);
		}
		$("#enemy-ship-section-table").append(row);
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

