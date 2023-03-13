package io.github.light0x00.binarytreeprinter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Function;

/**
 * @author light
 * @since 2023/3/12
 */
public class BinaryTreePrinter {

    static final IPrintableBinaryTreeNode LAYER_MARK = new VirtualNode("LAYER_MARK");
    static final IPrintableBinaryTreeNode NULL_NODE = new VirtualNode("NULL_NODE");

    @AllArgsConstructor
    static class VirtualNode implements IPrintableBinaryTreeNode {

        String type;

        @Override
        public String text() {
            return null;
        }

        @Override
        public IPrintableBinaryTreeNode left() {
            return null;
        }

        @Override
        public IPrintableBinaryTreeNode right() {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("\n".length());
    }

    static String fill(char chr, int len) {
        char[] padding = new char[len];
        Arrays.fill(padding, ' ');
        return new String(padding);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    static class NodeDecorator implements IPrintableBinaryTreeNode {
        IPrintableBinaryTreeNode node;
        /**
         * 层级
         */
        int layer;
        /**
         * 层级序号
         */
        int seqInLayer;

        NodeDecorator nextSibling;

        Function<Integer, Integer> branchWidthCalculator;

        int maxNodeWidth;

        int layerTotal;

        public NodeDecorator(IPrintableBinaryTreeNode node, int layer, int seqInLayer) {
            this.node = node;
            this.layer = layer;
            this.seqInLayer = seqInLayer;
        }

        public NodeDecorator(IPrintableBinaryTreeNode node) {
            this.node = node;
        }

        @Override
        public String text() {
            if (node == NULL_NODE) {
                char[] txt = new char[maxNodeWidth];
                Arrays.fill(txt, ' ');
                return new String(txt);
            } else if (node.text() != null) {
                //为了排版 将长度不足的节点内容填充空格
                if (node.text().length() < maxNodeWidth) {
                    int diff = maxNodeWidth - node.text().length();
                    if ((seqInLayer & 1) == 0) {
                        /*
                        当前节点为右节点    没有子节点   右
                        当前节点为右节点    只有右节点   右
                        当前节点为右节点    只有左节点   中间
                        当前节点为右节点    有左右节点   中间

                        当前节点为左节点    没有子节点   左
                        当前节点为左节点    只有右节点   中间
                        当前节点为左节点    只有左节点   左
                        当前节点为左节点    有左右节点   中间
                        */
                        if (!hasLeft()) {
                            return fill(' ', diff) + node.text();
                        }
                    } else {
                        if (!hasRight())
                            return node.text() + fill(' ', diff);
                    }
                    return fill(' ', diff / 2) + node.text() + fill(' ', (diff & 1) == 0 ? diff / 2 : diff / 2 + 1);
                }
            }
            return node.text();
        }

        @Override
        public IPrintableBinaryTreeNode left() {
            return node.left();
        }

        @Override
        public IPrintableBinaryTreeNode right() {
            return node.right();
        }

        @Override
        public String desc() {
            return node.desc();
        }

        public boolean hasLeft() {
            return left() != null;
        }

        public boolean hasRight() {
            return right() != null;
        }

        public boolean isNullNode() {
            return node == NULL_NODE;
        }

        public boolean isLeaf() {
            return left() == null && right() == null;
        }

        public int childrenBranchWidth() {
            return branchWidthCalculator.apply(layer - 1);
        }

        public int branchWidth() {
            return branchWidthCalculator.apply(layer);
        }

        public boolean isHead() {
            return seqInLayer == 1;
        }

        public boolean isTail() {
            return nextSibling == null;
        }

        public int branchGapWidth() {
            return maxNodeWidth;
        }
    }

    static void printTieLine(NodeDecorator head) {
        boolean hasDesc = false;
        for (NodeDecorator cur = head; cur != null; cur = cur.nextSibling) {
            int branchWidth = cur.branchWidth();
            if (cur.isNullNode()) {
                print(" ", branchWidth * 2 + cur.maxNodeWidth);
            } else {
                print(" ", branchWidth);
                printC(cur.text());
                print(" ", branchWidth);
            }
            if (!cur.isTail()) {
                print(" ", cur.branchGapWidth());
            }
            if (cur.desc() != null) {
                hasDesc = true;
            }
        }
        System.out.println();
        if (hasDesc) {
            shit(head);
        }
        if (head.layer == 1) {
            return;
        }
        for (int i = 0; i < head.branchWidth() - head.childrenBranchWidth(); i++) {
            for (NodeDecorator cur = head; cur != null; cur = cur.nextSibling) {
                print(" ", cur.branchWidth() - i - 1);
                print(cur.hasLeft() ? "/" : " ");
                print(" ", cur.maxNodeWidth + 2 * i);
                print(cur.hasRight() ? "\\" : " ");
                print(" ", cur.branchWidth() - i - 1);
                if (!cur.isTail()) {
                    print(" ", cur.branchGapWidth());
                }
            }
            System.out.println();
        }
    }

    private static void shit(NodeDecorator head) {
        for (NodeDecorator cur = head; cur != null; cur = cur.nextSibling) {
            int branchWidth = cur.branchWidth();
            String desc = cur.desc() == null ? "" : cur.desc();

            int maxWidth = branchWidth * 2 + cur.maxNodeWidth;

            if (desc.length() > maxWidth) {
//                print(desc.substring(0, maxWidth));
                print(desc);
            } else {
                int diff = maxWidth - desc.length();
                int leftPadding = diff / 2;
                int rightPadding = diff - leftPadding;
                print(" ", leftPadding);
                print(desc);
                print(" ", rightPadding);
            }
            if (!cur.isTail()) {
                print(" ", cur.branchGapWidth());
            }
        }
        System.out.println();
    }

    private static void print(String chr) {
        print(chr, 1);
    }

    private static void print(String chr, int num) {
        for (int i = 0; i < num; i++) {
            System.out.print(chr);
        }
    }

    private static void printC(String text) {
        printC(text, 1);
    }

    private static void printC(String text, int num) {
        print("\033[0;107m\033[1;34m" + text + "\u001B[0m", num);
    }

    static class NodeDecoratorFactory {

        int layerTotal;
        int maxNodeWidth;
        Function<Integer, Integer> branchWidthCalculator;

        public NodeDecoratorFactory(int layerTotal, int maxNodeWidth) {
            this.layerTotal = layerTotal;
            this.maxNodeWidth = maxNodeWidth;
            branchWidthCalculator = nodeBranchWidthCalculator(layerTotal, maxNodeWidth);
        }

        NodeDecorator newNodeDecorator(IPrintableBinaryTreeNode node, int layer, int seqInLayer) {
            NodeDecorator nodeDecorator = new NodeDecorator(node, layer, seqInLayer);
            nodeDecorator.branchWidthCalculator = branchWidthCalculator;
            nodeDecorator.maxNodeWidth = maxNodeWidth;
            nodeDecorator.layerTotal = layerTotal;
            return nodeDecorator;
        }
    }

    public static void print(IPrintableBinaryTreeNode root) {
        //2. 确定最大字符长度
        int maxNodeTextLength = measureMaxTextLen(root);
        print(root, maxNodeTextLength);
    }

    public static void print(IPrintableBinaryTreeNode root, int maxNodeTextLength) {
        //1. 测量树层数
        int layerTotal = measureTreeLayerNum(root);

        NodeDecoratorFactory nodeDecoratorFactory = new NodeDecoratorFactory(layerTotal, maxNodeTextLength);

        LinkedList<IPrintableBinaryTreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        queue.addLast(LAYER_MARK);

        int layer = layerTotal;
        int layerSeq = 1;
        NodeDecorator head = null;
        int childrenNotNullCount = 0;
        while (!queue.isEmpty()) {
            IPrintableBinaryTreeNode node = queue.removeFirst();
            if (node == LAYER_MARK) {
                printTieLine(head);
                if (queue.isEmpty()) { //如果层级节点后面没有追加新的节点，说明树的最后一层已经遍历完。
                    break;
                }
                if (childrenNotNullCount <= 0) { //如果一个非空节点都没有，那么说明最后一层已经遍历完。
                    break;
                }

                queue.addLast(LAYER_MARK);
                --layer;
                head = null;
                layerSeq = (int) Math.pow(2, (layerTotal - layer)); //可用动态规划减少计算
                childrenNotNullCount = 0;
                continue;
            }

            IPrintableBinaryTreeNode right = node.right() != null ? node.right() : NULL_NODE;
            queue.addLast(right);
            IPrintableBinaryTreeNode left = node.left() != null ? node.left() : NULL_NODE;
            queue.addLast(left);

            /*生成同层级节点链表*/
            if (head == null) {
                head = nodeDecoratorFactory.newNodeDecorator(node, layer, layerSeq);
            } else {
                NodeDecorator cur = nodeDecoratorFactory.newNodeDecorator(node, layer, --layerSeq);
                cur.nextSibling = head;
                head = cur;
            }

            if (right != NULL_NODE)
                childrenNotNullCount++;
            if (left != NULL_NODE)
                childrenNotNullCount++;
        }
    }

    /**
     * 寻找树中字符长度最大值
     *
     * @param node
     * @return
     */
    public static int measureMaxTextLen(IPrintableBinaryTreeNode node) {
        int maxLen = len(node.text());
        if (node.left() != null) {
            maxLen = Math.max(measureMaxTextLen(node.left()), maxLen);
        }
        if (node.right() != null) {
            maxLen = Math.max(measureMaxTextLen(node.right()), maxLen);
        }
        return maxLen;
    }

    private static int len(String text) {
        return text == null ? 0 : text.length();
    }

    /**
     * 测量树高
     */
    public static int measureTreeLayerNum(IPrintableBinaryTreeNode node) {
        int l = 0, r = 0;
        if (node.left() != null) {
            l = measureTreeLayerNum(node.left());
        }
        if (node.right() != null) {
            r = measureTreeLayerNum(node.right());
        }
        return Math.max(l, r) + 1;
    }

    /**
     * 递推算出二叉树的指定层层节点的单边（左或右）所需要的宽度
     * <p>
     * R1    0
     * R2    R1*2+最长节点字符数
     * R3    R2*2+最长节点字符数
     * R4    R3*最长节点字符数+最长节点字符数
     * RN    R4*2+最长节点字符数
     * <p>
     */
    static Function<Integer, Integer> nodeBranchWidthCalculator(int layerTotal, int nodeWidth) {
        HashMap<Integer, Integer> cache = new HashMap<>();
        cache.put(1, 0);
        int r = 0;
        for (int i = 2; i <= layerTotal; i++) {
            r = r * 2 + nodeWidth;
            cache.put(i, r);
        }
        return cache::get;
    }


}
