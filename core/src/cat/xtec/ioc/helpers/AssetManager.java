package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    // Sprite Sheet
    public static Texture sheet;

    // Nau i fons
    public static TextureRegion warrior, background;

    // Enemy
    public static TextureRegion[] enemy, warriorDown, warriorUp;
    public static Animation enemyAnim, warriorDownAnim, warriorUpAnim;

    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Sons
    public static Sound explosionSound;
    public static Music music;

    // Font
    public static BitmapFont font;

    public static void load() {

        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("warrior_sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Sprites del guerrer
        warrior = new TextureRegion(sheet, 0, 1, 18, 28);
        warrior.flip(false, true);

        warriorUp = new TextureRegion[2];
        warriorUp[0] = new TextureRegion(sheet, 32, 0, 18, 28);
        warriorUp[1] = new TextureRegion(sheet, 64, 0, 18, 28);
        warriorUp[0].flip(false, true);
        warriorUp[1].flip(false, true);
        warriorUpAnim = new Animation(0.05f, warriorUp);

        warriorDown = new TextureRegion[2];
        warriorDown[0] = new TextureRegion(sheet, 32, 0, 18, 28);
        warriorDown[1] = new TextureRegion(sheet, 64, 0, 18, 28);
        warriorDown[0].flip(false, true);
        warriorDown[1].flip(false, true);
        warriorDownAnim = new Animation(0.05f, warriorDown);


        // Carreguem els 4 estats de l'enemic
        enemy = new TextureRegion[4];
        enemy[0] = new TextureRegion(sheet, 0, 32, 42, 35);
        enemy[1] = new TextureRegion(sheet, 48, 33, 42, 33);
        enemy[2] = new TextureRegion(sheet, 96, 32, 42, 35);
        enemy[3] = new TextureRegion(sheet, 144, 32, 42, 34);

        // Creem l'animació de l'enemy i fem que s'executi contínuament en sentit anti-horari
        enemyAnim = new Animation(0.05f, enemy);
        enemyAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // Creem els 7 estats de l'explosió
        explosion = new TextureRegion[7];
        explosion[0] = new TextureRegion(sheet, 0,  106, 36, 36);
        explosion[1] = new TextureRegion(sheet, 80,  106, 36, 36);
        explosion[2] = new TextureRegion(sheet, 157,  99, 46, 48);
        explosion[3] = new TextureRegion(sheet, 229,  96, 60, 56);
        explosion[4] = new TextureRegion(sheet, 305,  94, 63, 60);
        explosion[5] = new TextureRegion(sheet, 386,  92, 64, 63);
        explosion[6] = new TextureRegion(sheet, 465,  91, 67, 68);

        // Finalment creem l'animació
        explosionAnim = new Animation(0.04f, explosion);

        // Fons de pantalla
        background = new TextureRegion(sheet, 0, 174, 384, 240);
        background.flip(false, true);

        /******************************* Sounds *************************************/

        // Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));
        music.setVolume(0.2f);
        music.setLooping(true);


        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);
    }



    public static void dispose() {
        // Alliberem els recursos gràfics i de audio
        sheet.dispose();
        explosionSound.dispose();
        music.dispose();
    }
}
