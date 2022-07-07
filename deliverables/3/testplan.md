TEST FIXTURE:
1. Firefox browser version >= 97, or Chrome browser version >= 98 is installed and launched.
2. https://cs1632.appspot.com/ is opened on the web browser by typing the URL on the address box.
3. The Rent-A-Cat system is reset by clicking on the "Reset" link (all three cats available).

TEST CASES:

```
IDENTIFIER: TEST-LINKS
TEST CASE: Check that the "Reset" link properly points to `/reset`.
PRECONDITIONS: None.
EXECUTION STEPS: None.
POSTCONDITIONS: The href link of the "Reset" menu item points to `/reset`.
```

```
IDENTIFIER: TEST-LISTING
TEST CASE: Check that the listing has three cats and the third is "ID 3. Mistoffelees".
PRECONDITIONS: None.
EXECUTION STEPS: None.
POSTCONDITIONS: 
1. There are exactly three items in the listing.
2. The text in the third item is "ID 3. Mistoffelees".
```

```
IDENTIFIER: TEST-CATALOG
TEST CASE: Check that the second item in the catalog is an image named "cat2.jpg".
PRECONDITIONS: None.
EXECUTION STEPS: None.
POSTCONDITIONS: The source of the second image in the catalog is "/images/cat2.jpg".
```

```
IDENTIFIER: TEST-RENT-A-CAT
TEST CASE: Check that the "Rent" and "Return" buttons exist in the Rent-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Rent-A-Cat" link.
POSTCONDITIONS: 
1. A "Rent" button exists on the page.
2. A "Return" button exists on the page.
```

```
IDENTIFIER: TEST-RENT
TEST CASE: Check that renting cat ID 2 works as expected.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Rent-A-Cat" link.
2. Enter "2" into the input box for the rented cat ID.
3. Press the "Rent" button.
POSTCONDITIONS: 
1. The second item in the cat listing is "Rented out".
2. The text "Success!" appears beside the "Rent" button.
```

```
IDENTIFIER: TEST-RETURN
TEST CASE: Check that returning cat ID 2 works as expected.
PRECONDITIONS: Cat ID 2 has been rented out using these steps.
1. Press the "Rent-A-Cat" link.
2. Enter "2" into the input box for the rented cat ID.
3. Press the "Rent" button.
EXECUTION STEPS:
1. Enter "2" into the input box for the returned cat ID.
2. Press the "Return" button.
POSTCONDITIONS: 
1. The second item in the cat listing is "ID 2. Old Deuteronomy".
2. The text "Success!" appears beside the "Return" button.
```

```
IDENTIFIER: TEST-FEED-A-CAT
TEST CASE: Check that the "Feed" button exists in the Feed-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Feed-A-Cat" link.
POSTCONDITIONS: 
1. A "Feed" button exists on the page.
```

```
IDENTIFIER: TEST-FEED
TEST CASE: Check that feeding 6 catnips to 3 cats results in "Nom, nom, nom.".
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Feed-A-Cat" link.
2. Enter "6" into the input box for number of catnips.
3. Press the "Feed" button.
POSTCONDITIONS: 
1. The text "Nom, nom, nom." appears beside the "Feed" button.
```

```
IDENTIFIER: TEST-GREET-A-CAT
TEST CASE: Check that 3 cats respond with three "Meow!"s in the Greet-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Press the "Greet-A-Cat" link.
POSTCONDITIONS: 
1. The text "Meow!Meow!Meow!" appears on the page.

```

```
IDENTIFIER: TEST-GREET-A-CAT-WITH-NAME
TEST CASE: Check that greeting Jennyanydots results in "Meow!"s in the Greet-A-Cat page.
PRECONDITIONS: None.
EXECUTION STEPS:
1. Navigate to the `/greet-a-cat/Jennyanydots` URL be opening on browser.
POSTCONDITIONS: 
1. The text "Meow! from Jennyanydots." appears on the page.
```

```
IDENTIFIER: TEST-RESET
TEST CASE: Check that resetting the system results in all cats being available.
PRECONDITIONS: Cat ID 2 has been rented out using these steps.
1. Press the "Rent-A-Cat" link.
2. Enter "2" into the input box for the rented cat ID.
3. Press the "Rent" button.
EXECUTION STEPS:
1. Press the "Reset" link.
POSTCONDITIONS: 
1. The first item in the cat listing is "ID 1. Jennyanydots".
2. The second item in the cat listing is "ID 2. Old Deuteronomy".
3. The third item in the cat listing is "ID 3. Mistoffelees".
```
