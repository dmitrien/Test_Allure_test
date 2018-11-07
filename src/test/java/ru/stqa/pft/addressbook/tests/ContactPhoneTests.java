package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    //создаем контакт с нужными данными, если нет ни одного контакта
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Shakal").withLastname("Shakalovich")
                    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333"));
        }
    }

    //в тесте сравниваем все номера, полученные с главной страницы, с номерами со страницы редактирования контакта
    @Test
    public void testContactPhones(){
        app.contact().goToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }


     /*метод для склеивания строк
     создаем коолекцию и получаем данные
     запускаем поток и отфильтровываем пустые строки
     в map вызываем метод cleaned
     функция collect склеивает строки, разделяя слэшем склеиваемые фрагменты*/
    private String mergePhones(ContactData contact) {
       return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    //метод для удаления тех символов, которые не отображаются на главной странице (-, (, ))
    public static String cleaned (String phone) {
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }

}
