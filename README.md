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

## server.clients.
 * __HttpDownloader__ demonstrates how www ressources can be accessed an downloaded by the help of the URL class.

 * __NasaAstronomyPicViewer__ downloads the current NASA picture of the day (using HttpDownloader)
and shows it in a Swing-UI.

 * __Webcrawler.WebCrawler__ runs through the tags of the web-page given by the url
to collect picture information and download the image files.

## server.sockets.
 * __SingleClassSocketDemo__ is Server and Client at the same time
using an extra thread for the server.

 * __chat.ChatServer__ implements a simple console based chat

 * __echo.EchoServer__ implements a simple console based echo-server,
the server just echos all incoming messages from the clients.

 * __simpleserver.SimpleDateServer__ is a TCP server that runs on port 9090.
When a client connects, it sends the client the current date and time,
then closes the connection with that client.
Arguably just about the simplest server you can write.
   (see http://cs.lmu.edu/~ray/notes/javanetexamples/)

 * __telnet.TelnetServer__ is the server of the TelnetServer-Demo
The server listens for incoming connections from new clients and
delegates the newly created socket for a client to an extra TelnetService thread to be answered.
Remark: Run an instance of this before starting any clients.
Login/Password is hardcoded, use "java" and "class".
ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!

 * __telnetWithCHAP.TelnetWithCHAPServer__ is the server of the TelnetServerWithCHAP-Demo
The server listens for incoming connections from new clients and
delegates the newly created socket for a client to an extra TelnetService thread to be answered.
Run an instance of this before starting any clients.
For authentication the CHAP (challenge-authentication-protocol) is implemented.
The users are hard-coded, use java/class or admin/1234.
ATTENTION: the commands entered will be executed in a command-shell (cmd.exe) on the server!

 * __telnetWithCHAP.BruteForcePasswordCrackerCHAPClient__ demonstrates a brute-force password attack
implementing CHAP (challenge-authentication-protocol).
