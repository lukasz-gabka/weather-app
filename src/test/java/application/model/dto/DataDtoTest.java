package application.model.dto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DataDtoTest {

    DataDto dataDto = new DataDto();

    @Test
    void shouldReturnDateWhenDatetimeHasCorrectFormat() {
        //given
        dataDto.setDatetime("2020-01-19");

        //when
        String date = dataDto.getDate();

        //then
        assertThat(date, equalTo("2020-01-19"));
    }

    @Test
    void shouldReturnDateInProperFormatWhenDatetimeHasWrongFormat() {
        //given
        dataDto.setDatetime("2020-01-19:17");

        //when
        String date = dataDto.getDate();

        //then
        assertThat(date, equalTo("2020-01-19"));
    }

    @Test
    void shouldReturnWindSpdConvertedFromMetersPerSecondToKilometersPerHourRounded() {
        //given
        dataDto.setWind_spd(1);

        //when
        int convertedNumber = dataDto.getWindSpd();

        // when
        assertThat(convertedNumber, equalTo(4));
    }
}