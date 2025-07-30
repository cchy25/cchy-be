package hackerthon.cchy.cchy25.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookmarkResponse {

    private boolean current;

    private LocalDateTime when;
}
