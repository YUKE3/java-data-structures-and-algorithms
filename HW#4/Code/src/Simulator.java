/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static java.lang.System.out;

public class Simulator {
    public static final int MAX_PACKETS = 3;

    private Router dispatcher;
    private Collection<Router> routers;
    private int totalServiceTime;
    private int totalPacketArrived;
    private int packetsDropped;
    private double arrivalProb;
    private int numIntRouters;
    private int maxBufferSize;
    private int minPacketSize;
    private int maxPacketSize;
    private int bandWidth;
    private int duration;

    /**
     * Empty constructor for instantiating the object.
     */
    public Simulator() {}

    /**
     * Main method that prompts user input.
     * @param args Not used.
     */
    public static void main(String[] args) {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        out.println("Starting simulator...");

        program: while (true) {
            try {
                out.print("\nEnter the number of Intermediate routers: ");
                int numIntRouters = Integer.parseInt(reader.readLine());
                if (numIntRouters < 1) {
                    throw new IllegalArgumentException();
                }
                out.print("Enter the arrival probability of a packet: ");
                double arrivalProb = Double.parseDouble(reader.readLine());
                if (arrivalProb < 0 || arrivalProb > 1) {
                    throw new IllegalArgumentException();
                }
                out.print("Enter the maximum buffer size of a router: ");
                int maxBufferSize = Integer.parseInt(reader.readLine());
                if (maxBufferSize < 0) {
                    throw new IllegalArgumentException();
                }
                out.print("Enter the minimum size of a packet: ");
                int minPacketSize = Integer.parseInt(reader.readLine());
                if (minPacketSize < 0) {
                    throw new IllegalArgumentException();
                }
                out.print("Enter the maximum size of a packet: ");
                int maxPacketSize = Integer.parseInt(reader.readLine());
                if (maxPacketSize < minPacketSize) {
                    throw new IllegalArgumentException();
                }
                out.print("Enter the bandwidth size: ");
                int bandWidth = Integer.parseInt(reader.readLine());
                if (bandWidth < 0) {
                    throw new IllegalArgumentException();
                }
                out.print("Enter the simulation duration: ");
                int duration = Integer.parseInt(reader.readLine());
                if (duration < 0) {
                    throw new IllegalArgumentException();
                }

                Simulator simulator = new Simulator();
                simulator.simulate(numIntRouters,arrivalProb,maxBufferSize,
                  minPacketSize,maxPacketSize,bandWidth,duration);
            } catch (NumberFormatException e) {
                out.println("\nInvalid input.");
            } catch (IOException e) {
                out.println("\n" + e.getMessage());
            } catch (IllegalArgumentException e) {
                out.println("\nNumber not in range with expected values.");
            }

            prompt: while (true) {
                out.print("\nDo you want to try another simulation? (y/n): ");
                try {
                    String userInput = reader.readLine().toLowerCase();
                    if (userInput.equals("n")) {
                        out.println("\nProgram terminating successfully...");
                        break program; // Breaks the whole program.
                    } else if (userInput.equals("y")) {
                        break prompt; // Breaks this while.
                    } else { // Reissue.
                        out.println("\nInvalid input.");
                    }
                } catch (IOException e) {
                    out.println("\n" + e.getMessage());
                }
            }
        }
    }

