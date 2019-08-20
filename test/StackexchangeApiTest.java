import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.api.stackexchange.StackexchangeApi;

public class StackexchangeApiTest {
	@Test
	public void testGetPageNum() {
		StackexchangeApi api = new StackexchangeApi();
		assertEquals(1, api.GetPageNum(null));
		assertEquals(1, api.GetPageNum(""));
		assertEquals(1, api.GetPageNum("1"));
		assertEquals(2, api.GetPageNum("2"));
	}
}
