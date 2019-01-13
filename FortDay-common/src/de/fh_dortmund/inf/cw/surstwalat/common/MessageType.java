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
	
}
