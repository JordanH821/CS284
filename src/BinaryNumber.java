/* I pledge my honor that I have abided by the Stevens Honor System.
 * Jordan Handwerger
 * CS284
 * 
 *       **********Binary Number operations based on most significant digit being the leftmost digit************
 * 
 */
public class BinaryNumber {
	//holds the digits of the binary number in an integer array
	private int data[];
	//True when the addition of two binary numbers yields a binary number with a larger length
	private boolean overflow = false;
	
	//Constructors
	/**Constructs a binary number of zeros for the given length
	  	@param length the binary Number's length
	 */
	public BinaryNumber(int length){
		data = new int[length];
		}
	
	/** Constructs a binary number given by the string
	 	@param str the binary number
	 */
	public BinaryNumber(String str){
		data = new int[str.length()];
		
		for(int i = 0; i<str.length(); i++){
			data[i] = java.lang.Character.getNumericValue(str.charAt(i));	
		}
	}
	
	/** Returns the length of the binary number
	 * @return length of the binary number
	 */
	public int getLength(){
		return data.length;
	}
	
	/** Returns the binary digit at a given an index 
	 * @param index the index of some integer in the binary number
	 * @return the integer at the given index
	 */
	public int getDigit(int index){
		if(index<0 || index >= this.getLength()){
			System.out.println("That index is out of bounds");
			//index is not in the number, returns -1 as the int
			return -1;
		}
		else{
			return data[index];
		}
	}
	
	/** Shifts a binary number a given amount of spaces to the right and pads with zeros
	 * @param amount the number of digits to shift
	 * @return the new shifted binary number
	 */
	public BinaryNumber shiftR(int amount){
		//Creates a new BinaryNumber in order to easily shift the integer array
		BinaryNumber dataHolder = new BinaryNumber(this.getLength() + amount);
		//loops through the original BinaryNumber and moves it down the array a given length
		for(int i = 0; i < this.getLength(); i++){
			dataHolder.data[i + amount] = this.data[i];
		}
		this.data = dataHolder.data;
		return this;
		
		}
	
	/** Sets the overflow parameter to false
	 */
	public void clearOverflow(){
		overflow = false;
	}
	
	/** Adds a given binary number to the the number represented by the instantiation 
	 * @param aBinaryNumber the number that will be added 
	 * @return a BinaryNumber representing the sum 
	 */
	public BinaryNumber add(BinaryNumber aBinaryNumber){
		//Checks the length of each and prints out a message if the two BinaryNumbers are of different length
		if(this.getLength() != aBinaryNumber.getLength()){
			System.out.println("These two binary numbers are not the same length.");
			//no addition is done, the original number is returned
			return this;
		}
		else{
		 	BinaryNumber sum = new BinaryNumber(data.length);
		 	sum.clearOverflow();
		 	/*	loops through each pair digits in the BinaryNumbers, adds them,
		 		and determines if there if an overflow. In this
		 		method, overflow is used as a carry indicator and
		 		becomes the true overflow boolean value at the last digits.
		 	*/
		 	for(int i = data.length-1; i>-1; i--){
		 		int bitsum;
		 		if(sum.overflow == true){
		 			bitsum = data[i] + aBinaryNumber.data[i] + 1;
		 			
		 			if(bitsum > 1){
		 				
			 			sum.overflow = true;
			 			sum.data[i] = bitsum % 2; 
		 			}
		 			else{
		 				sum.clearOverflow();
		 				sum.data[i] = bitsum;
		 			}
		 		}
		 		else{
		 			bitsum = data[i] + aBinaryNumber.data[i];
		 			if(bitsum >1){
		 				sum.overflow = true;
		 				sum.data[i] = 0;
		 			}
		 			else{
		 				sum.data[i] = bitsum;
		 			}
		 		}
		 	}
		 	//after the addition is completed, the number is shifted for the final carry, or not
		 	if(sum.overflow == true){
		 		/*The actual binary addition is carried out with the overflow carry added on,
		 		 * but this overflow will not be printed unless the data of the sum is accessed.
		 		 * This is due to toString not printing overflow numbers.
		 		 */
		 		sum.shiftR(1);
		 		sum.data[0] = 1; 
		 		return sum;
		 	}
		 	else{
		 		return sum;
		 	}
		}
	}
	
	@Override
	/**
	 * override the toString method  
	 */
	public String toString(){
		if(overflow == true){
			return "Overflow";
		}
		else{
			String number = "";
			for(int i=0; i < data.length ; i++){
				number += data[i];
			}
		return number;
		}
	}
	
	/** Converts the binary number into a decimal number
	 * @return an integer decimal value of the binary number
	 */
	public int toDecimal(){
		int decimalNumber = 0;
		int exponent = data.length-1;
		
		for(int number: data){
			decimalNumber += (int) Math.pow(2, exponent) * number;
			exponent -= 1;
		}
		return decimalNumber;
	}

	public static void main(String[] args){
		
	}
}