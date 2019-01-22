package de.fh_dortmund.inf.cw.surstwalat.common;

public interface MessageType {
	//DISPATCHER
	public static final int ASSIGN_ACTIVE_PLAYER = 0;
	public static final int ASSIGN_PLAYER = 1;
	public static final int ELIMINATE_PLAYER = 2;
	public static final int END_ROUND = 3;
	public static final int PLAYER_ROLL = 4;
	public static final int SPAWN_ITEM = 5;
	public static final int START_ROUND = 6;
	public static final int PLAYER_ELIMINATED = 8;
	public static final int PLAYER_WINS = 9;
	//LOBBY MANAGEMENT
	public static final int GAME_CREATED = 10;
	public static final int GAME_STARTED = 11;
	//USER MANAGEMENT
	public static final int USER_CONNECTED = 20;
	public static final int USER_DISCONNECTED = 21;
	//USER SESSION
	public static final int USER_TIMEOUT = 22;
	public static final int USER_CREATEGAME = 23;
	public static final int USER_JOINGAME = 24;
	public static final int USER_LOGIN = 25;
	public static final int USER_REGISTER = 26;
	public static final int USER_LOGOUT = 27;
	public static final int USER_DISCONNECT = 28;
	public static final int USER_CHANGE_PASSWORD = 29;
	public static final int USER_UPDATE_EMAIL = 34;
	public static final int USER_DELETE = 35;
	public static final int ADD_ITEM_TO_PLAYER = 36;
	public static final int PLAYER_ACTION = 37;
	//GLOBAL EVENT MANAGEMENT
	public static final int UPDATE_ZONE = 30;
	public static final int TRIGGER_AIRDROP = 31;
	public static final int TRIGGER_STARTING_ITEMS = 32;
	public static final int TRIGGER_DAMAGE = 33;
	//Item MANAGEMENT 100 - 110
	public static final int ITEM_SPAWN = 100;
	public static final int ITEM_ADD_TO_USER = 101;
	public static final int ITEM_INVENTAR = 102;
	public static final int ITEM = 103;
	//LocationManagement
    public static final int NO_COLLISION = 111;
    public static final int PLAYER_ON_FIELD = 112;
    public static final int COLLISION_WITH_PLAYER = 113;
    public static final int COLLISION_WITH_ITEM = 114;
    public static final int CHARACTERS_IN_TOXIC = 115;
    public static final int COLLISION_WITH_OWN_CHARACTER = 116;
    public static final int TOXIC_CHANGE = 117;
    
    //HealthManagement
    public static final int PLAYER_DEATH = 121;
    public static final int TOKEN_CREATED = 122;
    public static final int SET_TOKEN_HEALTH = 123;
    public static final int TOKEN_DEATH = 124;
    
    //Richtige UserSession
    public static final int MOVE_TOKEN=130;
}
