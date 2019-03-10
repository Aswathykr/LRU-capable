public class LFUNode {

    String data;
    LFUNodeList parent;

    public String getData() {
        return data;
    }

    public LFUNode(String data) {
        this.data = data;
    }

    public void setParent(LFUNodeList parent) {
        this.parent = parent;
    }

    public LFUNodeList getParent() {
        return parent;
    }
}
