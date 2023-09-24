package com.project.springapistudy.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BigDecimalTest 학습 테스트")
public class BigDecimalStudyTest {

    @Test
    @DisplayName("스트링 비교는 소수점 아래로 값이 틀리면 생략 되지 않고 값이 틀리다.")
    void test1() {
        assertThat(new BigDecimal("1.01"))
                .isNotEqualTo(new BigDecimal("1.010"));
    }

    @Test
    @DisplayName("valueOf의 double 타입은 소숫점 뒷자리가 생략되어 String 생성과 값이 다르다.")
    void test2() {
        assertSoftly((it) -> {
            it.assertThat(BigDecimal.valueOf(1.010)).isNotEqualTo(new BigDecimal("1.010"));
            it.assertThat(BigDecimal.valueOf(1.01)).isEqualTo(new BigDecimal("1.01"));
        });
    }

    @Test
    @DisplayName("비교할 값이 더크면 1, 작으면 -1, 같으면 0 이다")
    void test3() {
        assertSoftly((it) -> {
            it.assertThat(BigDecimal.ONE.compareTo(BigDecimal.ZERO)).isEqualTo(1);
            it.assertThat(BigDecimal.ONE.compareTo(BigDecimal.TEN)).isEqualTo(-1);
            it.assertThat(BigDecimal.ONE.compareTo(BigDecimal.ONE)).isEqualTo(0);
        });
    }

}
