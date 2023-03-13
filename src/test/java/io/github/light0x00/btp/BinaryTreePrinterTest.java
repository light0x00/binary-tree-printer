package io.github.light0x00.btp;

import io.github.light0x00.binarytreeprinter.BinaryTreePrinter;
import io.github.light0x00.binarytreeprinter.IPrintableBinaryTreeNode;
import lombok.AllArgsConstructor;
import org.junit.Test;

/**
 * @author light
 * @since 2023/3/12
 */
public class BinaryTreePrinterTest {

    @AllArgsConstructor
    static class BinaryTreeNode implements IPrintableBinaryTreeNode {

        public BinaryTreeNode(Object val) {
            this.val = val;
        }

        private Object val;
        private BinaryTreeNode left;
        private BinaryTreeNode right;

        @Override
        public String text() {
            return val.toString();
        }

        @Override
        public BinaryTreeNode left() {
            return left;
        }

        @Override
        public BinaryTreeNode right() {
            return right;
        }
    }

    static BinaryTreeNode buildTree() {
        BinaryTreeNode n1 = new BinaryTreeNode("1");
        BinaryTreeNode n2 = new BinaryTreeNode("2");
        BinaryTreeNode n3 = new BinaryTreeNode("33");

        n1.left = n2;
        n1.right = n3;

        BinaryTreeNode n4 = new BinaryTreeNode("4");
        BinaryTreeNode n5 = new BinaryTreeNode("5");

        n2.left = n4;
        n2.right = n4;

        BinaryTreeNode n6 = new BinaryTreeNode("6");
        BinaryTreeNode n7 = new BinaryTreeNode("7");

        n3.left = n6;
        n3.right = n7;
        return n1;
    }

    @Test
    public void test0() {
        BinaryTreeNode tree = buildTree();
        BinaryTreePrinter.print(tree);
    }


}
