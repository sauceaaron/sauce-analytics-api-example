import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.saucelabs.saucerest.SauceREST;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class GetAnalyticsTestData
{
	//curl -u USERNAME:ACCESS_KEY "https://saucelabs.com/rest/v1/analytics/trends/tests?interval=1h&start=2017-03-01T12:00:00Z&end=2017-03-02T12:00:00Z&os=Linux&pretty"
	String TEST_TRENDS_ENDPOINT = "https://saucelabs.com/rest/v1/analytics/trends/tests";


	String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	@Test
	public void test() throws UnirestException
	{
		OffsetDateTime endDate = LocalDateTime.now().atOffset(ZoneOffset.of("Z"));
		OffsetDateTime startDate = endDate.minusDays(30);

		System.out.println(startDate);
		System.out.println(endDate);

		Map<String, Object> params = new HashMap<>();
		params.put("interval", "1h");
		params.put("start", startDate.toString());
		params.put("end", endDate.toString());
		params.put("pretty", true);
		params.put("os", "Windows 10");

		HttpResponse<String> response = Unirest.get(TEST_TRENDS_ENDPOINT)
				.basicAuth(SAUCE_USERNAME, SAUCE_ACCESS_KEY)
				.queryString(params)
				.asString();

		if (response.getStatus() != 200)
		{
			System.out.println(response.getStatus());
			System.out.println(response.getStatusText());
			System.out.println(response.getBody());

			throw new RuntimeException("request failed");
		}

		System.out.println(response.getBody());

	}
}
