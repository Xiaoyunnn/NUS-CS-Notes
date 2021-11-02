//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.junit.Assert;
import org.junit.Test;

public class MedianFinderTest {
    public MedianFinderTest() {
    }

    @Test
    public void insert5Times() throws Exception {
        MedianFinder mf = new MedianFinder();
        mf.insert(1);
        mf.insert(2);
        mf.insert(3);
        mf.insert(4);
        mf.insert(5);
    }

    @Test
    public void getSingleMedian() throws Exception {
        MedianFinder mf = new MedianFinder();
        mf.insert(1);
        Assert.assertEquals(1L, (long)mf.getMedian());
    }

    @Test
    public void getMedian1() throws Exception {
        MedianFinder mf = new MedianFinder();
        mf.insert(1);
        mf.insert(2);
        mf.insert(3);
        mf.insert(4);
        Assert.assertEquals(3L, (long)mf.getMedian());
        Assert.assertEquals(2L, (long)mf.getMedian());
        Assert.assertEquals(4L, (long)mf.getMedian());
        Assert.assertEquals(1L, (long)mf.getMedian());
    }

    @Test
    public void getMedian2() throws Exception {
        MedianFinder mf = new MedianFinder();
        mf.insert(1);
        Assert.assertEquals(1L, (long)mf.getMedian());
        mf.insert(2);
        Assert.assertEquals(2L, (long)mf.getMedian());
        mf.insert(3);
        Assert.assertEquals(3L, (long)mf.getMedian());
        mf.insert(4);
        Assert.assertEquals(4L, (long)mf.getMedian());
    }

    @Test
    public void getMedian3() throws Exception {
        MedianFinder mf = new MedianFinder();
        mf.insert(1);
        mf.insert(4);
        mf.insert(5);
        mf.insert(6);
        mf.insert(1);
        mf.insert(1);
        mf.insert(1);
        mf.insert(1);
        mf.insert(1);
        mf.insert(1);
        mf.getMedian();
        mf.getMedian();
        mf.getMedian();
        mf.getMedian();
        mf.getMedian();
        mf.getMedian();
        mf.getMedian();
        mf.getMedian();
        mf.getMedian();
    }
}
