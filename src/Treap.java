import java.util.Random;
import java.util.Stack;

/* Jordan Handwerger
 * November 18, 2016
 * CS284A
 */
public class Treap<E extends Comparable<E>> {
	private Random priorityGenerator;
	private Node<E> root;
	
	//Internal Private Node class
	private static class Node<E>{
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		//Constructors
		/**
		 * Creates a new Node object with given data and priority 
		 * @param data the data that would be in a binary search tree
		 * @param priority the heap priority
		 * @throws NullPointerException if data is null
		 */
		public Node(E data, int priority) throws NullPointerException{
			if(data == null){
				throw new NullPointerException("Node must contain non-Null data");
 			}else{
				this.data = data;
				this.priority = priority;
				right = null;
				left = null;
			}
		}
		
		/**
		 * Performs a right tree rotation so the current parent's left child is now the root. Maintains BST properties and updates pointers.
		 */
		public void rotateRight(){ //Never going to have null problems
			Node<E> rotateRoot = this;
			Node<E> newRoot = rotateRoot.left;
			rotateRoot.left = newRoot.right;
			newRoot.right = rotateRoot;
			}
		
		/**
		 * Performs a left tree rotation so the current parent's right child is now the root. Maintains BST properties and updates pointers.
		 */
		public void rotateLeft(){//shouldnt rotate a null node
			Node<E> rotateRoot = this;
			Node<E> newRoot = rotateRoot.right;
			rotateRoot.right = newRoot.left;
			newRoot.left = rotateRoot;
			}
		
		@Override
		/**
		 * Creates and returns a String representation of this Node
		 */
		public String toString(){
			return "(key=" + this.data + ", priority=" + this.priority + ")";
		}
	}
	
	//Constructors
	/**
	 * Creates a new empty Treap with a null root and random priority generator. 
	 */
	public Treap(){
		root = null;
		priorityGenerator = new Random();
	}
	
	/**
	 * Creates a new empty Treap with a null root and a random priority generator based on the given seed
	 * @param seed initial value of the PRNG
	 */
	public Treap(long seed){
		root = null;
		priorityGenerator = new Random(seed);
	}
	
	/**
	 * Adds a given key to the Treap with a random heap priority
	 * @param key data of the node
	 * @return true if added successfully, false if node already exists containing the given key
	 */
	public boolean add(E key){
		if(key == null){
			return false;
		}
		if(find(key) != null){ //The node containing E is already in the Treap
			return false;
		} else { //Add the node
			if(root == null){//empty treap set node as root
				root = new Node<E>(key, priorityGenerator.nextInt());
			}else{ //Insert as in a binary tree
				Node<E> curr = root;
				Stack<Node<E>> nodeStack = new Stack<Node<E>>();
				while(curr != null){
					nodeStack.push(curr);
					if(curr.data.compareTo(key) < 0){
						curr = curr.right;
					} else {
						curr = curr.left;
					}
				}
				curr = new Node<E>(key, priorityGenerator.nextInt());
				if(nodeStack.peek().data.compareTo(key) > 0){// set the parent of current to point to it 
					nodeStack.peek().left = curr;
				}else{
					nodeStack.peek().right = curr;
				}
				while(!nodeStack.empty() && nodeStack.peek().priority < curr.priority){//bubble up the heap to achieve Treap properties
					Node<E> parentNode = nodeStack.pop();
					if(parentNode == root){//One element in stack, the element bubbles up to root
						if(root.left == curr){
							root.rotateRight();
							root = curr;
							return true;
						} else{
							root.rotateLeft();
							root = curr;
							return true;
						}
					}
					if(curr == parentNode.left){//rotates current up the Treap and sets pointers
						parentNode.rotateRight();
						if(nodeStack.peek().left == parentNode){
							nodeStack.peek().left = curr;
						} else{
							nodeStack.peek().right = curr;
						}
					} else{
						parentNode.rotateLeft();
						if(nodeStack.peek().left == parentNode){
							nodeStack.peek().left = curr;
						} else{
							nodeStack.peek().right = curr;
						}
					}
				}
			} return true;
		}
		
	}
	
