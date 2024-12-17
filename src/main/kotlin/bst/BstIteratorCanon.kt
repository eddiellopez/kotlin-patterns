package bst

/**
 * The canonical solution to the Binary Search Tree iterator.
 */
class BstIteratorCanon(root: BstNode<Int>?) {

    private var stack: ArrayDeque<BstNode<Int>> = ArrayDeque()

    init {
        if (root != null) {

            // Add root
            stack.addLast(root)

            // Add all the consecutive "left" nodes.
            if (root.left != null) {
                addLeft(root.left!!)
            }
        }
    }

    /// Adds all the 'left' nodes from node 
    private fun addLeft(node: BstNode<Int>) {
        var n: BstNode<Int>? = node
        while (n != null) {
            stack.addLast(n)
            n = n.left
        }
    }

    fun next(): Int {
        val result = stack.removeLast()
        // Each time next is called, check if the node at top of the stack
        // has nodes to its right: add all to the left of that node (including it).

        if (result.right != null) {
            addLeft(result.right!!)
        }

        return result.`value`
    }

    fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

}
