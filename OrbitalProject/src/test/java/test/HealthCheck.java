package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseClass;

import java.util.concurrent.TimeUnit;

public class HealthCheck extends BaseClass {

    // ── TC01: Valid login redirects to dashboard ───────────────
    @Test(priority = 1, description = "Valid credentials should land on dashboard")
    public void TC01_ValidLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("commander", "orbit2026");

        // Allow redirect animation to complete
        TimeUnit.SECONDS.sleep(3);

        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(
            currentUrl.contains("dashboard.html"),
            "Expected redirect to dashboard.html but got: " + currentUrl
        );
        System.out.println("TC01 PASSED — Redirected to: " + currentUrl);
    }

    // ── TC02: Invalid password shows ACCESS DENIED ────────────
    @Test(priority = 2, description = "Wrong password should show ACCESS DENIED message")
    public void TC02_InvalidPassword() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("commander", "wrongpass");

        TimeUnit.SECONDS.sleep(2);

        String message = loginPage.getMessageText();
        Assert.assertTrue(
            message.toUpperCase().contains("ACCESS DENIED"),
            "Expected ACCESS DENIED message but got: " + message
        );
        System.out.println("TC02 PASSED — Message: " + message);
    }

    // ── TC03: Empty fields show validation message ────────────
    @Test(priority = 3, description = "Empty fields should trigger a required field message")
    public void TC03_EmptyFields() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickLogin();

        TimeUnit.SECONDS.sleep(2);

        String message = loginPage.getMessageText();
        Assert.assertFalse(
            message.isEmpty(),
            "Expected a validation message but got nothing"
        );
        System.out.println("TC03 PASSED — Validation message: " + message);
    }

    // ── TC04: Login page title is correct ────────────────────
    @Test(priority = 4, description = "Page title should contain ORBITAL COMMAND")
    public void TC04_PageTitle() {
        LoginPage loginPage = new LoginPage(driver);

        String title = loginPage.getPageTitle();
        Assert.assertTrue(
            title.toUpperCase().contains("ORBITAL COMMAND"),
            "Expected ORBITAL COMMAND in title but got: " + title
        );
        System.out.println("TC04 PASSED — Title: " + title);
    }

    // ── TC05: Login button is visible on page load ────────────
    @Test(priority = 5, description = "Login button should be visible when page loads")
    public void TC05_LoginButtonVisible() {
        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(
            loginPage.isLoginButtonDisplayed(),
            "Login button should be visible on page load"
        );
        System.out.println("TC05 PASSED — Login button is visible");
    }

    // ── TC06: Second valid user (admin) can log in ────────────
    @Test(priority = 6, description = "Admin credentials should also work")
    public void TC06_AdminLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("admin", "launch123");

        TimeUnit.SECONDS.sleep(3);

        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(
            currentUrl.contains("dashboard.html"),
            "Admin login failed — URL: " + currentUrl
        );
        System.out.println("TC06 PASSED — Admin redirected to: " + currentUrl);
    }
}
