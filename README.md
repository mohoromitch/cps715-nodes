# cps716-nodes

Repository to hold the HTTP and CoAP dummy nodes that send and receive test requests for the simulation.

```
  +------+        +------------+     +------+
  | HTTP +---1---->  HTTP/CoAP +--2--> CoAP |
  | Node <---4----+  Proxy     <--3--+ Node |
  +------+        +------------+     +------+
```

The diagram above shows the topology of the simulation.
HTTP nodes and CoAP nodes are created to test guage the performance of the HTTP/CoAP proxy in the centre. 

The numbers on the arrows show the order of the actions being made, HTTP -> Proxy, proxy repackages the response into a CoAP response, then forwards it to the CoAP node.

The CoAP node then responds, and the proxy handles that and repackes that into a HTTP response to the original HTTP node.

The simulation uses basic GET requests for HTTP, and NON's for CoAP.

Using log4j, timings for the respones are logged, and will be processed to determine performance of the proxy.

# Running

## HTTP Client

```
java -jar node.jar http client 10 localhost:8000/test
```

This will create an HTTP node that sends 10 requests a second to localhost on port 8000 for the resource `/test`.

If you would like to test the HTTP rate, run:

`./http_serve.py`

Which is python script that will listen on localhost:8000 for requests.

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
The default port for Californium is 5683, so that port is used by default. 
After the initial run, a Californium.properties file will be made which can be modified for further configuration.

# Configuration

No configuration is necessary for the nodes, other than the arguments passed to it.

