package org.apache.coyote.model.request;

import java.io.BufferedReader;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(final RequestLine requestLine, final RequestHeader requestHeader, final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(final BufferedReader reader) {
        try {
            final RequestLine requestLine = RequestLine.of(reader.readLine());
            final RequestHeader requestHeader = createHeader(reader);
            final RequestBody requestBody = createBody(reader, requestHeader.getContentLength());
            return new HttpRequest(requestLine, requestHeader, requestBody);
        } catch (Exception e) {
            throw new IllegalArgumentException("Request Error");
        }
    }

    private static RequestHeader createHeader(final BufferedReader reader) {
        return RequestHeader.of(
                reader.lines()
                        .takeWhile(readLine -> !"".equals(readLine))
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    private static RequestBody createBody(final BufferedReader reader, final int contentLength) {
        try {
            final char[] buffer = new char[contentLength];
            reader.read(buffer, 0, contentLength);
            return RequestBody.of(new String(buffer));
        } catch (Exception e) {
            throw new IllegalArgumentException("Request Body Error");
        }
    }

    public boolean checkGetMethod() {
        return requestLine.checkMethod(Method.GET);
    }

    public boolean checkPostMethod() {
        return requestLine.checkMethod(Method.POST);
    }

    public Map<String, String> getParams() {
        return requestLine.getQueryParams();
    }

    public boolean existCookie(final String cookie) {
        return requestHeader.existKey(cookie);
    }

    public String getCookieKey() {
        return requestHeader.getCookieKey();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Method getHttpMethod() {
        return requestLine.getMethod();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