    /**
     * Runs a simulation with given parameters.
     * @param numIntRouters <i>int</i> representing the number of
     *                      intermediate routers.
     * @param arrivalProb <i>double</i> representing the probability of a new
     *                   packet.
     * @param maxBufferSize <i>int</i> representing the max size the routers
     *                      can have.
     * @param minPacketSize <i>int</i> representing the smallest size of a
     *                      packet.
     * @param maxPacketSize <i>int</i> representing the biggest size of a
     *                      packet.
     * @param bandWidth <i>int</i> representing the highest amount of packets
     *                 that can be sent to destination in a cycle.
     * @param duration <i>int</i> representing how much cycles the simulation
     *                is going to run.
     * @return <i>1.0</i> as stated in #172 on Piazza board.
     */
    public double simulate(int numIntRouters, double arrivalProb,
      int maxBufferSize, int minPacketSize, int maxPacketSize, int bandWidth,
      int duration) {
        // Populates all the member variables.
        this.numIntRouters = numIntRouters;
        this.arrivalProb = arrivalProb;
        this.maxBufferSize = maxBufferSize;
        this.minPacketSize = minPacketSize;
        this.maxPacketSize = maxPacketSize;
        this.bandWidth = bandWidth;
        this.duration = duration;
        this.totalServiceTime = 0;
        this.totalPacketArrived = 0;
        this.packetsDropped = 0;

        this.dispatcher = new Router();
        LinkedList<Router> readyRouter = new LinkedList<Router>();

        Packet.setPacketCount(0); // Resets packet count for new simulation.

        // Adds all the intermediate routers into an Array list.
        routers = new ArrayList<Router>();
        for (int i = 0; i < this.numIntRouters; i++) {
            routers.add(new Router());
        }

        // Loop for the duration.
        for (int i = 1; i <= this.duration; i++) {
            out.println("\nTime: " + i);

            // Creates new packets every cycle.
            // Boolean is check if any packets arrived.
            boolean arrived = false;
            for (int u = 0; u < MAX_PACKETS; u++) {
                if (Math.random() < this.arrivalProb) {
                    arrived = true;

                    Packet p = new Packet();
                    p.setPacketSize(randInt(this.minPacketSize,
                      this.maxPacketSize));
                    p.setTimeArrive(i);
                    p.setTimeToDest(p.getPacketSize()/100);

                    this.dispatcher.enqueue(p);
                    out.println("Packet " + p.getId() + " arrives at " +
                      "dispatcher with size " + p.getPacketSize() + ".");
                }
            }
            if (!arrived) { // If nothing arrived.
                out.println("No Packets arrived.");
            }

            // Dispatcher sends packets to intermediate routers.
            while (!this.dispatcher.isEmpty()) {
                try {
                    int target = Router.sendPacketTo(routers);
                    if (target == 0) {
                        // Since if all routers have the size, the index will
                        // be zero, this checks if that size is the max
                        // buffer size to see if all the routers are filled
                        // or not.
                        if (((ArrayList<Router>)routers).get(0).size()
                          == maxBufferSize) {
                            throw new FullBufferException();
                        }
                    }
                    Packet p = this.dispatcher.dequeue();
                    ((ArrayList<Router>)routers).get(target).enqueue(p);
                    out.println("Packet " + p.getId() + " sent to Router "
                      + (target+1) + ".");
                } catch (FullBufferException e) {
                    // When network is congested.
                    out.println("Network is congested. Packet " +
                      this.dispatcher.dequeue().getId() + " is dropped.");
                    packetsDropped++;
                } catch (IllegalArgumentException e) {
                    out.println("There is no intermediate routers.");
                }
            }

            // Decrement all packets counters in intermediate routers.
            for (int u = 0; u < routers.size(); u++) {
                // Reference to a intermediate router.
                Router r = ((ArrayList<Router>)routers).get(u);

                // Decrement TimeToDest counter if it isn't zero already.
                if (!r.isEmpty()) { // Don't run if router is empty.
                    if (r.peek().getTimeToDest() != 0) {
                        r.peek().setTimeToDest(r.peek().getTimeToDest() - 1);
                        // Adds this Router to a queue for sending the packet.
                        if (r.peek().getTimeToDest() == 0) {
                            readyRouter.add(r);
                        }
                    }
                }
            }

            // packet to sent to destination.
            for (int u = 0; u < this.bandWidth; u++) {
                if (!readyRouter.isEmpty()) {
                    // Sends packet from routers in the ready queue.
                    Packet p = readyRouter.removeFirst().dequeue();
                    totalServiceTime += i - p.getTimeArrive();
                    totalPacketArrived++;
                    out.println("Packet " + p.getId() + " has successfully " +
                      "reached its destination: +" + (i - p.getTimeArrive()));
                }
            }

            // Prints all routers
            for (int u = 0; u < routers.size(); u++) {
                out.println("R" + (u+1) + ": " +
                  ((ArrayList<Router>)routers).get(u));
            }
        }

        // Last print for the simulation.
        out.println("\nSimulation ending...");
        out.println("Total service time: " + totalServiceTime);
        out.println("Total packets served: " + totalPacketArrived);
        out.println("Average service time per packet: " +
          totalServiceTime / totalPacketArrived);
        out.println("Total packets dropped: " + packetsDropped);

        return 1.0; // From Piazza Board #172
    }

    /**
     * Creates a random number within given range.
     * @param minVal <i>int</i> representing the lowest value.
     * @param maxVal <i>int</i> representing the highest value.
     * @return <i>int</i> between minVal and maxVal.
     */
    private int randInt(int minVal, int maxVal) {
        return (int)((Math.random() * (maxVal - minVal + 1)) + minVal);
    }
}
