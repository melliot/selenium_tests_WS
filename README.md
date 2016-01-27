wordstat-regression-tests
==========================
Steps to run tests:
1. Install maven on host machine
-- sudo apt-get install maven (ubuntu)
-- brew install maven (os x)
2. Download Selenium Standalone Server (http://www.seleniumhq.org/download/)
3. Download chromedriver (https://sites.google.com/a/chromium.org/chromedriver/downloads)
4. Run selenium-server-standalone with path to chromedriver
(java -jar selenium-server-standalone-*.jar -Dwebdriver.chrome.driver=/path/to/chromedriver)
5. cd {project_directory}
6. Run tests 
-- mvn test