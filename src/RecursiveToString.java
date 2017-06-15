/* Jordan Handwerger
 * October 7, 2016
 * Section A
 * CS284 HW2
 * Pledge: I pledge my honor that I have abided by the Stevens Honor System. - JH
 */

import java.util.ArrayList;

public class RecursiveToString<E> {
	private Node<E> head; //reference to the first item in the doubly linked list
	private Node<E> tail; //reference to the last item in the doubly linked list 
	private int size; //number of items in the list 
	private ArrayList<Node<E>> indices; //ArrayList for the fast accessing 
	
	private static class Node<E>{
		private E data; //the info the node holds
		private Node<E> next; //reference to the next node
		private Node<E> prev; //reference to the previous node
		
		//Constructors 
		/**Constructs a new node holding given data.
		 * @param elem the data to be stored in the node
		 */
		private Node(E elem){
			data = elem;
		}
		
		/** Constructs a new node holding the given data, with references to the given previous and next nodes.
		 * @param elem the data to be stored in the node
		 * @param prev the previous node in the list
		 * @param next the next node in the list
		 */
		private Node(E elem, Node<E> prev, Node<E> next){
			data = elem;
			this.prev = prev;
			this.next = next;
		}
	}
	//Constructor
	/**Constructs an empty doubly linked list**/
	public RecursiveToString(){
		head = null;
		tail = null;
		size = 0;
		indices = new ArrayList<Node<E>>();	
	}
	/**Adds a new node containing the given data to the doubly linked list at the given index using indices for fast accessing.
	 * @param index the position at which the node will be added
	 * @param elem the data to be stored in the node
	 * @return true if the item is successfully added, false otherwise. 
	 */
	public boolean add(int index, E elem){
		//Assumes adding at index zero is done by overload add method and adding at the end is done by append method.
		
		//Fixes the references in the doubly linked list.
		if(index > 0 && index < size){ 
			Node<E> nodeAtIndex = indices.get(index);
			Node<E> nodeBeforeIndex = nodeAtIndex.prev;
			Node<E> newNode = new Node<E>(elem, nodeBeforeIndex, nodeAtIndex);
			nodeBeforeIndex.next = newNode;
			nodeAtIndex.prev = newNode;
		//Fixes the arrayList indices.
			indices.add(index, newNode);
			size++;
			return true;
		}else if(index == 0){ //adds at the beginning 
			this.add(elem);
			return true;
		}else if(index == size){ //adds at the end
			this.append(elem);
			return true;
		}else{ //index out of bounds
			System.out.println("The index: " + Integer.toString(index) + " is not in the list.");
			return false;
		}
	}
	
	/**Adds a new node containing the given data at the head, or start of the list.
	 * @param elem the data to be stored in the new node
	 * @return true after the node is successfully added
	 */
	public boolean add(E elem){
		Node<E> newNode = new Node<E>(elem, null, head);
		if(head == null){	//Adding to an empty list
			head = newNode;
			tail = newNode; //the node is the only one in the list and therefore both the head and the tail
			size++;
			indices.add(newNode);
			return true;
		}
		else{				//Adding to a list with a head
			head.prev = newNode;
			head = newNode;
			size++;
			indices.add(0, newNode);
			return true;
		}
	}
	
	/**Adds a node containing the given data at the end of the doubly linked list.
	 * @param elem the data to be stored in the node
	 * @return true after node is added at the end of the doubly linked list
	 */
	public boolean append(E elem){
		if(this.size() >= 1){//appending to a list with at least one element
			Node<E> newNode = new Node<E>(elem, tail, null);
			tail.next = newNode;
			tail = newNode;
			size++;
			indices.add(newNode);
			return true;
		}else{//appending to an empty list
			Node<E> newNode = new Node<E>(elem, null, null);
			head = newNode;
			tail = newNode;
			size++;
			indices.add(newNode);
			return true;
		}
	}
		
	
	/**Retrieves the node at the given index in the doubly linked list.
	 * @param index the position of the node to be retrieved
	 * @return the data of the node in the doubly linked list at the given index
	 */
	public E get(int index){
		if((0 <= index) && (index <= (indices.size()-1))){ //checks if index is in the doubly linked list
			return indices.get(index).data;
		}else{//illegal index
		System.out.println("There is no node at index " + Integer.toString(index) + ".");
		return null;
		}
	}
	
