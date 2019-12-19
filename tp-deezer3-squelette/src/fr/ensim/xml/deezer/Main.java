package fr.ensim.xml.deezer;

import static org.junit.Assert.fail;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import fr.ensim.xml.deezer.data.Album;
import fr.ensim.xml.deezer.data.Track;
import fr.ensim.xml.deezer.dom.SearchAlbumTracks;
import fr.ensim.xml.deezer.sax.SAXSearchAlbums;
import fr.ensim.xml.deezer.stax.HtmlAlbum;


/**
 * @author Denis Apparicio
 * 
 */
public class Main {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // configuration du proxy et des logs
    DOMConfigurator.configure("log4J.xml");
    ProxyConfiguration.configure();
    
    Logger log = Logger.getLogger(Main.class);

   
    FileOutputStream out = null;
    try { 
      List<Album> listAlbum = new SAXSearchAlbums().find("Shaka%20Ponk");

      // recuperation du 1er album
      Album album = listAlbum.get(0);

      // recuperation des titres de l album
      List<Track> tracks = SearchAlbumTracks.find(album.getId());
      album.setTracks(tracks);

      // Ecriture de la page html
      File fileHtml = new File(album.getId() + ".html");
      out = new FileOutputStream(fileHtml);
      HtmlAlbum.write(album, out);
      out.close();

      // Ouverture de la page
      if (fileHtml.isFile()) {
        if (Desktop.isDesktopSupported()
            && Desktop.getDesktop().isSupported(Action.BROWSE)) {
          Desktop.getDesktop().browse(fileHtml.toURI());
        }
      }
    }
    catch (Exception e) {
      log.error("", e);
      fail(e.getMessage());
    }
    finally {
      try {
        if (out != null) {
          out.close();
        }
      }
      catch (IOException e) {
        log.error("", e);
      }
    }

    log.debug("<<main");
  }

}
