var SERVER_ADDRESS = "http://localhost:8081/";

var TOKEN_ACCESS_NAME = "authentication-token";
var USERNAME = "username";
var USER_ID = "user-id";

var MAIN_CHANEL_NAME = "/main/first10";
var USER_CHANEL_PREFIX = "/main/user/";
var USER_CHANEL_NAME = "user-chanel-name";
var MAIN_ENDPOINT = "/socket";
var SHIP_CONTAINER = "ship-container";

var userChanel;


var USER_CHANEL_WR_CODES = {
	MOVE_TO_GAME: 0
};

var USER_CHANEL_GR_CODES = {
	ENEMY_SHOT_SUCCESS: 0,
	YOUR_SHOT_SUCCESS: 1,
	
	ENEMY_SHOT_FAILURE: 2,
	YOUR_SHOT_FAILURE: 3,
	
	YOUR_SHIP_DESTROYED: 4,
	ENEMY_SHIP_DESTROYED: 5,
	
	LOSS_OF_OWN_TIME: 6,
	LOSS_OF_ENEMY_TIME: 7,
	
	VICTORY_RESULT: 8,
	LOSS_MATCH: 9,
	
	FIRST_WAIT: 10,
	FIRST_SHOOT: 11
};