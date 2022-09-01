package nextstep.jwp.model;

import nextstep.jwp.exception.NotFoundContentTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeTest {

    @Test
    @DisplayName("확장자에 맞는 contentType을 반환한다.")
    void checkReturnExtensionForContentType() {
        // given
        String extension = "html";

        // when
        ContentType contentType = ContentType.getExtension(extension);

        // then
        assertThat(contentType.getType()).isEqualTo("text/html");
    }

    @Test
    @DisplayName("등록되지 않은 확장자를 호출할 경우 예외가 발생한다.")
    void checkNonContentType() {
        assertThatThrownBy(() -> ContentType.getExtension("hwp"))
                .isInstanceOf(NotFoundContentTypeException.class);
    }
}