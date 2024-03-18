class BST<K : Comparable<K>, V> {

    var root: Node? = null

    inner class Node(
            var key: K,
            var value: V?,
            var number: Int,
            var left: Node?,
            var right: Node?,
    ) {
        constructor(key: K, value: V?, number: Int) : this(key, value, number, null, null) {}
    }

    fun size(): Int {
        return size(root)
    }

    fun size(node: Node?): Int {
        return node?.number ?: 0
    }

    fun get(key: K): V? {
        return get(root, key)
    }

    fun get(node: Node?, key: K): V? {
        if (null == node) return null
        val cmp = key.compareTo(node.key)
        if (cmp < 0) {
            return get(node.left, key)
        }
        if (cmp > 0) {
            return get(node.right, key)
        }
        return node.value
    }

    fun put(key: K, value: V) {
        root = put(root, key, value)
    }

    fun put(node: Node?, key: K, value: V?): Node {
        node?.let {
            val cmp = key.compareTo(node.key)
            if (cmp < 0) {
                node.left = put(node.left, key, value)
            } else if (cmp > 0) {
                node.right = put(node.right, key, value)
            } else {
                node.value = value
            }
            node.number = size(node.left) + size(node.right) + 1
            return node
        } ?: return BST<K, V>().Node(key, value, 0)
    }

    fun preOrderRecursive() {
        preOrderRecursive(root)
    }

    fun preOrderRecursive(node: Node?) {
        node?.let {
            println("[${it.key} : ${it.value}]")
            preOrderRecursive(node.left)
            preOrderRecursive(node.right)
        }
    }
}

fun main() {
    var bst = BST<Char, String>();
    bst.put('F', "==value==")
    bst.put('D', "==value==")
    bst.put('G', "==value==")
    bst.put('B', "==value==")
    bst.put('E', "==value==")
    bst.put('I', "==value==")
    bst.preOrderRecursive()
    println("Tree nodes ${bst.size()}")
}