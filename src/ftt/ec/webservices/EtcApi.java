package ftt.ec.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.ws.rs.core.MediaType;

import java.io.StringWriter;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@Path("/v1") // anotation

public class EtcApi {   
	
	// http://localhost:8080/FTTWEBJAXRS/japi/v1/add/123/1000
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add/{value1}/{value2}")
    public Response doAdd(@PathParam("value1") BigInteger value1, 
    		              @PathParam("value2") BigInteger value2) {
    	
        String output = "{\"value\" : " + (value1.add(value2)) + "}";
        //Simply return the parameter passed as message
        return Response.status(200).entity(output).build();
    
    } //doAdd
    
    // Rich Text Format (RTF) 
    // Mime tipes: https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Basico_sobre_HTTP/MIME_types/Complete_list_of_MIME_types
    @GET
    @Produces("application/rtf")
    @Path("/rtf/{title}/{author}")
    public Response getRtf(@PathParam("title") String title, 
    		               @PathParam("author") String author) {
    	
        StringBuffer output = new StringBuffer();
        
        output.append("{\\rtf1\\ansi\\deff0 {\\fonttbl {\\f0 Times New Roman;}} ");
        output.append("{\\colortbl;\\red0\\green0\\blue0;\\red255\\green0\\blue0;\\red255\\green255\\blue0;}");
        output.append("{\\pard\\cf1\\qc\\fs120\\b " + title + "\\par}");
        output.append("{\\pard\\qr\\fs88\\i " + author + "\\par}");
        output.append("{\\pard\\ul\\fs44\\u " + new Date() + "\\par}");
        output.append("{\\pard Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\\par}");
        output.append("{\\pard\\bullet Alpha\\line\\bullet Betta \\line\\bullet Gamma\\par}");
        output.append("{\\pard\\box\\cb2 Abacateiro acataremos seu ato nós também somos do mato como o gato e o leão... \\par}");
        output.append("};");
        
        //Simply return the parameter passed as message
        return Response.status(200)
 		               .header("Content-Disposition", "attachment; filename=info.rtf")
        		       .entity(output.toString())
        		       .build();
    
    } //getRtf
    
