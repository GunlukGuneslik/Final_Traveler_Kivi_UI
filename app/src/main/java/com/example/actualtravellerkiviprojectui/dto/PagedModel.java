package com.example.actualtravellerkiviprojectui.dto;

import java.util.List;

/**
 * Wrapper for {@literal PagedModel} from Spring.
 *
 * @param <T>
 */
public class PagedModel<T> {
    public List<T> content;
    public PageMetadata page;

    public static class PageMetadata {
        public Integer size;
        public Integer number;
        public Integer totalElements;
        public Integer totalPages;
    }
}
