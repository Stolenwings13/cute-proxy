package net.dongliu.byproxy.data;

import net.dongliu.byproxy.store.Body;
import net.dongliu.byproxy.utils.NetAddress;

import javax.annotation.Nullable;
import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * Http request and response
 *
 * @author Liu Dong
 */
public class Http1Message extends HttpMessage implements Serializable {
    private static final long serialVersionUID = -8007788167253549079L;
    private final Http1RequestHeaders requestHeader;
    private final Body requestBody;
    @Nullable
    private volatile Http1ResponseHeaders responseHeader;
    @Nullable
    private volatile Body responseBody;

    public Http1Message(String scheme, NetAddress address, Http1RequestHeaders requestHeader, Body requestBody) {
        super(requestHeader.getHeader("Host").orElse(address.getHost()), getUrl(scheme, address, requestHeader));
        this.requestHeader = requireNonNull(requestHeader);
        this.requestBody = requireNonNull(requestBody);
    }

    private static String getUrl(String scheme, NetAddress address, Http1RequestHeaders requestHeader) {
        String host = requestHeader.getHeader("Host").orElse(address.getHost());
        StringBuilder sb = new StringBuilder(scheme).append("://").append(host);
        if (!(scheme.equals("https") && address.getPort() == 443 || scheme.equals("http") && address.getPort() == 80)) {
            sb.append(":").append(address.getPort());
        }
        sb.append(requestHeader.getRequestLine().getPath());
        return sb.toString();
    }

    @Override
    public String displayText() {
        return getUrl();
    }

    public Http1RequestHeaders getRequestHeader() {
        return requestHeader;
    }

    public Body getRequestBody() {
        return requestBody;
    }

    @Nullable
    public Http1ResponseHeaders getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(Http1ResponseHeaders responseHeader) {
        this.responseHeader = requireNonNull(responseHeader);
    }

    @Nullable
    public Body getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(@Nullable Body responseBody) {
        this.responseBody = requireNonNull(responseBody);
    }

    @Override
    public String toString() {
        return "Http1Message{url=" + getUrl() + "}";
    }
}