package app;

/**
 * Created by mloda on 2016-06-10.
 */
public class Utils {

    public int limitColor(int color) {
        if(color > 255) {
            return 255;
        } else if (color < 0) {
            return 0;
        } else {
            return color;
        }
    }
}
