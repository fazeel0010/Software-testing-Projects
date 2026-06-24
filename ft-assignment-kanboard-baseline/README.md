# ft-assignment-kanboard-1.2.15

Team repository for the Functional Testing assignment experiment on **LLM-generated JUnit 5 assertions** using **Kanboard 1.2.15**.

## Scope

This repo contains the second case-study setup and experiment artifacts for evaluating assertion generation under three zero-shot treatments:

1. **No comments**
2. **With assertion-intent comments**
3. **With comments + cleaned HTML context**

## Upstream Baseline

Source application/test baseline comes from BEWT:

- https://github.com/bonigarcia/BEWT/tree/main/kanboard/baseline/kanboard-1.2.15

## Goal

Measure and compare assertion quality across treatments using:

- **Functional outcome**: pass / fail / runtime error / not compiling
- **Qualitative label**: Correct / Over-assertive / Under-assertive / Wrong assertion / Not executable

## Branching

- `main`: stable baseline
- `exp/no-comments`
- `exp/with-comments`
- `exp/with-comments-html`

## Requirements

The project requires the Kanboard application to be running.

Use the following command to start the application:

```bash
docker run -d --name kanboard -p 8080:80 -t kanboard/kanboard:v1.2.15
```

Alternatively, use the provided PowerShell script `kanboard.ps1`:

```powershell
# Restart Kanboard only
.\kanboard.ps1

# Restart Kanboard and run the test suite
.\kanboard.ps1 -RunTests
```

You can also run Selenium Grid (optional but recommended) to execute tests with the remote browser setup:

```bash
docker run -d -p 4444:4444 -p 7900:7900 --shm-size="2g" --name=browser selenium/standalone-chrome:127.0-chromedriver-127.0
```

When using the remote browser container, set the test app URL to `http://host.docker.internal:8080`.
