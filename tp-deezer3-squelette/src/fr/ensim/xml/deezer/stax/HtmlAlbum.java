package fr.ensim.xml.deezer.stax;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;

import fr.ensim.xml.deezer.data.Album;

/**
 * @author Denis Apparicio
 * 
 */
public class HtmlAlbum {
  private static Logger log = Logger.getLogger(HtmlAlbum.class);
 
  /**
   * Ecriture de la page HTML avec StAX.
   * 
   * @param album
   * @param out
   * @throws XMLStreamException
   * @throws IOException
   */
  public static void write(Album album, OutputStream outputStream) throws XMLStreamException,
                                                                  IOException {
    log.debug(">>write");

    //TODO Ecriture de l'album en StAX
 
    
    XMLOutputFactory factory = XMLOutputFactory.newInstance();
    FileWriter fileWrite = new FileWriter("stax-d.html");
    try {
    	
    	XMLStreamWriter writer = factory.createXMLStreamWriter(fileWrite);
    	//writer.writeStartDocument();
    	writer.writeStartElement("html");
    	writer.writeStartElement("h1");
    	writer.writeCharacters("album title: " + album.getTitle());
    	writer.writeStartElement("br");
    	writer.writeStartElement("img");
    	writer.writeAttribute("src", album.getCover());
    	writer.writeStartElement("br");
    	for(int i = 0; i < (album.getTracks()).size(); i++) {
    		
    		writer.writeCharacters(album.getTracks().get(i).getTitle());
	    	writer.writeStartElement("br");
	    	writer.writeStartElement("audio");
	    	writer.writeAttribute("controls", "");
	    	writer.writeAttribute("src", album.getTracks().get(i).getPreview());
	    	writer.writeEndElement();
    	}
    	
    	writer.writeEndElement();
    	writer.writeEndElement();
    	//writer.writeEndDocument();
    	
    }
    catch(Exception e ) {
    	System.out.print(e.toString());
    }
    finally {
    	fileWrite.close();
    }
    
    log.debug("<<write");
  }
}
