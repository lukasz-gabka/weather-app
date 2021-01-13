package application.model.dto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DataDtoTest {

    @Test
    void shouldReturnWindSpdConvertedFromMetersPerSecondToKilometersPerHourRounded() {
        //given
        DataDto dataDto = new DataDto();
        dataDto.setWind_spd(1);

        //when
        int convertedNumber = dataDto.getWindSpd();

        // when
        assertThat(convertedNumber, equalTo(4));
    }
}