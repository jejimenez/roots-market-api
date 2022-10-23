/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.DeliveryCycleManager;
import co.logike.roots.market.core.api.manager.EmailNotificationManager;
import co.logike.roots.market.core.api.objects.DeliveryCycleDTO;
import co.logike.roots.market.core.api.objects.EmailNotificationDTO;
import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.api.objects.OrderProductMailedDTO;
import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;
import co.logike.roots.market.core.app.components.EmailNotificationSender;
import co.logike.roots.market.core.app.entity.DeliveryCycle;
import co.logike.roots.market.core.app.entity.Person;
import co.logike.roots.market.core.app.parser.DeliveryCycleParser;
import co.logike.roots.market.core.app.repository.DeliveryCycleRepository;
import co.logike.roots.market.core.app.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Handler for {@link DeliveryCycle}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */


@Service
@Slf4j
public class EmailNotificationHandler implements EmailNotificationManager{

	//private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class)
	//@Autowired
	//private JavaMailSender sender;
	
    private final PersonRepository personRepository;
    private final EmailNotificationSender emailSender;
	
    @Autowired
    public EmailNotificationHandler(PersonRepository personRepository, EmailNotificationSender emailSender) {
        this.personRepository = personRepository;
        this.emailSender = emailSender;
    }


    @Override
    public ResponseEvent<Boolean> sendNotificationTest() {
        log.info("method: sendNotificationTest({})");
        String[] emails = {"draizsocial@gmail.com"} ;
        
        try {
            EmailNotificationDTO email = new EmailNotificationDTO();
        	email.setTo(emails);
            email.setContent("SMTP Setup is working!");
            email.setSubject("TEST SUBJECT");
            
            emailSender.setEmail(email);
            emailSender.Send();
            log.info("method: sendNotificationTest({})", "Sent succesfully");
        
            return new ResponseEvent<Boolean>().ok("Success", true);
        } catch (Exception ex) {
            log.error("method: sendNotificationTest({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<Boolean>().conflict(ex.getMessage());
		}
    }
    
    @Override
    public ResponseEvent<Boolean> sendNotificationNewPurchase(ResponseEvent<PurchaseOrderDTO> responseEvent, ResponseEvent<List<OrderProductMailedDTO>> responseOrderProducts) {
        log.info("method: sendNotificationNewPurchase({})", responseEvent);
        PurchaseOrderDTO purchase = responseEvent.getData();
        String[] emails ;
        List<OrderProductMailedDTO> ordproducts = responseOrderProducts.getData();
        try {
        	// Get admin email
	        List<Person> adminList = personRepository.findOrgAdminByPurchaseOrd(Long.parseLong(purchase.getId()));
        	// Get order email
	        Person orderPerson = personRepository.findByPurchaseOrd(Long.parseLong(purchase.getId()));
	        //
	        emails = new String[adminList.size()+1];
	        int i = 0;
	        // Email admin 
	        for(Person person:adminList) {
	            emails[i] = person.getEmail();
	            log.info("method: sendNotificationNewPurchase({})",  person.getEmail());
	            i++;
	        }
	        // Email order owner
	        emails[i] = orderPerson.getEmail();
            log.info("method: sendNotificationNewPurchase({})",  orderPerson.getEmail());
            EmailNotificationDTO email = new EmailNotificationDTO();
	        //email.setTo(new String[]{person.getEmail(), orderPerson.getEmail()});
        	email.setTo(emails);
            email.setContent("");
            email.setSubject("Nueva Orden");
            //EmailNotificationSender sender = new EmailNotificationSender();
            emailSender.setEmail(email);
            emailSender.BuildNewPurchaseMessage(purchase, ordproducts, orderPerson);
            emailSender.Send();
            log.info("method: sendNotificationNewPurchase({})", "Sent succesfully");
        
            return new ResponseEvent<Boolean>().ok("Success", true);
        } catch (Exception ex) {
            log.error("method: sendNotificationNewPurchase({}, {})", ex.getMessage(), ex);
            //return new ResponseEvent<Boolean>().conflict(ex.getMessage());
            // If there is an email error anyway the answer is success because the order was created
            return new ResponseEvent<Boolean>().ok("Success", true);
		}
    }
    



}
