package co.logike.roots.market.core.app.components;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import co.logike.roots.market.core.api.objects.EmailNotificationDTO;
import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.api.objects.OrderProductMailedDTO;
import co.logike.roots.market.core.api.objects.PersonDTO;
import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;
import co.logike.roots.market.core.app.entity.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@Setter
@Service
public class EmailNotificationSender {
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	public SimpleMailMessage template;
	
	private EmailNotificationDTO email;
	
	public EmailNotificationSender(){
	}
	
	public boolean Send(){
        log.debug("method: Send()");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");		
		try {
			helper.setTo(email.getTo());
			helper.setText(email.getContent(), true);
			helper.setSubject(email.getSubject());
			sender.send(message);
	        log.debug("method: sendNotification() Email Enviado!");
            return true;
		} catch (MessagingException ex) {
            log.error("method: Send({}, {})", ex.getMessage(), ex);
            return false;
		}
	}
	
	@Bean
	public SimpleMailMessage templateNewOrder() {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setText(
	      "Se ha creado una nueva orden desde http://alimentaccionalternativa.draiz.co/ de %s con los siguientes productos"
	      + ":<br>%s");
	    return message;
	}
	
	
	public void BuildNewPurchaseMessage(PurchaseOrderDTO purchase, List<OrderProductMailedDTO> OProducts, Person orderPerson) {
		String suser = "<b>"+purchase.getPerson()+"</b>";
		String br = "<br>";
		String sproducts ="";
		sproducts += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\">";
		sproducts += "<tr>";
		sproducts += "<td>"+"Producto"+" "+"</td>";
		sproducts += "<td>"+"Presentación"+" "+"</td>";
		sproducts += "<td>"+"Cantidad"+" "+"</td>";
		sproducts += "<tr>";
		for(OrderProductMailedDTO products:OProducts) {
			sproducts += "<tr>";
			sproducts += "<td>"+products.getProduct()+" "+"</td>";
			sproducts += "<td>"+products.getQuantity()+" "+"";
			sproducts += ""+products.getUnit()+" "+"</td>";
			sproducts += "<td>"+products.getUnits()+" "+"</td>";
			sproducts += "<tr>";
		}
		sproducts += "</table>";
		sproducts += br;
		sproducts += br;
		sproducts += "<b>Datos de envío<b>"+br;
		sproducts += "<b>Ciudad:<b>"+orderPerson.getCity()+br;
		sproducts += "<b>Departamento:<b>"+orderPerson.getState()+br;
		sproducts += "<b>Dirección:<b>"+orderPerson.getAddress()+br;
		sproducts += "<b>Teléfono:<b>"+orderPerson.getTelephone()+br;
		
		/*
        <div *ngFor="let product of products">
        <div class="row">
            <div class="col-7">
                <h5>{{product.name}}</h5>
            </div>
            <div class="col-3">
                <h5>{{product.quantity | number:'1.0-0' | numberFormat}}{{product.unit}} x {{product.amount}}</h5>
            </div>
            <div class="col-2">
                <h5>{{product.total  | number:'1.0-0' | numberFormat}}</h5>
            </div>
        </div>
</div>
*/
		//sproducts += "</ul>";
		String text = String.format(template.getText(), suser, br+sproducts); 
		this.email.setContent(text);
	}

}
