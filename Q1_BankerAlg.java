//Abo Rabia Rami 
//Stav Zysblatt 

import java.util.Scanner;

public class Q1_BankerAlg {

	public static Scanner input = new Scanner(System.in); 

	public static void main(String[] args) {

		//input:
		System.out.println("Enter the number of processes: ");
		int numOfProcesses = input.nextInt();

		System.out.println("Enter the number of resources: ");
		int numOfResources = input.nextInt();

		int[][] allocation = new int[numOfProcesses][numOfResources];
		int[][] claim = new int[numOfProcesses][numOfResources];
		int[] available = new int[numOfResources]; //only one line in the table

		int[][] need = new int[numOfProcesses][numOfResources];
		int[] resources = new int[numOfResources]; //when avail = Res we didn't had an compute errors

		//initialization:
		initialization(allocation, claim, available, numOfProcesses, numOfResources);

		//******************************

		boolean negative = false;
		//repeat until the initialization is correct:
		while(!negative) {

			negative = computeNeed(claim, allocation, need);//need = claim - alloc

			if(!negative) {
				System.out.println("There is an error in the initialization process"
						+ "\nthere is an allocation value that greater then the Max Demand (Claim)."
						+ "\nplease repeat on the initialization process:");
				initialization(allocation, claim, available, numOfProcesses, numOfResources);
			}

		}

		computeResource(available, allocation, resources);//resources = avail + sumOfAllocation

		safeState(allocation, available, need, numOfResources, numOfProcesses);
		
		System.out.println("=====End of part 1=====\n");
		//******************************
		
		findMinRes(allocation, available, need, numOfResources, numOfProcesses);
		
		System.out.println("=====End of part 2=====\n");
		//******************************
		
		resRequest(allocation, available, need, numOfResources, numOfProcesses);
		
		System.out.println("=====End of part 3=====\n");
	}



	private static void resRequest(int[][] allocation, int[] available, int[][] need, int numOfResources,
			int numOfProcesses) {

		int[] request = new int[numOfResources];

		System.out.println("enter the index of the process: ");
		int index = input.nextInt();

		System.out.println("insert the request vector " + "(" + numOfResources +") elements: ");
		for(int i = 0; i < numOfResources; i++) {//validation check: 

			request[i] = input.nextInt();

			if (request[i] > need[index][i] || request[i] > available[i]) {
				System.out.println("This request cannot be granted.");
				return;
			}

		}
		//resource allocation:
		for (int i = 0; i < numOfResources; i++) {
	        
			available[i] -= request[i];
	        allocation[index][i] += request[i];
	        need[index][i] -= request[i];
	        
	    }
		
		//banker:
		boolean isSafe = safeState(allocation, available, need, numOfResources, numOfProcesses);
		if (isSafe)
			System.out.println("This request can be granted safely.");

		else 
			System.out.println("This request cannot be granted at the moment due to a potential deadlock.");

	}



	private static void findMinRes(int[][] allocation, int[] available, int[][] need, int numOfResources,
			int numOfProcesses) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter the name of resource that will be found for him the minimal number of copies:");
		
		System.out.println("The availabe resources: ");
		for(int i = 0; i < numOfResources; i++) {
			
			char charA = 'A';
			System.out.print((char)(charA + i) + " ");
			
		}
		System.out.println();
		
		char choice = input.next().charAt(0);
		int index = choice - 'A';
		int[] work = new int[numOfResources];
		
		for(int i = 0; i < numOfResources; i++) {
			work[i] = available[i];
		}
		
		work[index] = 0;
		boolean found = false;
		
		
		while(found == false) {
			
			found = safeState(allocation, work, need, numOfResources, numOfProcesses);
			
			if(found == false)
				work[index]++;
		}
		
		System.out.println("The minimal copies for:" + choice +" is: " + work[index]);
		
		System.out.println("The Availabe vector with the minimal copies for resource " + choice + ", is: ");
		
		System.out.print("[");
		for(int i = 0; i < numOfResources; i++) {
			System.out.print(work[i] + " ");
		}
		System.out.println("]");
		
	}



	private static boolean safeState(int[][] allocation, int[] available, int[][] need, int numOfResources,
			int numOfProcesses) {
		
		boolean[] finish = new boolean[numOfProcesses];
		int[] areSafe = new int[numOfProcesses];
		
		int[] work = new int[numOfResources];
		for(int i = 0; i < numOfResources; i++) {
			work[i] = available[i];
		}
		
		int count = 0; //until all processes will finish
		
		while(count < numOfProcesses) {
			
			boolean finished = false;//will tell if there was a deadlock in the future
			
			for(int i = 0; i < numOfProcesses; i++) {//in the last iteration it will tell if there is a deadlock
				
				if(finish[i] == false) {//working only on the unfinished
					
					int matched = 0;
					for(int j = 0; j < numOfResources; j++) {
						if(need[i][j] > work[j]) {
							System.out.println("can't run this proccess: p" + j +" right now.\n");
							break;
						}
						else
							matched++; //if the resource can be taken
					}
					//if all the resources are matched to the work statement 
					if(matched == numOfResources) { //current process can run
						
						//release the resources and put them in the work array/mat
						for(int j = 0; j < numOfResources; j++) 
							work[j] += allocation[i][j];
						
						//the safe process will be saved, the rest are involved in deadlock
						areSafe[count] = i;
						count++;
						finish[i] = true;
						finished = true;
					}
				}
				
			}
			
			//if we run in the while loop and in the last iteration there is a process that can't be proceed
			//we reach here:
			if (finished == false) {
				System.out.println("there is a deadlock, not all the proccess can be proceed");
				
				for(int k = 0; k < numOfProcesses; k++) 
					if(finish[k] == false)
						System.out.print("p" + k +", ");
				System.out.println(": are involved in deadlock.");
				return false;
			}
				
		}
		
		System.out.println("all the proccess can be executed. the list: \n");
		for(int i = 0; i < numOfProcesses; i++) {
			
			System.out.print("p" + areSafe[i] + " ");
			System.out.println();
			
		}
		
		return true;
	}



	private static void computeResource(int[] available, int[][] allocation, int[] resources) {

		for (int i = 0; i < allocation[0].length; i++) {
			
			int sumOfAllocation = 0;
			
			for (int j = 0; j < allocation.length; j++) {
				sumOfAllocation += allocation[j][i];//running over each column
			}
			resources[i] = sumOfAllocation + available[i];
		}


	}



	private static boolean computeNeed(int[][] claim, int[][] allocation, int[][] need) {

		for (int i = 0; i < need.length; i++) {
			
			for (int j = 0; j < need[i].length; j++) {
				
				need[i][j] = claim[i][j] - allocation[i][j];
				
				if (need[i][j] < 0) {
					return false;
				}
			
			}
		}
		return true;

	}

	private static void loadClaimAlloc(int[][] matrix) {
		// TODO Auto-generated method stub
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = input.nextInt();
			}
		}

	}

	private static void initialization(int[][] allocation, int[][] claim, int[] available, int numOfProcesses, int numOfResources) {

		System.out.println("insert Allocation matrix of size: " + numOfProcesses*numOfResources);
		loadClaimAlloc(allocation); //because the dimensions is the same

		System.out.println("insert Claim matrix of size: " + numOfProcesses*numOfResources);
		loadClaimAlloc(claim);

		System.out.println("insert the Available matrix of size: " + numOfResources);
		for (int i= 0; i< available.length; i++){
			available[i]= input.nextInt();
		}

	}

}
