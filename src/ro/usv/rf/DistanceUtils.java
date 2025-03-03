package ro.usv.rf;

public class DistanceUtils {
    public static double distEuclid ( double x[], double y[] ) {
        if(x.length != y.length) throw new DifferentSpaceSizeException(
                "("+x.length+", "+y.length+")");

        double d = 0;
        for(int j=0; j< x.length; j++)
            d += (x[j]-y[j])* (x[j]-y[j]);

        return Math.sqrt(d);
    }

    public static double distCityBlock ( double x[], double y[] )  {
        // de completat
        if(x.length != y.length) throw new DifferentSpaceSizeException(
                "("+x.length+", "+y.length+")");
        double d=0;
        for(int j=0;j< x.length;j++){
            d+= Math.abs((x[j]-y[j]));
        }
        return d;
    }
    public static double distCebisev ( double x[], double y[] )  {
        // de completat
        if(x.length != y.length) throw new DifferentSpaceSizeException(
                "("+x.length+", "+y.length+")");
        double d=0;
        double dis=0;
        for(int j=0;j< x.length;j++){
            d=Math.max((Math.abs(x[j]-y[j])),dis);
            dis=Math.abs(x[j]-y[j]);
        }
        return d;
    }

}

class DifferentSpaceSizeException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DifferentSpaceSizeException(String message) {
        super(message);
    }
}
