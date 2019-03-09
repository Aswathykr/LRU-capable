import javax.lang.model.element.UnknownElementException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    Map<LFUNode, LFUNodeList> lookupMap;
    LFUNodeList headFrequencyListNode;

    public LFUCache() {
        lookupMap = new HashMap<LFUNode, LFUNodeList>();
        headFrequencyListNode = new LFUNodeList(1);
    }

    public void addNewLFUNode(LFUNode node)throws KeyAlreadyExistsException{
        if(lookupMap.get(node) != null){
            throw new KeyAlreadyExistsException();
        }
        if(headFrequencyListNode == null){
            headFrequencyListNode = new LFUNodeList(1);
        }
        if(headFrequencyListNode.getFrequency() == 1) {
            headFrequencyListNode.addToList(node);
        }else{
            LFUNodeList newHead = new LFUNodeList(1);
            newHead.setNextNode(headFrequencyListNode);
            headFrequencyListNode.setPrevNode(newHead);
            headFrequencyListNode = newHead;
        }
        lookupMap.put(node, headFrequencyListNode);
    }

    public int accessLFUNode(LFUNode node){
        LFUNodeList newFrequencyListNode = null;
        LFUNodeList currentFrequencyListNode = lookupMap.get(node);
        if(currentFrequencyListNode == null){
            throw new IllegalArgumentException("Node not Present");
        }

        LFUNodeList nextFrequencyListNode = currentFrequencyListNode.getNextNode();
        int newFrequency = currentFrequencyListNode.getFrequency() + 1;
        if(nextFrequencyListNode!= null && nextFrequencyListNode.getFrequency() == newFrequency){
            nextFrequencyListNode.addToList(node);
            newFrequencyListNode = nextFrequencyListNode;
        }else{
            newFrequencyListNode = new LFUNodeList(newFrequency);
            newFrequencyListNode.addToList(node);
            insertAfter(newFrequencyListNode, currentFrequencyListNode);
        }
        deleteLFUNodeFromParent(node, currentFrequencyListNode);
        lookupMap.put(node, newFrequencyListNode);
        return newFrequency;
    }

    private void deleteLFUNodeFromParent(LFUNode node, LFUNodeList currentFrequencyListNode ) {
        if(currentFrequencyListNode == null){
            throw new IllegalArgumentException("Node not Present");
        }
        currentFrequencyListNode.deleteFromList(node);
        if(currentFrequencyListNode.getListSize() == 0){
            delete(currentFrequencyListNode);
        }
    }

    public void deleteLFUNode(LFUNode node) {
        LFUNodeList currentFrequencyListNode = lookupMap.get(node);
        deleteLFUNodeFromParent(node, currentFrequencyListNode);
        lookupMap.remove(node);
    }

    private void delete(LFUNodeList currentFrequencyListNode) {
        if(currentFrequencyListNode == headFrequencyListNode) {
            headFrequencyListNode = currentFrequencyListNode.getNextNode();
            currentFrequencyListNode.setPrevNode(null);
        }else
        {
            LFUNodeList prevList = currentFrequencyListNode.getPrevNode();
            LFUNodeList nextList = currentFrequencyListNode.getNextNode();
            prevList.setNextNode(nextList);
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
        newFrequencyListNode.setPrevNode(newFrequencyListNode);
    }

    public int numberofNodesInHead(){
        int count = 0;
        if(headFrequencyListNode != null){
            count = headFrequencyListNode.getListSize();
        }
        return count;
    }

    public LFUNode popLFUNode(){
        LFUNode node = null;
        if(headFrequencyListNode != null){
            node = headFrequencyListNode.popFromList();
        }
        return node;
    }
}
