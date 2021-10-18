package binaryTree;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;


public class BiTree<T> implements Cloneable {
	private BiTreeNode<T> root;

	private static class BiTreeNode<T> {
		T data;
		BiTreeNode<T> lchild;
		BiTreeNode<T> rchild;

		BiTreeNode(T data, BiTreeNode<T> left, BiTreeNode<T> right) {
			this.data = data;
			lchild = left;
			rchild = right;
		}
	}

	public BiTree() {// 建立空树

	}

	// 在左子树、右子树上加上根结点，得到新的二叉树
	public BiTree(T data, BiTree<T> left, BiTree<T> right) {
		root = new BiTreeNode<>(data, left.root, right.root);
	}

	@SuppressWarnings("unchecked")
	public BiTree(String preString, String inString) {
		if (preString.length() == 0) {//如果不特殊处理，makeBitree将崩溃
			root = null;
		} else {
			root = (BiTreeNode<T>) makeBitree(preString, inString);
			// root = (BiTreeNode<T>)makeBitree(preString, inString,0,0,preString.length());
		}
	}

	// 常见的System.out.print的先序、中序和后序遍历
	public void preRootTraversal() {
		preRootTraversal(root);
	}

	private void preRootTraversal(BiTreeNode<T> t) {
		if (t == null)
			return;

		// 处理结点t
		System.out.println(t.data);

		preRootTraversal(t.lchild);
		preRootTraversal(t.rchild);
	}

	public void inRootTraversal() {
		inRootTraversal(root);
	}

	private void inRootTraversal(BiTreeNode<T> t) {
		if (t == null)
			return;
		inRootTraversal(t.lchild);

		// 处理结点t
		System.out.println(t.data);

		inRootTraversal(t.rchild);
	}

	public void postRootTraversal() {
		postRootTraversal(root);
	}

	private void postRootTraversal(BiTreeNode<T> t) {
		if (t == null)
			return;
		postRootTraversal(t.lchild);

		postRootTraversal(t.rchild);

		// 处理结点t
		System.out.println(t.data);
	}

