//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.TreeMap;

public class MedianFinderSol {
    TreeMap<Integer, Integer> leftTree = new TreeMap<>();
    TreeMap<Integer, Integer> rightTree = new TreeMap<>();
    int leftSize = 0;
    int rightSize = 0;

    public MedianFinderSol() {
    }

    public void insert(int x) throws Exception {
        if (this.leftSize == 0 && this.rightSize == 0) {
            this.addRight(x);
        } else {
            int temp;
            if (this.leftSize == this.rightSize) {
                if (x < this.rightTree.firstKey()) {
                    this.addLeft(x);
                    temp = this.removeLeft();
                    this.addRight(temp);
                } else {
                    this.addRight(x);
                }
            } else if (this.leftSize + 1 == this.rightSize) {
                if (x < this.rightTree.firstKey()) {
                    this.addLeft(x);
                } else {
                    this.addRight(x);
                    temp = this.removeRight();
                    this.addLeft(temp);
                }
            }

            assert this.leftSize + 1 == this.rightSize || this.leftSize == this.rightSize;

        }
    }

    public int getMedian() {
        int toReturn = -1;
        if (this.leftSize == this.rightSize) {
            toReturn = this.removeRight();
            int temp = this.removeLeft();
            this.addRight(temp);
        } else if (this.leftSize + 1 == this.rightSize) {
            toReturn = this.removeRight();
        }

        assert this.leftSize + 1 == this.rightSize || this.leftSize == this.rightSize;

        return toReturn;
    }

    private int removeLeft() {
        int last = this.leftTree.lastKey();
        int count = this.leftTree.get(last);
        if (count > 1) {
            this.leftTree.put(last, count - 1);
        } else {
            this.leftTree.remove(last);
        }

        --this.leftSize;
        return last;
    }

    private int removeRight() {
        int first = this.rightTree.firstKey();
        int count = this.rightTree.get(first);
        if (count > 1) {
            this.rightTree.put(first, count - 1);
        } else {
            this.rightTree.remove(first);
        }

        --this.rightSize;
        return first;
    }

    private void addLeft(int x) {
        int count = 1;
        if (this.leftTree.containsKey(x)) {
            count += this.leftTree.get(x);
        }

        this.leftTree.put(x, count);
        ++this.leftSize;
    }

    private void addRight(int x) {
        int count = 1;
        if (this.rightTree.containsKey(x)) {
            count += (Integer)this.rightTree.get(x);
        }

        this.rightTree.put(x, count);
        ++this.rightSize;
    }
}
