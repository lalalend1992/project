package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Rectangle2D ourscreen = Screen.getPrimary().getBounds();
        PerspectiveCamera ourcamera = new PerspectiveCamera();
        final List<KeyCode> acceptedKey = Arrays.asList(KeyCode.UP, KeyCode.DOWN, KeyCode.RIGHT, KeyCode.LEFT);
        final Set<KeyCode> codes = new HashSet<>();

        ourcamera.getTransforms().addAll(new Rotate(0, Rotate.Y_AXIS), new Rotate(-45, Rotate.X_AXIS), new Translate(0,0,0));
        ControlCameraV2 ourcontrol = new ControlCameraV2(ourcamera);
        MeshView ourmeshview1 = this.CreateCustomTriangle();
        ourmeshview1.setTranslateX(300);
        ourmeshview1.setTranslateY(300);

        AmbientLight ambientlight = new AmbientLight(Color.color(0.2,0.2,0.2));
        Group ourgroup = new Group(ourmeshview1, createPointLight(), ambientlight);
        Scene ourscene = new Scene(ourgroup, ourscreen.getWidth(), ourscreen.getHeight(), true, SceneAntialiasing.BALANCED);

        AnimationTimer ourtimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                ourcontrol.Demonstration();
            }
        };


        ourscene.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN){ ourcontrol.brakingY(); codes.remove(KeyCode.UP); codes.remove(KeyCode.DOWN);}
            if(e.getCode() == KeyCode.RIGHT){ourcontrol.brakingX();codes.remove(KeyCode.RIGHT);}
            if(e.getCode() == KeyCode.LEFT){ourcontrol.brakingX();codes.remove(KeyCode.LEFT);}
            }); // очищаем коллекцию, когда отпускаем кнопку

        ourscene.setOnKeyPressed(e -> {
            if(acceptedKey.contains(e.getCode()))
            {
                codes.add(e.getCode());
                if(codes.contains(KeyCode.UP))
                {
                    ourcontrol.speedY(2);
                }
                if(codes.contains(KeyCode.DOWN))
                {
                    ourcontrol.speedY(-2);
                }
                if(codes.contains(KeyCode.RIGHT))
                {
                    ourcontrol.speedX(2);
                }
                if(codes.contains(KeyCode.LEFT))
                {
                    ourcontrol.speedX(-2);
                }
            }
        });

        ourscene.setCamera(ourcamera);
        primaryStage.setScene(ourscene);
        primaryStage.show();
        ourtimer.start();

    }
    private PointLight createPointLight() {
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(45);
        light.setTranslateY(45);
        light.setTranslateZ(-300);

        return light;
    }
    public MeshView CreateCustomTriangle() {
        Image diffuseMap = new Image(getClass().getResource("road.jpg").toExternalForm());
        PhongMaterial ourmaterial = new PhongMaterial();
        ourmaterial.setDiffuseMap(diffuseMap);
        float[] points={
                0,0,0,//0
                (float)Math.sqrt(40000+40000), 0, 0,//1
                (float)Math.sqrt(40000+40000), 50, 0,//2
                0, 50, 0,//3
                (float)Math.sqrt(40000+40000)/2, 0, (float)Math.sqrt(2)*100,//4
                (float)Math.sqrt(40000+40000)/2, 50, (float)Math.sqrt(2)*100,//5
        };
        float[] texCrds={
                0.125f, 0.4f,//0
                0.875f, 0.4f,//1
                0.875f, 0.6f,//2
                0.125f, 0.6f,//3
                0.5f, 0.1f,//4
                0.5f, 1.0f,//5
                0, 0.3f,//6
                0.4f, 0,//7
                0.6f, 0,//8
                1.0f, 0.3f//9
        };
        int[] faces ={
                0, 0, 4, 4, 1, 1,
                0, 0, 1, 1, 4, 4,
                3, 3, 2, 2, 5, 5,
                3, 3, 5, 5, 2, 2,
                0, 0, 1, 1, 3, 3,
                0, 0, 3, 3, 1, 1,
                1, 1, 2, 2, 3, 3,
                1, 1, 3, 3, 2, 2,
                0, 0, 5, 7, 4, 4,
                0, 0, 4, 4, 5, 7,
                0, 0, 3, 6, 5, 7,
                0, 0, 5, 7, 3, 6,
                1, 1, 4, 4, 2, 9,
                1, 1, 2, 9, 4, 4,
                4, 4, 5, 8, 2, 9,
                4, 4, 2, 9, 5, 8
        };
        TriangleMesh ourtriangle = new TriangleMesh();
        ourtriangle.getPoints().addAll(points);
        ourtriangle.getTexCoords().addAll(texCrds);
        ourtriangle.getFaces().addAll(faces);
        MeshView ourmeshview = new MeshView();
        ourmeshview.setMesh(ourtriangle);
        ourmeshview.setMaterial(ourmaterial);
        return  ourmeshview;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
