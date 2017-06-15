public class TrafficLight {
	//possible states of traffic lights
	private final static String[] STATES={"red","green","amber"};
	//transitions states of traffic lights
	private final static String[] TRANSITION_STATES={"green","amber","red"};
	// current state of traffic
	private String currentState;
	
	//constructor 
	public TrafficLight(String firststate){
		//this.currentState="red";
		this.currentState = firststate;
	}
	
	/**
	 * perform the transition of traffic lights
	 * @return currentState in string
	 */
	// just a reminder that the method name should be verb (e.g. changeLight())
	// "Transition" is not a good example of method name
	//take a look at the following code conventions
	//http://www.oracle.com/technetwork/java/codeconventions-135099.html
	// public String Transition()
	
	public String changeLight(){
		
		//record the location/place/index of current state
		int foundIndex = 0;
		//traverse
		for (int i=0;i<STATES.length;i++){
			if (this.currentState==STATES[i]){
				foundIndex = i;
			}
		}
		//assign the  changed light state
		this.currentState=TRANSITION_STATES[foundIndex];
		return this.currentState;
	}
	
	@Override
	/**
	 * override the toString method
	 */
	public String toString(){
		return this.currentState;
		//return "hello";
	}
	//test the traffic model
	public static void main (String[] args) {
		//set the initial state
		TrafficLight t1 = new TrafficLight("green");
		System.out.println(t1);
		// modeling the traffic lights
		for (int i=0;i<4;i++){
			System.out.println(t1.changeLight());
		}
		//System.out.println(t1.currentState);
		
		// TODO Auto-generated method stub

	}

}
