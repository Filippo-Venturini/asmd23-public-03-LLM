# Lab 03 - LLM

## Task 1 - Code Generation

### ChatGPT without tests

With this first approach I simply requested to ChatGPT to implement the TimetableFactory, sending all the interfaces available but not the Tests.

The code generated consists in two implementations one for the factory and the other for the timetable interface.

-The tests are not working because he didnt checked if the timetable was empty when I add an activity for the first time.

-The method addHour with the "zero-shot" prompt strategy didn't considered the scenario of adding to the timetable hours with activities or days not already present. But with the "few-shot" approach it fixed the error. 

Prompt:"Why when you add an hour you search in the table for the activity or the day? You cannot assume that the day or the activity that I want to add is already in the table. For example I can first add one hour on day1: addHour("act1", "day1") and then another hour on day2 on a different activity: addHour("act2", "day2")".


### ChatGPT with tests

With this second approach I sent to ChatGPT also the file with all the tests that must pass, using a similar prompt as in the first approach but asking also to make the tests pass.

The code generated consist also in this scenario in the two implementations, one of the factory one of the timetable.

- Only 1/4 test pass with the first generation,  the implementation of the timetable contain an error in the method for adding the hours, that when solved make 3/4 tests pass

- The remaining failing test is about the cut method: when is abpplied a cut for put to 0 all the hours in the table, the generated code was deleting also the activities and the day registered.

In general the code produced is more accurate if passing also the tests, but there is not a proper check on the tests because it need some other prompt for handle the correct solution.
