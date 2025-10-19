package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 빈_문자열_입력() {
        assertSimpleTest(() -> {
            run(" ");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 공백만_입력() {
        assertSimpleTest(() -> {
            run("   ");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 단일_숫자() {
        assertSimpleTest(() -> {
            run("5");
            assertThat(output()).contains("결과 : 5");
        });
    }

    @Test
    void 기본_구분자_쉼표() {
        assertSimpleTest(() -> {
            run("1,2,3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 기본_구분자_콜론() {
        assertSimpleTest(() -> {
            run("1:2:3:4");
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    void 기본_구분자_혼용() {
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 기본_구분자_복합_혼용() {
        assertSimpleTest(() -> {
            run("1,2,3:4:5");
            assertThat(output()).contains("결과 : 15");
        });
    }

    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 커스텀_구분자_세미콜론() {
        assertSimpleTest(() -> {
            run("//;\\n1;2;3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 커스텀_구분자와_기본_구분자_혼용() {
        assertSimpleTest(() -> {
            run("//;\\n1;2,3:4");
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    void 커스텀_구분자_다른_문자() {
        assertSimpleTest(() -> {
            run("//#\\n4#5#6");
            assertThat(output()).contains("결과 : 15");
        });
    }

    @Test
    void 커스텀_구분자_특수문자_별표() {
        assertSimpleTest(() -> {
            run("//*\\n2*3*5");
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    void 두자리_이상_숫자() {
        assertSimpleTest(() -> {
            run("10,20:30");
            assertThat(output()).contains("결과 : 60");
        });
    }

    @Test
    void 큰_숫자_여러자리() {
        assertSimpleTest(() -> {
            run("100,200,300");
            assertThat(output()).contains("결과 : 600");
        });
    }

    @Test
    void 매우_큰_수_BigInteger_합산() {
        assertSimpleTest(() -> {
            // Long.MAX_VALUE를 초과하는 값
            run("9223372036854775808,1");
            assertThat(output()).contains("결과 : 9223372036854775809");
        });
    }

    @Test
    void BigInteger_여러_큰_수_합산() {
        assertSimpleTest(() -> {
            run("//;\\n100000000000000000000;200000000000000000000");
            assertThat(output()).contains("결과 : 300000000000000000000");
        });
    }

    @Test
    void 공백_포함_입력_trim_처리() {
        assertSimpleTest(() -> {
            run(" 1 , 2 : 3 ");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 커스텀_구분자_닷_메타문자() {
        assertSimpleTest(() -> {
            run("//.\\n1.2.3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 커스텀_구분자_공백_허용() {
        assertSimpleTest(() -> {
            run("// \\n1 2 3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 기본_구분자와_공백_혼용_trim_확인() {
        assertSimpleTest(() -> {
            run(" 1 , 2 : 3 ");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 매우_큰_수_여러개_BigInteger_확인() {
        assertSimpleTest(() -> {
            run("//;\\n100000000000000000000;200000000000000000000;300000000000000000000");
            assertThat(output()).contains("결과 : 600000000000000000000");
        });
    }

    @Test
    void 커스텀_구분자_숫자_금지() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//1\\n1,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_헤더_개행_누락() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;1;2")) // \n 없음
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_헤더_CR_개행_형식_오류() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;\\r1;2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_시작에_연속() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;\\n;1;2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_끝에_연속() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;\\n1;2;"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 기본_구분자만_입력() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(",,:"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 토큰이_공백만인_경우_예외() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1, ,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_음수_단일값() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("-1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_음수_쉼표_구분자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,-2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_음수_콜론_구분자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1:2:-3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_음수_커스텀_구분자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;\\n1;-2;3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_영_입력_단일() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("0"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_영_포함_쉼표() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,0,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_영_포함_콜론() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1:0:3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_영_커스텀_구분자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;\\n1;0;3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_비숫자_문자_알파벳() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,a,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_비숫자_특수문자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,@,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_비숫자_한글() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,가,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_연속_쉼표() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_연속_콜론() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1::3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_연속_구분자_혼합() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,:3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_시작_구분자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(",1,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_끝_구분자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2,"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_커스텀_구분자_연속() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;\\n1;;3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_구분자만_입력() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(",,:"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
