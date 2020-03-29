#! /bin/bash

javac dxk180015/*.java
echo "---------- 1 Million ----------"
java dxk180015.Cuckoo$HashingDriverMain 1000000 0
echo ''
java dxk180015.Cuckoo$HashingDriverMain 1000000 1
echo ''

echo "---------- 2 Million ----------"
java dxk180015.Cuckoo$HashingDriverMain 2000000 0
echo ''
java dxk180015.Cuckoo$HashingDriverMain 2000000 1
echo ''

echo "---------- 3 Million ----------"
java dxk180015.Cuckoo$HashingDriverMain 3000000 0
echo ''
java dxk180015.Cuckoo$HashingDriverMain 3000000 1
echo ''

echo "---------- 4 Million ----------"
java -Xms2g dxk180015.Cuckoo$HashingDriverMain 4000000 0
echo ''
java -Xms2g dxk180015.Cuckoo$HashingDriverMain 4000000 1
echo ''

echo "---------- 5 Million ----------"
java -Xms2g dxk180015.Cuckoo$HashingDriverMain 5000000 0
echo ''
java -Xms2g dxk180015.Cuckoo$HashingDriverMain 5000000 1
echo ''

echo "---------- 8 Million ----------"
java -Xms2g dxk180015.Cuckoo$HashingDriverMain 8000000 0
echo ''
java -Xms2g dxk180015.Cuckoo$HashingDriverMain 8000000 1
echo ''

echo "---------- 10 Million ----------"
java -Xms3g dxk180015.Cuckoo$HashingDriverMain 10000000 0
echo ''
java -Xms3g dxk180015.Cuckoo$HashingDriverMain 10000000 1
echo "-------------------------------"

exit 0