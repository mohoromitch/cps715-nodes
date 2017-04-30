# cps716-nodes

Repository to hold the HTTP and CoAP dummy nodes that send and receive test requests for the simulation.

# Running

## HTTP Client

```
java -jar node.jar http client 10 localhost:8000/test
```

This will create an HTTP node that sends 10 requests a second to localhost on port 8000 for the resource `/test`.
When the proxy receives the request, it forwards the repackaged request to the IP of the node mapped to that URI.

So, when the proxy receives node1.net/status, it reads node1.net which is mapped to a CoAP node, then sends the /status request in that CoAP request.
Note, NON CoAP requests are made in the simulation, since we are measuring concurrency performance not link layer and packets will not be dropped in this simulation.
The reliability of 802.15.4 makes NON requests feasible in the real world, so this setup is reasonable.

If you would like to test the HTTP rate, run:

`./http_serve.py`

Which will listen on localhost:8000 for requests.

## CoAP Client

```
java -jar node.jar coap client 10 localhost:5683/test
```

This will create a CoAP client node that sends 10 request a second to localhost on port 5683 (default port for californium) for the resource `/test`.

## CoAP Server

```
java -jar node.jar coap server
```

This will create a CoAP server node.
For this simulation any URI it receives it will simply respond with the same data.

# Configuration

No configuration file is needed for this setup, a Makefile is provided that contains example nodes with arguments supplied for a test topology.

