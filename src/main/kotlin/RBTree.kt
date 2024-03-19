enum class Color {
    RED,
    BLACK
}

class RBTree<K : Comparable<K>, V> {
    inner class Node<K : Comparable<K>, V>(
        var key: K,
        var value: V,
        var left: Node<K, V>?,
        var right: Node<K, V>?,
        var num: Int,
        var color: Color
    ) {

        constructor(key: K, value: V, num: Int, color: Color)
                : this(key, value, null, null, num, color)

        fun from(source: Node<K, V>) {
            this.key = source.key
            this.value = source.value
            this.left = source.left
            this.right = source.right
            this.num = source.num
        }

    }

    private var root: Node<K, V>? = null;

    fun put(key: K, value: V) {
        root = put(root, key, value);
        root?.color = Color.BLACK
    }

    fun put(node: Node<K, V>?, key: K, value: V): Node<K, V> {
        if (null == node) return Node(key, value, 1, Color.RED)
        val cmp = key.compareTo(node.key)
        if (cmp < 0) {
            node.left = put(node.left, key, value)
        } else if (cmp > 0) {
            node.right = put(node.right, key, value)
        } else {
            node.value = value
        }

        var newNode: Node<K, V>? = null
        if (isRed(node.right) && !isRed(node.left)) {
            newNode = rotateLeft(node)
        }
        if (isRed(node.left) && isRed(node.left?.left)) {
            newNode = rotateRight(node)
        }
        newNode = newNode ?: node
        if (isRed(newNode.left) && isRed(newNode.right)) {
            flipColors(newNode)
        }
        return newNode
    }

    private fun isRed(node: Node<K, V>?): Boolean {
        return node?.color?.equals(Color.RED) ?: false
    }

    fun size(node: Node<K, V>?): Int {
        return node?.num ?: 0
    }

    fun rotateLeft(node: Node<K, V>): Node<K, V> {
        val rh = node.right!!
        node.right = rh.left
        rh.left = node
        rh.color = node.color
        node.color = Color.RED
        rh.num = node.num
        node.num = 1 + size(rh.left) + size(rh.right)
        return rh
    }

    fun rotateRight(node: Node<K, V>): Node<K, V> {
        val rl = node.left!!
        node.left = rl.right
        rl.right = node
        rl.color = node.color
        node.color = Color.RED
        node.num = 1 + size(rl.left) + size(rl.right)
        return rl
    }

    fun flipColors(node: Node<K, V>) {
        node.color = if (node.color == Color.RED) Color.BLACK else Color.RED
        node.left?.color = if (node.left?.color == Color.RED) Color.BLACK else Color.RED
        node.right?.color = if (node.right?.color == Color.RED) Color.BLACK else Color.RED
    }

    fun preOrderRecursive() {
        preOrderRecursive(root)
    }

    fun preOrderRecursive(node: Node<K, V>?) {
        node?.let {
            println("[${it.color} => ${it.key} : ${it.value}]")
            preOrderRecursive(node.left)
            preOrderRecursive(node.right)
        }
    }

}


fun main() {
    val rbTree = RBTree<Char, String>();
    println("Start insert")
    rbTree.put('S', "==value==");
    rbTree.put('E', "==value==");
    rbTree.put('A', "==value==");
    rbTree.put('R', "==value==");
    rbTree.put('C', "==value==");
    rbTree.put('H', "==value==");
    rbTree.preOrderRecursive()

}