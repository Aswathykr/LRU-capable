import java.util.HashSet;
import java.util.Set;

public class LFUNodeList<T> {
    int frequency;
    Set<LFUNode<T>> frequencyNodeList;
    LFUNodeList prevNode;
    LFUNodeList nextNode;

    public LFUNodeList getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(LFUNodeList prevNode) {
        this.prevNode = prevNode;
    }

    public LFUNodeList getNextNode() {
        return nextNode;
    }

    public void setNextNode(LFUNodeList nextNode) {
        this.nextNode = nextNode;
    }

    public LFUNodeList(int frequency) {
        this.frequency = frequency;
        frequencyNodeList = new HashSet<>();
    }

    public void addToList(LFUNode<T> node) {
        node.setParent(this);
        frequencyNodeList.add(node);
    }

    public int getFrequency() {
        return frequency;
    }

    public void deleteFromList(LFUNode<T> node) {
        frequencyNodeList.remove(node);
    }

    public int getListSize() {
        return frequencyNodeList.size();
    }

    public LFUNode<T> popFromList() {
        LFUNode<T> node = null;
        if(frequencyNodeList.size() != 0) {
            node = frequencyNodeList.iterator().next();
            frequencyNodeList.remove(node);
        }
        return node;
    }
}

