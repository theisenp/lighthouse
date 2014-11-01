Lighthouse
==========

Lighthouse is Swing application that displays the active [Harbor][1] clients on the network.

Usage
-----

### Maven ###

To run Lighthouse from Maven, type:

    mvn exec:java
    
You can also provide alternate Harbor configuration parameters:

    mvn exec:java -Dexec.args="-a 239.255.76.67 -p 7667 --ttl 1 --period 1000 --timeout 5000"
    
To print the help information, type:

    mvn mvn exec:java -Dexec.args="--help"
    
### Command Line ###

To run Lighthouse from the command line, type:

    java -jar lighthouse.jar
    
You can also provide alternate Harbor configuration parameters:

    java -jar lighthouse.jar -a 239.255.76.67 -p 7667 -ttl 1 -period 1000 -timeout 5000
    
To print the help information, type:

    java -jar lighthouse.jar -help
    
Build
-----

Lighthouse is built with [Maven][2]. To build the project and install it in your local Maven repositiory, type:

	mvn clean install

Download
--------

To include Lighthouse in another Maven project, add the following dependency to the project's `pom.xml`:

```xml
<dependency>
  <groupId>com.theisenp.harbor</groupId>
  <artifactId>lighthouse</artifactId>
  <version>(latest version)</version>
</dependency>
```

Developed By
------------

* Patrick Theisen - <theisenp@gmail.com>

License
-------

    Copyright 2014 Patrick Theisen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: https://github.com/theisenp/harbor
[2]: http://maven.apache.org/