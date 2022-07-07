- [CS 1632 - Software Testing](#cs-1632---software-testing)
  * [Description](#description)
  * [Coffee Maker Quest](#coffee-maker-quest)
  * [Format](#format)
  * [Test Plan / Defect Reporting Tips](#test-plan--defect-reporting-tips)
  * [Grading](#grading)
  * [Submission](#submission)
  * [Groupwork Plan](#groupwork-plan)

# CS 1632 - Software Testing
Spring Semester 2022

* DUE: Feb 1, 2022 1:00 PM

## Description

For this assignment, your group will determine a test plan for the simple game
Coffee Maker Quest, based on the requirements listed.  There are several known
**defects** in the software; you will need to find and report on **at least three**.
Additionally, a traceability matrix showing the mapping of test cases to
requirements is required.

Each requirement should have AT LEAST one test case associated with it in order
to have good test coverage.  Each test case should have AT LEAST one
requirement associated with it (no orphaned test cases).  One test case may
happen to test more than one requirement using a single set of inputs, and that
is fine.  The above can easily be checked via a traceability matrix (which you
should also deliver). 

Test cases should mention all necessary preconditions, execution steps, and
postconditions.  Please refer to [Exercise 1](../../exercises/1) on how to write
good test cases.

I expect you to test some **edge cases (at least one)** and some **corner cases (at least one)** as part of the test plan.
If you do this, I'd estimate that the number of test cases is at least 2x the
number of requirements.  If the number of test cases is more than 3x the number
of requirements, you are probably overtesting.

You are expected to execute the test plan in order to find the defects.  There
are AT LEAST three.  Full credit will be given only to those who properly find
and describe them.  While you are not expected to find all of the defects, a
reasonable test plan should definitely find at least three.  This is an
intentionally target-rich environment.

## Coffee Maker Quest

Coffee Maker Quest is a simple game.  The goal is get coffee, sugar, and cream,
and then drink it so that you can stay up and study.  In order to do so, you
need to visit several rooms in a house and look around.  Once you have obtained
all the necessary elements, you win.  If you decide to drink before getting all
of the necessary elements, you lose.

You can run it downloading the coffeemaker.jar file and running:
```
bash$ java -jar coffeemaker.jar
```

The requirements are listed in the file [requirements.md](requirements.md).

## Format

The report should start with a cover page with:
* The names of the people in the group

Write a short introduction that includes:
* How you divided the work between members of the group.
* IDENTIFIER of one edge case and why you think it is an edge case.
* IDENTIFIER of one corner case and why you think it is a corner case.

ON A NEW PAGE, a traceability matrix should be provided mapping the test cases
with their associated requirements.  Remember that all requirements should map
to AT LEAST ONE test case, and all test cases should map to AT LEAST ONE
requirement.  

ON A NEW PAGE, a list of the actual test cases should follow.  You may name
them any way you wish, but please be consistent.  Please write them out in this
format -

	IDENTIFIER:
	TEST CASE: 
	PRECONDITIONS:
	EXECUTION STEPS:
	POSTCONDITIONS:

ON A NEW PAGE, list at least three defects found.  The defects should follow
the defect reporting template:

	IDENTIFIER:
	SUMMARY:
	DESCRIPTION:
	REPRODUCTION STEPS:
	EXPECTED BEHAVIOR:
	OBSERVED BEHAVIOR:

Please apply all the things you learned through **Exercise 1** to this
deliverable.  For example, don't forget to include any preconditions at the
beginning of the REPRODUCTION STEPS.

There are a few things that are different from Exercise 1 though:

1. Execution Steps: Exercise 1 had just one execution step (launching the
   program).  You will have multiple steps for most test cases in this
deliverable.  All steps must be numbered, clearly delineated, and unambiguous.
For example, it should not say "Go north".  Instead it should say "Type N and
press [Enter]".

2. Preconditions: Unlike Exercise 1, the user goes through a prolonged
   interaction with the program.  Everytime there is an interaction, internal
program state gets modified.  Certain test cases may be performed only when a
set of internal program state prerequisites are satisfied.  For example, you
might want express a precondition that the player has sugar in possession but
no cream or coffee.  As you can see, now preconditions can include program
state as well as system setup (such as the Java installation).  

   In order to express this precondition, you have two options: 1) Just
straight up say "The player has sugar" or 2) List the input steps that led to
the program state where the player has sugar.  Which do you prefer?

   The former has brevity on its side, but if you really want your test case to
be reproducible you should really do the latter.  Because depending on the
steps you took the acquire the sugar, the program could be internally in a
different state.  For example, suppose the program has an internal counter that
counts the number of rooms visited by the player.  Depending on the route you
took to get to the sugar, that counter value would be different and that value
may impact the final outcome of your program!

   So in summary, always describe preconditions as a series of performed
actions rather than the externally visible program state, especially if you
feel that the externally visible state does not fully describe internal program
state.  Then you may ask, wouldn't that make the preconditions harder to read?
It's much easier to understand "the player has sugar" rather than trying to
decypher the series of steps that leads to that precondition.  So the best
thng to do is: describe each precondition conceptually then below it, list the steps that
implement the precondition.  For example:
   ```
   PRECONDITIONS:
   "java -version" shows java version "1.8.0_231"
   The game has started and the player has sugar.  Steps:
     1. Launch program by: "java -jar coffeemaker.jar"
     2. Type "N [Enter]" in prompt 
     ...
   ```

The above also applies to defect reporting.  

## Test Plan / Defect Reporting Tips

Please read [tips.md](tips.md) for additional hints on how to properly document test cases and defects.

## Grading

* Introduction: 10% of grade
* Test Plan: 40% of grade
* Traceability Matrix: 20% of grade
* Defects Found and Described: 30% of grade

Please review the [grading_rubric.txt](grading_rubric.txt) for details.

## Submission

Please use the [ReportTemplate.docx](ReportTemplate.docx) file provided in this
directory to write your report.  If you don't have a .docx compatible word processor,
that's perfectly fine as long as you follow the same organization.  A PDF version of
the file is at [ReportTemplate.pdf](ReportTemplate.pdf).  Please make sure that
the intro, traceability matrix, test cases, and defects are on seperate pages.  You will
be submitting to GradeScope in PDF format.  When you submit, you will be asked
to assign pages in the PDF file to each rubric item: 1. Introduction, 2.
Traceability Matrix, 3. Test Cases, and 4. Defects.

Each pairwise group will do one submission to the **Deliverable 1** GradeScope
link in the manner described on Exercise 1.  Make sure that your partner is
added, or he/she will not get a grade!

## Groupwork Plan

Please create a shared online document for the report just like you did for
Exercise 1.  Divide the requirements up between the two of you in a sensible
way as in Exercise 1.  Once you are done, check each other's work, discuss, and
submit!
