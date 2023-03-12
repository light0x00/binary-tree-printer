package io.github.light0x00.btp;

import io.github.light0x00.binarytreeprinter.BinaryTreePrinter;
import io.github.light0x00.binarytreeprinter.IPrintableBinaryTreeNode;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.Test;
import sun.jvm.hotspot.debugger.Page;

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
        BinaryTreeNode n2 = new BinaryTreeNode("2222222");
        BinaryTreeNode n3 = new BinaryTreeNode("333");

        n1.left = n2;
        n1.right = n3;

        BinaryTreeNode n4 = new BinaryTreeNode("4");
        BinaryTreeNode n5 = new BinaryTreeNode("5");
//        n2.left = n4;
        n2.right = n5;

//        BinaryTreeNode n6 = new BinaryTreeNode("6");
//        BinaryTreeNode n7 = new BinaryTreeNode("7");
//        n3.left = n6;
//        n3.right = n7;
//
//        BinaryTreeNode n8 = new BinaryTreeNode("8");
//        BinaryTreeNode n9 = new BinaryTreeNode("9");
//        n7.right = n8;
//        n7.right = n9;
//
//        BinaryTreeNode n10 = new BinaryTreeNode("10");
//        BinaryTreeNode n11 = new BinaryTreeNode("11");
////        n9.left = n10;
//        n5.left = n11;
        return n1;
    }

    @Test
    public void test0(){
        BinaryTreeNode tree = buildTree();
        BinaryTreePrinter.print(tree);
    }

    @Test
    public void test1(){
        int i = BinaryTreePrinter.measureMaxTextLen(buildTree());
    }

}
