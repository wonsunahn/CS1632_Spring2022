- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Description](#description)
  * [Connecting to thoth.cs.pitt.edu](#connecting-to-thothcspittedu)
  * [Building](#building)
  * [Testing and Debugging Memory Errors](#testing-and-debugging-memory-errors)
    + [Turning off ASLR (Address Space Layout Randomization)](#turning-off-aslr-address-space-layout-randomization)
    + [Using Google ASAN (Address Sanitizer)](#using-google-asan-address-sanitizer)
    + [Debugging](#debugging)
    + [Comparing Google ASAN with Valgrind](#comparing-google-asan-with-valgrind)
  * [Testing and Debugging Datarace Errors](#testing-and-debugging-datarace-errors)
    + [Using Google TSAN (Thread Sanitizer)](#using-google-tsan-thread-sanitizer)
    + [Debugging](#debugging-1)
  * [Submission](#submission)
  * [Division of Work](#division-of-work)
  * [Resources](#resources)

# CS 1632 - Software Quality Assurance
Spring Semester 2022 - Supplementary Exercise 1

DUE: Jan 28 (Friday), 2022 11:59 PM

**GitHub Classroom Link:** https://classroom.github.com/a/X8oN2isF

When you accept the assignment in GitHub Classroom, you will be asked to create
a new Team, or choose an existing Team.  If you are the first group member to
accept the assignment, please create a new Team.  Otherwise, please look for an
existing Team that your partner has already created.  Please use the team name
that you put on your **Partnership Contract**.  If you haven't yet decided on a
name, choose any name your prefer --- just keep it classroom friendly.

## Description

This set of code demonstrates concepts we learned in the Software QA and
Nondeterminism lecture.  By trying out these programs, you will learn the following:

1. Observe how values of pointers in C are randomized through ASLR leading to nondeterministic program behavior.

1. Observe how pointer values can leak out to program output through memory errors.

1. Learn how to turn ASLR off to make C pointers deterministic.

1. Learn how to use ASAN (Google Address Sanitizer) to debug stack overflow memory errors.

1. Learn how to use ASAN (Google Address Sanitizer) to debug dangling pointer memory errors.

1. Observe how dataraces in C leads to nondeterministic program behavior.

1. Learn how to use TSAN (Google Thread Sanitizer) to debug datarace errors.

1. Compare ASAN with Valgrind, another memory error detection tool.

## Connecting to thoth.cs.pitt.edu

In order to use ASAN or TSAN, you need to clang version >= 3.1 or gcc version >= 4.8.  Since you are unlikely to have either installed on your local
computer, I will ask you to connect using SSH to one of the departmental public
Linux servers at thoth.cs.pitt.edu.

If you use Windows, please follow these steps:

1. Every OS comes with an SSH commandline client nowadays.  Open a commandline shell (e.g. cmd, terminal) then type:
   ```
   $ ssh USERNAME@thoth.cs.pitt.edu
   ```
   Where USERNAME is replaced with your own Pitt ID.

   If you would like a GUI SSH client, Putty is a free open source SSH terminal:
   https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html
   Connect to "thoth.cs.pitt.edu" by typing that in the "Host Name" box.  Make sure that port is 22 and SSH is selected in the radio button options.

1. Once connected, the host will ask for your Pitt credentials.  Enter your Pitt ID and password.

Once logged in, you may see an unsettling welcome message showing system
diagnostics.  Don't panic, the machine is just going through some system
updates and admins haven't yet settled on a nice welcome message. :) 

## Building

Once logged in, you will also notice that your home directories are empty,
which may be unsettling for those of you who used thoth before and had files
there.  That is because the admins have not yet connected your thoth accounts
to your departmental AFS (Andrew File System) home directories.  For now, your
home directories are located in the local hard disk.  These home directories
will be razed once the semester is over.

Create and go to a directory of your choice (or you can stay at your default
home directory) and then clone your GitHub Classroom repository:

```
$ git clone <your GitHub Classroom repository HTTPS URL>
```

This will ask for your Username and Password.  Username is your GitHub
account username, but Password is not your password.  Password
authentication on GitHub has been deprecated on August 2021, so now you have
to use something called a Personal Authenication Token (PAT) in place of the
password.  Here are instructions on how to create a PAT:
https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token

Now cd into your cloned directory.  I have provided a Makefile build script
to automate the build.  All you have to do is invoke 'make':

```
$ make
gcc -c -g -w heap.c -o heap.o
gcc heap.o -lm -o heap.bin
gcc -c -g -w stack.c -o stack.o
gcc stack.o -lm -o stack.bin
gcc -c -g -w stack_overflow.c -o stack_overflow.o
gcc stack_overflow.o -lm -o stack_overflow.bin
gcc -c -g -w stack_pointer_return.c -o stack_pointer_return.o
gcc stack_pointer_return.o -lm -o stack_pointer_return.bin
gcc -c -g -w binary_tree.c -o binary_tree.o
gcc binary_tree.o -lm -o binary_tree.bin
gcc -c -g -w -pthread datarace.c -o datarace.o
gcc datarace.o -lm -pthread -o datarace.bin
gcc -c -g -w -fsanitize=address stack_overflow.c -o stack_overflow.asan.o
gcc stack_overflow.asan.o -lm -fsanitize=address -o stack_overflow.asan
gcc -c -g -w -fsanitize=address stack_pointer_return.c -o stack_pointer_return.asan.o
gcc stack_pointer_return.asan.o -lm -fsanitize=address -o stack_pointer_return.asan
gcc -c -g -w -fsanitize=address binary_tree.c -o binary_tree.asan.o
gcc binary_tree.asan.o -lm -fsanitize=address -o binary_tree.asan
gcc -c -g -w -fPIE -fsanitize=thread -pthread datarace.c -o datarace.tsan.o
gcc datarace.tsan.o -lm -pie -fsanitize=thread -o datarace.tsan
```

Note how when I create ASAN instrumented binaries (e.g. stack_overflow.asan,
stack_pointer_return.asan, ...), I pass the **-fsanitize=address** compiler option
to gcc.  You need to pass it to both the compilation stage and the linking
stage.

Also note how when I create TSAN instrumented binaries (e.g. datarace.tsan)
I pass the **-fsanitize=thread** compiler option
to gcc.  I also pass the **-fPIE** and **-pie** options to the compilation and linking
stages respectively.  This makes your code position independent, and is needed for
TSAN to work flawlessly (I'm assuming you learned what PIE is in CS 449).

## Testing and Debugging Memory Errors

### Turning off ASLR (Address Space Layout Randomization)

heap.c is a simple program that mallocs some bytes on the heap and prints out
the pointer to that heap location.  You can use 'nano' to view the file on the
terminal (or your favorite Linux editor):

```
$ nano heap.c
```

Or, you can view it on the GitHub.  As we learned, even this simple program can
display nondeterministic behavior due to ASLR.  Try it out yourself!

```
$ ./heap.bin
p = 0x55de862c92a0
$ ./heap.bin
p = 0x56317e9b52a0
$ ./heap.bin
p = 0x5654dc8022a0
```

Your actual values will vary but you can see how the output is randomized.

Likewise, stack.c is a simple program that prints out the pointer to a stack
location.  And it also displays nondeterministic behavior due to ASLR:

```
$ ./stack.bin
p = 0x7fff5e1060d0
$ ./stack.bin
p = 0x7fff4526e850
$ ./stack.bin
p = 0x7ffd5c507230
```

Now, let's try running both with ASLR turned off.  I've written a simple script
named 'run_aslr_off.sh' that does exactly that:

```
$ bash run_aslr_off.sh ./heap.bin
setarch x86_64 -R ./heap.bin
p = 0x5555555592a0
$ bash run_aslr_off.sh ./heap.bin
setarch x86_64 -R ./heap.bin
p = 0x5555555592a0
$ bash run_aslr_off.sh ./heap.bin
setarch x86_64 -R ./heap.bin
p = 0x5555555592a0
$ bash run_aslr_off.sh ./stack.bin
setarch x86_64 -R ./stack.bin
p = 0x7fffffffe3e0
$ bash run_aslr_off.sh ./stack.bin
setarch x86_64 -R ./stack.bin
p = 0x7fffffffe3e0
$ bash run_aslr_off.sh ./stack.bin
setarch x86_64 -R ./stack.bin
p = 0x7fffffffe3e0
```

Note that now the output is no longer random!  This is what it says if you 'man
setarch':

```
$ man setarch
...
 -R, --addr-no-randomize
              Disables randomization of the virtual address space (turns on ADDR_NO_RANDOMIZE).
...
```

Did you ever get the feeling that your C program that used to behave randomly
suddenly becomes deterministic when you run it on top of GDB (GNU Debugger)?
That is because GDB by default turns off ASLR for debugging purposes so that
behavior is reproducible.  Turning off ASLR can be very useful in a debug
setting.

### Using Google ASAN (Address Sanitizer)

stack_overflow.c is a buggy program that demonstrates the stack buffer overflow
issue that we discussed in the lecture.  In the main function, it starts by
creating a linked list of 3 nodes on the stack.  Then, it sends 16 bytes of
first.data to the screen:

```
send_data(first.data, 16);
```

First, let's try executing the program a few times as before:

```
$ ./stack_overflow.bin
[Sent data]
48 65 6c 6c 6f 2e 2e  0 b0 eb 75 61 fd 7f  0  0
$ ./stack_overflow.bin
[Sent data]
48 65 6c 6c 6f 2e 2e  0 b0 49 e2 96 fc 7f  0  0
$ ./stack_overflow.bin
[Sent data]
48 65 6c 6c 6f 2e 2e  0 f0 66 5f 9d ff 7f  0  0
```

You can see that this is also a nondeterministic program.  But we only sent
data to the output and no addresses this time, so where did the nondeterminism
come from?  If you look more closely, you will notice that the first 8 bytes do
not change from run to run.  If you know ASCII code, you will be able to
decypher it to he the "Hello.." string (with nul terminator) inside first.data.
Then, what are the second 8 bytes that keep on changing?  For that, let's try
running stack_overflow in verbose mode using the "-v" option:

```
$ ./stack_overflow.bin -v
[Stack]
third.data = .......
third.next = (nil)
second.data = World..
second.next = 0x7ffd6175ebb0 <--- Sent!
first.data = Hello.. <--- Sent!
first.next = 0x7ffd6175eba0
[Sent data]
48 65 6c 6c 6f 2e 2e  0 b0 eb 75 61 fd 7f  0  0
```

You will notice that the second 8 bytes is the pointer address inside
second.next (in reverse, since x86 architecture uses little endian ordering)!
So why is second.next being sent along with first.data?  That is because the
first.data buffer is only 8 bytes long, so when send_data attempts to send 16
bytes, it also sends the 8 bytes that come after first.data, which in the stack
layout happens to be second.next.

In short, the address inside second.next **leaks out** to program output even
though the programmer never intended it in the source code.  And this address
randomized by ASLR is what is causing the nondeterminism.  Of course, you could
again turn off ASLR to make the buggy program run deterministically at least
while debugging:

```
$ bash run_aslr_off.sh ./stack_overflow.bin
setarch x86_64 -R ./a.out
[Sent data]
48 65 6c 6c 6f 2e 2e  0 e0 e3 ff ff ff 7f  0  0
$ bash run_aslr_off.sh ./stack_overflow.bin
setarch x86_64 -R ./a.out
[Sent data]
48 65 6c 6c 6f 2e 2e  0 e0 e3 ff ff ff 7f  0  0
$ bash run_aslr_off.sh ./stack_overflow.bin
setarch x86_64 -R ./a.out
[Sent data]
48 65 6c 6c 6f 2e 2e  0 e0 e3 ff ff ff 7f  0  0
```

But your end users will most likely have ASLR turned on in their machines for
security.  What then?  Your programs will again be nondeterministic and testing
would no longer guarantee correct behavior.  So we may still get surprise
defects.

How can we have a deterministic program when all addresses are randomized?
Easy: just don't let addresses leak out to program output!  As we discussed,
unless for debugging purposes, programs will almost never intentionally output
addresses where data is stored --- they will typically output the data.  It is
just that addresses leak out to output due to memory errors (like in this
case).  So if we can catch all memory errors, then problem solved!  ASAN is
exactly the kind of tool that can help you do that.

Now let's see if ASAN can find the bug for us by running the instrumented binary:

```
$ ./stack_overflow.asan
[Sent data]
=================================================================
==2438106==ERROR: AddressSanitizer: stack-buffer-overflow on address 0x7fffd2a23770 at pc 0x556267aa2353 bp 0x7fffd2a236f0 sp 0x7fffd2a236e0
READ of size 1 at 0x7fffd2a23770 thread T0
    #0 0x556267aa2352 in send_data /home/PITT/wahn/nondeterminism/C/stack_overflow.c:17
    #1 0x556267aa2809 in main /home/PITT/wahn/nondeterminism/C/stack_overflow.c:45
    #2 0x7f5c31cfd0b2 in __libc_start_main (/lib/x86_64-linux-gnu/libc.so.6+0x270b2)
    #3 0x556267aa222d in _start (/u/home/PITT/wahn/nondeterminism/C/stack_overflow.asan+0x122d)

Address 0x7fffd2a23770 is located in stack of thread T0 at offset 48 in frame
    #0 0x556267aa2394 in main /home/PITT/wahn/nondeterminism/C/stack_overflow.c:22

  This frame has 3 object(s):
    [32, 48) 'first' (line 23) <== Memory access at offset 48 overflows this variable
    [64, 80) 'second' (line 23)
    [96, 112) 'third' (line 23)
...
```

ASAN is able to pinpoint exactly where the illegal "READ of size 1" happened at
stack_overflow.c:17!  That is where the out of bounds array access happens.
Below that line is the stack trace so we know the calling context.

stack_pointer_return.c is another buggy program with a common memory error
where a function returns a pointer to a local array.  When the function
returns, the local array is deallocated with the rest of the function frame
as it is now out of scope, thereby leaving the pointer dangling.  Similar to
the stack overflow memory error, accessing dangling pointers results in
**undefined behavior** according to the C language specifications.  The
particular version of GCC installed on the machine (GCC 9.3.0) chose to set
this dangling pointer to a null pointer before returning from the function
bar() rather than leaving it dangling (which is a good choice).  So, you get
deterministic behavior in this case --- it's just that accessing a null
pointer results in a segmentation fault:

```
$ ./stack_pointer_return.bin
Segmentation fault (core dumped)
```

Old versions of GCC and some other compilers would just leave the pointer
dangling which would cause an access of a dangling pointer.  So what happens
then?  Well, the pointer is dangling because the memory that it used to
point to is deallocated.  That piece of memory eventually gets reallocated
to hold other values (in this case, when a new stack frame is allocated).
If that value is an address, you would get nondeterministic behavior.

In any case, it is a memory error that needs to be fixed!  So let's see if
ASAN can help us this time as well:

```
$ ./stack_pointer_return.asan
[Sent data]
AddressSanitizer:DEADLYSIGNAL
=================================================================
==2438240==ERROR: AddressSanitizer: SEGV on unknown address 0x000000000000 (pc 0x561accf992c7 bp 0x7ffc3c7d7630 sp 0x7ffc3c7d7610 T0)
==2438240==The signal is caused by a READ memory access.
==2438240==Hint: address points to the zero page.
    #0 0x561accf992c6 in send_data /home/PITT/wahn/nondeterminism/C/stack_pointer_return.c:7
    #1 0x561accf99428 in main /home/PITT/wahn/nondeterminism/C/stack_pointer_return.c:22
    #2 0x7f40b08d10b2 in __libc_start_main (/lib/x86_64-linux-gnu/libc.so.6+0x270b2)
    #3 0x561accf991ad in _start (/u/home/PITT/wahn/nondeterminism/C/stack_pointer_return.asan+0x11ad)

AddressSanitizer can not provide additional info.
SUMMARY: AddressSanitizer: SEGV /home/PITT/wahn/nondeterminism/C/stack_pointer_return.c:7 in send_data
==2438240==ABORTING
```

Again, stack_pointer_return.c:7 is flagged as an illegal read because it is
attempting to read a location that has already been deallocated.  

### Debugging

Modify stack_overflow.c and stack_pointer_return.c so that they no longer
contain memory errors.  You can use 'nano', a very simple editor:

```
$ nano stack_overflow.c
```

Or you can use your favorite Linux editor (mine is Vim), or feel free to
edit the files on your favorite IDE on your laptop and upload to thoth.

Once you are done, invoke 'make' again to recompile the binaries:

```
$ make
gcc -c -g -w stack_overflow.c -o stack_overflow.o
gcc stack_overflow.o -lm -o stack_overflow.bin
gcc -c -g -w -fsanitize=address stack_overflow.c -o stack_overflow.asan.o
gcc stack_overflow.asan.o -lm -fsanitize=address -o stack_overflow.asan
...
```

Now, you should now see the data properly sent to the output in both cases:


```
$ ./stack_overflow.asan 
[Sent data]
48 65 6c 6c 6f 2e 2e  0
```

```
$ ./stack_pointer_return.asan
[Sent data]
 1  2  3  4  5  6  7  8 
```

Since there are no memory errors, the ASAN instrumentation should not output
any errors.

If you are stuck debugging the programs, here are some hints:

1. stack_overflow.c contains a stack overflow, so the solution is to reduce the
   number of bytes to fit within the provided buffer.
1. stack_pointer_return.c is attempting to return a pointer to a stack
   location.  One way to fix it is to declare the array to be a static local
variable so that it gets moved from the stack to static memory which has
persistent duration.  

### Comparing Google ASAN with Valgrind

You may have used a runtime memory error checking tool called Valgrind in CS
449: Introduction to System Software or somewhere else.  In terms of purpose,
ASAN and Valgrind share common goals.  However, ASAN is superior to Valgrind in
some ways.  That is because ASAN performs instrumentation at the source code
level whereas Valgrind performs instrumentation at the binary level.  A lot of
the semantic information that was present at the source code level is removed
at the binary level, meaning Valgrind instrumentation cannot be as detailed and
as efficient as ASAN instrumentation.  

For one thing, ASAN is much faster than Valgrind.  Since the source code
provides much more semantic information, ASAN can make a much better decision
on where instrumentation is needed.  Also, the instrumentation gets optimized
along with other code using compiler optimizations.  'binary_tree.c' is a
benchmark in the [Language Shootout Benchmark
Suite](https://benchmarksgame-team.pages.debian.net/benchmarksgame/index.html).
Let's time 'binary_tree.c' a) running without instrumentation, b) running with
ASAN instrumentation, and c) running with Valgrind instrumentation:

   ```
   $ time ./binary_tree.bin 10
   ...
   real    0m0.012s
   user    0m0.012s
   sys     0m0.000s

   $ time ./binary_tree.asan 10
   ...
   real    0m0.174s
   user    0m0.145s
   sys     0m0.029s

   $ time valgrind ./binary_tree.bin 10
   ...
   real    0m1.831s
   user    0m1.723s
   sys     0m0.108s
   ```

   'time' is a Linux utility used to time an application.  The last three rows
starting with 'real', 'user', and 'sys' is output from the 'time' utility and
not from the application.  We are going to learn more about it when we talk
about Performance Testing, but for now, all you need to know is that 'real'
measures real time (as in actual wall clock time to run an application).  As
you can see, ASAN results in an approximately 14.5X slowdown whereas Valgrind
results in an approximately 152.6X slowdown!

So, is Valgrind obsolete?  No, Valgrind does have one strong point: that it can
instrument binaries without the need of source code and without the need of
recompilation.  But if you do have the source code (which is typically the case
for tested software), most people would prefer ASAN over Valgrind.

## Testing and Debugging Datarace Errors

### Using Google TSAN (Thread Sanitizer)

datarace.c is a buggy program with a datarace on the variable 'shared'.  Hence,
everytime you run the program you will get nondeterministic output:

```
$ ./datarace.bin
shared=1024461
$ ./datarace.bin
shared=1041862
$ ./datarace.bin
shared=1021775
```

Now let's try using TSAN to discover this bug by running the instrumented binary:

```
$ ./datarace.tsan
==================
WARNING: ThreadSanitizer: data race (pid=2438881)
  Write of size 4 at 0x55eb33b6f014 by thread T1:
    #0 add /home/PITT/wahn/nondeterminism/C/datarace.c:8 (datarace.tsan+0x12af)

  Previous read of size 4 at 0x55eb33b6f014 by main thread:
    #0 add /home/PITT/wahn/nondeterminism/C/datarace.c:8 (datarace.tsan+0x129a)
    #1 main /home/PITT/wahn/nondeterminism/C/datarace.c:18 (datarace.tsan+0x1325)

  Location is global 'shared' of size 4 at 0x55eb33b6f014 (datarace.tsan+0x000000004014)

  Thread T1 (tid=2438883, running) created by main thread at:
    #0 pthread_create ../../../../src/libsanitizer/tsan/tsan_interceptors_posix.cpp:962 (libtsan.so.0+0x5ea79)
    #1 main /home/PITT/wahn/nondeterminism/C/datarace.c:16 (datarace.tsan+0x131b)

SUMMARY: ThreadSanitizer: data race /home/PITT/wahn/nondeterminism/C/datarace.c:8 in add
==================
shared=1000000
ThreadSanitizer: reported 1 warnings
```

It tells you exactly what each thread was doing to cause the datarace.  The
"main thread" was executing add in line datarace.c:8 and "thread T1" (the
child thread) was likewise executing add at the same source line.  That is
exactly where the unprotected 'shared++' is happening.

### Debugging

Modify datarace.c so that it no longer contains datarace errors.  Edit and
compile using 'make' as before.  After debugging, you should now see the
shared variable incremented 2000000 times every time you run it:

```
$ ./datarace.bin
shared=2000000
```

Hint: you will have to use the pthreads mutex API to acquire and relase the
lock while the update to the shared variable is happening.  Use the
pthread_mutex_init, pthread_mutex_lock, and pthread_mutex_unlock APIs you
learned in CS 449.  If you need a reminder, here is the pthread_mutex_init manpage:
https://linux.die.net/man/3/pthread_mutex_init

There is some example code at the bottom of the manpage.  For your uses, using
the PTHREAD_MUTEX_INITIALIZER initializer should suffice.

## Submission

Once you got it working, it's time to commit your changes and push them to
the GitHub.com origin repository.  Before you do that, let's clean up the
repository such that you remove all generated binary files:

```
$ make clean
```

Once you do that, if you do 'git status' you should see only three modified
files:

```
$ git status
On branch main
Your branch is up to date with 'origin/main'.

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
	modified:   datarace.c
	modified:   stack_overflow.c
	modified:   stack_pointer_return.c

no changes added to commit (use "git add" and/or "git commit -a")
```

Now, let's commit those files to your local repository (after adding the
modifications using the -a option):

```
$ git commit -a
```

That will launch an editor where you can add comments.  After saving the
comments, you will see the commit happening.  Now the only thing left to do
is to push the changes to the origin:

```
$ git push
```

Feel free to double check that your changes have to been reflected on to
your GitHub Classroom origin repository.  Please submit that repository to
the "Supplementary Exercise 1 GitHub" link.

Again, don't forget to add your partner to each submission.  You can
resubmit as many times as you with until you get a perfect score on the
autograder.

## Division of Work

For this exercise, I recommend that you both try to do the full exercise.
Compare your debugged files in the end, discuss, and submit!

## Resources

* Reproducible Builds Website: https://reproducible-builds.org/docs/
* Windows SSH Terminal Client: [Putty](https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html)
* File Transfer Client: [FileZilla](https://filezilla-project.org/download.php?type=client)
* Linux command line tutorial: [The Linux Command Line](http://linuxcommand.org/lc3_learning_the_shell.php)

Stack overflow, stack pointer return, and data race are also well know security
vunerabilities documented by MITRE in the Common Weakness Enumeration (CWE).

* CWE-121: Stack-based Buffer Overflow: https://cwe.mitre.org/data/definitions/121.html
* CWE-562: Return of Stack Variable Address: https://cwe.mitre.org/data/definitions/562.html
* CWE-362: Concurrent Execution using Shared Resource with Improper Synchronization ('Race Condition'): https://cwe.mitre.org/data/definitions/362.html
