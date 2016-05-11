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
    private Bitmap handAnimation;
    private Bitmap handCumAnimation;
    private Bitmap happyAnimation;
    private Bitmap cumAnimation;
    private Bitmap[] fuckAnimation;
    private Bitmap[] fuckCumAnimation;
    private long[] fuckDuration;

    private long crumbsHideStart;
    private long crumbsHideDuration;
    private long cumHideStart;
    private long cumHideDuration;

    public Creature(Context context, int idleA, int bathA, int eatingA, int petA, int handA, int handCumA, int happyA,int cumA, int[] fuckA, int[] fuckCA, long[] fuckD, long chStart, long chDuration, long cuhStart, long cuhDuration) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        idleAnimation = BitmapFactory.decodeResource(context.getResources(), idleA, options);
        bathAnimation = BitmapFactory.decodeResource(context.getResources(), bathA, options);
        eatingAnimation = BitmapFactory.decodeResource(context.getResources(), eatingA, options);
        petAnimation = BitmapFactory.decodeResource(context.getResources(), petA, options);
        handAnimation = BitmapFactory.decodeResource(context.getResources(), handA, options);
        handCumAnimation = BitmapFactory.decodeResource(context.getResources(), handCumA, options);
        happyAnimation = BitmapFactory.decodeResource(context.getResources(), happyA, options);
        cumAnimation = BitmapFactory.decodeResource(context.getResources(), cumA, options);

        fuckAnimation = new Bitmap[fuckA.length];
        fuckCumAnimation = new Bitmap[fuckA.length];
        for (int i = 0; i < fuckA.length; i++) {
            fuckAnimation[i] = BitmapFactory.decodeResource(context.getResources(), fuckA[i], options);
            fuckCumAnimation[i] = BitmapFactory.decodeResource(context.getResources(), fuckCA[i], options);
        }

        fuckDuration = fuckD;
        crumbsHideStart = chStart;
        crumbsHideDuration = chDuration;
        cumHideStart = cuhStart;
        cumHideDuration = cuhDuration;
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

    public Bitmap[] getFuckCumAnimation() {
        return fuckCumAnimation;
    }

    public long[] getFuckDuration() {
        return fuckDuration;
    }

    public long getCrumbsHideStart() {
        return crumbsHideStart;
    }

    public long getCrumbsHideDuration() {
        return crumbsHideDuration;
    }

    public long getCumHideStart() {
        return cumHideStart;
    }

    public long getCumHideDuration() {
        return cumHideDuration;
    }

    public Bitmap getHandCumAnimation() {
        return handCumAnimation;
    }

    public Bitmap getHandAnimation() {
        return handAnimation;
    }

    public Bitmap getCumAnimation() {
        return cumAnimation;
    }
}
