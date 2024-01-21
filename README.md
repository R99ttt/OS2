# Banker's Algorithm Implementation

This Java program implements the Banker's algorithm for deadlock avoidance. The algorithm is used to manage the allocation of resources to processes in a way that avoids deadlock. The program consists of three main parts:

1. **Initialization:** The user is prompted to input the number of processes and resources. Matrices for allocation, claim, and available resources are initialized based on user input.

2. **Error Checking:** The program checks for errors in the initialization process. It ensures that the allocation values do not exceed the maximum demand (claim) for resources. If an error is detected, the user is prompted to repeat the initialization process.

3. **Banker's Algorithm:**
   - The program calculates the `need` matrix by subtracting the allocation matrix from the claim matrix.
   - It computes the total resources by summing up the allocated resources and available resources.
   - The Banker's algorithm is applied to check if the system is in a safe state. If a safe state is found, it prints the order in which processes can execute safely. If a deadlock is detected, it provides information about the processes involved in the deadlock.

## How to Run

1. Compile the Java program:

   ```bash
   javac Q1_BankerAlg.java
   ```

2. Run the compiled program:

   ```bash
   java Q1_BankerAlg
   ```

3. Follow the prompts to input the number of processes, resources, and matrices. The program will then execute the Banker's algorithm and provide the output.

## Contributors

- Abo Rabia Rami
- Stav Zysblatt

Feel free to explore, modify, and use the code for educational purposes. If you have any questions or suggestions, please contact the contributors.
