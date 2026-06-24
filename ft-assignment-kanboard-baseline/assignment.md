# Using Large Language Models to Automatically Generate Assertions in Selenium Tests

## Objective

The goal of this assignment is to **explore the use of Large Language
Models (LLMs) to automatically generate assertions in Selenium WebDriver
test scripts** where they are missing.

Students will design an experiment to evaluate the effectiveness of an
LLM in producing meaningful, executable, and correct assertions under
different prompting conditions**. The goal of the experiment is to
analyses how the quality of the generated assertions varies depending on
the contextual information made available to the model**.

The LLM to be used will be unique for all groups and will be established
by the instructor.

## Applications

- As a first case study, the experiments must **be carried out on the
  Spring Petclinic web application**, a widely used reference app for
  testing. This will allow students to become familiar with the
  experimental procedure using an already known application. The test
  suite will be made available on Aulaweb, and you will have to use that
  one.

- As a second case study, students **will work on an additional
  open-source web application** assigned by the instructor. The test
  suite assigned to the different groups will be one of those available
  in the BEWT repository: <https://github.com/OlianasD/BEWT>.A table
  will be made available on Aulaweb indicating, for each group, the
  application to be considered.

  *Before starting the experiment, after installing the applications,
  run the test suite several times to verify that all test cases pass
  and that there are no flakiness issues. If any flakiness problems
  occur, try to fix them by adding appropriate Thread.sleep statements.
  Also pay attention to the fact that running a test suite multiple
  times may change the state of the application, for example by adding a
  user. This may cause some tests to fail if the application is not
  restored to a clean initial state. Therefore, before running the
  experiment, make sure that the application is in a clean initial
  state. The Petclinic test suite provided on AulaWeb is designed to
  test Petclinic running locally. If you are using the Dockerized
  version instead, please modify it accordingly. Also, make sure to
  customize the POM files as needed.*

## Prompting Strategy and Treatments

The experiments must be performed in a **zero-shot setting**, meaning
that the model will receive only the instruction and the available
context, without worked-out examples of how assertions should be
generated. The purpose is to evaluate how well the model can understand
the Selenium test context and generate meaningful assertions without
additional examples.

For each application, students must consider the **following three
treatments**:

1.  **Without comments**: each test (deprived of the original assertion)
    contains no explicit comments describing the assertion to be
    generated. The process will consist of two main steps. First, the
    assertions will be removed from the original test, which will then
    serve as the gold standard for the evaluation (see below). Second,
    in place of each removed assertion, a placeholder comment will be
    inserted to explicitly indicate where the LLM is expected to
    generate the missing assertion. Specifically, the following comment
    will be added: // Insert here a JUnit 5 assertion that correctly
    reflects the purpose of the test.

2.  **With comments**: each test (deprived of the original assertion)
    contains a natural language comment indicating what the assertion
    should check within the page. These comments may be generated
    previously and automatically using an LLM by analyzing the assertion
    to be described., but they must be manually validated before being
    used in the experiment. The natural language comment describing the
    assertion must be placed immediately before the placeholder comment:
    // Insert here a JUnit 5 assertion that correctly reflects the
    purpose of the test that substitutes the assertion.

3.  **With comments plus web pages**: each test (deprived of the
    original assertion) contains a natural-language comment indicating
    what the assertion should check on the page. In addition to the
    commented tests, the corresponding HTML pages observed before the
    assertion point will also be provided to the LLM. The web page must
    contain only the HTML and therefore must be cleaned of any
    JavaScript and CSS. These pages can be recorded in advance by
    executing the tests with suitable Selenium commands able to get the
    HTML and then included in the prompt given to the LLM

## Experimental Procedure

- Download from Aulaweb the Selenium test scripts for Petclinic.

- Prepare the three treatment versions described above: without
  comments, with comments, and with comments plus web pages.

- Compare results across treatments following the steps 2, 3 and 4
  explained below.

- Fill in the given excel file available on Aulaweb.

- For the second application, use the application assigned to your group
  in the table published on Aulaweb.

