public class LFUNode<T> {

    T data;
    LFUNodeList parent;

    public T getData() {
        return data;
    }

    public LFUNode(T data) {
        this.data = data;
    }

    public void setParent(LFUNodeList parent) {
        this.parent = parent;
    }

    public LFUNodeList getParent() {
        return parent;
    }
}
