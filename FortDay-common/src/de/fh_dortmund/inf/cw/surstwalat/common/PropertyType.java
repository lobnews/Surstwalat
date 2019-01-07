package de.fh_dortmund.inf.cw.surstwalat.common;

public enum PropertyType {
	DisplayMessage("DISPLAY_MESSAGE"),
	GameId("GAME_ID"),
	MessageType("MESSAGE_TYPE"),
	PlayerNo("PLAYER_NO"),
	UserId("USER_ID")
	;
	
	private final String name;
	PropertyType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
