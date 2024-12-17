package bst


/**
 * A node in a Binary Search Tree.
 */
class BstNode<T>(val `value`: T) {
    var left: BstNode<T>? = null
    var right: BstNode<T>? = null
}

