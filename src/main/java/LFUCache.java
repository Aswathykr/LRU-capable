import javax.lang.model.element.UnknownElementException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    Map<String, LFUNode> lookupMap;
    LFUNodeList headFrequencyListNode;

    public LFUCache() {
        lookupMap = new HashMap<String, LFUNode>();
        headFrequencyListNode = new LFUNodeList(1);
    }

    public void addNewLFUNode(String data)throws KeyAlreadyExistsException{
        LFUNode node = new LFUNode(data);
        if(lookupMap.get(node.getData()) != null){
            throw new KeyAlreadyExistsException();
        }
        if(headFrequencyListNode == null){
            headFrequencyListNode = new LFUNodeList(1);
        }
        else if(headFrequencyListNode.getFrequency() != 1) {
            LFUNodeList newHead = new LFUNodeList(1);
            newHead.setNextNode(headFrequencyListNode);
            headFrequencyListNode.setPrevNode(newHead);
            headFrequencyListNode = newHead;
        }

        headFrequencyListNode.addToList(node);
        node.parent = headFrequencyListNode;
        lookupMap.put(node.getData(), node);
    }

    public int accessLFUNode(String data){
        LFUNodeList newFrequencyListNode = null;
        LFUNode node = lookupMap.get(data);
        if(node == null){
            throw new IllegalArgumentException("Node not Present");
        }

        LFUNodeList currentFrequencyListNode = node.getParent();
        LFUNodeList nextFrequencyListNode = currentFrequencyListNode.getNextNode();

        int newFrequency = currentFrequencyListNode.getFrequency() + 1;

        if(nextFrequencyListNode != null && nextFrequencyListNode.getFrequency() == newFrequency){
            nextFrequencyListNode.addToList(node);
            node.setParent(nextFrequencyListNode);
            newFrequencyListNode = nextFrequencyListNode;
        }else{
            newFrequencyListNode = new LFUNodeList(newFrequency);
            newFrequencyListNode.addToList(node);
            insertAfter(newFrequencyListNode, currentFrequencyListNode);
            node.setParent(newFrequencyListNode);
        }
        deleteLFUNodeFromParent(node, currentFrequencyListNode);
        lookupMap.put(node.getData(), node);
        return newFrequency;
    }

    private void deleteLFUNodeFromParent(LFUNode node, LFUNodeList currentFrequencyListNode ) {
        currentFrequencyListNode.deleteFromList(node);
        if(currentFrequencyListNode.getListSize() == 0){
            delete(currentFrequencyListNode);
        }
    }

    public void deleteLFUNode(String data) {

        LFUNode node = lookupMap.get(data);
        if(node == null){
            throw new IllegalArgumentException("Node not Present");
        }
        deleteLFUNodeFromParent(node, node.getParent());
        lookupMap.remove(node.getData());
    }

    private void delete(LFUNodeList currentFrequencyListNode) {
        if(currentFrequencyListNode == headFrequencyListNode) {
            headFrequencyListNode = currentFrequencyListNode.getNextNode();
            currentFrequencyListNode.setPrevNode(null);
        }else {
            LFUNodeList prevList = currentFrequencyListNode.getPrevNode();
            LFUNodeList nextList = currentFrequencyListNode.getNextNode();
            if(prevList != null) {
                prevList.setNextNode(nextList);
            }
            if(nextList != null) {
                nextList.setPrevNode(prevList);
            }
        }
    }

    private void insertAfter(LFUNodeList newFrequencyListNode, LFUNodeList frequencyListNode) {
        LFUNodeList nextFrequencyListNode = frequencyListNode.getNextNode();
        frequencyListNode.setNextNode(newFrequencyListNode);
        newFrequencyListNode.setNextNode(nextFrequencyListNode);
        newFrequencyListNode.setPrevNode(frequencyListNode);
        if(nextFrequencyListNode != null) {
            nextFrequencyListNode.setPrevNode(newFrequencyListNode);
        }
    }

    public int numberofNodesInHead(){
        int count = 0;
        if(headFrequencyListNode != null){
            count = headFrequencyListNode.getListSize();
        }
        return count;
    }

    public String popLFUNode(){
        LFUNode node = null;
        if(headFrequencyListNode != null){
            node = headFrequencyListNode.popFromList();
        }
        return node.getData();
    }
}
