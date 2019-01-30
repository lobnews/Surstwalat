package de.fh_dortmund.inf.cw.surstwalat.common;

/**
 * 
 * @author Johannes Heiderich, Rebekka Michel
 *
 * @author Johannes Heiderich
 * @author Rebekka Michel
 * @author Stephan Klimek
 */
public interface PropertyType {

    // User/account types
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PWD = "USER_PWD";
    public static final String USER_PWD_OLD = "USER_PWD_OLD";

    // Lobby types
    public static final String USER1_ID = "USER1_ID";
    public static final String USER2_ID = "USER2_ID";
    public static final String USER3_ID = "USER3_ID";
    public static final String USER4_ID = "USER4_ID";
    public static final String LOBBY_NAME = "LOBBY_NAME";

    // Game types
    public static final String DISPLAY_MESSAGE = "DISPLAY_MESSAGE";
    public static final String GAME_ID = "GAME_ID";
    public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
    public static final String PLAYER_ID = "PLAYER_ID";
    public static final String PLAYER_NO = "PLAYER_NO";
    public static final String ACTION_TYPE = "ACTION_TYPE";
    public static final String GAME_FIELDSIZE = "GAME_FIELDSIZE";
    public static final String CURRENT_ZONE_BEGIN = "CURRENT_ZONE_BEGIN";
    public static final String CURRENT_ZONE_SIZE = "CURRENT_ZONE_SIZE";
    public static final String NEXT_ZONE_BEGIN = "NEXT_ZONE_BEGIN";
    public static final String NEXT_ZONE_SIZE = "NEXT_ZONE_SIZE";
    public static final String DAMAGE = "DAMAGE";
    public static final String HEALTH = "HEALTH";
    public static final String FIELD_ID = "FIELD_ID";
    public static final String ENEMY_CHARACTER_ID = "ENEMY_CHARACTER_ID";
    public static final String ENEMY_PLAYER_ID = "ENEMY_PLAYER_ID";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String TOKEN_ID = "TOKEN_ID";
    public static final String ITEM_POS = "ITEM_POS";
    public static final String ROUND_NO = "ROUND_NO";
    
    // Timeout
    public static final String TIMEOUT_SECONDS_LEFT = "TIMEOUT_SECONDS_LEFT";
}
