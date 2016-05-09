package com.pokerunes.lewdagotchi;

import android.app.NotificationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;

public class SpriteSheetAnimation extends Activity {

    // Our object that will hold the view and
    // the sprite sheet animation logic
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view
        gameView = new GameView(this);
        setContentView(gameView);

    }

    // Here is our implementation of GameView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside SpriteSheetAnimation

    // Notice we implement runnable so we have
    // A thread and can override the run method.
    class GameView extends SurfaceView implements Runnable {

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        long animationTimer = 0;
        long animationDuration = 0;
        boolean animationRunning = false;

        LinkedList<Long> aniTimer = new LinkedList<>();
        LinkedList<Bitmap> aniSprites = new LinkedList<>();

        UI ui = new UI(getContext());

        Bestiary bestiary = new Bestiary(getContext());
        Creature c = bestiary.getStage3GG();

        // Declare an object of type Bitmap
        Bitmap currentSprite;

        // Bob starts off not moving
        boolean isMoving = false;

        boolean menuEnabled = false;

        // He can walk at 150 pixels per second ##################################
        float walkSpeedPerSecond = 250;

        // He starts 10 pixels from the left
        float spriteXPosition = 0;

        //Number of options in the menu
        private int numberOptions = 9;

        private final int OP_PLAY = 0,
                OP_FEED = 1,
                OP_FUCK = 2,
                OP_STORE = 3,
                OP_CUSTOM = 4,
                OP_BACK = 5,
                OP_OPTION = 6,
                OP_BATH = 7,
                OP_PET = 8;

        private final int VIEW_PET = 0,
                VIEW_STORE = 1,
                VIEW_ARCADE = 2;

        private final int MENU_MAIN = 0,
                MENU_SUB = 1,
                MENU_SUB_BIG = 2;

        private final int SUB_FEED = 0,
                SUB_OPTIONS = 1,
                SUB_STORE_MAIN = 2,
                SUB_STORE_FOOD = 3,
                SUB_STORE_HOME = 4,
                SUB_STORE = 5;

        private int selectedOption = OP_PLAY;
        private int subMenuType = 0;
        private int subMenuSlot = 0;

        private int currentView = VIEW_PET;
        private int currentMenu = MENU_MAIN;

        private int subMenuFeedSlots = 2;
        private int subMenuStoreSlots = 4;


        //option sprite measurements
        private int optionWidth = 30;
        private int optionHeight = 9;
        private int optionGap = 2;
        private int optionDistanceX = 46;
        private int optionDistanceY = 78;
        // New for the sprite sheet animation

        // These next two values can be anything you like
        // As long as the ratio doesn't distort the sprite too much
        private int frameWidth = 128;
        private int frameHeight = 96;

        // How many frames are there on the sprite sheet?
        private int frameCount = 2;

        // Start at the first frame - where else?
        private int currentFrame = 0;

        // What time was it when we last changed frames
        private long lastFrameChangeTime = 0;

        // How long should each frame last
        private int frameLengthInMilliseconds = 250;


        // A rectangle to define an area of the
        // sprite sheet that represents 1 frame
        private Rect frameToDraw = new Rect();

        // A rect that defines an area of the screen
        // on which to draw
        RectF whereToDraw = new RectF();

        private Rect drawOnCenter = new Rect();
        private Rect leftBorder = new Rect();
        private Rect rightBorder = new Rect();
        private Rect button = new Rect();
        private Rect menuToDraw = new Rect();
        Rect whereToDrawMenu = new Rect();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public GameView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            mBuilder.setSmallIcon(R.drawable.system_notificationicon);
            mBuilder.setContentTitle("Notification Alert, Click Me!");
            mBuilder.setContentText("Hi, This is Android Notification Detail!");

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            paint.setDither(false);
            paint.setAntiAlias(false);
            paint.setFilterBitmap(false);

            currentSprite = c.getIdleAnimation();

            isMoving = true;

            // Set our boolean to true - game on!
            //playing = true;

        }

        @Override
        public void run() {
            while (playing) {

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                //update();

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }

            }

        }

        // Everything that needs to be updated goes in here
        // In later projects we will have dozens (arrays) of objects.
        // We will also do other things like collision detection.
        public void update() {

            // If bob is moving (the player is touching the screen)
            // then move him to the right based on his target speed and the current fps.
            if(isMoving){
                spriteXPosition = spriteXPosition + (walkSpeedPerSecond / fps);
            }

        }

        public void getCurrentFrame(){

            long time  = System.currentTimeMillis();

            if(overlayWait){
                if(overlayStart <= time){
                    overlayWait = false;
                    overlayPlay = true;
                }
            }

            if(overlayPlay){
                if(overlayEnd <= time){
                    overlayPlay = false;
                }
            }

            if(crumbWait){
                if(crumbDelay <= time){
                    crumbWait = false;
                    c.setCrumbs(true);
                }
            }

            if(cumWait){
                if(cumDelay <= time){
                    cumWait = false;
                    c.setCum(true);
                }
            }

            if(overlayHideDelay){
                if(overlayHideStart <= time){
                    overlayHideDelay = false;
                    overlaysEnabled = false;
                }
            }

            if(!overlaysEnabled){
                if(overlayHideDuration <= time){
                    overlaysEnabled = true;
                }
            }

            if(animationRunning){
                if ( time-animationTimer >= aniTimer.getFirst() ) {
                    aniTimer.removeFirst();
                    aniSprites.removeFirst();
                    if(aniTimer.isEmpty()){
                        stopAnimation();
                    }
                    else{
                        animationTimer = System.currentTimeMillis();
                        currentSprite = aniSprites.getFirst();
                    }
                }
                else{
                    currentSprite = aniSprites.getFirst();
                }

            }

            if(isMoving) {// Only animate if bob is moving
                if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                    lastFrameChangeTime = time;
                    currentFrame++;
                    if (currentFrame >= frameCount) {

                        currentFrame = 0;
                    }
                }
            }
            //update the left and right values of the source of
            //the next frame on the spritesheet
            frameToDraw.left = currentFrame * frameWidth;
            frameToDraw.right = frameToDraw.left + frameWidth;

        }

        // Draw the newly updated scene
        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawColor(Color.argb(255,  255, 255, 255));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  249, 129, 0));

                // Make the text a bit bigger
                paint.setTextSize(45);

                // Display the current fps on the screen
                canvas.drawText("FPS:" + fps, 20, 40, paint);

                initRects();

                getCurrentFrame();

                if(currentView == VIEW_PET){
                    if(overlaysEnabled) {
                        if (c.hasCrumbs()) {
                            canvas.drawBitmap(ui.getSpriteDirtCrumbs(),
                                    drawOnCenter,
                                    whereToDraw, paint);
                        }
                    }

                    canvas.drawBitmap(currentSprite,
                            frameToDraw,
                            whereToDraw, paint);


                    if(menuEnabled) {
                        canvas.drawBitmap(ui.getSpriteMenu(),
                                drawOnCenter,
                                whereToDraw, paint);
                        if(currentMenu == MENU_MAIN) {
                            canvas.drawBitmap(ui.getSpriteSelector(),
                                    drawOnCenter,
                                    whereToDraw, paint);
                            canvas.drawBitmap(ui.getSpriteOptions(),
                                    menuToDraw,
                                    whereToDrawMenu, paint);
                        }
                        else if(currentMenu == MENU_SUB){
                            canvas.drawBitmap(ui.getSMSelector(subMenuSlot),
                                    drawOnCenter,
                                    whereToDraw, paint);
                        }

                    }
                }
                else if(currentView == VIEW_STORE){
                    canvas.drawBitmap(ui.getSpriteStoreUI(),
                            frameToDraw,
                            whereToDraw, paint);
                    if(subMenuType == SUB_STORE_MAIN) {
                        canvas.drawBitmap(ui.getSpriteMenu(),
                                drawOnCenter,
                                whereToDraw, paint);
                        canvas.drawBitmap(ui.getSMSelector(subMenuSlot),
                                drawOnCenter,
                                whereToDraw, paint);
                    }
                }

                if(overlayPlay){
                    canvas.drawBitmap(overlaySprite,
                            drawOnCenter,
                            whereToDraw, paint);
                }


                paint.setColor(Color.argb(255, 0, 0, 0));
                canvas.drawRect(leftBorder,paint);
                canvas.drawRect(rightBorder,paint);


                // Draw everything to the screen
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        private void initRects(){
            menuToDraw.set(
                    (2*optionWidth+2*optionGap)+(selectedOption*optionWidth+optionGap*selectedOption)-optionDistanceX,
                    0,
                    ((2*optionWidth+2*optionGap)+(selectedOption*optionWidth+optionGap*selectedOption)-optionDistanceX)+optionDistanceX*2+optionWidth,
                    optionHeight
            );

            whereToDrawMenu.set(
                    this.getWidth()/2-(((frameWidth-6)*this.getHeight())/frameHeight)/2,
                    (optionDistanceY*this.getHeight())/frameHeight,
                    this.getWidth()/2+(((frameWidth-6)*this.getHeight())/frameHeight)/2,
                    ((optionDistanceY+optionHeight)*this.getHeight())/frameHeight
            );

            frameToDraw.set(
                    0,
                    0,
                    frameWidth,
                    frameHeight
            );

            whereToDraw.set(
                    this.getWidth()/2-((frameWidth*this.getHeight())/frameHeight)/2,
                    0,
                    this.getWidth()/2+((frameWidth*this.getHeight())/frameHeight)/2,
                    this.getHeight()
            );

            drawOnCenter.set(
                    0,
                    0,
                    frameWidth,
                    frameHeight);

            leftBorder.set(
                    0,
                    0,
                    this.getWidth()/2-(((frameWidth+2)*this.getHeight())/frameHeight)/2,
                    this.getHeight()
            );

            rightBorder.set(
                    this.getWidth()/2+(((frameWidth+2)*this.getHeight())/frameHeight)/2,
                    0,
                    this.getWidth(),
                    this.getHeight()
            );

            button.set(
                    this.getWidth()/2-((frameWidth*this.getHeight())/frameHeight)/2,
                    (69*this.getHeight())/frameHeight,
                    this.getWidth()/2+((frameWidth*this.getHeight())/frameHeight)/2,
                    this.getHeight()
            );
        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started theb
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        private void playAnimation(Bitmap[] animation, long[] animationDuration){
            //this.animationDuration = animationDuration;
            //currentSprite = animation;
            animationTimer = System.currentTimeMillis();
            for(int i = 0; i < animation.length; i++){
                aniSprites.add(animation[i]);
                aniTimer.add(animationDuration[i]);
            }
            animationRunning = true;
        }

        private void stopAnimation(){
            animationRunning = false;
            animationDuration = 0;
            animationTimer = 0;
            currentSprite = c.getIdleAnimation();
        }

        private boolean overlayWait = false;
        private boolean overlayPlay = false;
        private long overlayStart;
        private long overlayEnd;
        private Bitmap overlaySprite;

        private void playOverlay(long start, long duration, Bitmap overlay){
            overlaySprite = overlay;
            overlayStart = System.currentTimeMillis() + start;
            overlayEnd = overlayStart + duration;
            overlayWait = true;
        }

        private long crumbDelay;
        private boolean crumbWait = false;

        private void activateCrumbs(long start){
            crumbDelay = System.currentTimeMillis() + start;
            crumbWait = true;
        }

        private long cumDelay;
        private boolean cumWait = false;

        private void activateCum(long start){
            cumDelay = System.currentTimeMillis() + start;
            cumWait = true;
        }


        private boolean overlaysEnabled = true;
        private boolean overlayHideDelay = false;
        private long overlayHideStart;
        private long overlayHideDuration;

        private void hideOverlays(long start, long duration){
            overlayHideStart = System.currentTimeMillis() + start;
            overlayHideDuration = overlayHideStart + duration;
            overlayHideDelay = true;
        }

        private boolean isInside(int x, int y, Rect rect){
            if(x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom){
                return true;
            }
            else{
                return false;
            }

        }

        private long addupTime(long[] times){
            long time = 0;
            for(int i = 0; i < times.length; i++){
                time += times[i];
            }
            return time;
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    // Set isMoving so Bob is moved in the update method
                    //isMoving = true;

                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    // Set isMoving so Bob does not move
                    //isMoving = false;
                    if (currentView == VIEW_PET) {
                        if(!menuEnabled) {
                            menuEnabled = true;
                        }
                        else{
                            if(currentMenu == MENU_MAIN) {
                                if (isInside(x, y, leftBorder)) {
                                    if (selectedOption > 0) {
                                        selectedOption--;
                                    } else {
                                        selectedOption = numberOptions - 1;
                                    }
                                } else if (isInside(x, y, rightBorder)) {
                                    if (selectedOption < numberOptions - 1) {
                                        selectedOption++;
                                    } else {
                                        selectedOption = 0;
                                    }
                                } else if (isInside(x, y, button)) {
                                    switch (selectedOption) {
                                        case OP_PLAY:
                                            /*TODO*/
                                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                            mNotificationManager.notify(001, mBuilder.build());
                                            break;
                                        case OP_FEED:
                                            currentMenu = MENU_SUB;
                                            subMenuType = SUB_FEED;
                                            subMenuSlot = 0;
                                            break;
                                        case OP_FUCK:
                                            playAnimation(c.getFuckAnimation(), c.getFuckDuration());
                                            hideOverlays(c.getOverHideStart(),c.getOverHideDuration());
                                            activateCum(addupTime(c.getFuckDuration()));
                                            menuEnabled = false;
                                            break;
                                        case OP_STORE:
                                            menuEnabled = true;
                                            currentMenu = MENU_SUB;
                                            subMenuType = SUB_STORE_MAIN;
                                            subMenuSlot = 0;
                                            currentView = VIEW_STORE;
                                            break;
                                        case OP_CUSTOM:
                                            if(c.equals(bestiary.getStage1())){
                                                c = bestiary.getStage3GG();
                                            }else{
                                                c = bestiary.getStage1();
                                            }
                                            currentSprite = c.getIdleAnimation();
                                            menuEnabled = false;
                                            break;
                                        case OP_BACK:
                                            menuEnabled = false;
                                            selectedOption = 0;
                                            break;
                                        case OP_OPTION:
                                            break;
                                        case OP_BATH:
                                            c.setCrumbs(false);
                                            c.setCum(false);
                                            playAnimation(new Bitmap[]{c.getBathAnimation(), c.getHappyAnimation()}, new long[]{3000, 3000});
                                            menuEnabled = false;
                                            break;
                                        case OP_PET:
                                            playAnimation(new Bitmap[]{c.getPetAnimation(), c.getHappyAnimation()}, new long[]{3000, 3000});
                                            menuEnabled = false;
                                            break;
                                    }
                                }
                            }
                            else if(currentMenu == MENU_SUB){
                                if(subMenuType == SUB_FEED){
                                    if (isInside(x, y, leftBorder)) {
                                        if(subMenuSlot > 0){
                                            subMenuSlot--;
                                        }
                                        else{
                                            subMenuSlot = subMenuFeedSlots-1;
                                        }
                                    }
                                    else if(isInside(x, y, rightBorder)) {
                                        if(subMenuSlot < subMenuFeedSlots-1){
                                            subMenuSlot++;
                                        }
                                        else{
                                            subMenuSlot = 0;
                                        }
                                    }
                                    else if(isInside(x,y,button)){
                                        switch(subMenuSlot){
                                            case 0:
                                                currentMenu = MENU_MAIN;
                                                break;
                                            case 1:
                                                playOverlay(0,3000,ui.getSpriteFoodCandy());
                                                playAnimation(new Bitmap[]{c.getEatingAnimation(), c.getHappyAnimation()}, new long[]{3000, 3000});
                                                activateCrumbs(3000);
                                                menuEnabled = false;
                                                currentMenu = MENU_MAIN;
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                                else if(subMenuType == SUB_OPTIONS){

                                }
                            }
                        }
                    }
                    else if(currentView == VIEW_STORE){
                        if(subMenuType == SUB_STORE_MAIN) {
                            if (isInside(x, y, leftBorder)) {
                                if (subMenuSlot > 0) {
                                    subMenuSlot--;
                                } else {
                                    subMenuSlot = subMenuStoreSlots - 1;
                                }
                            } else if (isInside(x, y, rightBorder)) {
                                if (subMenuSlot < subMenuStoreSlots - 1) {
                                    subMenuSlot++;
                                } else {
                                    subMenuSlot = 0;
                                }
                            }else if(isInside(x,y,button)){
                                switch (subMenuSlot){
                                    case 0:
                                        menuEnabled = false;
                                        currentMenu = MENU_MAIN;
                                        currentView = VIEW_PET;
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }




                    /*if(currentSprite.equals(stage1_idle))
                        currentSprite = stage1_happy;
                    else
                        currentSprite = stage1_idle;
                        */

                    break;
            }
            return true;
        }

    }
    // This is the end of our GameView inner class

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }

}