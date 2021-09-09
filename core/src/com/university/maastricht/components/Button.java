package com.university.maastricht.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
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
                addAction(scaleAction(1.13f,0.03f));
            }
        };
    }

    private ClickListener hover() {
        return new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(scaleAction(1.07f,0.1f));
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1)
                    addAction(scaleAction(1.07f,0.05f));
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1)
                    addAction(scaleAction(1f, 0.15f));
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


}
