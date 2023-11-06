import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class App {
    public static void main(String[] args) throws Exception {
        // Especifica la ubicación del controlador WebDriver para Chrome
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\dgallarz\\Documents\\selenium\\demoselenium\\src\\drivers\\chromedriver.exe");

        // Inicializa el controlador de Chrome
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); 
        // Buscamos google
        driver.get("https://www.google.com");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[3]/div[3]/span/div/div/div/div[3]/div[1]/button[2]"))); //Esperamos que el botón de aceptar esté visible se usa Xpath, debido a que Google utiliza tecnicas antiscraping
        WebElement aceptarBoton = driver
                .findElement(By.xpath("/html/body/div[2]/div[3]/div[3]/span/div/div/div/div[3]/div[1]/button[2]")); //clicamos en el botón de aceptar
        aceptarBoton.click();
        // Buscamos el item en google
        wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
        WebElement searchBox = driver.findElement(By.name("q"));
        String busqueda = "Fire Stick";
        searchBox.sendKeys(busqueda);
        searchBox.submit();
                                                    
        try{
            // Busca los div con id que contengan "amazon.es"
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(@data-dtld, 'amazon.es')]")));
            List<WebElement> divsAmazon = driver.findElements(By.xpath(".//div[contains(@data-dtld, 'amazon.es')]")); //buscamos los elementos de origen de Amazon.
            // Si se encuentra al menos un div con id que contiene "amazon.es"
            // Busca el enlace <a> dentro del primer div encontrado
            WebElement primerDivAmazon = divsAmazon.get(0);
            WebElement primerEnlaceAmazon = primerDivAmazon.findElement(By.tagName("a"));
            // Buscamos el enlace
            driver.get(primerEnlaceAmazon.getAttribute("href")); //Buscamos ese prodcuto
            Thread.sleep(2000);
        }catch(Exception e){
            System.out.println("No se encontraron divs con id que contenga 'amazon.es'.");
        }

        // Clicamos el botón de cookies
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("sp-cc-accept")));
            WebElement boton = driver.findElement(By.id("sp-cc-accept"));
            boton.click();
        } catch (Exception e) {
            System.out.println("El botón de cookies no aparece");
        }
        
        //Escribimos el precio del producto. Esperamos a que el precio sea visible y lo recuperamos
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("a-price-whole")));
        String whole = driver.findElement(By.className("a-price-whole")).getText();
        String fraction = driver.findElement(By.className("a-price-fraction")).getText();
        String symbol = driver.findElement(By.className("a-price-symbol")).getText();
        System.out.println(whole+"."+fraction+symbol);
        //Buscamos otro producto en la búsqueda
        String busqueda2 = "Compresor electrico";
        wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(busqueda2, Keys.ENTER);
        //Clicamos el selector
        wait.until(ExpectedConditions.elementToBeClickable(By.id("a-autoid-0")));
        driver.findElement(By.id("a-autoid-0")).click();
        //Clicamos la opción de ordenar de mayor a menor
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div/ul/li[3]")));
        driver.findElement(By.xpath("/html/body/div[4]/div/div/ul/li[3]")).click();
        Thread.sleep(2000);//Esperamos que los nuevos productos se rendericen
        //Recuperamos todos los divs de producto
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-component-id]")));
        List<WebElement> productos = driver.findElements(By.cssSelector("div[data-component-id]"));
        System.out.println("prods"+productos.size());
        //Por cada div, recuperamos su texto y su precio
        for (WebElement webElement : productos) {
            
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.a-text-normal")));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.a-price-whole")));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.a-price-fraction")));
                WebElement textElements = webElement.findElement(By.cssSelector("span.a-text-normal"));
                WebElement prices = webElement.findElement(By.cssSelector("span.a-price-whole"));
                WebElement fractions = webElement.findElement(By.cssSelector("span.a-price-fraction"));
                String text = textElements.getText();
                String price = prices.getText();
                String fracString = fractions.getText();
                System.out.println("Producto: " + text);
                System.out.println("Price: " + price+"."+fracString+"€");
            } catch (Exception e) {
                System.out.println("Error en la búsqueda");
            }
                
        }
   
        // Cerrar el navegador
        driver.quit();
    }
}
