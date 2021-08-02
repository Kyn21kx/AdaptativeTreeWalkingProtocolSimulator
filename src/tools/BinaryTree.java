package tools;
import java.util.Comparator;

public class BinaryTree<T> {
    public Node<T> top;
    private int size;
    private Comparator<T> comparator;

    public BinaryTree(Comparator<T> comparator) {
        top = new Node<>(null);
        this.comparator = comparator;
    }

    public boolean add(T element) {
        return addRecursive(top, element);
    }

    private boolean addRecursive(Node<T> node, T element) {
        if (element == null) return false;
        if (node.getElement() == null) {
            node.setElement(element);
            node.setLeft(new Node<>(null));
            node.setRight(new Node<>(null));
            size++;
            return true;
        }
        int pos = comparator.compare(node.getElement(), element);
        if (pos > 0)
            return addRecursive(node.getRight(), element);
        return addRecursive(node.getLeft(), element);
    }

    public int getSize() {
        return size;
    }

}
