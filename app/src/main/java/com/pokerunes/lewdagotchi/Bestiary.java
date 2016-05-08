package com.pokerunes.lewdagotchi;

import android.content.Context;

/**
 * Created by Xenowar on 25.04.2016.
 */
public class Bestiary {

    private Creature stage1;
    private Creature stage2G;
    private Creature stage2B;
    private Creature stage3GG;
    private Creature stage3GB;
    private Creature stage3BG;
    private Creature stage3BB;

    private Context context;

    public Bestiary(Context context){
        this.context = context;
        initCreatures();
    }

    private void initCreatures(){
        stage1 = new Creature(context,R.drawable.stage1_idle,
                R.drawable.stage1_bath,
                R.drawable.stage1_eating,
                R.drawable.stage1_pet,
                R.drawable.stage1_happy,
                new int[]{R.drawable.stage1_blush,R.drawable.stage1_fuck1,R.drawable.stage1_fuck2},
                new long[]{3000,3000,3000},
                0,0);
       /* stage2G = new Creature();
        stage2B = new Creature();*/
        stage3GG = new Creature(context,R.drawable.stage3gg_idle,
                R.drawable.stage3gg_bath,
                R.drawable.stage3gg_eating,
                R.drawable.stage3gg_happy,
                R.drawable.stage3gg_happy,
                new int[]{R.drawable.stage3gg_fuck1,R.drawable.stage3gg_fuck2,R.drawable.stage3gg_fuck3,R.drawable.stage3gg_fuck4},
                new long[]{3000,3000,3000,3000},
                3000,6000);
        /*stage3GB = new Creature();
        stage3BG = new Creature();
        stage3BB = new Creature();*/
    }


    public Creature getStage1() {
        return stage1;
    }

    public Creature getStage2G() {
        return stage2G;
    }

    public Creature getStage2B() {
        return stage2B;
    }

    public Creature getStage3GG() {
        return stage3GG;
    }

    public Creature getStage3GB() {
        return stage3GB;
    }

    public Creature getStage3BG() {
        return stage3BG;
    }

    public Creature getStage3BB() {
        return stage3BB;
    }

}
