package com.example.actualtravellerkiviprojectui.dto;

/**
 * Wrapper for {@literal PagedModel} from Spring.
 *
 * @param <T>
 */
public class PagedModel<T> {
    public T content;
    public PageMetadata page;

    public static class PageMetadata {
        public Integer size;
        public Integer number;
        public Integer totalElements;
        public Integer totalPages;
    }
}
