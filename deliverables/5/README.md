- [CS 1632 - Software Quality Assurance](#cs-1632-software-quality-assurance)
  * [Overview](#overview)
  * [Background](#background)
  * [Compiling and Running](#compiling-and-running)
    + [Luck Mode](#luck-mode)
    + [Skill Mode](#skill-mode)
    + [Text UI Mode](#text-ui-mode)
  * [What to do](#what-to-do)
    + [Plain JUnit Testing](#plain-junit-testing)
    + [Model Checking Using JUnit](#model-checking-using-junit)
    + [Applying Model Checking to Your / Solution / Buggy Implementations](#applying-model-checking-to-your--solution--buggy-implementations)
    + [Debugging on Model Checking Failures](#debugging-on-model-checking-failures)
    + [Add an Extra Invariant Test](#add-an-extra-invariant-test)
    + [Coding](#coding)
    + [Linting and Auditing](#linting-and-auditing)
    + [Manual System Testing](#manual-system-testing)
  * [Grading](#grading)
  * [Submission](#submission)
  * [GradeScope Feedback](#gradescope-feedback)
  * [Resources](#resources)

# CS 1632 - Software Quality Assurance
Spring Semester 2022

DUE: Apr 29 (Friday), 2022 11:59 PM

Due to the grading deadline, there is no late submission.  Please work with your partner to complete the project.

**GitHub Classroom Link:** https://classroom.github.com/a/1mRZ9UWg

## Overview

For this final deliverable, you will develop a full-fledged GUI program
(with the help of some skeleton code), along with various tests.  Don't
worry if you don't know GUI programming --- that part has already been done
for you.  In addition, there is a [Text UI mode](#text-ui-mode) that you can
use to manual test and debug your program.

All the projects so far have used some form of dynamic testing.  In this
project, we will focus grading on static testing.  You will be graded
upon is static testing techniques such as: linting, pattern-based bug finding,
and model checking.  

But you are still expected to use all the techniques we have learned so far:
test-driven development (TDD), automated unit testing, code coverage, and
manual testing to name a few to come up with good quality software.  The
GradeScope autograder will do extensive testing to test various features of
the software.  If any of the tests fail, that means you have not thoroughly
tested your software.

* IMPORTANT: You need Java 8 (1.8.0.231, preferably) to run the Java Path
  Finder model checker.  Make sure you have the correct Java version by doing
"java -version" and "javac -version" before going into the JPF section.  If you
don't have the correct version, here is a link to a folder with installation
packages for each OS:

https://drive.google.com/drive/folders/1E76H7y2nMsrdiBwJi0nwlzczAgTKKhv7

## Background

The bean counter is a device for statistics experiments devised by English
scientist Sir Francis Galton. It consists of an upright board with evenly
spaced pegs in a triangular form.  Beans are dropped from an opening at the top
of the board. Every time a bean hits a peg, it has a 50% chance of falling to
the left or to the right.  In this way, each bean takes a random path and
eventually falls into one of the slots at the bottom of the board.  After all
the beans fall through, the number of beans in each slot is counted.

See the following link for a more detailed description of the machine:
https://en.wikipedia.org/wiki/Bean_machine.

The bean counter had two contributions to statistics by demonstrating the following:
1. When the sample size is large enough, a [binomial distribution](https://en.wikipedia.org/wiki/Binomial_distribution) approaches a [bell curve](https://en.wikipedia.org/wiki/Normal_distribution).
2. It also demonstrated a phenomenon named [regression to the mean](https://en.wikipedia.org/wiki/Regression_toward_the_mean).

Regression to the mean had been (and still is) a source of numerous scientific
misconceptions.  People make conjectures all the time about all types of things
and provide reasons for it.

1. Why is my favorite sports team performing in a mediocre way when it won the championships last year?  Because my favorite player was traded.
2. Why did the crime rate in my city fall down to the national average?  Due to better policing.
3. Why did a student who did exceptionally well on the midterms perform just about average on the finals?  Because the student slacked off.

People always look for reasons for changes in data.  But often the reason
cannot be explained, because there was no reason for the change to begin with.
The change in data can just be due to a statistical anomaly called "regression
to the mean".  For example, an answer to question 3 can simply be that the
student was exceptionally lucky during the midterms (she guessed all multiple
choices and she got them all correct).  In the finals, her luck just wore off
and she got what she deserved.  This is called regression to the mean.
When a data point is on the extremes of the bell curve, it is often not because
there is anything special about that data point, it is because the laws of
probability worked in favor of it (or against it, depending on context) for
that particular trial.  If that's the case, chances are that the data point
will move to the mean in the next trial.

Now if the exceptional score was due to skill, then the regression would not
happen unless there was a regression in skill.  The problem is, it is very hard
to tell whether something was due to luck or skill just by looking at the
results, hence the numerous misconceptions.

## Compiling and Running

The program simulates a bean machine with 10 slots at the bottom (0-9).

Let's first compile the program by running compile.bat:
```
$ compile.bat
```
Mac/Linux users, please run compile.sh.  Run the .sh extension for all .bat files that follow.

The program is executed with two commandline arguments:
```
$ java -cp bin BeanCounterGUI
Usage: java BeanCounterGUI <number of beans> <luck | skill>
Example: java BeanCounterGUI 500 luck 
```

The second argument "luck" or "skill" decides whether individual beans will use
luck or skill in navigating the bean machine.  

Let's do some exploratory testing.  You could use the class files that you have
just compiled, but the app doesn't do much at this point because the internal
logic has not yet been implemented (by you).  Instead, let's use a reference
implementation (that I wrote) named BeanCounterSolution.jar.

### Luck Mode

In luck mode, the bean counter operates conventionally as originally built by
Galton: a bean has an equal chance of going left or right on a peg.  So where
the bean lands at the bottom is purely due to luck.  Hence, you would expect
the beans to be heavily susceptible to regression to the mean.  Try the following:

1. Run BeanCounterSolution.jar in luck mode:
```
$ java -jar BeanCounterSolution.jar 500 luck
```
2. Press the "Fast" button to fast-forward to the end.
3. Note the average (should be close to 4.5 = 0 + 9 / 2).
4. Press the "Upper Half" button to just take the upper half of the sample.
5. Note the average (now it should be much higher since it's the upper half).
6. Press the "Repeat" button to scoop all the beans and bring them to the top.
7. Press the "Fast" button to restart the machine.
8. Note the average is again close to 4.5.

You have just observed regression to the mean.  You took the upper half of the
class, but when they were put through the exam again, they scored just about
average.  Did they slack off in the second exam?  No, they were just no better
than the other students to begin with.

To run your own (currently incomplete) code, you will be doing:
```
$ java -cp bin BeanCounterGUI 500 luck
```

### Skill Mode

In skill mode, the beans choose direction based on pure skill.  Each bean is
assigned a skill level from 0-9 on creation according to a bell curve with
average 4.5 and standard deviation 1.5.  A skill level of 9 means the bean
always makes the "right" choices (pun intended).  That means the bean will
always go right when a peg is encountered, resulting it falling into the
rightmost 9th slot. A skill level of 0 means that the bean will always go left,
resulting it falling into the leftmost 0th slot. For the in-between skill
levels, the bean will first go right then left. For example, for a skill level
of 7, the bean will go right 7 times then go left twice.  So where the bean
lands at the bottom would be determined entirely by the skill level of the
bean.  Try the following:

1. Run BeanCounterSolution.jar in skill mode:
```
$ java -jar BeanCounterSolution.jar 500 skill
```
2. Press the "Fast" button to fast-forward to the end.
3. Note the average (should be close to 4.5 = 0 + 9 / 2).
4. Press the "Upper Half" button to just take the upper half of the sample.
5. Note the average (now it should be much higher since it's the upper half).
6. Press the "Repeat" button to scoop all the beans and bring them to the top.
7. Press the "Fast" button to restart the machine.
8. Note the average is exactly the same as the average noted in Step 5.

You will observe that the average does not change at all when you repeat the
experiment with the upper half of the samples.  There is no regression to the
mean because the results are pre-determined by skill level.  In this case, the
student performed well on the first exam because they were actually skilled!

Also, you will notice the slots filling sequentially one by one in the repeat
run.  This is a side-effect of the slots at the bottom being collected one by
one when the repeat button is pressed.  All the beans in one slot have the same
skill level so the beans naturally get sorted out as a result of the
collection.

To run your own (currently incomplete) code, you will be doing:
```
$ java -cp bin BeanCounterGUI 500 skill
```

### Text UI Mode

You will notice that BeanCounterLogicImpl.java has an alternate main()
method.  This main() method is used to provide a rudimentary text user
interface.  You can invoke it by doing:

```
java -cp BeanCounterSolution.jar BeanCounterLogicSolution 10 500 luck debug
```

This is the usage information that gets printed when you don't pass any arguments:

```
Usage: java BeanCounterLogic slot_count bean_count <luck | skill> [debug]
Example: java BeanCounterLogic 10 400 luck
Example: java BeanCounterLogic 20 1000 skill debug
```

The last optional debug enables verbose output that prints the state of the
bean counter at each step.  This makes debugging easier as it is able to test
your bean counter logic in isolation from the GUI.  It also allows you to
adjust the slot count, something which is not possible with the GUI.

To run your own (currently incomplete) code, you will be doing:
```
$ java -cp bin BeanCounterLogicImpl 10 500 luck debug
```

## What to do

Here is a list of files and their contents:

* BeanCounterLogicImpl.java - The core logic of the bean machine.  Maintains a
  list of beans that fall one step in the machine whenever advanceStep() is
called.  Maps beans into a logical coordinate system.  Also contains a main
method which implements the Text UI of the program. (**modify**)

* BeanImpl.java - The Bean implementation.  Maintains the x-coordinate of the
  bean, as well as how many right moves are remaining if in skill mode.
Governs the movement of that particular bean when choose() is called, depending
on whether the bean is a skilled bean or a lucky bean.  The Random number
generator that gives randomness to the movement is injected in the BeanImpl
constructor for easier testing. (**modify**)

* BeanCounterLogicTest.java - The JUnit test class for the BeanCounterLogicImpl
  class composed entirely of property-based tests.  Depending on Config, it
either runs in plain JUnit mode or JPF on JUnit mode as can be seen in the
setUp() method.  In plain JUnit mode, a particular machine configuration is
chosen for testing (5 slots, 3 beans, luck mode).  Also the Random number
generator is seeded with 42 to make tests reproducible.  In JPF on JUnit mode,
you are asked to exhaustively test different machine configurations (see [Model
Checking Using JUnit](#model-checking-using-junit)) and all random numbers will
be exhaustively tested too. (**modify**)

* GradeScopeTest.java - The JUnit test class used by GradeScope to autograde
  your implementation.  It only runs in plain JUnit mode since it contains
non-property-based tests.  It contains input-specific tests that are not
covered in BeanCounterLogicTest.java.  Currently, only the testReset method is
implemented and you need to fill in the // TODO comments to have a full test
suite.  (**modify**)

* Config.java - The high level configuration of the program.  Controls
  LogicType (your impl, buggy, or solution) and TestType (plain JUnit or JPF on
JUnit).

* BeanCounterLogic.java - The public interface of BeanCounterLogic.  The
  createInstance method creates an instance of BeanCounterLogicImpl (your
code), BeanCounterLogicBuggy (a buggy implementation), or
BeanCounterLogicSolution (the solution implementation) based on Config.  Do not
modify.

* Bean.java - The public interface of Bean.  The createInstance method creates
  an instance of BeanImpl (your code), BeanBuggy (a buggy implementation), or
BeanSolution (the solution implementation) based on Config.

* BeanCounterGUI.java - Contains the main method for the GUI interface of the
  program.  Creates a MainFrame.

* MainFrame.java - Contains the MainPanel and the ButtonPanel.

* MainPanel.java - The main display of the program where all the bean machine
  animations happen.  Renders the state of BeanCounterLogic on to the window
coordinate system (the "physical" coordinates).  In the process the logical
coordinates of Beans are translated to physical coordinates.

* <Some>Button.java - GUI buttons at the bottom of the main frame, along with
  event handlers when the button is pressed.

* BeanCounterSolution.jar - The solution implementation of bean counter.

* BeanCounterBuggy.jar - A buggy implementation of bean counter.

* compile.bat / compile.sh - Scripts to compile Java source code to bytecode.

* runJUnit\*.bat / runJUnit\*.sh - Scripts to run JUnit tests on bean machine.

* runJPF\*.bat / runJPF\*.sh - Scripts to run JUnit tests on bean machine with JPF enabled.

You are asked to fill in and modify 3 files: BeanCounterLogicImpl.java,
BeanImpl.java, BeanCounterLogicTest.java.  The first two files complete the
bean counter implementation.  The last file tests the implementation using the
Java Path Finder model checker via the JUnit framework.  Take care that you
limit your modifications to these three files as all the other files will be
ignored in your submission.  Also, take care that you do not change the public
interfaces of BeanCounterLogic and Bean as GradeScope relies on them.

I expect you to employ test-driven development (TDD) for this project and fully
embrace it.  I can guarantee you that it will shorten development time.  You
are going to write the tests anyway.  Why not write them at the beginning when
they will be the most useful?  I will lay down the steps, roughly in the order
you should perform them.

### Plain JUnit Testing

Start by completing the tests in [GradeScopeTest.java](src/GradeScopeTest.java)
by replacing the // TODO comments.  One test testReset() is already implemented
for you.  These are the tests that GradeScope is going to run to verify your
implementation.

Note that we are not mocking the Bean objects, even though the Bean class is
an external class from the perspective of the BeanCounterLogicImpl class that
we are testing.  This is intentional.  In this case, I made a conscious
decision that I wanted to systems test the entire application, instead of
mocking external objects to enforce unit testing.

Next, complete [BeanCounterLogicTest.java](src/BeanCounterLogicTest.java) by
again replacing the // TODO comments.  Again, we are not mocking the Bean
objects for the same reason.  Pay close attention to the invariants you are
asked to test described in the Javadoc comment above each @Test method.  Note
that when the configured test type is TestType.JUNIT, this class operates as a
plain JUnit test.  Only when it is configured with test type
TestType.JPF_ON_JUNIT will it run with JPF.

Using the TestRunner, You are able to test both GradeScopeTest and
BeanCounterLogicTest against your BeanCounterLogicImpl implementation, a buggy
implementation, and the solution implementation.  Here is how.

1. To test your BeanCounterLogicImpl implementation on Windows:

    ```
    $ runJUnit.bat
    ```

    Or on Mac or Linux:

    ```
    $ bash runJUnit.sh
    ```

    When you run this initially, it should display the below:

    ```
    TESTING YOUR IMPLEMENTATION WITH PLAIN JUNIT

    [GradeScopeTest]

    testReset(GradeScopeTest): [Slot Count = 1] Test case with 2 initial beans failed. Check on remaining bean count expected:<1> but was:<0>
    [BeanCounterLogicTest]

    Failure in (slotCount=5, beanCount=3, isLucky=true):
    ```

    The first line is a failure on testReset in GradeScopeTest.java, which
naturally failed because you have not yet implemented the reset(Bean[]) method.
The second line is a println in the BeanCounterLogicTest.java testReset()
method of the failString just to show you how it looks like.  As you implement
more tests, you would get more failures.

1. To test the BeanCounterLogicSolution implementation on Windows:

    ```
    $ runJUnitSolution.bat
    ```

    Or on Mac or Linux:

    ```
    $ bash runJUnitSolution.sh
    ```

    Since this is the defect-free solution implementation, it should not display any failures:

    ```
    TESTING BUGGY IMPLEMENTATION WITH PLAIN JUNIT

    [GradeScopeTest]

    [BeanCounterLogicTest]

    Failure in (slotCount=5, beanCount=3, isLucky=true):
    ```

    Note the failure on testReset is gone.  All your other tests should pass as well.

1. To test the buggy BeanCounterLogicBuggy implementation on Windows:

    ```
    $ runJUnitBuggy.bat
    ```

    Or on Mac or Linux:

    ```
    $ bash runJUnitBuggy.sh
    ```

    The output does not display the testReset failure either.  

    ```
    TESTING BUGGY IMPLEMENTATION WITH PLAIN JUNIT

    [GradeScopeTest]

    [BeanCounterLogicTest]

    Failure in (slotCount=5, beanCount=3, isLucky=true):
    ```

    So the reset(Bean[]) method is correct apparently, but many parts are not.
Most of the bugs in this buggy implementation will remain hidden until we do
rigorous state space exploration using JPF.

    To see the bugs with your own eyes, you only need to try invoking the main method inside
BeanCounterLogicBuggy to see that something is not quite right:

    ```
    java -jar BeanCounterBuggy.jar 20 400 luck
    ```

    Note that there are a lot of beans in the first slot for some reason.
There are also other defects lurking inside.  We need JPF to find these bugs.

### Model Checking Using JUnit

Exhaustively model check BeanCounterLogic using JPF to find hidden bugs.

Notice that I have intentionally separated out the logic part of the program
from the GUI.  This was done to make model checking easier.  Model checking a
GUI is tricky and so is a multi-threaded event-driven program like
BeanCounterGUI.  Yes, JPF can model check even multi-threaded programs (!) by
exhaustively going through all the interleavings.  But it is complicated and it
takes a long time because it has to go through many more states.  So we will
just check the core logic (BeanCounterLogic), which is the important part
anyway.

These are roughly the steps involved in the testing infrastructure:

1. Launch Java Path Finder and load the target main class (specified in the "target" in BeanCounter.win.jpf)
2. In this case, the target is TestRunner and the main method starts to run.
3. TestRunner subsequently invokes JUnit on your BeanCounterLogicTest class.

What does this mean?  It means JUnit is going to test your test cases one by
one like always using the assertions you provide, but JPF is going to explore
all the paths your program can take while you are doing that.  So in the end,
this means each of your test cases will actually be run hundreds or thousands
of times for each of the paths that JPF discovers!  As we learned, paths in a
program can diverge when a random number is generated (e.g. when trying to
decide whether a bean should fall left or right).  This way, you can even prove
nondeterministic programs correct by just trying all paths that your program
can take, something that regular JUnit testing cannot do.  

Also, paths can diverge at tester specified points using the Verify API.  The
Verify API allows the tester to verify a range of input values.  JPF explores
each of the input values as a separate path that your program can take.
Speaking of which, the first thing you should do in BeanCounterLogicTest.java
is to modify the setUp() method to insert the Verify calls afore mentioned.
The three input values relevant here are: slot count, bean count, and the
boolean value isLuck ("luck" or "skill" mode).  Once you insert the Verify
calls, JPF will explore each combination of input values.  As described in the
"// TODO" comment in the setUp() method, verify 1-5 slot count, 0-3 bean count,
and both "luck" and "skill" modes.  We will not test slot count 0 because then
it means there are no slots to receive beans and the machine basically falls
apart.  Although the range of values is not exhaustive, these are enough values
to give us confidence that our machine works, while ensuring that JPF
terminates within more or less 5 seconds to not lengthen turnaround time.

The testReset() method contains a println statement inserted in order to
demonstrate to you all the combinations of input values JPF explores.  Let's
see what it prints out initially without Verify API calls.  Try the following
command.

On Windows:

```
$ runJPF.bat BeanCounter.win.jpf
```

Or on Mac or Linux:

```
$ bash runJPF.sh BeanCounter.macos.jpf
```

That should print out an output like the following:

```
JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


====================================================== system under test
TestRunner.main("impl","jpf")

====================================================== search started: 3/31/22 9:13 PM
TESTING YOUR IMPLEMENTATION WITH JPF ON JUNIT

Failure in (slotCount=0, beanCount=0, isLucky=false):

====================================================== results
no errors detected

====================================================== statistics
elapsed time:       00:00:00
states:             new=2,visited=0,backtracked=2,end=1
search:             maxDepth=2,constraints=0
choice generators:  thread=2 (signal=0,lock=1,sharedRef=0,threadApi=0,reschedule=1), data=0
heap:               new=2675,released=1119,maxLive=1697,gcCycles=2
instructions:       71564
max memory:         245MB
loaded code:        classes=276,methods=3921

====================================================== search finished: 3/31/22 9:13 PM
```

After inserting the Verify calls, JPF should give you an output like this:

```
JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


====================================================== system under test
TestRunner.main("impl","jpf")

====================================================== search started: 10/28/21 3:39 AM
TESTING YOUR IMPLEMENTATION WITH JPF ON JUNIT

Failure in (slotCount=1, beanCount=0, isLucky=false):
Failure in (slotCount=1, beanCount=0, isLucky=true):
Failure in (slotCount=1, beanCount=1, isLucky=false):
Failure in (slotCount=1, beanCount=1, isLucky=true):
Failure in (slotCount=1, beanCount=2, isLucky=false):
...
[Truncated for brevity]
```

Please remove the println in your final submission.

Now we are confident that all our @Test JUnit methods will benefit from
exhaustive testing.  Please go ahead and **complete all test cases in
BeanCounterLogicTest**.  As you do so, I recommend that you always insert the
failString that I initialized for you as the first argument of every JUnit
assert call so that you get that as part of your failure message.  The
failString describes the machine configuration that is being currently tested
and it will tell you which configuration led to the failure.

### Applying Model Checking to Your / Solution / Buggy Implementations

Now that you have completed BeanCounterLogicTest you can run that JUnit class
on top of JPF to test your BeanCounterLogicImpl implementation.  Not only that,
you can also run the test against a buggy implementation and a solution
implementation, to verify that your test cases themselves pass the solution
without failures and also detect all the bugs in the buggy implementation.
Here is how:

1. To test your BeanCounterLogicImpl implementation on Windows:

    ```
    $ runJPF.bat BeanCounter.win.jpf
    ```
    Or on Mac or Linux:

    ```
    $ bash runJPF.sh BeanCounter.macos.jpf
    ```

    Since this is before completing BeanCounterLogicImpl and BeanImpl, you will
see the same failures we saw for plain JUnit testing, except this time for many
different machine configurations:

    ```
    JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


    ====================================================== system under test
    TestRunner.main("impl","jpf")

    ====================================================== search started: 10/29/21 3:51 PM
    TESTING YOUR IMPLEMENTATION WITH JPF ON JUNIT

    testReset(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=1, isLucky=false): expected:<1> but was:<0>
    testAdvanceStepPostCondition(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=1, isLucky=false): expected:<1> but was:<0>
    testReset(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=1, isLucky=true): expected:<1> but was:<0>
    testAdvanceStepPostCondition(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=1, isLucky=true): expected:<1> but was:<0>
    testReset(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=2, isLucky=false): expected:<1> but was:<0>
    testAdvanceStepPostCondition(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=2, isLucky=false): expected:<2> but was:<0>
    testReset(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=2, isLucky=true): expected:<1> but was:<0>
    testAdvanceStepPostCondition(BeanCounterLogicTest): Failure in (slotCount=1, beanCount=2, isLucky=true): expected:<2> but was:<0>

    ...

    testReset(BeanCounterLogicTest): Failure in (slotCount=5, beanCount=3, isLucky=false): expected:<2> but was:<0>
    testAdvanceStepPostCondition(BeanCounterLogicTest): Failure in (slotCount=5, beanCount=3, isLucky=false): expected:<3> but was:<0>
    testReset(BeanCounterLogicTest): Failure in (slotCount=5, beanCount=3, isLucky=true): expected:<2> but was:<0>
    testAdvanceStepPostCondition(BeanCounterLogicTest): Failure in (slotCount=5, beanCount=3, isLucky=true): expected:<3> but was:<0>

    ====================================================== results
    no errors detected

    ====================================================== statistics
    elapsed time:       00:00:01
    states:             new=67,visited=0,backtracked=67,end=40
    search:             maxDepth=5,constraints=0
    choice generators:  thread=2 (signal=0,lock=1,sharedRef=0,threadApi=0,reschedule=1), data=26
    heap:               new=21129,released=24445,maxLive=1724,gcCycles=42
    instructions:       911286
    max memory:         245MB
    loaded code:        classes=289,methods=4076

    ====================================================== search finished: 10/29/21 3:51 PM
    ```

1. To test the BeanCounterLogicSolution implementation on Windows:

    ```
    $ runJPFSolution.bat BeanCounter.win.jpf
    ```
    Or on Mac or Linux:

    ```
    $ bash runJPFSolution.sh BeanCounter.macos.jpf
    ```

    BeanCounterLogicSolution is a complete correct implementation, so you should see no failures:

    ```
    JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.
    
    
    ====================================================== system under test
    TestRunner.main("solution","jpf")
    
    ====================================================== search started: 10/29/21 2:53 PM
    TESTING SOLUTION IMPLEMENTATION WITH JPF ON JUNIT
    
    
    ====================================================== results
    no errors detected
    
    ====================================================== statistics
    elapsed time:       00:00:06
    states:             new=4161,visited=3533,backtracked=7694,end=468
    search:             maxDepth=65,constraints=0
    choice generators:  thread=3 (signal=0,lock=1,sharedRef=0,threadApi=0,reschedule=2), data=3839
    heap:               new=384143,released=243001,maxLive=2325,gcCycles=7669
    instructions:       15039902
    max memory:         700MB
    loaded code:        classes=334,methods=4665
    
    ====================================================== search finished: 10/29/21 2:53 PM
    ```

1. To test the BeanCounterLogicBuggy implementation on Windows:

    ```
    $ runJPFBuggy.bat BeanCounter.win.jpf
    ```
    Or on Mac or Linux:

    ```
    $ bash runJPFBuggy.sh BeanCounter.macos.jpf
    ```

    Now JPF uncovers some hidden defects that we missed before in plain JUnit testing:

    ```
    JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


    ====================================================== system under test
    TestRunner.main("buggy","jpf")

    ====================================================== search started: 10/28/21 3:24 AM
    TESTING BUGGY IMPLEMENTATION WITH JPF ON JUNIT

    testAdvanceStepCoordinates(BeanCounterLogicTest): Failure in (slotCount=2, beanCount=1, isLucky=true):
    testLowerHalf(BeanCounterLogicTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<2> but was:<1>
    testAdvanceStepBeanCount(BeanCounterLogicTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<3> but was:<2>
    testAdvanceStepPostCondition(BeanCounterLogicTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<3> but was:<2>
    testUpperHalf(BeanCounterLogicTest): Failure in (slotCount=2, beanCount=3, isLucky=false): expected:<2> but was:<1>
    testRepeat(BeanCounterLogicTest): Failure in (slotCount=4, beanCount=3, isLucky=false): expected:<3> but was:<2>

    ====================================================== results
    no errors detected

    ====================================================== statistics
    elapsed time:       00:00:06
    states:             new=4155,visited=3529,backtracked=7684,end=467
    search:             maxDepth=65,constraints=0
    choice generators:  thread=3 (signal=0,lock=1,sharedRef=0,threadApi=0,reschedule=2), data=3834
    heap:               new=383657,released=242713,maxLive=2321,gcCycles=7659
    instructions:       15150745
    max memory:         700MB
    loaded code:        classes=343,methods=4714

    ====================================================== search finished: 10/28/21 3:24 AM
    ```    

### Debugging on Model Checking Failures

What to do when you see a failure?  Now you want to zero in on the path that
caused you the failure.  The failString tells you the combination of input
values that caused the failure.  With this information on hand, here are a few
things you can do:

1. You can modify the hard coded values for slotCount, beanCount, and isLuck
   for plain JUnit testing mode in the BeanCounterLogicTest setUp() method to
match that configuration.  And try running JUnit again:

   ```
   $ runJUnit.bat (or bash runJUnit.sh for Mac/Linux)
   ```

   Now that same failure will likely manifest.  

1. Sometimes JUnit emits a failure but the failure did not come from an
   assertion, meaning the failString is not printed and just the "null" message
is shown.  This is most likely because JUnit suffered an exception while
running the tests, either in the test code or the application source code.  To
find out what happened, a Java stack trace would help a lot.  In order to add
stack traces to the output, uncomment the following lines inside
TestRunner.java:
   ```
   System.out.println(f.getTrace());
   ```

1. There is a possibility that the above will not trigger the bug due to
   nondeterminism coming from the random number generator.  So then you will
have to somehow use JPF to track down the defect.  For this, you may want to
add more information to the failString, beyond the slotCount, beanCount, and
isLuck input values.  The BeanCounterLogicImpl.toString() method is able to
generate a string representation of the state of the machine at the current
step (this is what gets printed in the text UI debug output).  You may want
to add to the failString this string representation for the current step, or
all previous steps.

1. Sometimes, even the additional information in the failString will fail to
   locate the bug.  In that case, you may have to look at the trace of
instructions and choices that led to the failure provided by JPF.  As discussed
in Exercise 5, JPF cannot detect an error and generate a trace if JUnit
intercepts all exceptions, so you will have to run JUnit test cases directly
without the help of JUnit.  I have created scripts for this, but unfortunately
the GitHub Classroom repositories have already been released and can no longer
be updated.  So you will have to copy the following files from the course
repository to your repository:

   * [src/TestRunner.java](src/TestRunner.java)
   * [runJPFTrace.bat](runJPFTrace.bat)
   * [runJPFTrace.sh](runJPFTrace.sh)

   Then, if you run on Windows:
   ```
   runJPFTrace.bat BeanCounter.win.jpf
   ```
   Or on Mac/Linux:
   ```
   bash runJPFTrace.sh BeanCounter.macos.jpf
   ```

   You should be able to get a trace that looks like this (before having completed BeanCounterLogicImpl and BeanImpl):

   ```
   JavaPathfinder core system v8.0 (rev 471fa3b7c6a9df330160844e6c2e4ebb4bf06b6c) - (C) 2005-2014 United States Government. All rights reserved.


   ====================================================== system under test
   TestRunner.main("impl","jpftrace")

   ====================================================== search started: 3/31/22 9:47 PM
   TESTING YOUR IMPLEMENTATION WITH JPF ON JUNIT WITH TRACING


   ====================================================== error 1
   gov.nasa.jpf.vm.NoUncaughtExceptionsProperty
   java.lang.reflect.InvocationTargetException: java.lang.AssertionError
	   at org.junit.Assert.fail(org/junit/Assert.java:88)
	   at org.junit.Assert.failNotEquals(org/junit/Assert.java:834)
	   at org.junit.Assert.assertEquals(org/junit/Assert.java:645)
	   at BeanCounterLogicTest.testReset(BeanCounterLogicTest.java:144)
	   at java.lang.reflect.Method.invoke(gov.nasa.jpf.vm.JPF_java_lang_reflect_Method)
	   at TestRunner.main(TestRunner.java:61)
   Caused by: java.lang.AssertionError: Failure in (slotCount=1, beanCount=1, isLucky=false):. Check on in-flight bean count expected:<1> but was:<0>
	   at org.junit.Assert.fail(org/junit/Assert.java:88)
	   at org.junit.Assert.failNotEquals(org/junit/Assert.java:834)
	   at org.junit.Assert.assertEquals(org/junit/Assert.java:645)
	   at BeanCounterLogicTest.testReset(BeanCounterLogicTest.java:144)
	   at java.lang.reflect.Method.invoke(gov.nasa.jpf.vm.JPF_java_lang_reflect_Method)
	   at TestRunner.main(TestRunner.java:61)


   ====================================================== trace #1
   ------------------------------------------------------ transition #0 thread: 0
   gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"ROOT" ,1/1,isCascaded:false}
	 [3168 insn w/o sources]
     TestRunner.java:8              : public class TestRunner {
	 [2 insn w/o sources]
     TestRunner.java:8              : public class TestRunner {
     TestRunner.java:1              : import java.util.ArrayList;
   ...
     BeanCounterLogicTest.java:144  : assertEquals(failString + ". Check on in-flight bean count",
	 [2 insn w/o sources]
     BeanCounterLogicTest.java:145  : inFlightExpected, inFlightObserved);
     BeanCounterLogicTest.java:144  : assertEquals(failString + ". Check on in-flight bean count",
	 [169 insn w/o sources]

   ====================================================== snapshot #1
   thread java.lang.Thread:{id:0,name:main,status:RUNNING,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
     call stack:
	   at org.junit.Assert.fail(Assert.java:88)
	   at org.junit.Assert.failNotEquals(Assert.java:834)
	   at org.junit.Assert.assertEquals(Assert.java:645)
	   at BeanCounterLogicTest.testReset(BeanCounterLogicTest.java:144)
	   at java.lang.reflect.Method.invoke(Method.java)
	   at TestRunner.main(TestRunner.java:61)


   ====================================================== results
   error #1: gov.nasa.jpf.vm.NoUncaughtExceptionsProperty "java.lang.reflect.InvocationTargetException: java...."
   ...

   ```


### Add an Extra Invariant Test

_Also add at one more test case of your own that helps you verify some
invariant property_.  Add that test at the very end.  Make sure you test a new
invariant that has not yet been tested.  There are many many invariants yet to
be tested: be creative!  Remember, it has to be an invariant.  If you write a
test applicable to a specific input combination using if statements, it doesn't
count.  Make sure that the test case is well documented with a Javadoc comment
just like other methods.

### Coding

All the GUI coding has already been done for you, since some of you are not
familiar with Java AWT and event-driven programming.  You only need to
implement the logic of the machine.  All the parts that you have to fill in
have been commented with // TODO in the files: BeanCounterLogicImpl.java and
BeanImpl.java.  You will not need to modify any of the other files.  As you are
coding, regularly run JUnit and the JPF model checker, both to check that the
coded feature was properly implemented and that you have not regressed.  Your
goal in coding should be to make those tests pass.

In order to get the bell curve in skill mode, you will have to use the
Random.nextGaussian() method.  A bell curve is synonymous with normal
distribution is synonymous with Gaussian distribution, hence the name.  Here is
the formula you should use:

```
SKILL_AVERAGE = (double) (SLOT_COUNT - 1) * 0.5
SKILL_STDEV = (double) Math.sqrt(SLOT_COUNT * 0.5 * (1 - 0.5))
SKILL_LEVEL = (int) Math.round(rand.nextGaussian() * SKILL_STDEV + SKILL_AVERAGE)
```

SKILL\_AVERAGE and SKILL\_DEV are the average and standard deviation of the
skill level that would get you the normal distribution that approximates the
binomial distribution created by "luck" mode.  If you are curious about how
those values were computed, refer to this [Wikipedia section on binomial
distribution approximation](https://en.wikipedia.org/wiki/Binomial_distribution#Normal_approximation).

### Linting and Auditing

Run the CheckStyle linter and the SpotBugs tool regularly while and after
coding.  When you are done, both tools should show a successful audit:

```
$ runCheckstyle.bat
Starting audit...
Audit done.
```

```
$ runSpotbugs.bat
The following classes needed for analysis were missing:
  org.junit.runner.JUnitCore
  org.junit.runner.Result
  org.junit.runner.notification.Failure
  BeanCounterLogicBuggy
  BeanBuggy
```

You can consider the above to be a successful audit.  The warning is just
showing that SpotBugs could not access the JUnit classes (which is a good thing
because you will see dozens of flagged "bugs" in the JUnit code base found by
SpotBugs that have nothing to do with you).

### Manual System Testing

Even after doing model checking, you still need to verify that the program
"looks" right end-to-end in the GUI.  This is hard to do using automated
testing so you will do manual testing for this.  Refer to the
[requirements.md](requirements.md) file for the features that need testing.  I
am not going to make you write a test plan for this, but you will be graded on
how closely the GUI follows the requirements as demonstrated in BeanCounterSolution.jar

## Grading

* GradeScope autograder - 80%
* Verify API used properly - 5%
* Extra test checking new invariant - 5%
* Visual inspection of GUI application - 10%

Please review the rubric in GradeScope for details.  I reserve the right to
deduct points for any attempt to try to game GradeScope into giving you more
points.  Also, plagiarism will get you a zero for the project.

## Submission

You will create a GitHub repository just for deliverable 5.  Make sure you keep
the repository *PRIVATE* so that nobody else can access your repository.  Once
you are done modifying code, don't forget to commit and push your changes to
the github repository.  Submit your GitHub repository to GradeScope at the
"Deliverable 5 GitHub" link.  Don't forget to add your partner, if you have one.
Once you submit, GradeScope will run the autograder to grade you and give feedback.
If you get deductions, fix your code based on the feedback and resubmit.  Repeat
until you don't get deductions.

## GradeScope Feedback

It is encouraged that you submit to GradeScope early and often.  Please use the
feedback you get on each submission to improve your code!

The GradeScope autograder works in 5 phases:

1. BeanCounterLogicImpl.java functionality testing  

    The purpose of this phase is to test BeanCounterLogicImpl for defects.  I
do this by running the JUnit class
[GradeScopeTest.java](src/GradeScopeTest.java) against BeanCounterLogicImpl.
On a failure, read the feedback to get a hint on which situation led to the
defect.

1. CheckStyle

    This phase runs the CheckStyle tool on your source code.  Each warning will
get you a point deduction.

1. SpotBugs

    This phase runs the SpotBugs tool on your class files.  Each warning will
get you a point deduction.

1. BeanCounterLogicTest on BeanCounterLogicSolution

   The purpose of this phase is to test BeanCounterLogicTest itself for
defects.  BeanCounterLogicSolution is the class used in the implementation of
BeanCounterSolution.jar.  Since BeanCounterLogicSolution is defect free, any
test failures in BeanCounterLogicTest while testing BeanCounterLogicSolution
indicate a bug in BeanCounterLogicTest.

1. BeanCounterLogicTest on BeanCounterLogicBuggy

    The purpose of this phase is to test BeanCounterLogicTest against the buggy
BeanCounterLogicBuggy implementation to see if it can find the defects inside
it.  BeanCounterLogicBuggy is the class used in the implementation of
BeanCounterBuggy.jar.  Since BeanCounterLogicBuggy is buggy, you expect most of
the tests to fail this time.  BeanCounterLogicTestSolution is the solution
implementation of BeanCounterLogicTest and most tests indeed fail for
BeanCounterLogicTestSolution.  If BeanCounterLogicTestSolution fails a test but
BeanCounterLogicTest passes a test, then this indicates a problem (and vice
versa).  Read the feedback to get a hint on what the problem is.  Try invoking
the exact scenario on the buggy implementation to see the bug for yourself and
figure out why your test cases are not handling it correctly.

    ```
    java -jar BeanCounterBuggy.jar 2 3 skill debug
    ```

    If you are observant, you should already see a bug in the above commandline.
    
## Resources

These links are the same ones posted at the end of the slides:

* JDK 8 installation packages:  
https://drive.google.com/drive/folders/1E76H7y2nMsrdiBwJi0nwlzczAgTKKhv7

* Java Path Finder manual:  
https://github.com/javapathfinder/jpf-core/wiki/How-to-use-JPF
http://javapathfinder.sourceforge.net/

* Java Path Finder Verify API:  
https://github.com/javapathfinder/jpf-core/wiki/Verify-API-of-JPF

* CheckStyle reference:  
https://checkstyle.sourceforge.io/checks.html  
If you don't understand a CheckStyle warning, read the corresponding entry inside google\_checks\_modified.xml under the checkstyle-jars folder and the above reference.

* SpotBugs reference:  
https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html

