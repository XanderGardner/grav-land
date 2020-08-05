import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application{
    AnchorPane anchorPane;
    ArrayList<Mass> mss;
    boolean running = false;
    boolean xFlag = false;
    boolean zFlag = false;
    boolean cFlag = false;
    boolean vFlag = false;
    boolean bFlag = false;
    public void start(Stage stage){
        Timer timer = new Timer();
        anchorPane = new AnchorPane();
        anchorPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(anchorPane);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case SPACE:
                    running = !running;
                    if (running){
                        timer.start();
                    } else {
                        timer.stop();
                    }
                    break;
                case Z:
                    zFlag = true;
                    break;
                case X:
                    xFlag = true;
                    break;
                case C:
                    cFlag = true;
                    break;
                case V:
                    vFlag = true;
                    break;
                case B:
                    bFlag = true;
                    break;
            }
        });
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.setTitle("[Space] Start/Stop  |  [Z] Clear  |  [X] Add Small  |  [C] Add Medium  |  [V] Add Large  |  [B] Add Tiny");
        stage.show();
        mss = new ArrayList<>();
    }
    public class Timer extends AnimationTimer{
        @Override
        public void handle(long now){
            if (bFlag){
                anchorPane.getChildren().clear();
                for (int i = 0; i < 250; i++){
                    Mass n = new Mass(Math.random()*1500, Math.random()*700, 0.5, Color.WHITE);
                    mss.add(n);
                }
                anchorPane.getChildren().addAll(mss);
                bFlag = false;
            }
            if (vFlag){
                anchorPane.getChildren().clear();
                for (int i = 0; i < 6; i++){
                    mss.add(new Mass(Math.random()*1500, Math.random()*700, Math.random()*10+15, Color.WHITE));
                }
                anchorPane.getChildren().addAll(mss);
                vFlag = false;
            }
            if (cFlag){
                anchorPane.getChildren().clear();
                for (int i = 0; i < 250; i++){
                    mss.add(new Mass(Math.random()*1500, Math.random()*700, Math.random()*3+1.3, Color.WHITE));
                }
                anchorPane.getChildren().addAll(mss);
                cFlag = false;
            }
            if (xFlag){
                anchorPane.getChildren().clear();
                for (int i = 0; i < 500; i++){
                    mss.add(new Mass(Math.random()*1500, Math.random()*700, 1, Color.WHITE));
                }
                anchorPane.getChildren().addAll(mss);
                xFlag = false;
            }
            if (zFlag){
                mss.clear();
                anchorPane.getChildren().clear();
                zFlag = false;
            }
            anchorPane.getChildren().clear();
            chk: while (!sfLnd()){
                for (int m = 0; m < mss.size(); m++) {
                    for (int chkM = m+1; chkM < mss.size(); chkM++) {
                        if (mss.get(m).intersects(mss.get(chkM).getLayoutBounds())) {
                            Mass n;
                            double radius = Math.cbrt(Math.pow(mss.get(m).getRadius(),3)+Math.pow(mss.get(chkM).getRadius(),3));
                            double xv = (mss.get(m).getMomentumX()+mss.get(chkM).getMomentumX())/(mss.get(m).getMass()+mss.get(chkM).getMass());
                            double yv = (mss.get(m).getMomentumY()+mss.get(chkM).getMomentumY())/(mss.get(chkM).getMass()+mss.get(chkM).getMass());
                            if (mss.get(m).getRadius() > mss.get(chkM).getRadius()){
                                n = new Mass(mss.get(m).getCenterX(),mss.get(m).getCenterY(),radius,Color.WHITE,xv,yv);
                            } else {
                                n = new Mass(mss.get(chkM).getCenterX(),mss.get(chkM).getCenterY(),radius,Color.WHITE,xv,yv);
                            }
                            mss.remove(m);
                            mss.remove(chkM-1);
                            mss.add(n);
                            continue chk;
                        }
                    }
                }
            }
            anchorPane.getChildren().addAll(mss);
            double tmpFx, tmpFy, tmpF, r;
            for (int m = 0; m < mss.size(); m++){
                tmpFx = 0;
                tmpFy = 0;
                for (int chkM = 0; chkM < mss.size(); chkM++){
                    if (m != chkM){
                        r = Math.pow(mss.get(m).getCenterX()-mss.get(chkM).getCenterX(),2)+Math.pow(mss.get(m).getCenterY()-mss.get(chkM).getCenterY(),2);
                        tmpF = 0.5*mss.get(m).getMass()*mss.get(chkM).getMass()/r;
                        tmpFx += tmpF/Math.sqrt(r)*(mss.get(chkM).getCenterX()-mss.get(m).getCenterX());
                        tmpFy += tmpF/Math.sqrt(r)*(mss.get(chkM).getCenterY()-mss.get(m).getCenterY());
                    }
                }
                mss.get(m).aclrtX(tmpFx);
                mss.get(m).aclrtY(tmpFy);
            }
        }
    }
    private boolean sfLnd(){
        for (int i = 0; i < mss.size(); i++){
            for (int j = i+1; j < mss.size(); j++){
                if (mss.get(i).intersects(mss.get(j).getLayoutBounds())){
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args){
        launch(args);
    }
}
