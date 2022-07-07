- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Description](#description)
  * [Task 1: Write test cases](#task-1-write-test-cases)
  * [Task 2: Find three defects and write test cases for them](#task-2-find-three-defects-and-write-test-cases-for-them)
  * [Task 3: Add test cases to test suite and save project](#task-3-add-test-cases-to-test-suite-and-save-project)
  * [Task 4: Export test suite to JUnit class](#task-4-export-test-suite-to-junit-class)
  * [Submission](#submission)
    + [GitHub submission](#github-submission)
    + [Report submission](#report-submission)
  * [Grading](#grading)
  * [GradeScope Feedback](#gradescope-feedback)
  * [Resources](#resources)

# CS 1632 - Software Quality Assurance
Spring Semester 2022

DUE: Mar 15 (Tuesday), 2022 1:00 PM

**GitHub Classroom Link:** https://classroom.github.com/a/BpbufWL7

## Description

For this assignment, you and a partner will write systems-level, automated
black-box tests for a web app using the Selenium IDE. 

The web app is located here: https://cs1632.appspot.com/

I am assuming that you have done Exercise 3 by now.  If you haven't, please go
do it before working on this deliverable.  I will skip detailed instructions on
the Selenium IDE that we already went over on Exercise 3.

For those of you who are working in groups, you will be working on the same
shared .side project file. So it is especially important that your pull before
opening the project file and push immediately after you have modified and saved
the project file. Otherwise, you may get merge conflicts. Merging conflicts is
possible by using the technique I went over with the
[Using\_Git](https://github.com/wonsunahn/CS1632_Spring2022/blob/master/lectures/Using_Git.pdf)
slides, but it's best to avoid it.  In the case of a merge conflict, the .side
file is in JSON format, so it shouldn't be too difficult to patch up.

## Task 1: Write test cases

Write all the test cases in [testplan.md](testplan.md) to test the requirements
listed in [requirements.md](requirements.md).  Name each test case using the
IDENTIFIER for the test case.  This is important for the purposes of GradeScope
autograding.  

## Task 2: Find three defects and write test cases for them

This is another buggy product.  You should find at least three defects and
report them using the standard defect template (just like in the first
deliverable).  You may want to do some exploratory testing first to see what
kind of issues are found before writing automated tests for them.  Think of
equivalence classes, edge cases and corner cases as we learned so far.

Next, write three additional test cases that fail and uncover those three
defects.  Name these test cases in this format: DEFECT[N]-[Requirement Name],
where N is 1, 2, or 3.  For example,  if you found 2 defects related to the
FUN-RENT requirement and 1 defect related to the FUN-FEED-A-CAT requirement, you
will name them:

* DEFECT1-FUN-RENT
* DEFECT2-FUN-RENT
* DEFECT3-FUN-FEED-A-CAT

Normally you wouldn't name them this way since there is no separate category of
tests that are meant to detect defects.  All tests are meant to detect defects!
This is only for ease of grading on GradeScope.

## Task 3: Add test cases to test suite and save project

1. Choose "Test Suites" from the left panel drop down menu.

1. There will already be a "Default Suite" there with possibly one or more tests.

1. Right click on "Default Suite", or click on the vertical-3-dot context menu button, and select "Rename" and rename to "D3".

1. Right click on "D3", or click on the vertical-3-dot context menu button, and
   select "Add tests".  Make sure all tests are checked.  Press the "Select"
button.

1. Click on the "Save project" button on the top right corner that looks like a
   floppy disk.  Save to file name "D3.side" in the deliverable root folder.

## Task 4: Export test suite to JUnit class

Once you are done writing your Selenium test suite, let's try exporting the test
suite in Selenium IDE to a Java JUnit test class.  

Follow these instructions:

1. Right click on "D3", or click on the vertical-3-dot context menu
   button, and select "Export".

1. Select "Java JUnit" in the list of language options and optionally check
   "Include step descriptions as a separate comment" to generate more detailed
comments.  Leave other boxes unchecked.

1. Save the resulting file into "D3Test.java".

1. As in Exercise 3, you will have to also add these lines to the beginning of the @Before
setUp() method in D3Test.java, depending on whether you are using Chrome of Firefox:

   If you are using Chrome:
   ```
   System.setProperty("webdriver.chrome.driver", "Chrome/chromedriver-win32.exe");
   ```
   Or whatever the path is to your OS compatible Chrome webdriver.  

   If you are using Firefox:
   ```
   System.setProperty("webdriver.gecko.driver", "Firefox/geckodriver-win64.exe");
   System.setProperty("webdriver.firefox.logfile", "/dev/null");
   ```
   Or whatever the path is to your OS compatible Firefox webdriver.  


You can now run the D3Test JUnit class using the provided
[TestRunner.java](TestRunner.java) using one of the following scripts:

* If you are running Windows:
   ```
   run.bat
   ```

* If you are running Mac or Linux:
   ```
   run.sh
   ```

Make sure all tests pass by looking at the TestRunner result (the tests that
should pass, not the ones that uncover defects of course :).  If there are any
failures, slightly touch up the D3Test.java Selenium tests to make them pass.
Although Selenium IDE usually does a good job in the translation, sometimes it
needs an extra hand.  Refer to the Exercise 3 troubleshooting guide:

https://github.com/wonsunahn/CS1632_Spring2022/blob/master/exercises/3/README.md#tips-for-junit--selenium-problem-solving

## Submission

Each group will do one submissions to GradeScope as usual.  The submission is
done in two parts: the GitHub Classroom repository and a report.

### GitHub submission

Submit your github repository to GradeScope at the **Deliverable 3 GitHub**
link.  Once you submit, GradeScope will run the autograder to grade you and
give feedback.  If you get deductions, fix your code based on the feedback and
resubmit.  Repeat until you don't get deductions.

Please make sure you have the **D3.side** and **D3Test.java** files in your
repository.

### Report submission

Submit your report to GradeScope at the **Deliverable 3 Report** link.  Please
use the [ReportTemplate.docx](ReportTemplate.docx) file provided in this
directory to write a short report.  A PDF version of the file is at
[ReportTemplate.pdf](ReportTemplate.pdf).

Everyone should have a title page with:
* Your names / team name
* The title "CS 1632 - DELIVERABLE 3: Automated Web Testing"

On the FIRST PAGE introduction, please describe the division of work between
group members and also any difficulties you faced while using Selenium.

ON A SEPARATE PAGE, write a defect report.  There should be at least 3 defects.
Each defect should contain all necessary components including REPRODUCTION
STEPS, EXPECTED BEHAVIOR, OBSERVED BEHAVIOR, etc. described in Deliverable 1.  

## Grading

* Summary and retrospective - 5% 
* Defect reports - 15%
* GitHub autograder results - 80%

Please review grading\_rubric.txt for details.

## GradeScope Feedback

The GradeScope autograder works in 2 phases:

1. **D3Test on https://cs1632.appspot.com/**: This tests your D3Test.java file
   on the D3 website as originally intended.  All of your tests should pass.

1. **D3Test on https://cs1632-buggier.appspot.com/**: This tests your
   D3Test.java on an even buggier version of the D3 website.  To do this, all your URLs are changed to the buggier website.  Now all tests should fail.  You can test this yourself easily by changing the base URL of your D3Test.java.

If you get deductions, both websites are available to you, so try them out
yourself.

## Resources

These links are the same ones posted at the end of the slides:

* Selenium IDE Command Reference:  
https://www.selenium.dev/selenium-ide/docs/en/api/commands
