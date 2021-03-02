package ui.mvvm.model;

import java.util.Arrays;

public class Board {
    public static final int NUMBER_OF_FIELDS = 9;
    private int[] fields = new int[NUMBER_OF_FIELDS];

    public Board() {
        clear();
    }

    public Board(Board that) {
        fields = Arrays.copyOf(that.fields, that.fields.length);
    }

    public int getField(int index) {
        return fields[index];
    }

    public int getField(int x, int y) {
        return fields[y * 3 + x];
    }

    public void setField(int index, int value) {
        fields[index] = value;
    }

    public void setField(int x, int y, int value) {
        fields[y * 3 + x] = value;
    }

    public static int indexToX(int index) {
        return index % 3;
    }

    public static int indexToY(int index) {
        return index / 3;
    }

    public static int xyToIndex(int x, int y) {
        return y * 3 + x;
    }


    @Override
    public String toString() {
        return Arrays.toString(fields);
    }

    public void clear() {
        Arrays.fill(fields, -1);
    }

    public int getNumberOfEmptyFields() {
        int cnt = 0;
        for (int n : fields) {
            if (n == -1)
                cnt++;
        }
        return cnt;
    }

}
