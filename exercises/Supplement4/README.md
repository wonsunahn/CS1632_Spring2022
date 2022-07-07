# CS 1632 - Software Quality Assurance
Spring Semester 2022 - Supplementary Exercise 4

* DUE: Apr 15 (Friday), 2022 11:59 PM 

## Description

During the semester, we learned various ways in which we can automate testing.
But all that automation is of no use if your software organization as a whole
does not invoke those automated test scripts diligently.  Preferably, those test
scripts should be run before every single source code change to the repository,
and for good measure, regularly every night or every weekend just in case.  Now,
there are many reasons why this does not happen if left to individual
developers:

1. Developers are human beings so they forget.  Or, they remember to run some
   tests, just not the test suites that are relevant to the changes they have
made.

1. Developers are sometimes on a tight schedule, so they are tempted to skip
   extensive testing that may delay them.

1. Developers are sometimes aware that certain test cases do not pass, but they
   are tempted to push the code to the repository anyway, because again, they
have a deadline to meet.  They justify their actions by telling themselves that
they will fix the failing tests "as soon as possible", or that the test cases
are not testing anything important, or that failing test cases in modules under
the purview of another team "is not my problem".

In Part 1 of this exercise, we will learn how to build an automated "pipeline"
of events that get triggered automatically under certain conditions (e.g. a
source code push).  A pipeline can automate the entire process from source code
push to software delivery to end users, making sure that if software is
delivered, it has been fully tested, and if one more tests fail, the delivery is
stopped in its tracks and the defects are reported properly to the relevant
people.

In Part 2, we will learn how to use dockers as part of our testing stage.
Dockers are virtualized execution environments which can emulate the execution
environments in the deployment sites (OS, libraries, webservers, databases,
etc.) so that software can be tested in situ.  In our case, we will test our
software package we built in Part 1 in the context of a webserver running on a
docker.

# Prerequisites

1. Get a free account from https://test.pypi.org/.

1. Get a free account from https://GitLab.com by starting the free trial.  If
   you are a new user, you may have to provide credit card information in order
to use CI/CD pipelines:
https://about.gitlab.com/pricing/#why-do-i-need-to-enter-credit-debit-card-details-for-free-pipeline-minutes.
It is still free up to 400 CI/CD minutes per month, which is more than enough
for this exercise.  You will be prevented from running CI/CD pipelines if you
go over the quota, so you don't need to be worried about surreptitious
charging.  GitLab used to not require credit card info but they started asking
for it to prevent cryptocurrency miners from abusing cloud resources.  In order
to find out if you need the credit card info, go to `CI/CD > Pipelines` after
having forked the Part 1 repository and try clicking on `Run pipeline`.  It
will ask you for the info at that point.  If you are an old user, like I am,
you wouldn't need to provide it and the pipeline will just run.

1. Install Python 3.9 from https://www.python.org/downloads/ (any version 3.9.*
   should work) After installing Python, running `python -V` should give you the
proper version number.  If it says there is no such command, make sure you check
a box that says "Add Python to environment variables" during installation.

   For MacOS and Linux, you may get an older version of Python because Python
version 3 installs a binary named python3 and does not overwrite the old binary
named python.  So you may have to alias python such that it refers to the new
python3 binary.  You can do that by editing ~/.bash_profile (using the nano
editor in this example):

   ```
   nano ~/.bash_profile
   ```

   And then adding somewhere the following two lines:

   ```
   alias python=/usr/local/bin/python3
   alias pip=/usr/local/bin/pip3
   ```

   After having done this, you can source your bash_profile to your current bash shell:

   ```
   source ~/.bash_profile
   ```

   Or, you can just launch a new terminal bash shell, and it will automatically read in the new profile.
    
   Now you should get the correct versions for python and pip (I install Python version 3.9.8):

   ```
   wahn:~ wahn$ python -V
   Python 3.9.8
   wahn:~ wahn$ pip -V
   pip 21.2.4 from /Library/Frameworks/Python.framework/Versions/3.9/lib/python3.9/site-packages/pip (python 3.9)
   ```

1. Install Docker from https://docs.docker.com/get-docker/. For Windows, you will
   have to install WSL2 along with docker as noted in the instructions.  If all
goes well, you should be able to launch Docker Desktop with no issues.

# Part 1: CI/CD Pipelines

Please go to: https://gitlab.com/wonsun.ahn/simple-python-package/.  And then
fork the repository by clicking on the `Fork` button at the top right.  This
will give you your own repository to work on.  Also, clone the forked
repository to your laptop using the HTTPS clone link.  Then, follow the
README.md in that repository after cd'ing into the local clone.  We will go
over the entire process in class.

# Part 2: Dockers

Please go to: https://gitlab.com/wonsun.ahn/dockerized-server.  Just like for
Part 1, fork the repository, clone it, and then cd into it.  Then follow the
README.md instructions.  Again, we will go over this in class so you just need to follow along.

# Submission

Please replace the example screenshots in
[ReportTemplate.docx](ReportTemplate.docx) with your own screenshots.  These
is a [PDF version](ReportTemplate.pdf) as well.  Please read the
instructions on the template carefully.  For those of you who are unable to
run pipelines on GitLab because you cannot / do not want to provide credit
card information, some parts of the report are optional.

Wnen you are done, submit to the "Supplementary Exercise 4 Report" link on
GradeScope.  You can divvy up the screenshot taking with your partner, but I
want each of you to try going through the exercise in your own GitLab
accounts.
