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
	RESULT_OF_OWN_SHOT: 0,
	RESULT_OF_ENEMY_SHOT: 1,
	LOSS_OF_OWN_TIME: 2,
	LOSS_OF_ENEMY_TIME: 3,
	VICTORY_RESULT: 4,
	LOSS_MATCH: 5,
}