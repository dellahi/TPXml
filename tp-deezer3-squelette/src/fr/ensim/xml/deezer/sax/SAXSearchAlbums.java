package fr.ensim.xml.deezer.sax;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import fr.ensim.xml.deezer.AbstractSearchAlbum;
import fr.ensim.xml.deezer.data.Album;

/**
 * @author Denis Apparicio
 * 
 */
public class SAXSearchAlbums extends AbstractSearchAlbum {
  private static final Logger LOG = Logger.getLogger(SAXSearchAlbums.class);
 

  @Override
  public List<Album> readAlbums(InputStream in) throws ParserConfigurationException, SAXException, IOException  {
    LOG.debug(">>readAlbums");

    //TODO Recherche les albums en SAX
    SAXParserFactory factory = SAXParserFactory.newInstance(); 
    factory.setNamespaceAware(true);
    SAXParser parser = factory.newSAXParser();  
    
    SAXSearchAlbumsHandler saxHandler = new SAXSearchAlbumsHandler();
    parser.parse(in, saxHandler);

    LOG.debug("<<readAlbums");
    return saxHandler.getListAlbum();
  }
}
