# au_software_design

##1. Shell

###Command line utility similar to Unix shell.

####Supported features:

* echo command
* cat command
* wc command
* exit command
* pwd command
* environment variables 
* unknown commands are passed to system shell as separate process through Java.Process library.

####Class diagram:
![shell class diagram](https://www.gliffy.com/go/share/image/smx5dub0j39jxied850w.png?utm_medium=live-embed&utm_source=custom)

####Data flow:
  * Main: run Shell object.
  * Shell: Read line from System.in.
   
   * Preprocessor: Substitute environment variables in input string. E. g. "Hello, $name" -> "Hello, Alex"
   * Tokeniser: Split string into a list of tokens: words and operators.
   * Parser: Parse list of tokens as sequence of commands divided by pipes.
   * Command Executor: Perform chained computation passing output of one comand as input to the next one.
   * Pass result to System.in and loop again.
