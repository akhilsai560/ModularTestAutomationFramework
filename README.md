# NBA Product Test Automation Framework - README

## 📌 Project Overview

This project is a modular Selenium-Cucumber-TestNG-based automation framework built to automate three NBA product websites:

* `core-product-tests`
* `derived-product1-tests`
* `derived-product2-tests`

All modules share a common, reusable library: `automation-framework`.

---

## 🧱 Module Structure

```bash
Assessment/  
├── automation-framework/       # Common reusable components
│   ├── config/                 # ConfigManager for reading configs
│   ├── driver/                 # DriverManager using ThreadLocal
│   ├── report/                 # ExtentReports wrapper
│   ├── utils/                  # CSV export, JSON parsing etc.
│   └── base/                   # BaseClass with driver & reporting access
│
├── core-product-tests/        # Tests for Core Product site
│   ├── src/
│   │   └── test/
│   │       ├── java/
│   │       │   ├── coreproduct/
│   │       │   │   ├── runner/
│   │       │   │   │   └── CoreProductRunnerTest.java
│   │       │   │   ├── pages/
│   │       │   │   │   ├── HomePageWarriors.java
│   │       │   │   │   ├── ShopMensPage.java
│   │       │   │   │   └── NewsAndFeaturesPage.java
│   │       │   │   └── stepDefinitions/
│   │       │   │       └── CoreProductStepDefinition.java
│   │       └── resources/
│   │           └── coreProductFeatures/
│   │               └── CP.feature
│
├── derived-product1-tests/    # Tests for Derived Product 1 (Sixers)
│   ├── src/
│   │   └── test/
│   │       ├── java/
│   │       │   ├── derivedProductOne/
│   │       │   │   ├── runner/
│   │       │   │   │   └── DerivedProductOneRunnerTest.java
│   │       │   │   ├── pages/
│   │       │   │   │   └── SixersPage.java
│   │       │   │   └── stepDefinitions/
│   │       │   │       └── DerivedProductOneStepDefinition.java
│   │       └── resources/
│   │           └── derivedProductOneFeatures/
│   │               └── DP1.feature
│
├── derived-product2-tests/    # Tests for Derived Product 2 (Bulls)
│   ├── src/
│   │   └── test/
│   │       ├── java/
│   │       │   ├── derivedProductTwo/
│   │       │   │   ├── runner/
│   │       │   │   │   └── DerivedProductTwoRunnerTest.java
│   │       │   │   ├── pages/
│   │       │   │   │   └── BullsPage.java
│   │       │   │   └── stepDefinitions/
│   │       │   │       └── DerivedProductTwoStepDefinition.java
│   │       └── resources/
│   │           └── derivedProductTwoFeatures/
│   │               └── DP2.feature
│
├── testng.xml                 # Controls tag-based module execution
└── README.md                  # Project documentation
```

---


## 🔧 Prerequisites

* Java 11 or higher
* Maven 3.6+
* IDE (e.g., Eclipse/IntelliJ)
* Google Chrome, Firefox, or Edge installed

---

## 🚀 How to Run Tests

### ▶️ From Command Line (Maven) (Preferred)

```bash
mvn clean install -DskipTests=false
mvn test                            # Run all tests
mvn test -Dcucumber.filter.tags="@dp1"   # Run tag-based tests
```

### ▶️ Via TestNG XML 

Update and use the `testng.xml` in the root folder on need basis:

```xml

<suite name="NBA Test Suite" verbose="1" parallel="false">
  
  <test name="Core Product Tests">
    <parameter name="cucumber.filter.tags" value="@cp" />
    <classes>
      <class name="coreproduct.runner.CoreProductRunnerTest" />
    </classes>
  </test>

  <test name="Derived Product 1 Tests">
    <parameter name="cucumber.filter.tags" value="@dp1" />
    <classes>
      <class name="derivedProductOne.runner.DerivedProductOneRunnerTest" />
    </classes>
  </test>

  <test name="Derived Product 2 Tests">
    <parameter name="cucumber.filter.tags" value="@dp2" />
    <classes>
      <class name="derivedProductTwo.runner.DerivedProductTwoRunnerTest" />
    </classes>
  </test>

</suite>

```

Run using:

```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

---

## 🏷️ Tag Usage

* `@cp` - Core Product Feature level
* `@cp1`, `@cp2` - Core Product Test Cases
* `@dp1`, `@dp2` - Derived Product 1 & 2 respectively

---

## 📂 Reports & Screenshots

* **Reports:**

  * `core-product-tests/reports/CPAutomationReport.html`
  * `derived-product1-tests/reports/DP1AutomationReport.html`
  * `derived-product2-tests/reports/DP2AutomationReport.html`

* **Screenshots:**
  Automatically saved per step inside `reports/screenshots/`

* Custom logging and screenshots embedded using **ExtentReports**

---

## 🧪 Test Data Management

* JSON files for input test data (e.g., slide titles)
* CSV file generation for extracted links/data

---

## 🧰 Utilities

* `DriverManager` with ThreadLocal for parallel-safe driver handling
* `DriverUtils` for wait, scrolling, zooming
* `FileUtils` for file exports & cleanup
* `JsonUtils` for loading JSON test data

---

## ⚠️ Troubleshooting

* `NoSuchSessionException`: Ensure driver is accessed via `getDriver()`
* `StaleElementReferenceException`: Always fetch fresh elements post-navigation
* `SLF4J: No provider`: Ensure proper logger setup (`log4j2.xml`)
* If tests pass without execution: make sure `dryRun = false`

---

## 🧹 Maintenance Tips

* Keep `PageFactory.initElements()` in StepDefs only (not inside Page constructors)
* Use `@BeforeAll` in Hooks to clean `screenshots/`
* Tag-based execution ensures modular test runs
* Keep reports and screenshots under module-specific folders

---

## 👨‍💻 Author

**Akhil Sai Boddu**

---

