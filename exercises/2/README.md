- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Before You Begin](#before-you-begin)
  * [Description](#description)
  * [Sample Output](#sample-output)
  * [Running Unit Tests](#running-unit-tests)
  * [Tips](#tips)
  * [Expected Outcome](#expected-outcome)
  * [Measuring Code Coverage](#measuring-code-coverage)
  * [Using an IDE (Integrated Development Environment)](#using-an-ide-integrated-development-environment)
  * [Submission](#submission)
  * [GradeScope Feedback](#gradescope-feedback)
  * [Groupwork Plan](#groupwork-plan)
  * [Resources](#resources)

# CS 1632 - Software Quality Assurance
Spring Semester 2022 - Exercise 2

* DUE: Feb 4 (Friday), 2022 11:59 PM

**GitHub Classroom Link:** https://classroom.github.com/a/LxtQneZu

## Before You Begin

Please download **Eclipse IDE**:
https://www.eclipse.org/downloads/

Have it downloading and installing while you read the rest of the exercise.

## Description

In this exercise, we will simulate the main Rent-A-Cat rental system software.
This is obviously a "toy" implementation of the vast and powerful Rent-A-Cat
apparatus.

I have created some skeleton code for you to fill in for this exercise.  It is
up to you to fill in the `returnCat()`, `rentCat()`, `listCats()` and
`catExists()` methods, and write unit tests for them.  Unit tests must use
doubles for the Cat object with appropriate stubbing.  I have intentionally
inserted a defect on every Cat method such that an exception is fired if you
try to use a real Cat object in any way during your unit testing!  Those
defects are turned off when Cat is used within the main RentACat program.

Rent-A-Cat rents cats to customers for various needs (mousing, companionship,
homework help, etc.).  From the main menu, users may:

1. See list of cats for rent
2. Rent a cat to a customer
3. Return a cat
4. Quit

A cat which is out for rental cannot be rented and will not be listed until it
has been returned.  We will not charge money for this exercise.

## Sample Output

This is an example expected interaction with the program:

```
Option [1,2,3,4] > 1
Cats for Rent
ID 1. Jennyanydots
ID 2. Old Deuteronomy
ID 3. Mistoffelees
Option [1,2,3,4] > 2
Rent which cat? > 1
Jennyanydots has been rented.
Option [1,2,3,4] > 1
Cats for Rent
ID 2. Old Deuteronomy
ID 3. Mistoffelees
Option [1,2,3,4] > 2
Rent which cat? > 1
Sorry, Jennanydots is not here!
Rent which cat? > 7
Invalid cat ID.
Rent which cat? > 3
Mistoffelees has been rented.
Option [1,2,3,4] > 1
Cats for Rent
ID 2. Old Deuteronomy
Option [1,2,3,4] > 3
Return which cat? > 7
Invalid cat ID.  
Return which cat? Jennyanydots
Invalid cat ID.
Return which cat? 1
Welcome back, Jennyanydots!
Option [1,2,3,4] > 1
Cats for Rent
ID 1. Jennyanydots
ID 2. Old Deuteronomy
Option [1,2,3,4] > 4
Closing up shop for the day!
```

Let's try running the program and observe the output for ourselves.

1. First let's compile the program using the following script. For Windows:
   ```
   compile.bat
   ```
   For Mac/Linux:
   ```
   bash compile.sh
   ```

1. Next, let's run the program on the commandline:
   ```
   java -cp bin RentACatImpl
   ```
   And then, try listing the cats available for rent:
   ```
   Option [1,2,3,4] > 1
   Cats for Rent
   WRITE CODE FOR THISOption [1,2,3,4] >
   ```

That's not what you expected!  That is because the Rent-A-Cat system is
incomplete.  For this exercise, you will modify two classes to complete the
system: **RentACatImpl.java** and **RentACatTest.java**.  The RentACatImpl
class is an (incomplete) implementation of the Rent-A-Cat system.  The
RentACatTest class is a JUnit test class that tests RentACatImpl.  All
locations where you should add code is marked with // TODO comments.

## Running Unit Tests

1. First let's do a sanity check to see if JUnit works well with the Java version installed on your machine.  For Windows try doing:
    ```
    runTest.bat
    ```
    For Mac or Linux, try doing:
    ```
    bash runTest.sh
    ```
    For those of you who prefer Makefiles, you can also do:
    ```
    make
    ```

    If successful, you will get a message "ALL TESTS PASSSED".  We are using a
TestRunner class to invoke JUnit on the RentACatTest class.  Let's take a look
into TestRunner.java for a moment.  Note how the RentACatTest.class is added to
the list of classesToTest.  Also note how "ALL TESTS PASSED" is printed in the
end if there are no failures.  So are we done since there are no failures?
Hold your horses!  The reason there were no failures is because all tests are
currently empty.

2. Now you are ready to fill in the test cases in RentACatTest.  If you want to do a sanity test, try a very simple assertion that always succeeds in testGetCatNullNumCats0:
    ```
    assertTrue(true);
    ```
    Now you see the message "ALL TESTS PASSED" again.  Yes!  Now let's try an assertion that fails.  Change the above to:
    ```
    assertFalse(true);
    ```
    Now you should see a test failure like the below:
    ```
    testGetCatNullNumCats0(RentACatTest): null

    !!! - At least one failure, see above.
    ```
    What does that null mean?  It just means you didn't supply a failure message.  Try the following:
    ```
    assertFalse("True is not false", true);
    ```
    Then you should get:
    ```
    testGetCatNullNumCats0(RentACatTest): True is not false

    !!! - At least one failure, see above.
    ```
    
3. Now you are ready to start writing the RentACatTest class for real.  Start
   by adding very simple tests to gain confidence.  Next, try adding more
complex cases that require Cat objects.  For that, you will have to modify
setUp() to create some Cat test doubles with proper stubs.  We learned how to
do that in class.  If you are still unsure, look at the
[LinkedListUnitTest.java](https://github.com/wonsunahn/CS1632_Spring2022/blob/main/sample_code/junit_example/LinkedListUnitTest.java)
sample code or the NoogieTest and CoogieTest under the Example/ directory.

## Tips

1. We will try to apply the Test Driven Development (TDD) model here.  Try
   writing the test case(s) FIRST before writing the code for a feature.  This
way, you will always have 100% test coverage for the code you have written and
are writing.  Hence, if you break any part of it in the course of adding a
feature or refactoring your code, you will know immediately.  Otherwise, if you
test at the very end, you may have to do some major changes.

1. Each @Test method represents a test case.  A JUnit class with one or more
   @Test methods represents a test plan. A JUnit class is usually named after
whichever class it is testing, with the string `Test` appended to the tested
class name.  For example, Foo.java would be tested by FooTest.java.  But this
is not necessarily the case.  A JUnit class may test multiple classes with
related functionality; in that case, it is typically named after the
functionality it is testing.
  
1. Make use of the @Before and @After methods in your JUnit testing.  @Before
   and @After methods are invoked before and after each @Test method.  They are
used to set up and tear down test fixtures.  Test fixtures in JUnit are objects
that need to be created and initialized before performing each test case.  You
can think of them as "actors" in your test script.  Having the @Before and
@After allows us to avoid duplicating test fixture code in each test case.

1. In RentACatTest.java, pay close attention to the Javadoc comments on top of
   each @Test method which describe the preconditions, execution steps, and
postconditions.  Remember, part of the preconditions may be already fulfilled
with the test fixture initialized in the @Before method.

1. You should use test doubles/mocks for any references to classes other than
   the one under test that the tested class is dependent upon (i.e. you need to
mock any Cat objects).  In fact, if you don't mock Cat and use the actual Cat
objects, your tests will most likely fail.  I have injected artificial defects
in the form of exceptions into the Cat class to emulate a buggy Cat class.
Because your tests should work regardless of what's inside Cat.  As to the
ArrayList class used to store the list of Cats, you do not need to mock it even
if RentACatImpl is dependent upon it.  ArrayList is a Java standard library
class so we will assume that it is fully tested and defect-free at this point.
:)

1. Remember to _not_ mock the class under test (i.e. RentACat), only external
   classes that it depends upon (i.e. Cat).  If you mock RentACat, and then
test the behavior that you are mocking, what are you testing?  You are testing
your test code and not your implementation!  In fact, this type of test is
called a **tautological test** since it will always pass, regardless of whether
your implementation has a defect or not.

1. The easiest thing to do is assert against a return value, but you can also
   assert against attributes of an object.  For example:

    ```
    @Test
    public void testCatName() {
       assertEquals("Expected name", cat.getName());
    }
    ```
    You can also use the Mockito verify method to perform behavior verification.

## Expected Outcome

Once you start filling in tests in RentACatTest, you will start to see some of
those tests fail for those methods you haven't completed yet for RentACatImpl.
As you start filling in the methods in RentACatImpl, those failures will go
away one by one until you again see the output:

```
ALL TESTS PASSED
```

You have come full circle!  But wait, does this mean RentACat is bug-free?  How
do you know if your unit tests themselves had defects and that's why they
passed, even when RentACat is buggy?We have to actually verify the unit tests
themselves to make sure that they are not defective!  One way to verify unit
tests is to test them on buggy programs to see if they detect the bugs as they
are intended to.  I have created a buggy version of Rent-A-Cat just for this
purpose named RentACatBuggy.java.  In order to apply your unit tests to
RentACatBuggy, execute the following.  For Windows:

```
runTestBuggy.bat
```

For Mac or Linux:
```
bash runTestBuggy.sh
```

If you run the above, you should get output that looks like
[runTestBuggy.output.txt](runTestBuggy.output.txt).  Note that I've commented
out the following line at TestRunner.java:30 to make the output less verbose:

```
System.out.println(f.getTrace());
```

The above will print a full Java stack trace for every failure.  It is useful
when a test fails due to a crash in your program and you want to locate exactly
in which source code line the Java exception was thrown.  The defects in
RentACatBuggy does not involve crashes due to exceptions so I've temporarily
commented it out for brevity.

You can see that all tests fail except the ones for getCat(int id).  That is
because I've inserted bugs into RentACatBuggy except for that method.  If your
unit test passes any other method, it must be defective.  Time to fix your
test!

## Measuring Code Coverage

Code coverage is a metric that measures what percentage of the code base a
particular test run covered.  Typically a code coverage of above 80\% or 90\%
is targeted in software organizations.  There are several ways to measure code
coverage (as we will learn later), but the most widespread method is to measure
the percentage of code lines covered.

Jacoco (short for **Ja**va **Co**de **Co**verage tool), is a popular code
coverage measurement tool among Java developers, and that's what we will use in
this class.  There are two ways to invoke Jacoco: 1. Using the Jacoco plug-in
pre-installed in Eclipse or 2. Using the Jacoco command line interface.

1. Using the Jacoco Eclipse plug-in

   Here is the user guide: https://www.jacoco.org/userdoc/launching.html.  It
is just a click of a button and requires no extra installation.  I have already
created an Eclipse project for you in the exercise directory so you can just
open that to run TestRunner using File > Open Projects from File System from
the menu.  If you can't open the project for some reason, you need to create a
new project using File > New > Java Project.  In the newly created project, you
need to include the four JAR files under CommandLineJUnit/ as external JARs for
it to compile.  You need to go to project properties > Java Build Path >
Libraries and Add JARs or Add External JARs.  

   When you run the code coverage tool, make sure you run TestRunner, not
RentACatImpl.  You can do that by clicking on and highlighting TestRunner.java
before clicking on the code coverage button.  Alternatively, you can right
click on TestRunner.java and click on the "Coverage as" item in the menu that
pops up.  This is important.  If you run RentACat.java, you will be getting the
code coverage while playing the game.

   Once run, a new "Coverage" tab gets created at the bottom panel beside the
"Problems", "Search", "Console", and "Terminal" tabs.  If you have implemented
all the code, it should look similar to the following screenshot:

   <img alt="Code Coverage Eclipse" src=code_coverage_eclipse.png width=700>

1. Using the Jacoco command line interface
   For Windows, do the following:
   ```
   runJacoco.bat
   ```
   For Mac or Linux, try doing:
   ```
   bash runJacoco.sh
   ```

   Running the above script will generate a coverage profile file jacoco.exec
and also generate a report in HTML format under the folder jacocoReport.  Open
the index.html file under JacocoReport on a web browser and navigate to the
coverage report for the RentACatImpl class
(jacocoReport/default/RentACatImpl.html).  If you have implemented all the
code, it should look similar to the following screenshot:

   <img alt="Code Coverage Jacoco" src=code_coverage_jacoco.png width=700>

   It's possible to generate reports in formats other than HTML such as CSV or
XML, which can be integrated into other tool chains.  Detailed Jacoco report
generation options are at: https://www.jacoco.org/jacoco/trunk/doc/cli.html

You don't have to have 100% coverage for this exercise but you will have
coverage requirements for Deliverable 2.  

## Using an IDE (Integrated Development Environment)

You may use any IDE or Java source code editor of your choice for this
exercise, and all remaining exercises and deliverables in this course.
However, the official IDE for this course is Eclipse, as in all examples will
be demonstrated using that IDE and that is the only IDE for which support is
provided.  If you haven't used Eclipse before, then it is a good time to start
learning it as it is one of the most popular IDEs in use for Java and other
programming languages.

That said, all instructions for all assignments will be given based on the
commandline, just like I did for this README.  Using an IDE is just an added
convenience.  For example, many of the actions you have to take on this README
are integrated into an IDE like Eclipse.  Eclipse auto-compiles your source
code on every code modification without you having to do anything.  Eclipse
allows you to save run configurations and run commandlines on the press of a
button.  Eclipse also provides integration with JUnit, allowing you to run
JUnit tests from the GUI.  I will show you how to do all of this in class, so
you only need to pay attention and follow along!

Note that I have pre-created Eclipse projects for each assignment so you just
need to open them to get the development environment (the .project file is the
Eclipse project file).  You only need to go to the menu item **File > Open
Projects from File System** and navigate to the exercise directory.

## Submission

Each pairwise group will do one submission to GradeScope as usual.  The
submitting member must use the "View or edit group" link at the top-right
corner of the assignment page after submission to add his/her partner.  

The submission this time is divided into two parts:

1.  Submit the repository created by GitHub Classroom for your team to
    GradeScope at the **Exercise 2 GitHub** link.  Once you submit, GradeScope
will run the autograder to grade you and give feedback.  If you get deductions,
fix your code based on the feedback and resubmit.  Repeat until you don't get
deductions.

1. Create a screenshot of code coverage stats using one of the two
   methodologies shown above.  Make sure you take the screenshot of the correct
screen.  After you have created the screenshot, save the picture to a PDF file
and submit to GradeScope at the **Exercise 2 Coverage** link.  Make sure the
picture fits in one page for easy viewing and grading.

## GradeScope Feedback

The GradeScope autograder works in 3 phases:
1. RentACatTestSolution.(some method) on RentACatImpl: RentACatTestSolution is the solution implementation of RentACatTest.  The purpose of this phase is to verify that RentACatImpl (your RentACat implementation) does not have any defects.
1. RentACatTest.(some method) on RentACatSolution: RentACatTest is your submitted JUnit test for RentACat.  The purpose of this phase is to test RentACatTest itself for defects.  RentACatSolution is the solution implementation of RentACat and contains no defects (that I know of).  Hence, all tests in RentACatTest should pass.
1. RentACatTest.(some method) on RentACatBuggy: RentACatTest is your submitted JUnit test for RentACat and you are testing against the buggy RentACatBuggy implementation.  The purpose of this phase is to further test RentACatTest for defects.  It does this by testing whether RentACatTest finds all the bugs that RentACatTestSolution is able to find within RentACatBuggy.
If you see test failures, read the feedback given by the autograder, fix your code, and retry.

Beside the feedback given by the autograder, the TA or myself will leave more detailed feedback on the "Feedback on source code" question.  We will also check your code coverage screenshot submission and give feedback.

## Groupwork Plan

There are two files needing modification: RentACatTest.java and
RentACatImpl.java.  One way you can divide up the work is like the following:

[RentACatTest.java]

* Partner 1:
  * setUp
  * testCatAvailableFalseNumCats0
  * testCatAvailableFalseNumCats3
  * testCatAvailableTrueNumCats3
  * testCatExistsFalseNumCats0
  * testCatExistsTrueNumCats3
  * testReturnCatFailureNumCats0
  * testReturnCatNumCats3
* Partner 2:
  * setUp
  * testGetCatNullNumCats0
  * testGetCatNumCats3
  * testListCatsNumCats0
  * testListCatsNumCats3
  * testRentCatFailureNumCats0
  * testRentCatFailureNumCats3

Notice that the setUp method is repeated for both partners.  Both of you will
have to implement the setUp method individually to have your assigned tests
working.  You will eventually merge the two methods into one on your final
submission.

[RentACatImpl.java]

* Partner 1:
  * catExists
  * returnCat
* Partner 2:
  * listCats
  * rentCat

Push frequently and also pull frequently from your GitHub repository whenever
you are done finishing a method to merge changes as you go along.  Since we are
doing TDD, start with RentACatTest.java and then move on to RentACatImpl.java.
Please help each other, as always.

When you are done with coding, each of you please produce the code coverage
stats using Jacoco and compare the results to make sure they are the same.
Submit one copy.

## Resources

These links are the same ones posted at the end of the slides:

* JUnit User Manual:  
https://junit.org/junit4/

* JUnit Reference Javadoc:  
http://junit.sourceforge.net/javadoc/  
For looking up methods only, not a user guide.

* Mockito User Manual:  
https://javadoc.io/static/org.mockito/mockito-core/3.2.4/org/mockito/Mockito.html  
Most useful is the sections about verification and stubbing.

* Jacoco User Manual:  
https://www.jacoco.org/userdoc/index.html

* Jacoco Java Agent Manual:  
https://www.jacoco.org/jacoco/trunk/doc/agent.html

* Jacoco CLI (Command Line Interface) Manual:  
https://www.jacoco.org/jacoco/trunk/doc/cli.html

* Eclipse IDE
https://help.eclipse.org/
Look at the "Java development user guide" chapter on the left.
