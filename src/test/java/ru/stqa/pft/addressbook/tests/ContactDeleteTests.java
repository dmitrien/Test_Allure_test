package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteTests extends TestBase {

    //создаем контакт, если нет ни одного
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Shakal").withLastname("Shakalovich"));
        }
    }

    /*получаем список всех контактов с данными до удаления, делаем переменную для удаляемого контакта,
    сравниваем количество контактов (без данных) после и до удаления, получаем список всех контактов с данными после удаления
    и сравниваем контакты с данными после удаления с конактами с данными до удаления без удаленного во время теста контакта*/

    @Test
    public void testDeleteContactTest() {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(before.size()-1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(deletedContact)));

    }
}
