package tests;

public class MathTest {

    public static void main(String[] args) {
        int x_diff = -0;
        int y_diff = 1;
        int x_arrow = 0;
        int y_arrow = 0;

        double angle;
        if (x_diff != 0) {
            if (y_diff < 0 && x_diff < 0) {
                angle = Math.atan(y_diff/x_diff) + Math.PI;
            } else if (y_diff >= 0 && x_diff < 0) {
                angle = Math.atan(y_diff/x_diff) + Math.PI;
            } else if (y_diff < 0 && x_diff >= 0) {
                angle = Math.atan(y_diff/x_diff) + Math.PI*2;
            } else {
                angle = Math.atan(y_diff/x_diff);
            }
        } else if (y_diff < 0) {
            angle = Math.PI * 3/2;
        } else {
            angle = Math.PI / 2;
        }
        System.out.println("angle: " + angle);

        double x_arrowbase;
        double y_arrowbase;

        x_arrowbase = x_arrow - 5*Math.cos(angle);
        y_arrowbase = y_arrow + 5*Math.sin(angle);

        System.out.println(x_arrowbase + " " + y_arrowbase);

        double x_arrow1 = x_arrowbase - 5*Math.cos(Math.PI/2 - angle);
        double y_arrow1 = y_arrowbase - 5*Math.sin(Math.PI/2 - angle);

        //double x_arrow2 = x_arrowbase - 5*Math.sin(angle);
        //double y_arrow2 = y_arrowbase + 5*Math.cos(angle);

        //System.out.println(x_arrow1 + " " + y_arrow1 + " " + x_arrow2 + " " + y_arrow2);
    }
}
