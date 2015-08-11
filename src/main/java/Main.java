import io.sphere.sdk.client.SphereAsyncHttpClientFactory;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientFactory;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;

import java.util.concurrent.CompletionStage;

//mvn compile exec:java
public final class Main {
    public static void main(String[] args) {
        final SphereClientFactory factory = SphereClientFactory.of(SphereAsyncHttpClientFactory::create);
        final SphereClient client = factory.createClient(
                "hello-87", //replace with your project key
                "fOrRJ6q7fP9gOikBlrHt6Pzi", //replace with your client id
                "tW3hRMeMldf-36U8DIS0v9K6Mh3Si5P2"); //replace with your client secret

        final CompletionStage<PagedQueryResult<ProductProjection>> resultCompletionStage =
                client.execute(ProductProjectionQuery.ofCurrent());
        final PagedQueryResult<ProductProjection> queryResult = resultCompletionStage.toCompletableFuture().join();
        queryResult.getResults().forEach(System.err::println);
        client.close();
    }
}
