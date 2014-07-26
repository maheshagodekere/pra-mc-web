package com.pradeya.cast.ui.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pradeya.cast.domain.Mail;
import com.pradeya.cast.service.MailService;

@SuppressWarnings("serial")
@Component
public class MailboxUIBean implements Serializable {

	@Autowired
	MailService mailService;
	private TreeNode mailboxes;

	private List<Mail> mails ;

	private Mail mail=new Mail();

	private TreeNode mailbox;

	@PostConstruct
	public void init() {
		mailboxes = new DefaultTreeNode("root", null);

		TreeNode inbox = new DefaultTreeNode("i","Inbox", mailboxes);
		TreeNode sent = new DefaultTreeNode("s", "Sent", mailboxes);
		TreeNode trash = new DefaultTreeNode("t", "Trash", mailboxes);
		// TreeNode junk = new DefaultTreeNode("j", "Junk", mailboxes);

//		TreeNode gmail = new DefaultTreeNode("Gmail", inbox);
//		TreeNode hotmail = new DefaultTreeNode("Hotmail", inbox);

		mails = new ArrayList<Mail>();
	}

	public TreeNode getMailboxes() {
		return mailboxes;
	}

	public List<Mail> getMails() {
		Mail m = new Mail();
		m.setFrom("pradeya.com");
		m.setSubject("trst");
		m.setDate(new Date());
		mails.add(m);
		return mails;
		
		//return mailService.findAllMail();
	}

	public Mail getMail() {
		//mail.setBody(null);
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public TreeNode getMailbox() {
		return mailbox;
	}

	public void setMailbox(TreeNode mailbox) {
		this.mailbox = mailbox;
	}


	public String saveMail(){

		Properties props = new Properties();  
		//props.setProperty("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");  
		props.put("mail.smtp.socketFactory.port", "465");  
		props.put("mail.smtp.socketFactory.class",  
				"javax.net.ssl.SSLSocketFactory");  
		props.put("mail.smtp.auth", "true");  
		props.put("mail.smtp.port", "465");  

		Session session = Session.getDefaultInstance(props,  
				new Authenticator() {  
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {  
				return new javax.mail.PasswordAuthentication(mail.getFrom(),mail.getPassword());  
			}  
		});  
		
		/*String tp=mail.getTypeOfMail();
		File f = new File("/home/pradeya-5/software/install/eclipse/workspace/fp-web/WebContent/index.html");*/
		/*File f;
		if(tp.equalsIgnoreCase("index"))
		{
			f=new File("/home/pradeya-5/software/install/eclipse/workspace/fp-web/WebContent/index.html");
		}
		else if(tp.equalsIgnoreCase("newsletter"))
		{
			f=new File("/home/pradeya-5/software/install/eclipse/workspace/fp-web/WebContent/newslatter.html");
		}
		else
		{
			f=new File("/home/pradeya-5/software/install/eclipse/workspace/fp-web/WebContent/cute-baby-wallpaper.jpg");
		}
*/
		
		String filedata=null;
		 BufferedReader br=null;
		 File f =new File("/home/manjunath/software/install/eclipse/workspace/fp-web/WebContent/index.html");
		 try {
			br =  new  BufferedReader(new FileReader(f));
			StringBuilder sb = new StringBuilder();
			while(br.ready()){
				sb.append(br.readLine()+"\n");
			}
			filedata= sb.toString();
			System.out.println("Title is..."+mail.getTitle());
			System.out.println("Description is..."+mail.getBody());

			
			 filedata.replace("TEMPLATETITLE", mail.getTitle());
			 filedata.replace("TEMPLATEDESC", mail.getBody());
			 System.out.println("Filedata....."+filedata);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 /*
			try {
				br = new BufferedReader(new FileReader(f));
				StringBuilder sb=new StringBuilder();
				while(br.ready()){
					sb.append(br.readLine()+"\n");
				}
				
			
				filedata=sb.toString();
				 filedata.replaceAll("TEMPLATETITLE", mail.getTitle());
				 filedata.replaceAll("TEMPLATEDESC", mail.getBody());
				 
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(br!=null){
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}

*/
		//Compose the message  
		try {  
			MimeMessage message = new MimeMessage(session);  

			System.out.println("From address is:"+mail.getFrom());
			message.setFrom(new InternetAddress(mail.getFrom()));  
			InternetAddress[] toCC=InternetAddress.parse(mail.getFolder());
			String scc=InternetAddress.toString(toCC);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTo())); 
			message.addRecipients(Message.RecipientType.CC,scc);
		
		
			  Multipart multipart = new MimeMultipart();
			  MimeBodyPart textPart=new MimeBodyPart();
			  textPart.setText(mail.getBody());
			 message.setSubject(mail.getSubject());
			 message.setContent(mail.getBody(),"text/html");
			message.setContent(filedata,"text/html");
					
			 
			mail.setBody(filedata);
			    
			     BodyPart htmlPart = new MimeBodyPart();
			     
			    htmlPart.setContent(filedata, "text/html" );
			     
			
			     multipart.addBodyPart(textPart);
			    multipart.addBodyPart(htmlPart);
			    message.setContent(multipart);
		
			System.out.println("Message is,,,,,"+message);

			Transport.send(message);  


			System.out.println("message sent successfully...");  

		} catch (MessagingException e) {e.printStackTrace();}  
		mailService.createMail(mail);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail Sent!"));
		return "newsLetter";
	}


	public void onNodeSelect(NodeSelectEvent event) {  
		try{
			System.out.println("here"+event.getTreeNode().getData());

			FacesContext.getCurrentInstance().getApplication()
			.getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), "inbox","/layoutMailbox.xhtml?faces-redirect=true");
		}
		catch(Exception e){
			System.out.println("connot load the page---------");
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail Sent!"));  
	}  
	public String createMail() {
		return "createMail";
	}
	public String cancelMail() {
		return "newsLetter";
	}

}
