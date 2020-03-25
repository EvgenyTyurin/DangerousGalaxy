package evgenyt.dangerousgalaxy.utils;

import static java.lang.Math.sqrt;

public class SpaceMath {

    // Random generator sequence vars
    static double x = 0;
    static boolean k = true;
    static double i = 1;

    public static class Point {

        private long x;
        private long y;
        private double angle;
        private long radius;

        public Point(double angle, long radius) {
            this.angle = angle;
            this.radius = radius;
            x = (long) (radius * Math.cos(angle));
            y = (long) (radius * Math.sin(angle));
        }

        public void rotate(double delta) {
            angle += delta;
            x = (long) (radius * Math.cos(angle));
            y = (long) (radius * Math.sin(angle));
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        @Override
        public String toString() {
            return  x + ":" +y;
        }
    }

    public static float distance(float x0, float x1, float y0, float y1) {
        float x = x0 - x1;
        float y = y0 - y1;
        return (float)sqrt(x * x + y * y);
    }

    public static Point getRandomPoint(long r) {
        double angle = getRandomAngle();
        return new Point(angle, r);
    }

    public static double getRandomAngle() {
        return getNextRandom() * Math.PI * 2;
    }

    public static double getNextRandom() {
        double x = getNextSequence();
        String str = String.valueOf(x);
        str = reverseString(str);
        str = str.substring(0, str.indexOf('.'));
        x = Double.valueOf(str);
        x = x / 1000000000;
        x = x / 10000000;
        return x;
    }

    public static double getNextSequence() {
        if (k)
            x += 1 / i;
        else
            x -= 1 / i;
        k = !k;
        i++;
        return x;
    }

    public static String reverseString(String str) {
        char[] charArray = str.toCharArray();
        // Cycle to half of char array and swap with another half
        for(int idx = 0; idx < charArray.length / 2; idx++) {
            char buf = charArray[idx];
            charArray[idx] = charArray[charArray.length - idx - 1];
            charArray[charArray.length - idx - 1] = buf;
        }
        // Char array to string
        return String.valueOf(charArray);
    }

}
