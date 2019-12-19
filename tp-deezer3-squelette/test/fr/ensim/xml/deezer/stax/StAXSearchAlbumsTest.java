package fr.ensim.xml.deezer.stax;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;

import fr.ensim.xml.deezer.data.Album;
import fr.ensim.xml.deezer.dom.DOMSearchAlbums;

/**
 * @author Denis Apparicio
 * 
 */
public class StAXSearchAlbumsTest {
  static {
    DOMConfigurator.configure("log4J.xml");
  }

  private static final Logger LOG = Logger
                                      .getLogger(StAXSearchAlbumsTest.class);
 
  @Test
  public void testCount() {
    LOG.debug(">>testCount");

    // Recuperation du flux a parser
    InputStream in = getClass().getResourceAsStream("../album-stupeflip.xml");

    //TODO
    
    LOG.debug("<<testCount");
  }
  
  @Test
  public void testParse() {
    LOG.debug(">>testParse");

    // Recuperation du flux a parser

    InputStream in = getClass().getResourceAsStream("../album-stupeflip.xml");
    String OUTPUT_FILE = "C:\\Users\\Dellahi\\Documents\\JavaProjects\\tp-deezer3-squelette\\stax-d.html";
    try {
    	
		FileOutputStream out = new FileOutputStream(OUTPUT_FILE);
		DOMSearchAlbums dom = new DOMSearchAlbums();

		List<Album> album = dom.readAlbums(in);
	
		HtmlAlbum htmlAlbum = new HtmlAlbum();
		htmlAlbum.write(album.get(0), out);
			
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
    LOG.debug("<<testParse");
  }
}
