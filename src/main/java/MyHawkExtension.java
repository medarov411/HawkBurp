import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class MyHawkExtension implements BurpExtension {
    @Override
    public void initialize(MontoyaApi api) {
        api.extension().setName("Hawk Authentication Extension");
        api.logging().logToOutput("Powered by @undefinxd");
        // Creating HTTP handler
        MyHawkHttpHandler handler = new MyHawkHttpHandler();
        api.http().registerHttpHandler(handler);
    }
}