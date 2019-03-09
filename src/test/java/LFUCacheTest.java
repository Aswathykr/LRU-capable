import org.junit.Assert;
import org.junit.Test;

import javax.management.openmbean.KeyAlreadyExistsException;

public class LFUCacheTest {
    LFUCache lfuCache = new LFUCache();

    @Test
    public void addNewNodeTest(){
        LFUNode node = new LFUNode("aswathy");
        lfuCache.addNewLFUNode(node);

        int newFrequency = lfuCache.accessLFUNode(node);
        Assert.assertEquals(newFrequency, 2);
    }

    @Test(expected = KeyAlreadyExistsException.class)
    public void addNewNodeAlreadyExistTest(){
        LFUNode node = new LFUNode("data");
        lfuCache.addNewLFUNode(node);
        lfuCache.addNewLFUNode(node);
    }

    @Test
    public void nodelookupTest(){
        LFUNode node = new LFUNode("nodelookupTest");
        lfuCache.addNewLFUNode(node);

        int newFrequency = lfuCache.accessLFUNode(node);
        Assert.assertEquals(newFrequency, 2);
        newFrequency = lfuCache.accessLFUNode(node);
        Assert.assertEquals(newFrequency, 3);
        newFrequency = lfuCache.accessLFUNode(node);
        Assert.assertEquals(newFrequency, 4);

    }
    @Test(expected = IllegalArgumentException.class)
    public void nodelookupNotExistTest(){

        LFUNode node = new LFUNode("nodelookupNotExistTest");
        int newFrequency = lfuCache.accessLFUNode(node);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nodeDeleteFromHeadTest(){
        LFUNode node = new LFUNode("nodeDeleteFromHeadTest");
        lfuCache.addNewLFUNode(node);

        lfuCache.deleteLFUNode(node);

        int newFrequency = lfuCache.accessLFUNode(node);

    }

    @Test
    public void lastNodeDeleteFromHeadTest(){
        LFUCache cache = new LFUCache();
        LFUNode node1 = new LFUNode("node1");
        LFUNode node2 = new LFUNode("node2");
        LFUNode node3 = new LFUNode("node3");
        LFUNode node4 = new LFUNode("node4");

        cache.addNewLFUNode(node1);
        cache.addNewLFUNode(node2);
        cache.addNewLFUNode(node3);
        cache.addNewLFUNode(node4);
        Assert.assertEquals(cache.numberofNodesInHead(), 4);

        cache.deleteLFUNode(node1);
        Assert.assertEquals(cache.numberofNodesInHead(), 3);

        cache.deleteLFUNode(node2);
        Assert.assertEquals(cache.numberofNodesInHead(), 2);

        cache.deleteLFUNode(node3);
        Assert.assertEquals(cache.numberofNodesInHead(), 1);

        cache.deleteLFUNode(node4);
        Assert.assertEquals(cache.numberofNodesInHead(), 0);


    }

    @Test(expected = IllegalArgumentException.class)
    public void nodeDeletefromMiddleTest(){
        LFUCache cache = new LFUCache();
        LFUNode node1 = new LFUNode("node1");
        LFUNode node2 = new LFUNode("node2");
        LFUNode node3 = new LFUNode("node3");
        LFUNode node4 = new LFUNode("node4");

        cache.addNewLFUNode(node1);
        cache.addNewLFUNode(node2);

        cache.accessLFUNode(node1);
        cache.accessLFUNode(node1);

        cache.addNewLFUNode(node3);
        cache.accessLFUNode(node2);

        cache.addNewLFUNode(node4);

        cache.deleteLFUNode(node2);

        cache.accessLFUNode(node2); // Should throw an exception

    }

    @Test(expected = IllegalArgumentException.class)
    public void nodeDeleteNotExistTest(){
        LFUCache cache = new LFUCache();
        LFUNode node1 = new LFUNode("node1");
        LFUNode node2 = new LFUNode("node2");

        cache.addNewLFUNode(node1);

        cache.deleteLFUNode(node2); // Should throw an exception

    }
    @Test
    public void addNewNodeToNewHeadTest(){
        LFUCache cache = new LFUCache();
        LFUNode node1 = new LFUNode("node1");
        LFUNode node2 = new LFUNode("node2");

        cache.addNewLFUNode(node1);
        cache.accessLFUNode(node1);
        cache.accessLFUNode(node1);

        cache.addNewLFUNode(node2);


    }
    @Test
    public void addNewNodeHeadNullTest(){
        LFUCache cache = new LFUCache();
        LFUNode node1 = new LFUNode("node1");
        LFUNode node2 = new LFUNode("node2");

        cache.addNewLFUNode(node1);
        cache.deleteLFUNode(node1);

        cache.addNewLFUNode(node2);

    }

    @Test
    public void popLFUNodeTest(){
        LFUCache cache = new LFUCache();
        LFUNode node1 = new LFUNode("node1");
        LFUNode node2 = new LFUNode("node2");

        cache.addNewLFUNode(node1);
        cache.addNewLFUNode(node2);

        cache.accessLFUNode(node1);

        LFUNode lfuNode = cache.popLFUNode();
        Assert.assertEquals(lfuNode, node2);
    }

}