- Prepare the same three treatment versions as done for PetClinic.

- Compare results across treatments following the steps 2, 3 and 4
  explained below.

- Fill in the given excel file available on Aulaweb.

## Prompt Engineering to generate assertions

- Use the LLM established by the instructor.

- Experiment with zero-shot prompts to guide the model in generating
  assertions.

- In the version without comments, ask the LLM to substitute the
  placeholder *// Insert here a JUnit 5 assertion that correctly
  reflects the purpose of the test* with a JUnit 5 assertion.

- In the version with comments, ask the LLM to generate the assertion
  immediately after the comment, previously generated that explain in
  natural language the original assertion, substituting the placeholder
  // Insert here a JUnit 5 assertion that correctly reflects the purpose
  of the test.

- In the version with comments plus web pages, the prompt will include
  both the tests with comments and, as additional context, the HTML
  pages visited by the test case before the assertion point.

## LLM Model

The model to be used for the experiment is **Gemma-4-31B-it**, an
instruction-tuned model from Google DeepMind's Gemma family. It is a
31B-parameter model designed to follow natural-language prompts and
suitable for tasks involving reasoning, text understanding, and code
generation. For this reason, it is appropriate for our task, where the
model must generate missing JUnit 5/Selenium assertions from a test
case, optionally supported by assertion-guiding comments and cleaned
HTML context. The model is available through Google AI Studio and should
be used via API rather than running it locally. This simplifies the
setup and ensures that all groups interact with the same model under
comparable conditions.

## Example of Prompt

*You are given a Selenium JUnit 5 test where the original assertion has
been removed.*

*Generate only the missing JUnit 5 assertion at the placeholder
position.*

*Do not modify the rest of the test.*

*Do not add explanations.*

*The assertion must be executable and consistent with the purpose of the
test.*

*\[Test code here\]*

*\[Optional: cleaned HTML pages visited before the assertion point
here\]*

## Execution and Functional Evaluation

- Run the three versions of the test suites corresponding to the three
  treatments (remember to first run the complete experiment on
  PetClinic, and only afterwards on the application assigned to you).

- Record the functional outcome of each generated assertion. In
  particular, each generated assertion must first be classified
  according to its execution outcome as ***passed***, when the test
  executes successfully with the generated assertion; ***failed***, when
  the generated assertion is executed but the test fails; or ***run time
  error***, when the generated assertion causes an execution exception.
  For example, in Selenium tests, an assertion should be classified as
  *run time error* if it triggers a NoSuchElementException because the
  generated locator refers to an element that is not present in the
  page. Finally, ***not compiling*** when the test cannot be compiled.

- Compare results across treatments filling in the given excel file
  available on Aulaweb.

## Qualitative Error Analysis

In addition to the functional evaluation, each generated assertion must
be **manually classified** into one of the following categories:

  -------------------------------------------------------------------------
  **Category**      **Meaning**
  ----------------- -------------------------------------------------------
  Correct           The assertion is equivalent to the
                    gold-standard/reference assertion.

  Over-assertive    The assertion checks more than necessary and may fail
                    because of irrelevant or unstable details.

  Under-assertive   The assertion checks less than necessary and may pass
                    even when the page behavior is wrong.

  Wrong assertion   The assertion checks the wrong element or the wrong
                    condition (it does not check the intended testing
                    objective).

  Not executable    The assertion does not compile or cannot be executed.
  -------------------------------------------------------------------------

This qualitative analysis is fundamental to understand why one treatment
works better than another. Passing tests alone are not sufficient: an
assertion may pass because it is too weak, while a failing assertion may
fail because it is too strong or because it checks irrelevant
implementation details.

### Examples: Under-assertive and Over-assertive

To evaluate the generated assertions, it is important to distinguish
between **under-assertive** and **over-assertive** assertions.

An **under-assertive assertion** checks something related to the
expected behavior, but it is **weaker** than the reference assertion. In
other words, it may pass even when the test does not fully verify the
intended behavior.

For example, the reference assertion checks whether the expected last
name appears in a **specific element** of the page:

