package utils;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double range(Position other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Position o) {
       if (y < o.y){
           return -1;
       }
       if (y > o.y) {
           return 1;
       }
       if (x < o.x) {
           return -1;
       }
       if (x > o.x) {
           return 1;
       }

       return 0;
       }
    }

