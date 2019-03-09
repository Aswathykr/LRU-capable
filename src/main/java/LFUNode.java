public class LFUNode {
    String data;
    LFUNodeList parent;

    public LFUNode(String data) {
        this.data = data;
    }

    public void setParent(LFUNodeList parent) {
        this.parent = parent;
    }
}
