package com.project.springapistudy.core;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class VerifyUtilsTest {

    @ParameterizedTest
    @MethodSource("verifyEmptySourceArguments")
    @DisplayName("값이 비어 이면 예외가 발생한다.")
    void verifyEmptySource(Object source, String message) {
        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> VerifyUtils.verifyEmptySource(source, message))
                .withMessage(message);
    }

    private static Stream<Arguments> verifyEmptySourceArguments() {
        return Stream.of(
                Arguments.of("", "값이 비어있습니다."),
                Arguments.of(null, "값이 비어있어요~")
        );
    }


}