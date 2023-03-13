![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.light0x00/binary-tree-printer/badge.svg)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


1. add dependency

```xml
<dependency>
    <groupId>io.github.light0x00</groupId>
    <artifactId>binary-tree-printer</artifactId>
    <version>0.0.1</version>
</dependency>
```

2. implement  `IPrintableBinaryTreeNode`

```java

@AllArgsConstructor
class BinaryTreeNode implements IPrintableBinaryTreeNode {
    //...   
}
```

3. construct your tree structure then print it

```java
BinaryTreeNode tree=buildTree();
BinaryTreePrinter.print(tree);
```