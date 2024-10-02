package com.nttdata.steps;
import org.junit.Assert;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductSteps {

    private WebDriver driver;

    // Constructor
    public ProductSteps(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String password) {
        driver.findElement(By.xpath("//*[@id='_desktop_user_info']/div/a/span")).click();
        driver.findElement(By.xpath("//*[@id='field-email']")).sendKeys(user);
        driver.findElement(By.xpath("//*[@id='field-password']")).sendKeys(password);
        driver.findElement(By.id("submit-login")).click();
    }

    public void navigateToCategory(String categoria, String subcategoria) {
        driver.findElement(By.xpath("//*[@id='category-3']/a")).click(); // Ir a "Clothes"
        driver.findElement(By.xpath("//*[@id='left-column']/div[1]/ul/li[2]/ul/li[1]/a")).click(); // Ir a "Men"
    }

    public void addProductToCart(int cantidad) {
        // Clic en el primer producto
        driver.findElement(By.xpath("//*[@id='js-product-list']/div[1]/div")).click();
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[1]/div/span[3]/button[1]/i")).click();



        // Clic en el botón para agregar al carrito
        driver.findElement(By.xpath("//*[@id='add-to-cart-or-refresh']/div[2]/div/div[2]/button")).click();
    }


    public boolean isProductAddedConfirmationVisible() {
        try {
            // Espera a que el popup sea visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Esperar a que el contenedor del popup esté visible
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("blockcart-modal")));

            // Verifica si la clase "modal-content" está visible dentro del popup
            return popup.findElement(By.cssSelector(".modal-content")).isDisplayed();

        } catch (NoSuchElementException | TimeoutException e) {
            // Si el elemento no se encuentra o no es visible, retorna false
            return false;
        }
    }


    public boolean isTotalAmountCalculatedCorrectly() {
        // Implementar lógica para verificar el monto total
        // Por ejemplo, verificar que el precio en el popup coincide con el esperado
        return true; // Cambia esto a la lógica real
    }

    public void proceedToCheckout() {
        driver.findElement(By.xpath("//*[@id='blockcart-modal']/div/div/div[2]/div/div[2]/div/div/a")).click();
    }

    public String getCartTitle() {
        return driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div/div[1]")).getText();
    }

    public boolean isPriceCalculationCorrect() {
        // Seleccionar el precio actual utilizando el selector del DOM
        WebElement currentPriceElement = driver.findElement(By.cssSelector(".current-price .price"));
        String currentPriceText = currentPriceElement.getText().replace("S/ ", "").replace(",", ".").replace("PEN", "").trim();
        double currentPrice = Double.parseDouble(currentPriceText);

        // Multiplicar el precio por 2
        double calculatedPrice = currentPrice * 2;

        // Seleccionar el precio esperado en el carrito utilizando el selector del DOM
        WebElement expectedPriceElement = driver.findElement(By.cssSelector(".product-price strong"));
        String expectedPriceText = expectedPriceElement.getText().replace("S/ ", "").replace(",", ".").replace("PEN", "").trim();
        double expectedPrice = Double.parseDouble(expectedPriceText);

        // Comparar precios y retornar el resultado
        return calculatedPrice == expectedPrice;
    }
}
