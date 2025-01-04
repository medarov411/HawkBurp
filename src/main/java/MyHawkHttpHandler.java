import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.message.requests.HttpRequest;
import net.jalg.hawkj.HawkContext;
import net.jalg.hawkj.Algorithm;
import net.jalg.hawkj.AuthorizationHeader;
import net.jalg.hawkj.util.Charsets;


public class MyHawkHttpHandler implements HttpHandler {
    private final String id = "your-id";
    private final String secret = "your-secret";

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent httpRequestToBeSent) {
        // Getting necessary values for request
        String method = httpRequestToBeSent.method();
        String path = httpRequestToBeSent.path();
        String bodyreq = httpRequestToBeSent.bodyToString();
        // Creating Hawk Context(hawkj lib)
        HawkContext hawkContext = HawkContext.request(method, path, "your-host", 443)
                .credentials(id, secret, Algorithm.SHA_256)
                .body(bodyreq.getBytes(Charsets.UTF_8), "application/json")
                .build();
        // Creating Authorization header
        AuthorizationHeader authHeader = hawkContext.createAuthorizationHeader();
        HttpRequest request = httpRequestToBeSent.withAddedHeader("Authorization", authHeader.toString());

        return RequestToBeSentAction.continueWith(request); // Return changed request
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived httpResponseReceived) {
        // Response handler
        return null; // Return null, if you don't want to handle response
    }
}