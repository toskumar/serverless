package test.com.traits.serverless.db;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traits.serverless.db.Profile;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoadProfiles extends BaseTable {

	private static final Logger LOG = LogManager.getLogger(LoadProfiles.class);
	

	@Before
	public void setUp() {
		super.init();
	}

	@After
	public void tearDown() {
		super.close();
	}
	

	@Test
	public void test01LoadProfiles() {
		this.loadProfile("male_profiles.json");
	}

	public void loadProfile(String fileName) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			LOG.info("Loading profiles from file " + this.getClass().getClassLoader().getResource(fileName).getFile());
			JsonNode node = mapper.readTree(new File(this.getClass().getClassLoader().getResource(fileName).getFile()));

			if (node.isArray()) {
				for (int i = 0; i < node.size(); i++) {
					JsonNode child = node.get(i);
					LOG.info("Loading profile " + child.get("id"));

					Profile profile = mapper.readValue(child.toString(), Profile.class);

					dbMapper.save(profile);

					assertNotNull(profile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
