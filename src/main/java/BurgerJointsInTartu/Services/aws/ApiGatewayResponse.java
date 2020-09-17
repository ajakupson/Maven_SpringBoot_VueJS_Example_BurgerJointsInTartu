package BurgerJointsInTartu.Services.aws;

import java.io.IOException;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.IOUtils;

public class ApiGatewayResponse {
    private final HttpResponse httpResponse;
    private final String body;

    public ApiGatewayResponse(HttpResponse httpResponse) throws IOException {
        this.httpResponse = httpResponse;
        if (httpResponse.getContent() != null) {
            this.body = IOUtils.toString(httpResponse.getContent());
        } else {
            this.body = null;
        }
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public String getBody() {
        return body;
    }
}
