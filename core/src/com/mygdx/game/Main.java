package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Main extends ApplicationAdapter {
    private static final float HELICOPTER_SPEED = 200.0f;
    private static final float HELICOPTER_MAX_DISTANCE_FROM_CENTER = 200.0f;
    private static final float WINDOW_WIDTH = 1280;
    private static final float WINDOW_HEIGHT = 720;

    private Batch batch;
    private OrthographicCamera camera;
    private Helicoptero helicoptero;
    private Cenario cenario;
    

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(WINDOW_WIDTH, WINDOW_HEIGHT);
        cenario = new Cenario();
        helicoptero = new Helicoptero(cenario.getWidth() / 2, cenario.getHeight() / 2);

        camera.position.set(cenario.getWidth() / 2, cenario.getHeight() / 2, 0);
        camera.update();

        Gdx.graphics.setWindowedMode((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float helicopterMovementX = 0;
        float helicopterMovementY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            helicopterMovementX = -HELICOPTER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            helicopterMovementX = HELICOPTER_SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            helicopterMovementY = HELICOPTER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            helicopterMovementY = -HELICOPTER_SPEED;
        }

        float movementX = helicopterMovementX * deltaTime;
        float movementY = helicopterMovementY * deltaTime;

        float newHelicopterX = helicoptero.getPositionX() + movementX;
        float newHelicopterY = helicoptero.getPositionY() + movementY;

        // Verificar os limites horizontais
        float minHelicopterX = HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        float maxHelicopterX = cenario.getWidth() - HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        if (newHelicopterX < minHelicopterX) {
            newHelicopterX = minHelicopterX;
        } else if (newHelicopterX > maxHelicopterX) {
            newHelicopterX = maxHelicopterX;
        }

        // Verificar os limites verticais
        float minHelicopterY = HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        float maxHelicopterY = cenario.getHeight() - HELICOPTER_MAX_DISTANCE_FROM_CENTER;
        if (newHelicopterY < minHelicopterY) {
            newHelicopterY = minHelicopterY;
        } else if (newHelicopterY > maxHelicopterY) {
            newHelicopterY = maxHelicopterY;
        }

        // Calcular o movimento atualizado
        float updatedMovementX = newHelicopterX - helicoptero.getPositionX();
        float updatedMovementY = newHelicopterY - helicoptero.getPositionY();

        helicoptero.update(updatedMovementX, updatedMovementY, deltaTime);
        for (Tiro tiro : helicoptero.getTiros()) {
            tiro.update(deltaTime);
        }

        float helicopterX = helicoptero.getPositionX();
        float helicopterY = helicoptero.getPositionY();

        float cameraX = camera.position.x;
        float cameraY = camera.position.y;

        float minCameraX = WINDOW_WIDTH / 2;
        float maxCameraX = cenario.getWidth() - WINDOW_WIDTH / 2;
        float minCameraY = WINDOW_HEIGHT / 2;
        float maxCameraY = cenario.getHeight() - WINDOW_HEIGHT / 2;

        if (helicopterX < minCameraX) {
            helicopterX = minCameraX;
        } else if (helicopterX > maxCameraX) {
            helicopterX = maxCameraX;
        }

        if (helicopterY < minCameraY) {
            helicopterY = minCameraY;
        } else if (helicopterY > maxCameraY) {
            helicopterY = maxCameraY;
        }

        camera.position.set(helicopterX, helicopterY, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        cenario.render(batch);
        for (Tiro tiro : helicoptero.getTiros()) {
            tiro.render(batch);
        }
        helicoptero.render(batch);

        batch.end();

        // Capturar o input do usuário
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            helicoptero.atirar();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        helicoptero.dispose();
        cenario.dispose();
    }
}