	/**
	 * Removes the node containing the given key from the Treap
	 * @param key data in the node to be deleted
	 * @return true if node is successfully deleted, false if there is no node in the Treap with the given key
	 */
	public boolean delete(E key){
		if(key == null){
			return false;
		}
		if(find(key) == null){//node not in Treap
			return false;
		} else{//Node in Treap
			Node<E> curr = root;
			Node<E> parent = root;
			while(curr.data.compareTo(key) != 0){ //First find parent of delete node
				parent = curr;
				if(curr.data.compareTo(key) < 0){
					curr = curr.right;
				} else {
					curr = curr.left;
				}
			}
			while(curr.right != null || curr.left != null){//rotate the node that is to be deleted so it is a leaf
				if(parent.right == curr){//curr is parent.right
					if(curr.left == null){
						parent.right = curr.right;
						parent = curr.right;
						curr.rotateLeft();
					} else if(curr.right == null){
						parent.right = curr.left;
						parent = curr.left;
						curr.rotateRight();
					}else if(curr.left.priority > curr.right.priority){
						parent.right = curr.left;
						parent = curr.left;
						curr.rotateRight();
					} else { //right > left priority
						parent.right = curr.right;
						parent = curr.right;
						curr.rotateLeft();
					}
				} else{ //curr is the left child
					if(curr.left == null){
						parent.left = curr.right;
						parent = curr.right;
						curr.rotateLeft();
					} else if(curr.right == null){
						parent.left = curr.left;
						parent = curr.left;
						curr.rotateRight();
					} else if(curr.left.priority > curr.right.priority){
						parent.left = curr.left;
						parent = curr.left;
						curr.rotateRight();
					} else { //right > left priority
						parent.left = curr.right;
						parent = curr.right;
						curr.rotateLeft();
					}
				}
			}
			//find if the node to delete is the left or right child, then deletes it
			if(parent.right == null){
				parent.left = null;
			} else if(parent.left == null){
				parent.right = null;
			} else {
				if(parent.right.data == key){
					parent.right = null;
				} else{
					parent.left = null;
				}
			}
			return true;
		}
		
	}
	
	/**
	 * Helper function for find(key)
	 * @param root the root of the treap
	 * @param key the data in the node that is being searched for
	 * @return the key if it is found, null if not
	 */
	private E find(Node<E> root, E key){
		Node<E> curr = root;
		while(curr.data != key){ //loops until curr contains the key
			if(curr.right == null && curr.left == null){ //May be redundant 
				return null;
			}
			if(curr.data.compareTo(key) > 0){//node containg data would be the left child using BST properties
				if(curr.left == null){
					return null;
				} else {
					curr = curr.left;
				}
			} else {
				if(curr.right == null){//node containg data would be right child
					return null;
				} else {
					curr = curr.right;
				}
			}
		}
		return curr.data;
	}
	
	/**
	 * Finds if a node containing the given key is in the list or not
	 * @param key data contained in node 
	 * @return the key if found, null if not
	 */
	public E find(E key){// key cannot be null
		if(key == null){//input check
			System.out.println("Key cannot be null");
			return null;
		} else if(root == null) { // empty tree
			return null;
		} else{//helper call
			return find(root, key);
		}
	}
	
	/**
	 * Adds a new node containing the given data and with the given priority
	 * @param key data in the new node
	 * @param priority heap priority of new node
	 * @return true if node successfully added, false if node already exists
	 */
	public boolean add(E key, int priority){
		if(key == null){
			return false;
		}
		if(find(key) != null){ //The node containing E is already in the Treap
			return false;
		} else {//add node
			if(root == null){//empty treap
				root = new Node<E>(key, priority);
				return true;
			}else{ //Insert as in a binary tree
				Node<E> curr = root;
				Stack<Node<E>> nodeStack = new Stack<Node<E>>();
				while(curr != null){
					nodeStack.push(curr);
					if(curr.data.compareTo(key) < 0){
						curr = curr.right;
					} else {
						curr = curr.left;
					}
				}
				curr = new Node<E>(key, priority);
				if(nodeStack.peek().data.compareTo(key) > 0){//set the parent of current to point to it
					nodeStack.peek().left = curr;
				}else{
					nodeStack.peek().right = curr;
				}
				
				while(!nodeStack.empty() && nodeStack.peek().priority < curr.priority){//bubble up the treap to achieve max heap priorities
					Node<E> parentNode = nodeStack.pop();
					if(parentNode == root){//One element in stack, the element bubbles up to root
						if(root.left == curr){
							root.rotateRight();
							root = curr;
							return true;
						} else{
							root.rotateLeft();
							root = curr;
							return true;
						}
					}
					else if(curr == parentNode.left){// bubbles up and sets parent pointers
						parentNode.rotateRight();
						if(nodeStack.peek().left == parentNode){
							nodeStack.peek().left = curr;
						} else{
							nodeStack.peek().right = curr;
						}
					} else{
						parentNode.rotateLeft();
						if(nodeStack.peek().left == parentNode){
							nodeStack.peek().left = curr;
						} else{
							nodeStack.peek().right = curr;
						}
					}
				}
			} return true;
		}
		
	}
	
	@Override 
	/**
	 * Creates and returns a preorder string representation of this treap
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		preOrderTraverse(root, 1, sb);
		return sb.toString();
	}
	
	/**
	 * helper function for toString()
	 * @param node current node
	 * @param depth what level of the treap current is at
	 * @param sb stringBuilder
	 */
	private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {
		for (int i = 1; i < depth; i++) { 
			sb.append(" ");
		} if (node == null) {
			sb.append("null\n");
		} else {
			sb.append(node.toString());
			sb.append("\n");
			preOrderTraverse(node.left, depth + 1, sb);
			preOrderTraverse(node.right, depth + 1, sb);
		}
	}
	public static void main(String args[]){
		
	}
}
