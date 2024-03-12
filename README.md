# Lab 03 - LLM

## Task 1 - Code Generation

### ChatGPT without tests

With this first approach, `ChatGPT` was requested to implement the `TimetableFactory`, providing all the interfaces available but **not** the tests.

The code generated is located in the package *chatgpt.withouttests*. It consists of two implementations: one for the factory and the other for the timetable interface. However, it had the following problems:

- The tests were not working because it didn't check if the timetable was empty when an activity was added for the first time.

- The method `addHour` with the **"zero-shot"** prompt strategy didn't consider the scenario of adding hours to the timetable with activities or days not already present. However, with the **"few-shot"** approach, it fixed the error.

> "Why do you search in the table for the activity or the day when you add an hour? You cannot assume that the day or the activity that I want to add is already in the table. For example, I can first add one hour on day1: `addHour("act1", "day1")` and then another hour on day2 with a different activity: `addHour("act2", "day2")`."


### ChatGPT with tests

With this second approach, `ChatGPT` had also the file with all the tests that must pass, and a similar prompt as the first approach was used but asking also to make the tests pass.

The code generated is located in the package *chatgpt.withtests* and it consists also in this scenario in the two implementations, one of the factory and one of the timetable. However, it has the following problems:

- Only 1/4 tests pass with the first generation. The implementation of the timetable contains an error in the method for adding the hours, which when solved make 3/4 tests pass.

- The remaining failing test is about the cut method: when it is applied a cut to set all the hours in the table to 0, the generated code was also deleting the registered activities and days.

### Conclusions

- In general, the code produced is more accurate if the tests are also passed, but there **is not** a proper check on the tests because it needs some other prompt for handling the correct solution.

- If the generation contains errors, it is more efficient to try a **few-shot** approach to fix the errors. With examples, the LLM understands the problem better.

- Regarding the **quality** of the code produced, the code is a bit **repetitive** in both scenarios, but cleaner in the second one.

