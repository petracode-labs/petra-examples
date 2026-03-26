# Petra Examples

![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)

Welcome to the **petra-examples** repository! This project is a curated collection of programming examples written in **Petra**, a language designed to bring scalable formal methods to the masses. 

This repository is designed as a sandbox for developers to learn, experiment, and formally verify Petra programs using a hybrid local/remote workflow.

**Official Documentation:** [www.petracode.co.uk](https://www.petracode.co.uk)

---

## 🚀 The Petra Workflow

Developing in Petra via this repository follows a unique two-step process:

1.  **Local Syntax Validation:** Use the provided Petra AST library to check your code's syntax on your local machine.
2.  **Remote Formal Verification:** Raise a Pull Request (PR) to trigger the **Petra Verifier Build Server** on GitHub, which performs a full formal verification analysis.

---

## 🛠️ Getting Started

Before you can validate Petra code locally, you must install the Petra AST dependency into your local Maven repository.

### 1. Prerequisites
* **Java:** JDK 8 or higher.
* **Build Tool:** Apache Maven.

### 2. Local Installation
A pre-built library is included in the `/lib` folder. Run the following command from the project root to install it:

```bash
mvn install:install-file \
   -Dfile=lib/petra-ast-1.0-SNAPSHOT-jar-with-dependencies.jar \
   -DgroupId=com.cognitionbox.petra \
   -DartifactId=petra-ast \
   -Dversion=1.0-SNAPSHOT \
   -Dpackaging=jar
```

### 3. Maven Dependency
Once installed, your project will recognize the following dependency, add it in order to perform local petra syntax checking:

```xml
<dependency>
    <groupId>com.cognitionbox.petra</groupId>
    <artifactId>petra-ast</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 4. Petra Syntax Checker
Run the syntax checker by executing the ```PetraVerification``` JUnit tests by right-clicking on the tests and running within the IDE, or by using the following maven command
```mvn clean test```.

### 5. Hazelcast Support (Local Testing Only)
Some examples in this repository (e.g. trading) demonstrate persistence and distributed state using Hazelcast.

**Add Hazelcast Dependency Locally:**
Do not push the Hazelcast dependency or any Hazelcast-related test classes to the repository. The verification server will reject changes to ```pom.xml``` and addition of traditional junit tests, and therefore will fail builds for security reasons.
You may add and use Hazelcast locally only for testing.

**Important:**
Add the following dependency to your local pom.xml (do not commit this change):
```
<dependency>
  <groupId>com.hazelcast</groupId>
  <artifactId>hazelcast</artifactId>
  <version>5.6.0</version>
</dependency>
```

**Hazelcast Unit Tests (Local Only):**
You can create the following test class locally (e.g. TradingHazelcastTest.java) to test Hazelcast-backed persistence. Do not push this file.
```
package com.cognitionbox.petra.examples.trading;

import com.cognitionbox.petra.examples.trading.strategy.data.DataSource;
import com.cognitionbox.petra.examples.trading.strategy.order.Order;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TradingHazelcastTest {
    private HazelcastInstance hazelcastInstance;

    @Before
    public void setUp() {
        // Starts a local Hazelcast member using the hazelcast.xml in your classpath
        hazelcastInstance = Hazelcast.newHazelcastInstance();
    }

    @After
    public void tearDown() {
        // Shutdown the instance after the test to free up ports and memory
        hazelcastInstance.shutdown();
    }

    @Test
    public void testOrderPersistence() {
        // 1. Create the order and perform an action
        Order order = new Order();
       
        // Assuming your updateMarketData or enterBuy calls persist()
        order.enterBuy();
       
        // 2. Verify local state
        assertTrue("Quote should be updated", order.bought());
       
        // 3. Create a second Order object (simulating a restart)
        // This new object should pull the data from the IMap in its constructor
        Order recoveredOrder = new Order();
       
        assertEquals("Recovered order should have same state",
                     order.bought(), recoveredOrder.bought());
    }

    @Test
    public void testTrading() {
        DataSource.loadMarketData("src/test/resources/21/EURUSD_Candlestick_1_Hour_BID_02.06.2003-16.06.2018.csv");
        TrendMeanRevStrategy strategy = new TrendMeanRevStrategy();
        while(DataSource.hasNextPrice()){
            strategy.run();
        }
    }
}
```

---

## 📝 Rules of Engagement

To keep the build server happy and the verification process secure, please observe these strict constraints:

* **Java Files Only:** You may only commit and push changes to `.java` files. Modifying `pom.xml`, project configurations, or the `lib/` directory will result in an automatic build failure.
* **Testing:** Traditional unit tests can be written and used locally, but they should not be committed to the main branch, as the build server is only for executing Petra's formal verification process.
* **Rate Limits:** Users are currently limited to approximately **3 builds per hour**. Use your local syntax checker to catch early mistakes before pushing to GitHub!
* **Verification:** Formal verification is only triggered upon raising a **Pull Request**.

---

## 📚 Resources & Community

* **Learn Petra:** Head over to [www.petracode.co.uk](https://www.petracode.co.uk) for the full language specification and tutorials.
* **Contribute:** Found a bug or have a great example? See [CONTRIBUTING.md](CONTRIBUTING.md).

Enjoy democratized, scalable formal methods programming with Petra!

---

## 📄 License

This project is licensed under the **Apache License 2.0**. See the [LICENSE](LICENSE) file for details.
