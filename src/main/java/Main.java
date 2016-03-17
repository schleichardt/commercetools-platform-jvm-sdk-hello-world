import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientFactory;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;

import static java.util.Locale.ENGLISH;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.joining;

//mvn clean compile exec:java
public final class Main {
    public static void main(String[] args) {
        final SphereClientFactory factory = SphereClientFactory.of();
        final SphereClient pureAsyncClient = factory.createClient(
                "hello-87", //replace with your project key
                "fOrRJ6q7fP9gOikBlrHt6Pzi", //replace with your client id
                "tW3hRMeMldf-36U8DIS0v9K6Mh3Si5P2"); //replace with your client secret
        try (final BlockingSphereClient client = BlockingSphereClient.of(pureAsyncClient, 122, SECONDS)) {
            final ProductProjectionQuery searchRequest =
                    ProductProjectionQuery.ofCurrent()
                            .withPredicates(m -> m.name().locale(ENGLISH).isPresent())
                            .withExpansionPaths(m -> m.categories())
                            .withLimit(10)
                            .withSort(m -> m.createdAt().sort().desc());
            final PagedQueryResult<ProductProjection> queryResult =
                    client.executeBlocking(searchRequest);
            for (final ProductProjection product : queryResult.getResults()) {
                final String name = product.getName().get(ENGLISH);
                final String categoryNamesString = product.getCategories()
                        .stream()
                        .filter(ref -> ref.getObj() != null)
                        .map(categoryReference -> {
                            final Category category = categoryReference.getObj();
                            return category.getName().find(ENGLISH).orElse("name unknown");
                        })
                        .collect(joining(", "));
                System.out.println("found product " + name + " in categories " + categoryNamesString);
            }
        }
    }
}