    @GET
    @Produces("application/csv")
    @Path("/csv/{size}")
    public Response getCsv(@PathParam("size") int size) {
    	
    	StringBuffer csvData = new StringBuffer();
    	
    	Random rand = new Random(); 
    	
    	String options[]= {"Alpha","Betta","Betta","Gamma","Gamma","Gamma","Delta","Delta","Delta","Delta"};
    	String type[]= {"A","B","B","C","D","D","D","E","E","E","E","F","F","F","F","F","F","F"};
    	
    	ArrayList<Float> item = new ArrayList<Float>();
    	
    	for (int i =0; i<30; i++)
    		item.add(rand.nextFloat()*1000);
    	
    	NumberFormat nf = new DecimalFormat ("###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
    	
    	for (int counter = 0; counter < size; counter++) {
    		
    		csvData.append(counter + 1);
    		csvData.append(";");
    		csvData.append(nf.format(rand.nextFloat()*100));
    		csvData.append(";");
    		csvData.append(rand.nextInt()*100);
    		csvData.append(";");
    		csvData.append(nf.format(Math.floor(rand.nextFloat()*10000)));
    		csvData.append(";");
    		csvData.append(options[rand.nextInt(options.length)]);
    		csvData.append(";");
    		csvData.append(type[rand.nextInt(options.length)]);
    		csvData.append(";");
    		csvData.append(nf.format(item.get(rand.nextInt(item.size()))));
    		csvData.append("\n");
    		
    	}
    	
        return Response.status(200)
        		       .header("Content-Disposition", "attachment; filename=data.csv")
        		       .entity(csvData.toString())
        		       .build();
    } //getCsv
        
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/xml/{size}")
    public Response getXml(@PathParam("size") int size) {
    	
    	Random rand = new Random();
    	
        String xmlOut = "";
    	
		  	  try {
		
			  		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			  		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			  		// root element
			  		Document doc = docBuilder.newDocument();
			  		Element rootElement = doc.createElement("Transactions");
			  		doc.appendChild(rootElement);
			  		
			  		for(int i = 0; i<size; i++) {			
			  		
			  			// account element
				  		Element account = doc.createElement("Account");
				  		rootElement.appendChild(account);
				
				  		// set attribute to account element
				  		Attr attr = doc.createAttribute("Id");
				  		attr.setValue(String.valueOf(rand.nextInt(100)));
				  		account.setAttributeNode(attr);
						
				  		// number element
				  		Element number = doc.createElement("Number");
				  		number.appendChild(doc.createTextNode(String.valueOf(i+100)));
				  		account.appendChild(number);
	
				  		// type element
				  		Element type = doc.createElement("Type");
				  		type.appendChild(doc.createTextNode(String.valueOf(rand.nextInt(10))));
				  		account.appendChild(type);
				  		
				  		// value element
				  		Element value = doc.createElement("Value");
				  		value.appendChild(doc.createTextNode(String.valueOf(rand.nextFloat()*1000)));
				  		account.appendChild(value);
				
				  		// currency element
				  		Element currency = doc.createElement("Currency");
				  		currency.appendChild(doc.createTextNode("US$"));
				  		account.appendChild(currency);
				
				  		// ammount element
				  		Element ammount = doc.createElement("Ammount");
				  		ammount.appendChild(doc.createTextNode(String.valueOf(rand.nextInt(1000))));
				  		account.appendChild(ammount);
			  		
			  		}//for
			  		
			  		// write the content into xml file
			  		TransformerFactory transformerFactory = TransformerFactory.newInstance();
			  		Transformer transformer = transformerFactory.newTransformer();
			  		DOMSource source = new DOMSource(doc);
			  	    
			  		// Transform Document to XML String
			  		StringWriter writer = new StringWriter();
			  		 
			  		transformer.transform(source, new StreamResult(writer));
			  		
			  	    // Get the String value of final xml document
			  		xmlOut = writer.getBuffer().toString();							
		
		  	  } catch (ParserConfigurationException pce) {
		  		pce.printStackTrace();
		  	  } catch (TransformerException tfe) {
		  		tfe.printStackTrace();
		  	  }
		
    	System.out.println(xmlOut);
        
    	return Response.status(200)
  		       .entity(xmlOut)
  		       .build();

    } //getXml
    
    @GET
    @Produces(MediaType.APPLICATION_SVG_XML)
    @Path("/svg/{msg}")
    public Response getSvg(@PathParam("msg") String message) {
    	
    	StringBuffer svgData = new StringBuffer();
        
        svgData.append("<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"425px\" width=\"525px\" version=\"1.1\">");
        svgData.append("<path style=\"stroke:black; stroke-linejoin:round; stroke-width:14px; stroke:#000000; fill:white\" d=\"M 72.140014,391.37082 L 113.90529,296.44975 C 87.508191,275.83832 40.499658,243.29395 41.222865,182.54446 C 40.834139,90.934537 165.68205,33.400040 263.06697,34.467593 C 392.52123,34.286792 490.51593,101.90676 490.33513,182.00206 C 490.15433,290.48329 338.64222,360.99608 183.87568,323.57006 z \"/>");
        svgData.append("<text x=\"100\" y=\"200\" fill=\"blue\" font-family=\"Verdana\" font-size=\"55\">" + message + "</text>");
        svgData.append("</svg>");
    	
    	return Response.status(200)
   		       .entity(svgData.toString())
   		       .build();

     } //getSvg

    //TODO: JSON - Gson lib
    //TODO: X3D
    //TODO: PNG
    //TODO: PDF
    //TODO: Apache Maven
    
} //MathApi
