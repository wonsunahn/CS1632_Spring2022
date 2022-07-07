- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Deliverable 2](#deliverable-2)
  * [Development Methodology](#development-methodology)
  * [Expected Outcome](#expected-outcome)
  * [Additional Requirements](#additional-requirements)
  * [Grading](#grading)
  * [Submission](#submission)
  * [GradeScope Feedback](#gradescope-feedback)
  * [Groupwork Plan](#groupwork-plan)
  * [Resources](#resources)

# CS 1632 - Software Quality Assurance
Spring Semester 2022

* DUE: Feb 22, 2022 1:00 PM 

**GitHub Classroom Link:** https://classroom.github.com/a/HNYcLObh

## Deliverable 2

For this assignment, your group will write code and unit tests for an
authorized reproduction of Coffee Maker Quest.  

Requirements for this program is that you mimic the behavior of the program
coffeemaker.jar given in this folder exactly.  This is a version of the Coffee
Maker Quest game we tested for Deliverable 1, but with defects removed.

Some of the work has already been done for you.  Classes such as
CoffeeMakerQuest.java, Config.java, Game.java, Player.java, Room.java, and
TestRunner.java are already complete.  You need only modify
CoffeeMakerQuestImpl.java and CoffeeMakerQuestTest.java.  As in the
exercise, the places where you need to modify code are marked by the // TODO
comments.  DO NOT TOUCH the already complete classes as they will be used AS
IS during grading.  Here is a brief rundown of the classes:

* CoffeeMakerQuest.java - the interface for the CoffeeMakerQuest game engine
* Config.java - allows configuration of bug injection into various classes
* Game.java - contains the main method; generates rooms and runs the game using the CoffeeMakerQuest engine
* Player.java - player object with inventory information
* Room.java - room object with furnishings and items
* TestRunner.java - the runner for the JUnit test class CoffeeMakerQuestTest
* CoffeeMakerQuestImpl.java - an implementation of CoffeeMakerQuest (_modify_)
* CoffeeMakerQuestTest.java - JUnit test class CoffeeMakerQuest (_modify_)


1. To run the game you need to invoke the Game class.  For Windows:
    ```
    runGame.bat
    ```
    For Mac or Linux, try doing:
    ```
    bash runGame.sh
    ```
    When you run it without any modification, you will suffer an exception and crash.  That is of course because you have not completed implementing CoffeeMakerQuestImpl.java!  When you are done implementing, you should get identical behavior as running the original jar file:
    ```
    java -jar coffeemaker.jar
    ```

1. To run the JUnit tests on CoffeeMakerQuestImpl, for Windows:
    ```
    runTest.bat
    ```
    For Mac or Linux, try doing:
    ```
    bash runTest.sh
    ```
    When you run it without any modification, you will get "ALL TESTS PASSED".  But don't get delirious.  That is because all your tests are currently empty.

1. To run the JUnit tests on CoffeeMakerQuestBuggy (included in the form of
   the coffeemaker-buggy.jar file), for Windows:
    ```
    runTestBuggy.bat
    ```
    For Mac or Linux, try doing:
    ```
    bash runTestBuggy.sh
    ```

## Development Methodology

Like Exercise 2, we will try to apply the Test Driven Development (TDD) model
here.  Try writing the test case(s) FIRST before writing the code for a
feature.  This way, you will always have 100% test coverage for the code you
have written and are writing.  Hence, if you break any part of it in the course
of adding a feature or refactoring your code, you will know immediately.

## Expected Outcome

You should see the following output when running runTest.bat (or runTest.sh):
```
ALL TESTS PASSED
```

And after running runTestBuggy.bat (or runTestBuggy.sh), you should get output that looks like [runTestBuggy.output.txt](runTestBuggy.output.txt).  If you do, this tells you that you have written your JUnit tests well so that they are able to find the bugs in CoffeeMakerQuestBuggy.  Note that I've commented out the following line at TestRunner.java:30 to make the output less verbose:
```
System.out.println(f.getTrace());
```
The above will print a full Java stack trace for every failure.  It is useful when a test fails due to a crash in your program and you want to locate exactly in which source code line the Java exception was thrown.  The defects in this CoffeeMakerQuestBuggy does not involve crashes due to exceptions so I've temporarily commented it out for brevity.

## Additional Requirements

* Code coverage of the class CoffeeMakerQuestImpl when the JUnit TestRunner is
  run should be at an absolute minimum of **90%**.  If coverage falls below that
number, add more unit tests to CoffeeMakerQuestTest.

* For this program, no requirements are given as the requirement is that you
  mimic the output of the given **coffeemaker.jar** file (note that this jar
file is slightly different from the version provided to you for Deliverable 1
as I have fixed most of the bugs!).  If GradeScope gives you a failure because
your output is different canonical output, it will show you where the
difference is between brackets [].  In fact, GradeScope itself uses JUnit
behind the scenes to test your program and showing the difference in brackets
is a JUnit assertEquals feature.

* You are asked to complete CoffeeMakerQuestImpl, but there are other support
  classes as well such as Player and Room.  You are expected to use the methods
provided in those classes and not repeat the code somewhere else.  In fact,
this is an important software testability principle called **DRY (Don't Repeat
Yourself)**.  For example, the Player class has the method
**Player.getInventoryString** that prints out the inventory contents based on
the current items.  You are required to use that method and not implement a
similar method of your own.

* Write at least one **private method** while implementing
  CoffeeMakerQuestImpl.  In general, private methods of a Java class work as
"helper" methods that implement a sub-functionality of a public method.  You
have the freedom to choose what sub-functionality you want to encapsulate
within a private method.  Also, add at least one unit test that directly tests
a private method at the very bottom of CoffeeMakerQuestTest.  Use **Java
reflection** to do this.

* Coding style is also important for software quality in the long run (even
  though they are not technically defects as we learned).  In particular, a
uniform naming convention greatly improves the readability of your code.  A
widely used convention is called
[lowerCamelCase](https://en.wikipedia.org/wiki/Naming_convention_(programming)#Java)
convention.  That is the convention that was [first adopted when Sun
Microsystems first created the Java
language](https://www.oracle.com/technetwork/java/codeconventions-135099.html).
This is still the convention at the biggest companies using Java like
[Oracle](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html)
and [Google](https://google.github.io/styleguide/javaguide.html#s5-naming).
Please make sure you follow the lower camel case convention for all your
variables and methods for this project.  There is less agreement on other
formatting issues like indentation and line wrapping, but try to maintain a
uniform convention whatever you choose.

## Grading

* GradeScope autograder: 70% of grade
* Private method added and tested: 5% of grade
* Source code style (lower camel case naming / formatting): 10% of grade
* Report (including coverage stats): 15% of grade

Please review the grading_rubric.txt for details.

## Submission

Each group will do one submissions to GradeScope as usual.

The submission is done in two parts:

1. Submit your GitHub Classroom Deliverable 2 repository to GradeScope at the
   **Deliverable 2 GitHub** link.  Once you submit, GradeScope will run the
autograder to grade you and give feedback.  If you get deductions, fix your
code based on the feedback and resubmit.  Repeat until you don't get
deductions.

1.  Please use the [ReportTemplate.docx](ReportTemplate.docx) file provided in
    this directory to write a short report.  A PDF version of the file is at
[ReportTemplate.pdf](ReportTemplate.pdf).  On the first page introduction,
please describe the division of work between group members and also any
difficulties you faced while using JUnit.  On the second page, paste a
screenshot of code coverage stats **after** having completed the coding.
Please refer to [Exercise 2](/exercises/2#measuring-code-coverage) on how to
create the screenshot.  Submit to GradeScope at the **Deliverable 2 Coverage**
link.  Your screenshot should look like either:

   <img alt="Code Coverage Eclipse" src=code_coverage_eclipse.png width=700>
   
   or ...

   <img alt="Code Coverage Jacoco" src=code_coverage_jacoco.png width=700>

   Make sure that the coverage of CoffeeMakerQuestImpl is showing and the
overall coverage is above **90%** as shown above.

## GradeScope Feedback

It is encouraged that you submit to GradeScope early and often.  Please use the
feedback you get on each submission to improve your code!

The GradeScope autograder works in 3 phases:

1. CoffeeMakerQuestImpl verification using CoffeeMakerQuestTestSolution:
   CoffeeMakerQuestTestSolution is the solution implementation of
CoffeeMakerQuestTest.  The purpose of this phase is to verify that CoffeeMakerQuestImpl (your CoffeeMakerQuest implementation) does not have any defects.

1. CoffeeMakerQuestTest on CoffeeMakerQuestSolution: CoffeeMakerQuestTest is your submitted JUnit test for CoffeeMakerQuest.  The purpose of this phase is
   to test CoffeeMakerQuestTest itself for defects.  CoffeeMakerQuestSolution is the solution implementation of CoffeeMakerQuest and contains no defects (that I know of).  Hence, all tests in CoffeeMakerQuestTest should pass.

1. CoffeeMakerQuestTest on CoffeeMakerQuestBuggy: CoffeeMakerQuestTest is your submitted JUnit test for CoffeeMakerQuest.  The purpose of this phase is
   to test CoffeeMakerQuestTest against the buggy CoffeeMakerQuestBuggy
implementation.  The class CoffeeMakerQuestBuggy is given to you in the form of
the coffeemaker-buggy.jar file.  Since CoffeeMakerQuestBuggy is buggy, you
expect the tests to fail this time.  If CoffeeMakerQuestTestSolution fails a
test but CoffeeMakerQuestTest passes a test (or vice versa), then this indicates a problem.

## Groupwork Plan

Just like for Exercise 2, I recommend that you divide the list of methods to
implement / test into two halves and working on one half each.  Please document
how you divided the work in your report.

## Resources

These links are the same ones posted at the end of the slides:

* JUnit User manual:  
https://junit.org/junit5/docs/current/user-guide/  
The Writing Tests section is probably the most useful.

* JUnit Reference Javadoc:  
http://junit.sourceforge.net/javadoc/  
For looking up methods only, not a user guide.

* Mockito User Manual:  
https://javadoc.io/static/org.mockito/mockito-core/3.2.4/org/mockito/Mockito.html  
Most useful is the sections about verification and stubbing.

* Jacoco User Manual:  
https://www.jacoco.org/userdoc/index.html

* Jacoco CLI (Command Line Interface) Manual:  
https://www.jacoco.org/jacoco/trunk/doc/cli.html

* Eclipse IDE
If you want more information, here is a page put up by a U Chicago professor:  
http://people.cs.uchicago.edu/~kaharris/10200/tutorials/eclipse/index.html  
It uses a much earlier version of Eclipse, but other than the outdated UI, the operations are the same.  I looked at several resources and this one was the most concise and to the point.  A more comprehensive manual is at eclipse.org:  
https://help.eclipse.org/2019-12/index.jsp  
Look at the "Java development user guide" chapter on the left.

