package org.jboss.tools.examples.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.tools.examples.controller.MemberRegistration;
import org.jboss.tools.examples.model.Member;
import org.jboss.tools.examples.util.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Ross Rowe
 */
@RunWith(Arquillian.class)
public class MemberRegistrationWebDriverTest {

    @Deployment(testable = false)
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Member.class, MemberRegistration.class, Resources.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"))
                .addAsWebResource(new File("src/main/webapp/templates/default.xhtml"), "templates/default.xhtml")
                .addAsWebResource(new File("src/main/webapp/index.html"))
                .addAsWebResource(new File("src/main/webapp/index.xhtml"))
                .addAsWebResource(new File("src/main/webapp/resources/css/screen.css"), "resources/css/screen.css")
                .addAsWebResource(new File("src/main/webapp/resources/gfx/banner.png"), "resources/gfx/banner.png")
                .addAsWebResource(new File("src/main/webapp/resources/gfx/logo.png"), "resources/gfx/logo.png")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Inject
    MemberRegistration memberRegistration;

    @Inject
    Logger log;

    @ArquillianResource
    URL contextPath;

    @Drone
    WebDriver webDriver;

    @Test
    public void verifyKitchenSink() throws Exception {
        //verify that the kitchen sink page is okay
        webDriver.get(contextPath.toString());
        assertEquals("Welcome to the Java EE 6 Clickstart", webDriver.findElement(By.cssSelector("h1")).getText());
    }

    @Test
    public void addRegistration() throws Exception {
        //adds a registration with all the details
        webDriver.get(contextPath.toString());
        webDriver.findElement(By.id("reg:name")).clear();
        webDriver.findElement(By.id("reg:name")).sendKeys("Valid Name");
        webDriver.findElement(By.id("reg:email")).clear();
        webDriver.findElement(By.id("reg:email")).sendKeys("testing@test.org");
        webDriver.findElement(By.id("reg:phoneNumber")).clear();
        webDriver.findElement(By.id("reg:phoneNumber")).sendKeys("1234567890");
        webDriver.findElement(By.id("reg:register")).click();

        //verify that a new row has been added
        //assertEquals("1234567890", webDriver.findElement(By.xpath("//div[@id='content']/table/tbody/tr[2]/td[4]")).getText());

    }

    @Test
    public void noName() throws Exception {
        //add a registration with no name
        webDriver.get(contextPath.toString());
        webDriver.findElement(By.id("reg:register")).click();
        //verify that an error is displayed
        assertEquals("size must be between 1 and 25", webDriver.findElement(By.cssSelector("span.invalid")).getText());
    }

}