	/**Retrieves the data in the node at the start, or head, of the doubly linked list.
	 * @return the data in the first node of the list
	 */
	public E getHead(){
		if(head != null){//nonempty list
			return head.data;
		}else{//empty list
			System.out.println("The list is empty.");
			return null;
		}
	}
	
	/**Retrieves the data in the last node, or tail, of the doubly linked list.
	 * @return the data contained in the last node in the doubly linked list.
	 */
	public E getLast(){
		if(tail != null){//nonempty list 
			return tail.data;
		}else{//empty list
			System.out.println("The list is empty.");
			return null;
		}
	}
	
	/**Returns the size of the doubly linked list.
	 * @return the size of the doubly linked list.
	 */
	public int size(){
		return indices.size();
	}
	
	/**Removes the node at the head of the doubly linked list.
	 * @return the data in the removed node
	 */
	public E remove(){
		if(this.size() > 1){//list with at least 2 items prevents null pointer exception
			Node<E> oldHead = head;
			head = head.next;
			head.prev = null;
			size--;
			indices.remove(0);
			return oldHead.data;
		}else if(this.size == 1){//single element list
			Node<E> removeNode = head;
			head = null;
			tail = null;
			size--;
			indices.remove(0);
			return removeNode.data;
		}else{//empty list
			System.out.println("Cannot remove the head of an empty list.");
			return null;
		}
	}
	
	/**Removes the last node in the doubly linked list.
	 * @return the data in the removed node
	 */
	public E removeLast(){//prevents null pointer exception
		if(this.size() > 1){
			E oldTailData = tail.data;
			tail = tail.prev;
			tail.next = null;
			indices.remove(indices.size()-1);
			size--;
			return oldTailData;
		}else if(this.size() == 1){//single element list
			E oldTailData = tail.data;
			tail = null;
			head = null;
			size--;
			indices.remove(0);
			return oldTailData;
		}else{//empty list
			System.out.println("Cannot remove the tail of an empty list.");
			return null;
		}
	}
	
	/**Removes the node at the given index (0 < index < size - 1) in the doubly linked list.
	 * @param index the position of the node to be removed
	 * @return the data in the removed node
	 */
	public E remove(int index){
		/*Assuming the node being removed is not at the head or tail,
			so there is a non-null previous and next node. */
		if( (0 < index) && (index < (size-1)) ){ //any valid index that is not head nor tail
			Node<E> removeNode = indices.get(index);
			removeNode.prev.next = removeNode.next;
			removeNode.next.prev = removeNode.prev;
			indices.remove(index);
			size--;
			return removeNode.data;	
		}else if(index == 0){ //remove head calls remove()
			Node<E> removeNode = indices.get(index);
			this.remove();
			return removeNode.data;
		}else if(index == (this.size() - 1)){ //remove tail calls removeLast()
			Node<E> removeNode = indices.get(index);
			this.removeLast();
			return removeNode.data;
		}else{//illegal index
			System.out.println("The index: " + Integer.toString(index) + " is not in the list.");
			return null;
		}
	}
			
	
	/**Removes the first node that contains the given data.
	 * @param elem the data inside the node that is to be removed
	 * @return true if the data is in the list and has been removed, false if it is not in the list
	 */
	public boolean remove(E elem){
		int removeIndex = 0;
		for(Node<E> p = head; p != null; p = p.next){
			if(p.data == elem){//element to be removed is in list
				if((removeIndex != 0) && (removeIndex != (size - 1))){
					this.remove(removeIndex);  
					return true;
				}else if(removeIndex == 0){
					this.remove();
					return true;
				}else{
					this.removeLast();
					return true;
				}
					
			}else{
				removeIndex++;
			}
		}
		return false;//element not in the list
	}
//	@Override
//	/**Creates a string representation of the list.*/
//	public String toString(){
//		ArrayList<String> listStr = new ArrayList<String>();
//		for(Node<E> p = head; p != null; p = p.next){
//			listStr.add(p.data.toString());
//		}
//		return listStr.toString(); //the doubly linked list is represented as a list
//	}
	
	public String toString(){
		return toStringHelp(head);
	}
	
	public String toStringHelp(Node<E> n){
		if(n.next == null){
			return "";
		}else{
			return n.data.toString() + toStringHelp(n.next);
		}
	}
	
	public static void main(String args[]){
		RecursiveToString<String> lst = new RecursiveToString<String>();
		lst.add("BOP");
		lst.add("BEEP");
		lst.add("BIP");
		System.out.println(lst);
		
	}
}