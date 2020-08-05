import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Mass extends Circle{
    private double mass;
    private double xv;
    private double yv;
    private double x;
    private double y;
    public Mass(double centerX, double centerY, double radius, Paint fill){
        super(centerX, centerY, radius, fill);
        mass = Math.PI*Math.pow(radius,3)/3;
        xv = 0;
        yv = 0;
    }
    public Mass(double centerX, double centerY, double radius, Paint fill, double xv, double yv){
        super(centerX, centerY, radius, fill);
        mass = Math.PI * radius * radius;
        this.xv = xv;
        this.yv = yv;
    }
    public double getMass(){
        return mass;
    }
    public double getXv(){
        return xv;
    }
    public double getYv(){
        return yv;
    }
    public void setMass(double mass){
        this.mass = mass;
    }
    public void setXv(double xv){
        this.xv= xv;
    }
    public void setYv(double yv){
        this.yv = yv;
    }
    public double getMomentumX(){
        return mass*xv;
    }
    public double getMomentumY(){
        return mass*yv;
    }
    public void aclrtX(double xForce){
        double ax = xForce / mass;
        xv += ax;
        setCenterX(getCenterX() + xv);
    }
    public void aclrtY(double yForce){
        double ay = yForce / mass;
        yv += ay;
        setCenterY(getCenterY() + yv);
    }
}
