package com.pokerunes.lewdagotchi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Xenowar on 25.04.2016.
 */
public class Creature {

    public final int STAGE1 = 0;
    public final int STAGE2G = 1;
    public final int STAGE2B = 2;
    public final int STAGE3GG = 3;
    public final int STAGE3GB = 4;
    public final int STAGE3BG = 5;
    public final int STAGE3BB = 6;

    private boolean status_crumbs = false;
    private boolean status_cum = false;

    private int stage = STAGE1;
    private int age = 0;

    private Bitmap idleAnimation;
    private Bitmap bathAnimation;
    private Bitmap eatingAnimation;
    private Bitmap petAnimation;
    private Bitmap happyAnimation;
    private Bitmap[] fuckAnimation;
    private long[] fuckDuration;

    public Creature(Context context, int idleA, int bathA, int eatingA, int petA, int happyA, int[] fuckA,long[] fuckD) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        idleAnimation = BitmapFactory.decodeResource(context.getResources(), idleA, options);
        bathAnimation = BitmapFactory.decodeResource(context.getResources(), bathA, options);
        eatingAnimation = BitmapFactory.decodeResource(context.getResources(), eatingA, options);
        petAnimation = BitmapFactory.decodeResource(context.getResources(), petA, options);
        happyAnimation = BitmapFactory.decodeResource(context.getResources(), happyA, options);

        fuckAnimation = new Bitmap[fuckA.length];
        for (int i = 0; i < fuckA.length; i++) {
            fuckAnimation[i] = BitmapFactory.decodeResource(context.getResources(), fuckA[i], options);
        }

        fuckDuration = fuckD;
    }

    public void evolve() {

    }

    public int getAge() {
        return age;
    }

    public void addAge(int time) {
        age += time;
    }


    public boolean hasCrumbs() {
        return status_crumbs;
    }

    public void setCrumbs(boolean status_crumbs) {
        this.status_crumbs = status_crumbs;
    }

    public boolean hasCum() {
        return status_cum;
    }

    public void setCum(boolean status_cum) {
        this.status_cum = status_cum;
    }


    public Bitmap getIdleAnimation() {
        return idleAnimation;
    }

    public Bitmap getBathAnimation() {
        return bathAnimation;
    }

    public Bitmap getEatingAnimation() {
        return eatingAnimation;
    }

    public Bitmap getPetAnimation() {
        return petAnimation;
    }

    public Bitmap getHappyAnimation() {
        return happyAnimation;
    }

    public Bitmap[] getFuckAnimation() {
        return fuckAnimation;
    }

    public long[] getFuckDuration() {
        return fuckDuration;
    }
}
