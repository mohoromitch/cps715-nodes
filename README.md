# cps716-nodes

Repository to hold the HTTP and CoAP dummy nodes that send and receive test requests for the simulation.

# Running

## HTTP Client

```
java -jar node.jar http 5 2 10.0.0.1 node1.net/status
```

This will run 5 node that sends 2 GET requests to the IP of the proxy requesting the URI node1.net/status.
When the proxy receives the request, it forwards the repackaged request to the IP of the node mapped to that URI.

So, when the proxy receives node1.net/status, it reads node1.net which is mapped to a CoAP node, then sends the /status request in that CoAP request.
Note, NON CoAP requests are made in the simulation, since we are measuring concurrency performance not link layer and packets will not be dropped in this simulation.
The reliability of 802.15.4 makes NON requests feasible in the real world, so this setup is reasonable.

## CoAP Server

```
java -jar node.jar coap 
```

This will run a node that responds to the CoAP requests it gets.
For this simulation any URI it receives it will simply respond with the same data.

# Configuration

No configuration file is needed for this setup, a Makefile is provided that contains example nodes with arguments supplied for a test topology.

