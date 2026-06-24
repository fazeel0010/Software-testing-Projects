param(
    [switch]$RunTests,
    [string]$Test = "tests.TestSuite"
)

# Check if the Selenium container is running
$browserRunning = (docker inspect -f "{{.State.Running}}" browser 2> $null)
if ($browserRunning -ne "true")
{
    docker rm browser 2> $null
    docker run -d -p 4444:4444 -p 7900:7900 --shm-size="2g" --name=browser selenium/standalone-chrome:127.0-chromedriver-127.0
}

# Start a new Kanboard container
docker stop kanboard 2> $null
docker rm kanboard 2> $null
docker run -d --name kanboard -p 8080:80 -t kanboard/kanboard:v1.2.15

if ($RunTests)
{
    do
    {
        Start-Sleep -Seconds 2
    } until ((Invoke-WebRequest -Uri "http://localhost:8080" -UseBasicParsing -ErrorAction SilentlyContinue).StatusCode -eq 200)

    .\mvnw clean test "-Dtest=$Test"
}
