package com.modhajai

import com.modhajai.configs.Sender
import com.modhajai.dimensions.Claim
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import static org.assertj.core.api.Assertions.assertThat;
import groovy.json.*

/**
 * @author Jai Modha
 * @since 2017-02-17
 * Test that basically call the sender to post claim messages to kafka stream.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class KafkaproducerApplicationTests {

	@Autowired
	private Sender sender;

	@Test
	public void testReceiver() throws Exception {

		for(int i = 0; i < 6 ; i++  ) {
			Claim claim = new Claim()
			claim.with {
				id = i as Long
				claimDocument = """
					ST*824*021390002*005010X186A1~
					BGN*11*FFA.ABCDEF.123456*20020709*0932**123456789**U~
					N1*41*DEF INSURANCE*46*222222222~
					PER*IC*TOM EVANS*TE*8005551212*EX*1439~
					N1*40*JONESCO*46*B5678~
					OTI*TR*TN*NA***20020709*0902*2*0001*124*005010~
					TED*024**VEH*36*2~
					RED*NA**94**IBP*E054~
					SE*9*021390002~
				""".stripIndent()
				patientId = i as Long

			}
			sender.sendMessage("test", new JsonBuilder(claim).toPrettyString());
		}

		assertThat(true);
	}
}
