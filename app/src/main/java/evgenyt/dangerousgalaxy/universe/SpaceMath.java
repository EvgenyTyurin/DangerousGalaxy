package evgenyt.dangerousgalaxy.universe;

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

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public Point(double angle, long radius) {
            this.angle = angle;
            this.radius = (long) radius / 10;
            x = (long) (this.radius * Math.cos(angle));
            y = (long) (this.radius * Math.sin(angle));
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

        public double getAngle() {
            return angle;
        }

        public long getRadius() {
            return radius;
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

    public static float distanceLY(float x0, float x1, float y0, float y1) {
        return distance(x0, x1, 0, y1) / 2;
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

    // Generate random planet near star
    static Planet generateRandomPlanet(String name , Star star, int orbit) {
        Planet planet = new Planet();
        planet.setName(name);
        double rand = SpaceMath.getNextRandom();
        if (rand <= 0.333) {
            planet.setPlanetType(Planet.PlanetType.GAS_GIANT);
        } else {
            // Water on planet?
            if (rand >= 0.666) {
                if (orbit >= star.getStarClass().getBoilOrbit() && orbit <= star.getStarClass().getFreezOrbit()) {
                    planet.setPlanetType(Planet.PlanetType.EARTH_LIKE);
                } else {
                    if (orbit < star.getStarClass().getBoilOrbit()) {
                        planet.setPlanetType(Planet.PlanetType.TOXIC);
                    } else {
                        planet.setPlanetType(Planet.PlanetType.ICY);
                    }
                }
            } else {
                planet.setPlanetType(Planet.PlanetType.BARREN);
            }
        }
        return planet;
    }

    // Generate planet near star
    static Planet generatePlanet(String name, Planet.PlanetType planetType) {
        Planet planet = new Planet();
        planet.setName(name);
        planet.setPlanetType(planetType);
        generateEconomy(planet);
        return planet;
    }

    static void generateEconomy(Planet planet) {
        double rand2 = SpaceMath.getNextRandom();
        switch (planet.getPlanetType()) {
            case EARTH_LIKE:
                if (rand2 < 0.05) {
                    planet.setPlanetEconomy(new Economy(Economy.EconomyType.UNINHABITED));
                }
                else {
                    if (rand2 < 0.4) {
                        planet.setPlanetEconomy(new Economy(Economy.EconomyType.AGRICULTURE));
                    } else {
                        if (rand2 < 0.5) {
                            planet.setPlanetEconomy(new Economy(Economy.EconomyType.EXTRACTION));
                        } else {
                            if (rand2 < 0.7) {
                                planet.setPlanetEconomy(new Economy(Economy.EconomyType.INDUSTRIAL));
                            } else {
                                planet.setPlanetEconomy(new Economy(Economy.EconomyType.POSTINDUSTRIAL));
                            }
                        }
                    }
                }
                break;
            case BARREN:
                if (rand2 < 0.4) {
                    planet.setPlanetEconomy(new Economy(Economy.EconomyType.UNINHABITED));
                }
                else {
                    if (rand2 < 0.8) {
                        planet.setPlanetEconomy(new Economy(Economy.EconomyType.EXTRACTION));
                    } else {
                        if (rand2 < 0.98) {
                            planet.setPlanetEconomy(new Economy(Economy.EconomyType.INDUSTRIAL));
                        } else {
                            planet.setPlanetEconomy(new Economy(Economy.EconomyType.POSTINDUSTRIAL));
                        }
                    }
                }
                break;
            case ICY:
                if (rand2 < 0.6) {
                    planet.setPlanetEconomy(new Economy(Economy.EconomyType.UNINHABITED));
                }
                else {
                    if (rand2 < 0.9) {
                        planet.setPlanetEconomy(new Economy(Economy.EconomyType.EXTRACTION));
                    } else {
                        planet.setPlanetEconomy(new Economy(Economy.EconomyType.INDUSTRIAL));
                    }
                }
                break;
            case TOXIC:
                if (rand2 < 0.8) {
                    planet.setPlanetEconomy(new Economy(Economy.EconomyType.UNINHABITED));
                }
                else {
                    planet.setPlanetEconomy(new Economy(Economy.EconomyType.EXTRACTION));
                }
                break;
        }
    }

}
