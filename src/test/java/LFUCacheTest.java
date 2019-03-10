import org.junit.Assert;
import org.junit.Test;

import javax.management.openmbean.KeyAlreadyExistsException;

public class LFUCacheTest {
    LFUCache lfuCache = new LFUCache();

    @Test
    public void addNewNodeTest(){
        String data = "aswathy";
        lfuCache.addNewLFUNode(data);

        int newFrequency = lfuCache.accessLFUNode(data);
        Assert.assertEquals(newFrequency, 2);
    }

    @Test(expected = KeyAlreadyExistsException.class)
    public void addNewNodeAlreadyExistTest(){
        String data = "data";
        lfuCache.addNewLFUNode(data);
        lfuCache.addNewLFUNode(data);
    }

    @Test
    public void nodelookupTest(){
        String data = "nodelookupTest";
        lfuCache.addNewLFUNode(data);

        int newFrequency = lfuCache.accessLFUNode(data);
        Assert.assertEquals(newFrequency, 2);
        newFrequency = lfuCache.accessLFUNode(data);
        Assert.assertEquals(newFrequency, 3);
        newFrequency = lfuCache.accessLFUNode(data);
        Assert.assertEquals(newFrequency, 4);

    }
    @Test(expected = IllegalArgumentException.class)
    public void nodelookupNotExistTest(){

        String data = "nodelookupNotExistTest";
        int newFrequency = lfuCache.accessLFUNode(data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nodeDeleteFromHeadTest(){
        String data = "nodeDeleteFromHeadTest";
        lfuCache.addNewLFUNode(data);

        lfuCache.deleteLFUNode(data);

        int newFrequency = lfuCache.accessLFUNode(data);

    }

    @Test
    public void lastNodeDeleteFromHeadTest(){
        LFUCache cache = new LFUCache();
        String data1 = "node1";
        String data2 = "node2";
        String data3 = "node3";
        String data4 = "node4";

        cache.addNewLFUNode(data1);
        cache.addNewLFUNode(data2);
        cache.addNewLFUNode(data3);
        cache.addNewLFUNode(data4);
        Assert.assertEquals(cache.numberofNodesInHead(), 4);

        cache.deleteLFUNode(data1);
        Assert.assertEquals(cache.numberofNodesInHead(), 3);

        cache.deleteLFUNode(data2);
        Assert.assertEquals(cache.numberofNodesInHead(), 2);

        cache.deleteLFUNode(data3);
        Assert.assertEquals(cache.numberofNodesInHead(), 1);

        cache.deleteLFUNode(data4);
        Assert.assertEquals(cache.numberofNodesInHead(), 0);


    }
    @Test
    public void insertionToNewFrequencyListTest(){
        LFUCache cache = new LFUCache();
        String data1 = "data1";
        String data2 = "data2";
        String data3 = "data3";
        String data4 = "data4";

        cache.addNewLFUNode(data1);
        cache.addNewLFUNode(data2);

        int frequency = cache.accessLFUNode(data1);
        Assert.assertEquals(frequency, 2);

        cache.addNewLFUNode(data3);
        frequency = cache.accessLFUNode(data1);
        Assert.assertEquals(frequency, 3);

        //insert between 1 and 3 frquency
        frequency = cache.accessLFUNode(data2);
        Assert.assertEquals(frequency, 2);


    }

    @Test(expected = IllegalArgumentException.class)
    public void nodeDeleteFromMiddleTest(){
        LFUCache cache = new LFUCache();
        String data1 = "data1";
        String data2 = "data2";
        String data3 = "data3";
        String data4 = "data4";

        cache.addNewLFUNode(data1);
        cache.addNewLFUNode(data2);

        cache.accessLFUNode(data1);

        cache.addNewLFUNode(data3);
        cache.accessLFUNode(data2);

        cache.accessLFUNode(data1);
        cache.accessLFUNode(data2);
        cache.accessLFUNode(data1);

        cache.addNewLFUNode(data4);
        cache.accessLFUNode(data3);

        cache.deleteLFUNode(data2);

        cache.accessLFUNode(data2); // Should throw an exception

    }

    @Test(expected = IllegalArgumentException.class)
    public void nodeDeleteNotExistTest(){
        LFUCache cache = new LFUCache();
        String data1 = "node1";
        String data2 = "node2";

        cache.addNewLFUNode(data1);

        cache.deleteLFUNode(data2); // Should throw an exception

    }
    @Test
    public void addNewNodeToNewHeadTest(){
        LFUCache cache = new LFUCache();
        String data1 = "data1";
        String data2 = "data2";

        cache.addNewLFUNode(data1);
        cache.accessLFUNode(data1);
        cache.accessLFUNode(data1);

        cache.addNewLFUNode(data2);


    }
    @Test
    public void addNewNodeHeadNullTest(){
        LFUCache cache = new LFUCache();
        String data1 = "data1";
        String data2 = "data2";

        cache.addNewLFUNode(data1);
        cache.deleteLFUNode(data1);

        cache.addNewLFUNode(data2);

    }

    @Test
    public void popLFUNodeTest(){
        LFUCache cache = new LFUCache();
        String data1 = "node1";
        String data2 = "node2";

        cache.addNewLFUNode(data1);
        cache.addNewLFUNode(data2);

        cache.accessLFUNode(data1);

        String retrievedData = cache.popLFUNode();
        Assert.assertEquals(retrievedData, data2);
    }

}
