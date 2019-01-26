package sample;

import javafx.scene.PerspectiveCamera;

public class ControlCameraV2 {
    PerspectiveCamera ourcamera;
    int accelerationX = 0;
    int accelerationY = 0;
    int speedX = 0;
    int speedY = 0;
    int frictionForceX = 0;
    int frictionForceY = 0;
    public ControlCameraV2(PerspectiveCamera camera){ourcamera = camera;}
    public void speedX(int acceleration){ accelerationX = acceleration; speedSynchronizationX();}
    public void speedY(int acceleration){accelerationY = acceleration;speedSynchronizationY();}
    public void brakingX(){accelerationX = 0;}
    public void brakingY(){accelerationY = 0;}
    public void speedSynchronizationX(){if(speedY != 0) speedX = speedY;}
    public void speedSynchronizationY(){if(speedX != 0) speedY = speedX;}
    public void Demonstration(){
        if(speedX > 0) frictionForceX = -1;
        if(speedX < 0) frictionForceX = 1;
        if (speedY > 0) frictionForceY = -1;
        if (speedY < 0) frictionForceY = 1;
        if (speedY == 0) frictionForceY = 0;
        if(speedX < 12 && speedX > -12){speedX = speedX + accelerationX;}
        if(speedY < 12 && speedY > -12){speedY = speedY + accelerationY;}
        speedX = speedX + frictionForceX;
        speedY = speedY + frictionForceY;
        if (speedX == 0) frictionForceX = 0;
        if (speedY == 0) frictionForceY = 0;
        ourcamera.setTranslateZ(ourcamera.getTranslateZ()+speedY);
        ourcamera.setTranslateX(ourcamera.getTranslateX()+speedX);
    }

}
