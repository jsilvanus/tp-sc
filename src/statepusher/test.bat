@echo off
rem ##  Trying to test TP plugin

echo Compiling sp.java
javac --release 8 StatePusher.java

Echo Running sp.
Echo Test: set tpsc_state to closed
java StatePusher closed
