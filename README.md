# geekHelper
Grab geektime's content tool,Geektime's homepage is https://time.geekbang.org/.
The tool can automatically capture purchased articles using accounts already registered on geektime.
You can extend its parsing and persistence behavior.

# Some of the techniques involved
[Selenium](https://www.seleniumhq.org/),Selenium automates browsers.

# Log
[Logback](https://logback.qos.ch/),Logback is intended as a successor to the popular log4j project, picking up where log4j leaves off.

# Test Framework
[Junit](https://junit.org/junit4/),JUnit is a simple framework to write repeatable tests.

# Extension
Current version, You can extends `cn.baopz.core.AbstractFetcher` abstract class to extend the persistence capabilities,By default,
This class implements a very crude persistence capability as `cn.baopz.core.DefaultArticleFetcher`.

# Details
The tool is completed in Four processes.
1. A thread completes the login function.
2. A threaded through all columns
3. Each column  is assigned a thread in the thread pool and USES WebDriver to launch a browser for each thread with the same cookies.
4. Call the implemented abstract method `cn.baopz.core.AbstractFetcher#persistence`.
