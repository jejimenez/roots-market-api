/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.parser;

import co.logike.roots.market.core.api.events.ResponseCode;
import co.logike.roots.market.core.api.events.ResponseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility for the response.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
public final class ResponseEntityUtility {

    public static <T> ResponseEntity<ResponseEvent<T>> buildHttpResponse(ResponseEvent<T> responseEvent) {
        log.debug("method: buildHttpResponse({})", responseEvent);
        final HttpStatus httpStatus = parseResponseCode(responseEvent.getCode());
        return new ResponseEntity<>(responseEvent, httpStatus);
    }

    public static HttpStatus parseResponseCode(final ResponseCode code) {
        log.debug("method: parseResponseCode({})", code);
        switch (code) {
            case OK:
                return HttpStatus.OK;

            case NO_CONTENT:
                return HttpStatus.NO_CONTENT;

            case BAD_REQUEST:
                return HttpStatus.BAD_REQUEST;

            case CONFLICT:
                return HttpStatus.CONFLICT;

            case ERROR:
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
