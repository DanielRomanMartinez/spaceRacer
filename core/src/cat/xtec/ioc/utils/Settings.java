package cat.xtec.ioc.utils;

public class Settings {

    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 384;
    public static final int GAME_HEIGHT = 240;

    // Propietats de la nau
    public static final float WARRIOR_VELOCITY = 75;
    public static final int WARRIOR_WIDTH = 18;
    public static final int WARRIOR_HEIGHT = 28;
    public static final float WARRIOR_STARTX = 20;
    public static final float WARRIOR_STARTY = GAME_HEIGHT/2 - WARRIOR_HEIGHT/2;

    // Rang de valors per canviar la mida de l'enemye.
    public static final float MAX_ENEMY = 1.5f;
    public static final float MIN_ENEMY = 0.5f;

    // Configuració Scrollable
    public static final int ENEMY_SPEED = -150;
    public static final int ENEMY_GAP = 75;
    public static final int BG_SPEED = -100;

    // TODO Exercici 2: Propietats per la moneda
    public static final int SCORE_INCREASE = 100; // s'incrementa en 100 cada cop que toca una moneda
    public static final int SCORE_SPEED = -175;

    // Propietats per al pause
    public static final float NORMAL_VOLUME = 2f;
    public static final float LOW_VOLUME = 0.5f;


}
