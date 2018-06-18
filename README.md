**jobber** - A simulation of jobs performed by a set of processors, where there might be more jobs than processors 
              therefore a queue must be made to wait until an open processor.
              
              
### **Run the program:** ###

Firstly, download all the files in this repository, by pasting this in the terminal or cmd (git clone 
https://github.com/oasysokubo/jobber.git). After downloaded, put all files into one directory file including the Makefile,
Program will not be able to run without all the files being in the same directory. 
Then, type in the following, 
| $ Simulation input_file |
into the command line argument, where the input_file will be found in the 'in' files.
Output should be named as 'x.rpt' and 'ex.trc' corrosponding to which test file was used.


This program can be thought out as some shoppers at a grocery store waiting in line at the check-out stands.
Abstractly, a job is an encapsulation of three quantities: arrival time, duration, and finish time. The arrival time
is the time at which a job becomes avaiable for processing. This is analogous to the time at which a shopper reaches the 
check-out stand area. If there is a free Processor, that job's "work" may begin. If not, it must wait in a processor queue. 
The duration of a job is the amount of processor time it will consume once it reaches a processor. This quantity is analogous 
to the amount of goods in the shopper’s basket. Both arrival time and duration are intrinsic to the job and are known ahead of 
time, whereas the finish time is only known when the job reaches the head of a processor queue. The finish time can then be 
calculated as start_time + duration. Before a job is underway its finish time will be considered undefined. Once a given job’s 
finish time is known, we can calculate the amount of time spent waiting in line, not counting processing time. Thus wait_time 
= finish_time – arrival_time – duration. In this simulation time will be considered a discrete quantity starting at 0 and 
assuming only non- negative integer values.

