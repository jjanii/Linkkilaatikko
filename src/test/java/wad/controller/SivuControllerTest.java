package wad.controller;

import java.util.UUID;
import org.assertj.core.api.AbstractBooleanAssert;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.Linkki;
import wad.domain.Kommentti;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SivuControllerTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void linkinLisaysToimii() throws Throwable {
        lisaaLinkki();
    }

    @Test
    public void kommentinLisaysToimii() throws Throwable {
        lisaaKommentti();
    }

    @Test
    public void sivunOtsikkoOikein() throws Throwable {
        String sivunnimi = UUID.randomUUID().toString().substring(0, 10);
        goTo("http://localhost:" + port + "/" + sivunnimi);
        AbstractBooleanAssert<?> assertThat = assertThat(webDriver.getTitle().equals(sivunnimi));
    }

    @Test
    public void lisattyLinkkiJaKommenttiNakyySivulla() throws Throwable {
        Linkki l = lisaaLinkki();
        Kommentti k = lisaaKommentti();

        goTo("http://localhost:" + port + "/testisivu");

        assertThat(pageSource()).contains(k.getKommentti());
        assertThat(pageSource()).contains(l.getKuvaus());
    }

    @Test
    public void viallinenLinkkiEiLisaaLinkkia() throws Throwable {
        goTo("http://localhost:" + port + "/testisivu");

        Linkki linkki = new Linkki();
        linkki.setKuvaus(UUID.randomUUID().toString().substring(0, 6));
        linkki.setNimi("Content: " + UUID.randomUUID().toString().substring(0, 6));
        linkki.setUrl("tamaEIoletoimivaURL");

        fill("input[name=kuvaus]").with(linkki.getKuvaus());
        fill("input[name=nimi1]").with(linkki.getNimi());
        fill("input[name=url]").with(linkki.getUrl());
        click("input[value='Lisää']");

        assertThat(pageSource()).doesNotContain(linkki.getKuvaus());
        assertThat(pageSource()).doesNotContain(linkki.getNimi());

    }

    @Test
    public void adminNakeeLogitKirjautumisenJalkeen() throws Throwable {
        goTo("http://localhost:" + port + "/admin/logs");
        assertThat(pageSource()).doesNotContain("Logit");
        syotaTiedot("admin", "admin");
        assertThat(pageSource()).contains("Logit");
    }

    @Test
    public void logeissaOikeaTieto() throws Throwable {
        Linkki l = lisaaLinkki();
        Kommentti k = lisaaKommentti();
        Linkki l2 = lisaaLinkki();
        Kommentti k2 = lisaaKommentti();
        goTo("http://localhost:" + port + "/admin/logs");
        syotaTiedot("admin", "admin");

        assertThat(pageSource()).contains(l.getUrl());
        assertThat(pageSource()).contains(k.getKommentti());
        assertThat(pageSource()).contains(l2.getUrl());
        assertThat(pageSource()).contains(k2.getKommentti());

    }

    @Test
    public void sivunPiilotusJaNayttaminenToimii() throws Throwable {

        goTo("http://localhost:" + port + "/testisivu");
        goTo("http://localhost:" + port + "/");
        assertThat(pageSource()).contains("testisivu");

        goTo("http://localhost:" + port + "/testisivu");
        click("input[value='Piilota sivu etusivun listalta']");
        goTo("http://localhost:" + port + "/");
        assertThat(pageSource()).doesNotContain("testisivu");

        goTo("http://localhost:" + port + "/testisivu");
        click("input[value='Näytä sivu etusivun listalla (oletus)']");
        goTo("http://localhost:" + port + "/");
        assertThat(pageSource()).contains("testisivu");

    }

    public Linkki lisaaLinkki() {
        goTo("http://localhost:" + port + "/testisivu");

        Linkki linkki = new Linkki();
        linkki.setKuvaus(UUID.randomUUID().toString().substring(0, 6));
        linkki.setNimi("Content: " + UUID.randomUUID().toString().substring(0, 6));
        linkki.setUrl("http://" + UUID.randomUUID().toString().substring(0, 10));

        fill("input[name=kuvaus]").with(linkki.getKuvaus());
        fill("input[name=nimi1]").with(linkki.getNimi());
        fill("input[name=url]").with(linkki.getUrl());
        click("input[value='Lisää']");

        assertThat(pageSource()).contains(linkki.getKuvaus());
        assertThat(pageSource()).contains(linkki.getNimi());

        return linkki;
    }

    public Kommentti lisaaKommentti() {

        goTo("http://localhost:" + port + "/testisivu");

        Kommentti k = new Kommentti();
        k.setKommentti(UUID.randomUUID().toString().substring(0, 30));

        fill("input[name=kommentti]").with(k.getKommentti());
        fill("input[name=nimimerkki]").with("nick");
        click("input[value='Kommentoi!']");

        assertThat(pageSource()).contains(k.getKommentti());

        return k;
    }

    private void syotaTiedot(String username, String password) {
        fill(find(By.name("username"))).with(username);
        fill(find(By.name("password"))).with(password);
        find(By.name("password")).submit();
    }

}
