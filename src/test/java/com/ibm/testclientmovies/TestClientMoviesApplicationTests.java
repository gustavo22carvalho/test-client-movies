package com.ibm.testclientmovies;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.testclientmovies.hackerrank.ApiMoviesClient;
import com.ibm.testclientmovies.hackerrank.Movie;
import com.ibm.testclientmovies.service.MovieService;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class TestClientMoviesApplicationTests {
	
	private static ObjectMapper MAPPER = new ObjectMapper();

	
	@Configuration
	@EnableFeignClients(clients = {ApiMoviesClient.class})
	@ImportAutoConfiguration({
			HttpMessageConvertersAutoConfiguration.class,
			RibbonAutoConfiguration.class,
			FeignRibbonClientAutoConfiguration.class,
			FeignAutoConfiguration.class})
	@Import(MovieService.class)
	static class ContextConfiguration {
		@Autowired
		Environment env;

		@Bean
		ServletWebServerFactory servletWebServerFactory(){
			return new TomcatServletWebServerFactory();
		}

		@Bean
		public ServerList<Server> ribbonServerList() {
			return new StaticServerList<>(new Server("localhost", Integer.valueOf(this.env.getProperty("wiremock.server.port"))));
		}
	}
	

	@Autowired
	private ApiMoviesClient apiMoviesClient;
	
	@Autowired
	private MovieService movieService;
	
	@Test(expected = IllegalArgumentException.class)
	public void quantoBuscarVazia_lancarExcessao() {
		this.movieService.findAllTitlesSorted("");
	}

	@Test
	public void getMoviesTest() throws Exception {
		String expected = MAPPER.writeValueAsString(getMovies());
		stubFor(get( urlEqualTo("/search"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(expected)
				)
		);
	
		String actual = MAPPER.writeValueAsString(movieService.findAllTitles("sa"));
		assertEquals(expected, actual);
	}
	
	private List<Movie> getMovies(){
		return null;
	}
	
}