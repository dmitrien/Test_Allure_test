package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    //создаем контакт, если нет ни одного

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Shakal").withLastname("Shakalovich"));
        }
    }


    /*получаем список всех контактов до модификации, делаем переменную для изменяемого контакта,
    создаем прерменную для параметров, изменяем контакт,
    сравниваем количество контактов после и до модификации, получаем список всех контактов с данными после модификации
    и сравниваем списки контактов
*/
    @Test
    public void testModificationContact(){
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("CopchoniyF").withLastname("CopchoniyL");
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
