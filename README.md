Amazon Add to Cart Automation

This Java Selenium automation project verifies the Amazon add-to-cart functionality. It validates that the price of the product in the product results page and the cart page are identical. The script performs the following steps:

1. Launches the Amazon website.
2. Searches for a specific product.
3. Retrieves the product price from the search results.
4. Adds the product to the cart.
5. Navigates to the cart and verifies that the product price matches the expected price

Setup Instructions
Follow these steps to set up and run the automation:
1. Clone the Repository:
You can use the repository URL to clone the project in IntelliJ IDEA or Eclipse IDE.

2. Update the maven repository:
Ensure that all required dependencies are updated via Maven.

3. Run the project:
Execute the pom.xml file to run the test.

4. Verify in execution report:
Check the report under:
test-output/cucumber-report.html

5. Executing on Different Browsers:
Change the browser driver in the configuration (e.g., set the browser to Chrome or Firefox).

Tech Stack
- Java
- Selenium WebDriver
- TestNG / JUnit (if applicable)
- Maven
