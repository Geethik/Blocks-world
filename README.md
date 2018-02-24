Blocks world solver
Artificial Intelligence - A-star search implementation in Java

State.java is used to store the state of the nodes as list of lists. 
Node.java has the characteristic properties of a node as in the State, depth, scores of f, g and h. 
ProblemGenerator.java is used to generate the initial states and the goal states. The initial states are generated both randomly and in a custom way as in the functions randomState and customState in it.
Solver.java is the driver program which calls the A* search on the given initial nodes along with specified heuristics. In the case of node generation, the successor function is used which generates all the nodes which are not visited and not in frontier with less depth. Nodes are marked visited at the time of generation itself to prevent the queue for expanding rapidly. Traceback function is used for printing back the solution.

Compiling:
  javac State.java
  javac Node.java
  javac ProblemGenerator.java
  javac Sovler.java
Running:
  java Solver