// Reference assertion\
WebElement firstResult = driver.findElement(\
By.xpath(\"/html\[1\]/body\[1\]/div\[1\]/div\[1\]/table\[1\]/tbody\[1\]/tr\[1\]/td\[1\]/b\[1\]\")\
);\
assertTrue(firstResult.getText().contains(\"Franklin\"));

);

The generated assertion only checks whether the last name appears
**somewhere in the page source**:

// Generated assertion\
assertTrue(\
driver.getPageSource().contains(\"Franklin\"),\
\"Owner with last name \'Franklin\' should be present\"\
);

This is **under-assertive** because the assertion is too weak: the last
name could appear anywhere on the page, not necessarily in the expected
result position.

An **over-assertive assertion** checks the expected behavior, but it
also adds **extra constraints** that were not required by the reference
assertion. This makes the test more restrictive than necessary and may
cause it to fail even when the application behaves correctly.

For example, the reference assertion directly searches for the bold
element containing the expected owner name:

// Reference assertion\
WebElement ownerElement = driver.findElement(\
By.xpath(\"//b\[contains(text(),\'George Franklin\')\]\")\
);\
assertEquals(\"George Franklin\", ownerElement.getText());

The generated assertion first restricts the search to the **first table
with class table**, and only then looks for a bold element:

// Generated assertion\
WebElement ownerNameElement = driver.findElement(\
By.xpath(\"//table\[contains(@class,\'table\')\]\[1\]//b\")\
);\
String actualOwnerName = ownerNameElement.getText();\
\
assertEquals(\
\"George Franklin\",\
actualOwnerName,\
\"Expected owner name to be \'\" + owner + \"\' but was: \" +
actualOwnerName\
);

This is **over-assertive** because it introduces an additional
structural constraint: the owner name must appear inside the first table
with class table. This constraint is not present in the reference
assertion.

In summary:

**Under-assertive** means: the generated assertion checks too little.\
**Over-assertive** means: the generated assertion checks too much.

## Experiment Execution Modes

The experiment can be carried out with different degrees of automation.

Students may use simple scripts that call the selected LLM while keeping
several steps manual, such as, e.g., manually removing the original
assertions that will constitute the gold standard and fill in the Excel
file..

Alternatively, students may implement a more agentic system, especially
those who attended the Advanced Secure Software Engineering course, to
automate almost all phases of the experiment. However, the validation of
the generated comments and the qualitative classification of the
generated assertions as *Correct*, *Over-assertive*, *Under-assertive*,
*Wrong assertion*, or *Not executable* must always be performed
manually.

## Deliverables

- A written report containing briefly:

<!-- -->

- Experimental setup, assigned application, and LLM used.

- The prompts used and the rationale behind them.

- A clear description of the various steps performed, such as how the
  comments related to the assertions were validated, how the web pages
  were produced, how they were cleaned of JavaScript and CSS, and how
  they were provided to the LLM.

- Problems, difficulties, anomalies encountered, and choices made while
  executing the task.

- A summary of the **results of test execution**, including the number
  of assertions generated and pass/fail/not executable/working rates.
  That is, a table similar to this one, accompanied by charts that
  visually show the percentages.

  ![](media/image1.png){width="6.674833770778653in"
  height="1.569284776902887in"}

- A summary of the **qualitative classification of each generated
  assertion** according to the categories above. That is, a table
  similar to this one, accompanied by charts that visually show the
  percentages.

  ![](media/image2.png){width="6.9in" height="1.6916666666666667in"}

<!-- -->

- An Excel file summarizing the obtained results. A template will be
  provided on Aulaweb and must be used for the submission.

## Expected Learning Outcomes

- Understand the role of assertions in automated testing.

- Gain experience with Selenium test development and execution.

- Learn how to design and conduct experiments with LLM-based tools.

- Understand the concept of zero-shot prompting and its implications for
  evaluating LLM capabilities.

- Develop critical skills in analyzing the effectiveness of LLM-based
  code generation.

- Assess how results vary across different applications, prompts, and
  treatments.
