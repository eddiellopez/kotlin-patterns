package bst

/**
 * A Binary Search Tree iterator using sequences.
 */
class BstIterator<T>(root: BstNode<T>?) {

    private val iterator: Iterator<T> = iterator {
        inorder(root)
    }

    private suspend fun <T> SequenceScope<T>.inorder(node: BstNode<T>?) {
        if (node != null) {
            inorder(node.left)
            yield(node.`value`)
            inorder(node.right)
        }
    }

    /**
     * @return the next smallest element.
     */
    fun next(): T {
        return iterator.next()
    }

    /**
     * @return whether we have a next element.
     */
    fun hasNext(): Boolean {
        return iterator.hasNext()
    }
}
