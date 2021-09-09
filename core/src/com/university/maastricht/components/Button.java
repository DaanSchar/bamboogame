package com.university.maastricht.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Button extends Image {

    public Button(TextureRegion texture, int x, int y, int width, int height) {
        super(texture);

        setBounds(x, y, width, height);
        setOrigin(width/2, height/2);

        addListener(hover());
        addListener(click());
    }

    private ActorGestureListener click()  {
        return new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == 0) {
                    addAction(scaleAction(1.13f, 0.03f));
                    addAction(rotateByAction(-25, 0.2f));
                }

            }
        };
    }

    private ClickListener hover() {
        return new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(scaleAction(1.07f,0.1f));
                addAction(rotateByAction(180, 0.4f));
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    addAction(scaleAction(1.07f, 0.05f));
                    addAction(rotateByAction(-30, 0.3f));
                }
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    addAction(scaleAction(1f, 0.15f));
                    addAction(rotateByAction(30, 0.3f));
                }
            }
        };
    }


    // -------------------- Animation Actions --------------------

    private Action scaleAction(float scale, float time) {
        ScaleToAction action = new ScaleToAction();
        action.setScale(scale);
        action.setDuration(time);
        return action;
    }

    private Action rotateToAction(float rotation, float time) {
        RotateToAction action = new RotateToAction();
        action.setRotation(rotation);
        action.setDuration(time);
        action.setInterpolation(Interpolation.exp5);
        return action;
    }


    private Action rotateByAction(float rotation, float time) {
        RotateByAction action = new RotateByAction();
        action.setAmount(rotation);
        action.setDuration(time);
        action.setInterpolation(Interpolation.exp5);
        return action;
    }


}
