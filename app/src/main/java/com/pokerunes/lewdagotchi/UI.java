package com.pokerunes.lewdagotchi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Xenowar on 26.04.2016.
 */
public class UI {

    private Bitmap spriteMenu;
    private Bitmap spriteOptions;
    private Bitmap spriteSelector;
    private Bitmap spriteStoreUI;

    private Bitmap[] spriteSMSelector = new Bitmap[5];

    private Bitmap spriteDirtCrumbs;

    private Bitmap spriteFoodCandy;

    /*Bitmap sprite;
    Bitmap sprite;
    Bitmap sprite;
    Bitmap sprite;*/

    public UI(Context context){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        spriteMenu = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu,options);
        spriteOptions = BitmapFactory.decodeResource(context.getResources(), R.drawable.options,options);
        spriteSelector = BitmapFactory.decodeResource(context.getResources(), R.drawable.selector,options);
        spriteStoreUI = BitmapFactory.decodeResource(context.getResources(), R.drawable.shopkeeper,options);

        spriteSMSelector[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.submenu_selector1,options);
        spriteSMSelector[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.submenu_selector2,options);
        spriteSMSelector[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.submenu_selector3,options);
        spriteSMSelector[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.submenu_selector4,options);
        spriteSMSelector[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.submenu_selector5,options);

        spriteDirtCrumbs = BitmapFactory.decodeResource(context.getResources(), R.drawable.dirt_crumbs,options);

        spriteFoodCandy = BitmapFactory.decodeResource(context.getResources(), R.drawable.food_candy,options);

    }

    public Bitmap getSpriteStoreUI() {
        return spriteStoreUI;
    }

    public Bitmap getSpriteMenu() {
        return spriteMenu;
    }

    public Bitmap getSpriteOptions() {
        return spriteOptions;
    }

    public Bitmap getSpriteSelector() {
        return spriteSelector;
    }

    public Bitmap getSMSelector(int i){
        return spriteSMSelector[i];
    }

    public Bitmap getSpriteDirtCrumbs() {
        return spriteDirtCrumbs;
    }

    public Bitmap getSpriteFoodCandy() {
        return spriteFoodCandy;
    }

}
