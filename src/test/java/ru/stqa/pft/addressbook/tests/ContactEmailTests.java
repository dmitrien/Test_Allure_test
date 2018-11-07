package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

    //создаем контакт с нужными данными, если нет ни одного контакта
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Shakal").withLastname("Shakalovich")
                    .withEmail("milo@mail.com").withSecondEmail("milo2@mail.com").withThirdEmail("milo3@mail.com"));
        }
    }

    /*в тесте сравниваем адреса эл почты, полученные с главной страницы, с адресами со страницы редактирования контакта*/
    @Test
    public void testContactEmail() {
        app.contact().goToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));

    }

     /*метод для склеивания строк
     создаем коолекцию и получаем данные
     запускаем поток и отфильтровываем пустые строки
     функция collect склеивает строки, разделяя слэшем склеиваемые фрагменты*/
    private String mergeEmails (ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getSecondEmail(), contact.getThirdEmail())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}
