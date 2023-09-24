package com.project.springapistudy.menu.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("가격을 테스트 한다.")
class PriceTest {

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-1000"})
    @DisplayName("가격은 0원 미만 일수가 없다.")
    void priceNotLessThenZero(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Price.valueOf(new BigDecimal(value)));
    }

    @Test
    @DisplayName("가격이 예외 없이 생성된다.")
    void createPrice() {
        assertSoftly((it) -> {
            assertThatNoException().isThrownBy(() -> Price.valueOf(new BigDecimal("1000")));
            assertThatNoException().isThrownBy(() -> Price.valueOf(1000L));
            assertThatNoException().isThrownBy(() -> Price.valueOf(1000.10));
        });
    }

    @Test
    @DisplayName("가격이 생성된다.")
    void createPrice2() {
        final Price soruce = Price.valueOf(1000L);

        assertSoftly((it) -> {
            it.assertThat(soruce.isEqualThen(Price.valueOf(1000.0))).isTrue();
            it.assertThat(soruce.isEqualThen(Price.valueOf(1000))).isTrue();
            it.assertThat(soruce.isEqualThen(Price.valueOf(BigDecimal.valueOf(1000)))).isTrue();
        });
    }

    @ParameterizedTest
    @MethodSource("가격_작음의_대한_값들")
    @DisplayName("가격이 더 작음을 확인한다.")
    void isLessThen(Price source, Price target, boolean result) {
        assertThat(source.isLessThen(target)).isEqualTo(result);
    }

    private static Stream<Arguments> 가격_작음의_대한_값들() {
        return Stream.of(
                Arguments.of(Price.valueOf(0), Price.valueOf(100L), true),
                Arguments.of(Price.valueOf(0), Price.valueOf(0.1), true),
                Arguments.of(Price.valueOf(0), Price.valueOf(1.1), true),
                Arguments.of(Price.valueOf(0), Price.valueOf(BigDecimal.valueOf(0.1)), true),
                Arguments.of(Price.valueOf(0), Price.valueOf(BigDecimal.valueOf(0)), false)
        );
    }

    @ParameterizedTest
    @MethodSource("가격_같음의_대한_값들")
    @DisplayName("가격이 같음을 확인한다.")
    void isEqual(Price source, Price target) {
        assertSoftly((it) -> {
            it.assertThat(source.isEqualThen(target)).isTrue();
            it.assertThat(source.equals(target)).isTrue();
        });
    }

    private static Stream<Arguments> 가격_같음의_대한_값들() {
        return Stream.of(
                Arguments.of(Price.valueOf(1), Price.valueOf(1L)),
                Arguments.of(Price.valueOf(1), Price.valueOf(1)),
                Arguments.of(Price.valueOf(1), Price.valueOf(1.0))
        );
    }

    @ParameterizedTest
    @MethodSource("가격_큼의_대한_값들")
    @DisplayName("가격이 큼을 확인한다.")
    void isEqual(Price source, Price target, boolean result) {
        assertSoftly((it) -> {
            it.assertThat(source.isBigThen(target)).isEqualTo(result);
        });
    }

    private static Stream<Arguments> 가격_큼의_대한_값들() {
        return Stream.of(
                Arguments.of(Price.valueOf(100L), Price.valueOf(0), true),
                Arguments.of(Price.valueOf(0.1), Price.valueOf(0), true),
                Arguments.of(Price.valueOf(1.1), Price.valueOf(0), true),
                Arguments.of(Price.valueOf(0.1), Price.valueOf(BigDecimal.valueOf(0)), true),
                Arguments.of(Price.valueOf(0), Price.valueOf(BigDecimal.valueOf(0)), false)
        );
    }


}
