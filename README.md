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

> **Note for Fork Owners:** GitHub automatically disables workflow files on forks for security. You will see a "Workflows aren’t being run" banner, which is expected behavior and can be safely ignored.
>
> **Security Disclaimer:** The Petra Verifier strictly forbids modifications to workflow files. Furthermore, the verification engine operates in an isolated environment that does not execute your Java code or allow custom JUnit tests, ensuring the process cannot be used as a vector for data exfiltration or security breaches.

---

## 🛠️ Getting Started

Before you can validate Petra code locally, you must install the Petra AST dependency on your local Maven repository and remove the Petra Verifier dependancy (these changes are just for your local environment, do not commit them, otherwise the server-side verification process will fail).

### 1. Prerequisites
* **Java:** JDK 8 or higher.
* **Build Tool:** Apache Maven.

### 2. Install Petra AST Maven Dependency
A pre-built library is included in the `/lib` folder. Run the following command from the project root to install it:

```bash
mvn install:install-file \
   -Dfile=lib/petra-ast-1.0-SNAPSHOT-jar-with-dependencies.jar \
   -DgroupId=com.cognitionbox.petra \
   -DartifactId=petra-ast \
   -Dversion=1.0-SNAPSHOT \
   -Dpackaging=jar
```

### 3. Add Petra AST Maven Dependency
Once installed, your project will recognize the following dependency, add it in order to perform local petra syntax checking:

```xml
<dependency>
    <groupId>com.cognitionbox.petra</groupId>
    <artifactId>petra-ast</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 4. Remove Petra Verifier Maven Dependency
In order to successfully compile the project you will need to remove the following ```petra-verifier``` dependancy, either by deleting or commenting out:

```xml
<dependency>
   <groupId>com.cognitionbox.petra</groupId>
   <artifactId>petra-verifier</artifactId>
   <version>0.0.3-SNAPSHOT</version>
 </dependency>
```

### 5. Petra Syntax Checker
Run the syntax checker by executing the ```PetraVerification``` JUnit tests by right-clicking on the tests and running within the IDE, or by using the following maven command
```mvn clean test```.

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
