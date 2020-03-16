package evgenyt.dangerousgalaxy.utils;

import static java.lang.Math.sqrt;

public class SpaceMath {

    public static class Point {

        public static long centerX;
        public static long centerY;
        public static float ratio = 1;

        private long x;
        private long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        public long getScrX() {
            return (long) ((x +centerX) * ratio + 500);
        }

        public long getSxrY() {
            return (long) ((centerY + y) * ratio + 750);
        }

        @Override
        public String toString() {
            return  x + "-" +y;
        }
    }

    public static float distance(float x0, float x1, float y0, float y1) {
        float x = x0 - x1;
        float y = y0 - y1;
        return (float)sqrt(x * x + y * y);
    }

    public static Point getRandomPoint(long r) {
        double angle = getRandomAngle(r);
        // r = (long) Math.sqrt((double)r);
        return new Point((long) (r * Math.cos(angle)),
                (long) (r * Math.sin(angle)));
    }

    public static double getRandomAngle(long r) {
        double x = getRandom(r);
        String str = String.valueOf(x);
        str = reverseString(str);
        str = str.substring(0, str.indexOf('.'));
        x = Double.valueOf(str);
        x = x / 1000000000;
        x = x / 10000000;
        return x * Math.PI * 2;
    }

    public static double getRandom(long r) {
        double x = 0;
        boolean k = true;
        for (int i = 1; i <= r; i++) {
            if (k)
                x += 1 / (double) i;
            else
                x -= 1 / (double) i;
            k = !k;
        }
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
