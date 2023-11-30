package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // Enemyes
    int numEnemys;
    public ArrayList<Enemy> enemies;
    public ArrayList<Bullet> bullets;

    // Objecte Random
    Random r;


    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 enemies
        numEnemys = 3;

        // Creem l'ArrayList
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY) * 34;

        // Afegim el primer Enemy a l'Array i al grup
        Enemy enemy = new Enemy(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ENEMY_SPEED);
        enemies.add(enemy);
        addActor(enemy);

        // Des del segon fins l'últim enemye
        for (int i = 1; i < numEnemys; i++) {
            // Creem la mida al·leatòria
            newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY) * 34;
            // Afegim l'enemy.
            enemy = new Enemy(enemies.get(enemies.size() - 1).getTailX() + Settings.ENEMY_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ENEMY_SPEED);
            // Afegim l'enemye a l'ArrayList
            enemies.add(enemy);
            // Afegim l'enemye al grup d'actors
            addActor(enemy);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < enemies.size(); i++) {

            Enemy enemy = enemies.get(i);
            if (enemy.isLeftOfScreen()) {
                if (i == 0) {
                    enemy.reset(enemies.get(enemies.size() - 1).getTailX() + Settings.ENEMY_GAP);
                } else {
                    enemy.reset(enemies.get(i - 1).getTailX() + Settings.ENEMY_GAP);
                }
            }
        }

        for(int i = bullets.size()-1; i >= 0; i--){
            Bullet bullet = bullets.get(i);
            for (int j = enemies.size()-1; j >= 0; j--){
                Enemy enemy = enemies.get(j);
                if(enemy.collides(bullet)){
                    Gdx.app.log("Update Running", "Enemy hit!");

                    // Remove Enemy
                    float newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY) * 34;
                    Enemy newEnemy = new Enemy(enemies.get(enemies.size() - 1).getTailX() + Settings.ENEMY_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ENEMY_SPEED);
                    enemies.add(newEnemy);
                    addActor(newEnemy);
                    bullet.remove();

                    // Create enemy exploding animation
                    enemy.makeItExplode();

                    // Remove enemy
                    enemies.remove(j);
                    bullets.remove(i);
                }
            }
        }
    }

    public boolean collides(Warrior nau) {

        // Comprovem les col·lisions entre cada enemy i la nau
        for (Enemy enemy : enemies) {
            if (enemy.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        // Posem el primer enemy fora de la pantalla per la dreta
        enemies.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'enemies.
        for (int i = 1; i < enemies.size(); i++) {
            enemies.get(i).reset(enemies.get(i - 1).getTailX() + Settings.ENEMY_GAP);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
        addActor(bullet);
    }
}