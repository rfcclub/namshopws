package com.test.demo.endpoint;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.service.OrderService;
import com.test.demo.service.ProductService;
import com.test.demo.service.ShopService;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;

/**
 * Base controller which is created to host other GraphQL services.
 * 
 * @author Nam
 *
 */
@RestController
public class GraphQLController {

	private static final String BASE_PACKAGE = "com.test.demo";
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ShopService shopService;

	private GraphQL graphQL;

	@PostConstruct
	public void init() {
		// create schema by scanning web service classes and domain objects.
		GraphQLSchema schema = new GraphQLSchemaGenerator()
				.withBasePackages(BASE_PACKAGE)
				.withOperationsFromSingleton(shopService)
				.withOperationsFromSingleton(orderService)
				.withOperationsFromSingleton(productService)
				.generate();
		graphQL = GraphQL.newGraphQL(schema).build();
	}
	
	@PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> graphql(@RequestBody Map<String, String> request, HttpServletRequest raw) {
		// build specification
        ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
                .query(request.get("query"))
                .operationName(request.get("operationName"))
                .context(raw)
                .build());
        return executionResult.toSpecification();
    }
}