	public void levelTraverse() {
		if (root == null)
			return;
		Deque<BiTreeNode<T>> queue = new ArrayDeque<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			BiTreeNode<T> t = queue.poll();

			// 处理结点t
			System.out.println(t.data);

			if (t.lchild != null)
				queue.offer(t.lchild);
			if (t.rchild != null)
				queue.offer(t.rchild);
		}
	}

	// 带处理方法的先序、中序和后序遍历
	public void preRootTraversal(Consumer<T> fun) {
		preRootTraversal(root, fun);
	}

	private void preRootTraversal(BiTreeNode<T> t, Consumer<T> fun) {
		if (t == null)
			return;

		// 处理结点t
		fun.accept(t.data);

		preRootTraversal(t.lchild, fun);
		preRootTraversal(t.rchild, fun);
	}

	public void inRootTraversal(Consumer<T> fun) {
		inRootTraversal(root, fun);
	}

	private void inRootTraversal(BiTreeNode<T> t, Consumer<T> fun) {
		if (t == null)
			return;
		inRootTraversal(t.lchild, fun);

		// 处理结点t
		fun.accept(t.data);

		inRootTraversal(t.rchild, fun);
	}

	public void postRootTraversal(Consumer<T> fun) {
		postRootTraversal(root, fun);
	}

	private void postRootTraversal(BiTreeNode<T> t, Consumer<T> fun) {
		if (t == null)
			return;
		postRootTraversal(t.lchild, fun);

		postRootTraversal(t.rchild, fun);

		// 处理结点t
		fun.accept(t.data);
	}

	public void levelTraverse(Consumer<T> fun) {
		if (root == null)
			return;
		Deque<BiTreeNode<T>> queue = new ArrayDeque<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			BiTreeNode<T> t = queue.poll();

			// 处理结点t
			fun.accept(t.data);

			if (t.lchild != null)
				queue.offer(t.lchild);
			if (t.rchild != null)
				queue.offer(t.rchild);
		}
	}

	// 将先序遍历的结果存入数组
	public Object[] toArray() {
		int n = countNode();// 因为没有size变量，只能笨办法
		Object[] elements = new Object[n];
		toArray(root, elements, 0);
		return elements;
	}

	private int toArray(BiTreeNode<T> t, Object[] array, int pos) {
		if (t == null)
			return 0;
		array[pos++] = t.data;
		int countLeft = toArray(t.lchild, array, pos);
		int countRight = toArray(t.rchild, array, pos  + countLeft);
		return countLeft + countRight + 1;
	}

	@SuppressWarnings("unchecked")
	public T[]  toArray(T[] a) {
		int n = countNode();
		// 注意，如何生成泛型数组。
		//语法上  new T[]是错误的
		//只能使用反射技术，调用类库提供的方法
		T[] elements = (T[]) Array.newInstance(a.getClass().getComponentType(), n);
		toArray2(root, elements, 0);
		return elements;
	}

	private int toArray2(BiTreeNode<T> t, T[] array, int pos) {// 注意方法名，否则和前面的冲突
		if (t == null)
			return 0;
		array[pos++] = t.data;
		int countLeft = toArray(t.lchild, array, pos);
		int countRight = toArray(t.rchild, array, pos + countLeft);
		return countLeft + countRight + 1;
	}

	// 求节点个数
	public int countNode() {
		return countNode(root);
	}

	// private int countNode(BiTreeNode<T> t) {
	// if(t == null)
	// return 0;
	// return countNode(t.lchild) + countNode(t.rchild) + 1;
	// }

	private int countNode(BiTreeNode<T> t) {
		if (t == null)
			return 0;
		// 分开写，方便debug观察
		int left = countNode(t.lchild);
		int right = countNode(t.rchild);
		int count = left + right + 1;
		return count;
	}

	// 求叶子节点个数
	public int countLeafNode() {
		return countLeafNode(root);
	}

	private int countLeafNode(BiTreeNode<T> t) {
		if (t == null)
			return 0;
		if (t.lchild == null && t.rchild == null)
			return 1;
		return countLeafNode(t.lchild) + countLeafNode(t.rchild);
	}

	// 求二叉树的高度
	public int height() {
		return height(root);
	}

	private int height(BiTreeNode<T> t) {
		if (t == null)
			return 0;
		int left = height(t.lchild);
		int right = height(t.rchild);
		return left > right ? left + 1 : right + 1;
	}

	// 二叉树中查找
	public BiTreeNode<T> search(T x) {
		return search(root, x);
	}

	private BiTreeNode<T> search(BiTreeNode<T> t, T x) {
		if (t == null)
			return null;
		if (t.data.equals(x))
			return t;
		BiTreeNode<T> left = search(t.lchild, x);
		if (left == null)
			return search(t.rchild, x);
		else
			return left;
	}

	// 判断二棵二叉树是不是相等
	public boolean isEqual(BiTree<T> rhd) {
		if (this == rhd)
			return true;
		return isEqual(this.root, rhd.root);
	}

	private boolean isEqual(BiTreeNode<T> t1, BiTreeNode<T> t2) {
		if (t1 == null & t2 == null)
			return true;
		if (t1 == null && t2 != null || t1 != null && t2 == null)
			return false;

		if (t1.data != t2.data)
			return false;
		return isEqual(t1.lchild, t2.lchild) && isEqual(t1.rchild, t2.rchild);
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		try {
			BiTree<T> v = (BiTree<T>) super.clone();
			v.root = copy(root);
			return v;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e);
		}
	}

	// 复制一棵二叉树
	private BiTreeNode<T> copy(BiTreeNode<T> t) {
		if (t == null)
			return null;
		BiTreeNode<T> left = copy(t.lchild);
		BiTreeNode<T> right = copy(t.rchild);
		return new BiTreeNode<>(t.data, left, right);
	}

	// 根据先序和中序遍历的结果，重构二叉树
	public BiTreeNode<String> makeBitree(String preString, String inString) {
		BiTreeNode<String> left = null, right = null;
		if (preString.length() != 1) {
			// 假设各个结点的值不一样，使用indexOf代替了书上的循环查找，
			int rootPos = inString.indexOf(preString.charAt(0));
			if (rootPos == -1)
				return null;
			int countLeft = rootPos;
			if (countLeft > 0)
				left = makeBitree(preString.substring(1, countLeft + 1), inString.substring(0, countLeft));

			int countRight = inString.length() - 1 - countLeft;
			if (countRight > 0)
				right = makeBitree(preString.substring(countLeft + 1), inString.substring(countLeft + 1));
		}

		return new BiTreeNode<String>(preString.substring(0, 1), left, right);
	}

	// preStart 左子树在preString的开始位置
	// inStart 右子树在inString的开始位置
	// count 树中结点的个数

	public BiTreeNode<String> makeBitree(String preString, String inString, int preStart, int inStart, int count) {
		BiTreeNode<String> left = null, right = null;
		if (count != 1) {
			// 假设各个结点的值不一样，使用indexOf代替了书上的循环查找，
			int rootPos = inString.indexOf(preString.charAt(preStart));
			if (rootPos == -1)
				return null;
			int countLeft = rootPos - inStart;
			if (countLeft > 0)
				left = makeBitree(preString, inString, preStart + 1, inStart, countLeft);

			int countRight = count - countLeft - 1;
			if (countRight > 0)
				// right = makeBitree(preString, inString, preStart + 1 + countLeft , rootPos +
				// 1, countRight);
				right = makeBitree(preString, inString, preStart + 1 + countLeft, inStart + countLeft + 1, countRight);
		}

		return new BiTreeNode<String>(preString.substring(preStart, preStart + 1), left, right);
	}

	// 树以二叉链表存储，求树的高度
	public int treeHeight() {
		return treeHeight(root);
	}

	private int treeHeight(BiTreeNode<T> t) {
		if (t == null)
			return 0;
		int height = 0;
		BiTreeNode<T> child = t.lchild;
		while (child != null) {
			int tmp = treeHeight(child);
			if (height < tmp)
				height = tmp;
			child = child.rchild;
		}
		return height + 1;
	}

	// 树以二叉链表存储，求树的根结点到各叶子结点的路径


	@SuppressWarnings("unchecked")
	public void treeRootToLeafPath() {
		class Path {// 为了减少函数的参数个数(stack)，使用了内部类，未必核实，只是一种思路
			Deque<BiTreeNode<T>> stack = new ArrayDeque<>();

			//采用先根遍历的思想，将结点的值存放到栈中
			public void treeRootToLeafPath(BiTreeNode<T> t) {

				if (t.lchild == null) {// 叶子结点，打印栈中的数据
					Object[] result = stack.toArray();//Deque将循环队列转换成数组，次序从head到tail	
					//因为是栈，head是栈顶，需要逆序
					for(int i = result.length - 1; i >= 0; i--) {
						System.out.print(((BiTreeNode<T>)result[i]).data+ " ");
					}					
					System.out.println(t.data);
					return;
				}

				stack.push(t);
				
				BiTreeNode<T> child = t.lchild;
				while (child != null) {//先根遍历各子树
					treeRootToLeafPath(child);
					child = child.rchild;
				}
				
				stack.pop();//遍历结束，返回父结点时，记得出栈
			}
		}

		if (root != null)
			new Path().treeRootToLeafPath(root);
	}

	public void treeRootToLeafPath1() {
		class Path {// 为了减少函数的参数个数(stack)，使用了内部类，未必核实，只是一种思路
			Deque<BiTreeNode<T>> stack = new ArrayDeque<>();

			//采用先根遍历的思想，将结点的值存放到栈中
			public void treeRootToLeafPath(BiTreeNode<T> t) {

				if (t.lchild == null) {// 叶子结点，打印栈中的数据
					for(BiTreeNode<T> n : stack){//从head到tail
						System.out.print(n.data+ " ");
					}					
					System.out.println(t.data);
					return;
				}

				stack.offerLast(t);//在tail端 插入
				
				BiTreeNode<T> child = t.lchild;
				while (child != null) {//先根遍历各子树
					treeRootToLeafPath(child);
					child = child.rchild;
				}
				
				stack.pollLast();//遍历结束，返回父结点时，在tail端删除
			}
		}

		if (root != null)
			new Path().treeRootToLeafPath(root);
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		BiTree<String> btnull = new BiTree<>();
		BiTree<String> btl = new BiTree<>("D", btnull, btnull);
		btl = new BiTree<>("B", btl, btnull);

		BiTree<String> btr = new BiTree<>("E", btnull, btnull);
		btr = new BiTree<>("C", btr, btnull);

		btl = new BiTree<>("A", btl, btr);

		btl.preRootTraversal(System.out::println);

		Object[] array = btl.toArray();

		for(int i = 0; i < array.length;i++){
			System.out.println(array[i]);
		}
//		System.out.println();
//
//		btl.preRootTraversal(x -> System.out.println(x));
//
//		btl.countNode();
//
//		@SuppressWarnings("unchecked")

//		BiTree<String> btl2 = (BiTree<String>) btl.clone();
//		if (btl.isEqual(btl2))
//			System.out.println("=");
//
//		System.out.println("=====================");
//		BiTree<String> btl3 = new BiTree<>("ABEFCDG", "EFBCGDA");
//		btl3.preRootTraversal(System.out::println);
//		System.out.println("-------------------");
//		btl3.inRootTraversal(System.out::println);
//
//		System.out.println("=====================");
//		BiTree<String> btl4 = new BiTree<>("ABEFCDGHIJK", "EFBCIJKHGDA");
		
//		btl4.postRootTraversal(System.out::println);
//		System.out.println("--------------------");
//		btl4.inRootTraversal(System.out::println);

//		System.out.println("--------------------");
//		btl4.treeRootToLeafPath1();
	}
}
