import io.sphere.sdk.client.SphereAsyncHttpClientFactory;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientFactory;
import io.sphere.sdk.products.queries.ProductProjectionQuery;

//mvn exec:java
public class Main {
    public static void main(String[] args) {
        final SphereClientFactory factory = SphereClientFactory.of(SphereAsyncHttpClientFactory::create);
        final SphereClient client = factory.createClient(
                "hello-87", //replace with your project key
                "fOrRJ6q7fP9gOikBlrHt6Pzi", //replace with your client id
                "tW3hRMeMldf-36U8DIS0v9K6Mh3Si5P2"); //replace with your client secret

        client.execute(ProductProjectionQuery.ofCurrent()).toCompletableFuture().join().head().ifPresent(System.err::println);
        client.close();
    }
}
