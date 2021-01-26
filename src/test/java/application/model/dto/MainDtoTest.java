package application.model.dto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class MainDtoTest {

    MainDto mainDto = new MainDto();

    @Test
    void shouldThrowAnExceptionIfDataIsNull() {
        //given
        mainDto.setData(null);

        //when
        assertThrows(NullPointerException.class, mainDto::isExist);
    }

    @Test
    void shouldReturnCityNameAndCountryCodeSeparatedWithACommaString() {
        //given
        mainDto.setCity_name("Katowice");
        mainDto.setCountry_code("PL");

        //when
        String fullName = mainDto.getFullCityName();

        //then
        assertThat(fullName, equalTo("Katowice, PL"));
    }
}