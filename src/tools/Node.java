package tools;

public class Node<T> {
	private T element;
	private Node<T> left;
	private Node<T> right;

	public Node(T element) {
		this.element = element;
		this.left = null;
		this.right = null;
	}

	public T getElement() {
		return element;
	}

	public Node<T> getLeft() {
		return left;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public void setElement(T element) {
		this.element = element;
	}
}
