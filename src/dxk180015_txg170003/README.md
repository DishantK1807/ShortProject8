SP-8 submitted by Tushar Gonawala(txg170003) and Dishant Khanna(dxk180015)

According to our program, we have implemented a Cuckoo Hashing Algorithm and 
compared it's performance with Java's HashTable/HashMap/HashSet on millions of 
operations: add, contains, and remove.
 
Steps to compile and run the program: (If you run on an ide)

    Download and import the package folder from the program file.
    Run the following command.

(If you run on command run prompt)

    Unzip the folder.
    javac dxk180015_txg170003/*.java
    dxk180015_txg170003.HashingDriverMain [num_of_values] [choice] 

Replace the "num_of_values" with the number of values, 1000000, 10000000, etc.

Replace choice with either 0[HashSet] or 1[Cuckoo Hashing]:

For values above 3 million, use -Xms parameter with the java command

Example: 

    java -Xms3g dxk180015_txg170003.HashingDriverMain 10000000 0
    java -Xms3g dxk180015_txg170003.HashingDriverMain 10000000 1


Sample Input:

    java dxk180015_txg170003.HashingDriverMain 3000000 0
    java dxk180015_txg170003.HashingDriverMain 3000000 1

Sample Output:
          
     Java HashSet: 3000000
     Choice: 0
     Time: 3633 msec.
     Memory: 194 MB / 570 MB.
     
     Cuckoo Hashing: 3000000
     Choice: 1
     Time: 8382 msec.
     Memory: 816 MB / 2228 MB.
