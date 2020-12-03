public class Tree<E> {

    public Node<E> root;
    public int height = 2;

    public Tree() {
        root = new Node<>();
        root.parent = null;
        createBrunch(root, height);
        System.out.println(height);
    }

    public void createBrunch(Node<E> node, int height) {
        node.createChildren();
        if (height > 0) {
            createBrunch(node.getChildren().get(0), --height);
        }
    }

}
