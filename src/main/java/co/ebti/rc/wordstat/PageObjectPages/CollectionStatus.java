package co.ebti.rc.wordstat.PageObjectPages;

import co.ebti.rc.wordstat.Page;
import org.openqa.selenium.WebDriver;

public class CollectionStatus extends Page{
    public CollectionStatus(WebDriver driver) {
        super(driver);
    }

    private String message1 = "Очередь сохранения новых ключевых слов";
    private String message2 = "Очередь обновления весов";
    private String message3 = "Очередь сохранения подсказок";
    private String message4 = "Очередь отправки сообщений в сборщик";
    private String message5 = "Отправка сборщиком запросов на Yandex WS";
    private String message6 = "Отправка сборщиком ответов в WordStat";

    @Override
    public void open() {
        driver.get(HOSTNAME+"/status");
    }
}
