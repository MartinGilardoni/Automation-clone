package projects.levis.testcases;

import projects.levis.pages.LevisHomePage;

import base.base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevisHomePageTest extends base {
    WebDriver driver;
    LevisHomePage levisHomePage;

    public static Logger log = LogManager.getLogger(base.class.getName());

    @BeforeTest
    public void initialize() throws IOException {
        driver = initializeDriver();
        log.info("Driver is initialized");
        driver.get(prop.getProperty("levisar"));
        log.info("Navigated to Home page");
    }

    @Test(priority = 1)
    public void footerTest() {
        levisHomePage = new LevisHomePage(driver);
        String[] expectedFooterLinks = {
                "https://www.levi.com.ar/ayuda/#faq", "https://www.levi.com.ar/ayuda/#faq", "https://www.levi.com.ar/ayuda/", "https://www.levi.com.ar/ayuda/#envios",
                "https://www.levi.com.ar/ayuda/#pagos", "https://www.levi.com.ar/ayuda/#cambios", "https://www.levi.com.ar/promociones/", "https://www.levi.com.ar/centro-de-ayuda/",
                "https://www.levi.com.ar/contacts/", "tel:(011) 5368 - 2373", "tel:(011) 5368 - 2373", "mailto:ventaonline@levi.com.ar", "https://www.levi.com.ar/contacts",
                "https://www.levi.com.ar/sobre-levis/", "https://www.levi.com.ar/puntos-de-venta/",
                "https://www.levi.com.ar/trabajo/", "https://www.levi.com.ar/contacts2/", "https://www.levi.com.ar/terminos/",
                "https://www.levi.com.ar/politica/", "https://twitter.com/levis_ar",
                "https://www.youtube.com/playlist?list=PLv2owRmD8gH84YXDaqCtNa0EnH2dC1Knr", "https://www.facebook.com/Levis.Argentina/",
                "https://www.instagram.com/levis_ar/", "https://www.levi.com.ar/live-in-levis", "http://qr.afip.gob.ar/?qr=uhts28dHsraazzzYTHn4zw,,",
        };
        List<WebElement> actualFooterLinks = levisHomePage.getDataFromComponent(driver, levisHomePage.lnk_footer);

        for (int i = 0; i < expectedFooterLinks.length; i++) {
            System.out.println(actualFooterLinks.get(i).getAttribute("href"));
            Assert.assertEquals(actualFooterLinks.get(i).getAttribute("href"), expectedFooterLinks[i], "Footer links doesn't match");
        }
    }

    @AfterClass
    public void close() {
        driver.quit();

    }
}
