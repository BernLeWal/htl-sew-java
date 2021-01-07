# htl-sew-java
This repo contains sample projects for the sew java course.

# Project structure

## core.collections
 * __CollectionsDemo__ shows in a simple very straight-forward way
the usage of the most common collections: ArrayList, HashSet and HashMap
in terms of creating, iterating, modifying and deletion.

## core.exceptions
 * __NumberFormatExceptionDemo__ shows the usage of try-catch
to handle NumberFormatExceptions in the process to convert
a strings content into a int-number.
   
## core.io
 * __BasicStreamDemo__ shows how to work with the basic InputStream and OutputStream
just by using the read() and write() methods
only capable of dealing with bytes and byte-arrays
 * __ObjectStreamDemo__ shows how to easily transfer complete objects between Object-Streams.  
Remark: the objects need to implement the Serializable interface in order to be enabled for transferation.

### core.io.file
 * __DirectorySamples__ shows various methods to access directories and directory-contents of the file system.
 * __FileStreamSamples__ shows how to read and write files in text or binary mode.
 * __ScannerDemo__ demonstrates how to parse a files content with the Scanner class.

### core.io.text
 * __BufferedStreamDemo__ shows how to work with complete strings provided by BufferedReader and BufferedWriter
 * __PrintStreamDemo__ shows how to work with strings in comfortable way provided by PrintWriter.

## server.clients
 * __HttpDownloader__ demonstrates how www ressources can be accessed an downloaded by the help of the URL class.

 * __NasaAstronomyPicViewer__ downloads the current NASA picture of the day (using HttpDownloader)
and shows it in a Swing-UI.

 * __WebCrawler__ runs through the tags of the web-page given by the url
to collect picture information and download the image files.

## server.rest
 * __OpenDataDemo__ accesses the following catalog of "Open Data Österreich" (https://www.data.gv.at/):  
_"Katalog Gestorbene in Österreich (ohne Auslandssterbefälle) ab 2000 nach Kalenderwoche"_  
(https://www.data.gv.at/katalog/dataset/d3b85461-fc0d-3639-9aa9-39211c4ecade)  
It calculates the total amount of deaths per year.  
Attention: when retrieving the URL from the web-site, always change the protocol to "https:", because
the server will return only empty files when "http:" is used!

## server.sockets
 * __SingleClassSocketDemo__ is Server and Client at the same time
using an extra thread for the server.

### server.sockets.chat
 * __ChatServer__ implements a simple console based chat

### server.sockets.echo
 * __EchoServer__ implements a simple console based echo-server,
the server just echos all incoming messages from the clients.

### server.sockets.simpleserver
 * __SimpleDateServer__ is a TCP server that runs on port 9090.  
When a client connects, it sends the client the current date and time,
then closes the connection with that client.  
Arguably just about the simplest server you can write.
   (see http://cs.lmu.edu/~ray/notes/javanetexamples/)

### server.sockets.telnet
 * __TelnetServer__ is the server of the TelnetServer-Demo
The server listens for incoming connections from new clients and
delegates the newly created socket for a client to an extra TelnetService thread to be answered.  
Remark: Run an instance of this before starting any clients.  
Login/Password is hardcoded, use "java" and "class".  
ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!

### server.sockets.telnetWithCHAP
 * __TelnetWithCHAPServer__ is the server of the TelnetServerWithCHAP-Demo
The server listens for incoming connections from new clients and
delegates the newly created socket for a client to an extra TelnetService thread to be answered.  
Run an instance of this before starting any clients.  
For authentication the CHAP (challenge-authentication-protocol) is implemented.  
The users are hard-coded, use java/class or admin/1234.  
ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!

 * __BruteForcePasswordCrackerCHAPClient__ demonstrates a brute-force password attack
implementing CHAP (challenge-authentication-protocol).

## utils
 * __CsvUtils__: Helper functions to read CSV files.
 * __ProcessUtils__: Helper methods to deal with processes.
 * __HashUtils__: Helper functions to deal with strings and hashes.