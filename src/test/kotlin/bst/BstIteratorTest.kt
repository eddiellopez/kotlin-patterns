package bst

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BstIteratorTest {

    @Test
    fun emptyTree() {
        // Given the tree:
        val root: BstNode<Int>? = null

        // And the iterator:
        val iterator = BstIterator(root = root)

        // We expect it is empty.
        assertThat(iterator.hasNext(), Matchers.`is`(false))
        assertThrows<NoSuchElementException> {
            iterator.next()
        }
    }

    @Test
    fun smallTree() {
        // Given the tree:
        val root: BstNode<Int> = BstNode(2)
        root.left = BstNode(1)
        root.right = BstNode(3)

        // And the iterator:
        val iterator = BstIterator(root = root)

        // When expect it produces three elements
        assertThat(iterator.hasNext(), Matchers.`is`(true))
        assertThat(iterator.next(), Matchers.`is`(1))
        assertThat(iterator.hasNext(), Matchers.`is`(true))
        assertThat(iterator.next(), Matchers.`is`(2))
        assertThat(iterator.hasNext(), Matchers.`is`(true))
        assertThat(iterator.next(), Matchers.`is`(3))
        assertThat(iterator.hasNext(), Matchers.`is`(false))
    }
}