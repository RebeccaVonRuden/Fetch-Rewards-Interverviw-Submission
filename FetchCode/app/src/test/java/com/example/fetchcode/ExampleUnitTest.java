package com.example.fetchcode;

import static org.junit.Assert.assertEquals;

import com.example.fetchcode.models.Item;
import com.example.fetchcode.utills.ApiService;

import org.junit.Test;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testApiService(){
        //Arrange
        int expectedPos0ListId = 2;
        int expectedPos0id = 755;
        String expectedPos0name = "";
        int expectedPos78ListId = 4;
        int expectedPos78id = 429;
        String expectedPos78name = null;
        int expectedPos124ListId = 3;
        int expectedPos124id = 416;
        String expectedPos124name = "Item 416";
        //Act
        ApiService apiService = new ApiService();
        apiService.fetchDataFromApi(new ApiService.ApiCallback() {
            @Override
            public void onSuccess(List<Item> myItems) {
                //Assert
                // Position 0
                assertEquals(expectedPos0ListId,myItems.get(0).getListId());
                assertEquals(expectedPos0id,myItems.get(0).getId());
                assertEquals(expectedPos0name,myItems.get(0).getName());
                // Position 78
                assertEquals(expectedPos78ListId,myItems.get(78).getListId());
                assertEquals(expectedPos78id,myItems.get(78).getId());
                assertEquals(expectedPos78name,myItems.get(78).getName());
                // Position 124
                assertEquals(expectedPos124ListId,myItems.get(124).getListId());
                assertEquals(expectedPos124id,myItems.get(124).getId());
                assertEquals(expectedPos124name,myItems.get(124).getName());
            }

            @Override
            public void onFailure(String errorMessage) {
                throw new RuntimeException(errorMessage);
            }
        });

    }

    @Test
    public void testPrint(){
        //Arrange
        Item item = new Item();
        item.setName("Item 235");
        item.setId(235);
        item.setListId(2);
        String desiredString= "List Id: 2" + ", Name: Item 235" + ", Id: 235";
        //Act
        String sut = MainActivity.prepareString(item.getListId(),  item.getName(),  item.getId());
        //Assert
        assertEquals(desiredString,sut);
    }
}