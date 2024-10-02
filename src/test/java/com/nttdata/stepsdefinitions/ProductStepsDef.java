package com.nttdata.stepsdefinitions;

import com.nttdata.steps.ProductSteps;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.nttdata.core.DriverManager.getDriver;
import static com.nttdata.core.DriverManager.screenShot;
import java.time.Duration;

public class ProductStepsDef {

    private WebDriver driver;

    @Dado("estoy en la página de la tienda")
    public void estoy_en_la_página_de_la_tienda() {
        driver = getDriver();
        driver.get("https://qalab.bensg.com/store");
        System.out.println("Página de la tienda cargada.");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        screenShot();
    }

    @Dado("me logueo con mi usuario {string} y clave {string}")
    public void me_logueo_con_mi_usuario_y_clave(String user, String password) {
        ProductSteps productSteps = new ProductSteps(driver);
        productSteps.login(user, password);
        screenShot();
    }

    @Cuando("navego a la categoria {string} y subcategoria {string}")
    public void navego_a_la_categoria_y_subcategoria(String categoria, String subcategoria) {
        ProductSteps productSteps = new ProductSteps(driver);
        productSteps.navigateToCategory(categoria, subcategoria);
        screenShot();
    }

    @Cuando("agrego {int} unidades del primer producto al carrito")
    public void agrego_unidades_del_primer_producto_al_carrito(int cantidad) {
        ProductSteps productSteps = new ProductSteps(driver);
        productSteps.addProductToCart(cantidad);
        screenShot();
    }

    @Entonces("valido en el popup la confirmación del producto agregado")
    public void valido_en_el_popup_la_confirmación_del_producto_agregado() {
        ProductSteps productSteps = new ProductSteps(driver);

        // Verifica si el popup de confirmación es visible
        Assertions.assertTrue(productSteps.isProductAddedConfirmationVisible(), "El popup de confirmación no está visible.");

        // Si el popup es visible, valida el mensaje
        WebElement messageElement = driver.findElement(By.xpath("//*[@id='blockcart-modal']/div/div/div[2]/div/div[2]/div/p[1]"));
        String expectedMessage = "Hay 2 artículos en su carrito.";
        String actualMessage = messageElement.getText();

        Assertions.assertEquals(expectedMessage, actualMessage, "El mensaje en el popup no es el esperado.");

        // Captura de pantalla
        screenShot();
    }


    @Entonces("valido en el popup que el monto total sea calculado correctamente")
    public void valido_en_el_popup_que_el_monto_total_sea_calculado_correctamente() {
        ProductSteps productSteps = new ProductSteps(driver);
        Assertions.assertTrue(productSteps.isTotalAmountCalculatedCorrectly(), "El monto total no está calculado correctamente.");
        screenShot();
    }

    @Cuando("finalizo la compra")
    public void finalizo_la_compra() {
        ProductSteps productSteps = new ProductSteps(driver);
        productSteps.proceedToCheckout();
        screenShot();
    }

    @Entonces("valido el titulo de la pagina del carrito")
    public void valido_el_titulo_de_la_pagina_del_carrito() {
        ProductSteps productSteps = new ProductSteps(driver);
        String title = productSteps.getCartTitle();
        Assertions.assertEquals("CARRITO", title, "El título del carrito no es el esperado.");
        screenShot();
    }

    @Entonces("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvo_a_validar_el_calculo_de_precios_en_el_carrito() {
        ProductSteps productSteps = new ProductSteps(driver);
        Assertions.assertTrue(productSteps.isPriceCalculationCorrect(), "El cálculo de precios en el carrito no es correcto.");
        screenShot();
    }
}
