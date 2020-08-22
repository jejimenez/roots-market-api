/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.events;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * A event providing information on response.
 *
 * @param <T>
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseEvent<T> {
    /**
     * Response code.
     */
    private ResponseCode code;
    /**
     * Response message.
     */
    private String message;
    /**
     * Requested page number.
     */
    private Integer number;
    /**
     * Number of elements per page.
     */
    private Integer numberOfElements;
    /**
     * Elements size per page.
     */
    private Integer size;
    /**
     * Total number of elements for the query.
     */
    private Long totalElements;
    /**
     * Number of pages for query.
     */
    private Integer totalPages;
    /**
     * True if the query returns elements.
     */
    private Boolean hasContent;
    /**
     * True if the following page exists.
     */
    private Boolean hasNext;
    /**
     * True if the previous page exists.
     */
    private Boolean hasPrevious;
    /**
     * True if it's the first page.
     */
    private Boolean isFirst;
    /**
     * True if it's the last page.
     */
    private Boolean isLast;
    /**
     * Data
     */
    private T data;

    /**
     * @param message The message to be response.
     * @return The response event with message and OK code.
     */
    public ResponseEvent<T> ok(String message) {
        return ok(message, null);
    }

    /**
     * @param message The message to be response.
     * @param data    The data to be response.
     * @return The response event with data, message and OK code.
     */
    public ResponseEvent<T> ok(String message, T data) {
        setCode(ResponseCode.OK);
        setMessage(message);
        setData(data);
        return this;
    }

    /**
     * @param message The message to be response.
     * @param data    The data to be response.
     * @param page    The page description to be response.
     * @return The response event with data, message and OK code.
     */
    public ResponseEvent<T> ok(String message, T data, Page<T> page) {
        setCode(ResponseCode.OK);
        setMessage(message);
        setData(data);
        setHasContent(page.hasContent());
        setHasPrevious(page.hasPrevious());
        setHasNext(page.hasNext());
        setIsFirst(page.isFirst());
        setIsLast(page.isLast());
        setNumber(page.getNumber());
        setNumberOfElements(page.getNumberOfElements());
        setSize(page.getSize());
        setTotalElements(page.getTotalElements());
        setTotalPages(page.getTotalPages());
        return this;
    }

    /**
     * @param message The message to be response.
     * @return The response event with NO_CONTENT code and message.
     */
    public ResponseEvent<T> noContent(String message) {
        setCode(ResponseCode.NO_CONTENT);
        setMessage(message);
        return this;
    }

    /**
     * @param message The message to be response.
     * @return The response event with BAD_REQUEST code and message.
     */
    public ResponseEvent<T> badRequest(String message) {
        setCode(ResponseCode.BAD_REQUEST);
        setMessage(message);
        return this;
    }

    /**
     * @param message The message to be response.
     * @return The response event with CONFLICT code and message.
     */
    public ResponseEvent<T> conflict(String message) {
        setCode(ResponseCode.CONFLICT);
        setMessage(message);
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
