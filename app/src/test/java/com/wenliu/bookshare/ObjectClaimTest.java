package com.wenliu.bookshare;

import com.wenliu.bookshare.object.User;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ObjectClaimTest {

    User userTest = new User();
    List mockedList = mock(List.class);

    @Test
    public void ClaimTest() throws Exception{

        assertEquals("", userTest.getEmail());
        assertEquals("", userTest.getCreateTime());
        assertEquals("", userTest.getId());
        assertEquals("", userTest.getName());
    }


}
