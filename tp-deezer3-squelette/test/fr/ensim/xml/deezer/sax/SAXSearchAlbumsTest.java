package fr.ensim.xml.deezer.sax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Denis Apparicio
 * 
 */
public class SAXSearchAlbumsTest {
  static {
    DOMConfigurator.configure("log4J.xml");
  }

  private static final Logger LOG = Logger.getLogger(SAXSearchAlbumsTest.class);
 
  @Test
  public void testParse() throws ParserConfigurationException, SAXException, IOException {
    LOG.debug(">>testParse");

    // Recuperation du flux a parser
    InputStream in = getClass().getResourceAsStream("../list-album.xml");
    
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);
    SAXParser parser = factory.newSAXParser();

    // constitution du flux xml
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    InputSource source = new InputSource(reader);

    // parsing
    SAXSearchAlbumsHandler handler = new SAXSearchAlbumsHandler();
    parser.parse(source, handler);

    assertEquals(5, handler.getListAlbum().size());
    assertEquals("http://api.deezer.com/2.0/album/68670211/image", handler.getListAlbum().get(0).getCover());
    assertEquals("Stup Virus", handler.getListAlbum().get(0).getTitle());
    assertEquals("68670211", handler.getListAlbum().get(0).getId());
    assertNotNull(handler.getListAlbum().get(0).getArtist());
    assertEquals("939", handler.getListAlbum().get(0).getArtist().getId());
    assertEquals("STUPEFLIP", handler.getListAlbum().get(0).getArtist().getName());
    assertEquals("http://www.deezer.com/artist/939", handler.getListAlbum().get(0).getArtist().getLink());
    assertEquals("http://api.deezer.com/2.0/artist/939/image", handler.getListAlbum().get(0).getArtist().getPicture());
    
    assertEquals("http://api.deezer.com/2.0/album/68454551/image", handler.getListAlbum().get(4).getCover());
    assertEquals("Terrora !!", handler.getListAlbum().get(4).getTitle());
    assertEquals("68454551", handler.getListAlbum().get(4).getId());
    assertNotNull(handler.getListAlbum().get(4).getArtist());
    assertEquals("939", handler.getListAlbum().get(4).getArtist().getId());
    assertEquals("STUPEFLIP", handler.getListAlbum().get(4).getArtist().getName());
    assertEquals("http://www.deezer.com/artist/939", handler.getListAlbum().get(4).getArtist().getLink());
    assertEquals("http://api.deezer.com/2.0/artist/939/image", handler.getListAlbum().get(4).getArtist().getPicture());

    LOG.debug("<<testParse");
  }

}
