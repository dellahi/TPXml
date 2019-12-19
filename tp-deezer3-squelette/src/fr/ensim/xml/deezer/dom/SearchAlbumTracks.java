package fr.ensim.xml.deezer.dom;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.ensim.xml.deezer.data.Track;

/**
 * @author Denis Apparicio
 * 
 */
public class SearchAlbumTracks {
  private static Logger log = Logger.getLogger(SearchAlbumTracks.class);
  private static Track track2;
  /**
   * Restitue la liste des chansons d'un album.
   * 
   * @param id
   *          id de l'album.
   * @return la liste des chansons.
   * @throws IOException
   * @throws ParserConfigurationException
   * @throws SAXException
   */
  public static List<Track> find(String id) throws IOException,
                                           ParserConfigurationException,
                                           SAXException {
    log.debug(">>albums");

    // Constitution de l'URL
    StringBuilder sUrl = new StringBuilder();
    sUrl.append("http://api.deezer.com/2.0/album/");
    sUrl.append(id);
    sUrl.append("?output=xml");

    URL url = new URL(sUrl.toString());

    log.debug(url);

    HttpURLConnection cnx = (HttpURLConnection) url.openConnection();
    cnx.setConnectTimeout(5000);
    cnx.setReadTimeout(5000);
    cnx.setRequestMethod("GET");
    cnx.setDoInput(true);
    cnx.addRequestProperty("Accept-Language", "en;q=0.6,en-us;q=0.4,sv;q=0.2");

    try {
      if (cnx.getResponseCode() == HttpURLConnection.HTTP_OK) {
        return find(cnx.getInputStream());
      }
    }
    finally {
      cnx.disconnect();
    }

    log.debug("<<albums");
    return null;
  }

  /**
   * Restitue la liste des chansons d'un album &agrave; partir d'un flux.
   * 
   * @param in
   *          le flux.
   * @return la liste des chansons.
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws IOException
   */
  protected static ArrayList<Track> find(InputStream in) throws 
  SAXException, ParserConfigurationException, IOException {
                  log.debug(">>find");

                  ArrayList<Track> listTracks = new ArrayList<Track>();

                  // TODO Recherche les pistes d'albums en DOM

                  try {
                          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                          org.w3c.dom.Document doc = dBuilder.parse(in);
                           doc.getDocumentElement().normalize();

                          NodeList trackNameList = doc.getElementsByTagName("track");

                          for (int count = 0; count < trackNameList.getLength(); count++) {
                                  Node node1 = trackNameList.item(count);

                                  if (node1.getNodeType() == node1.ELEMENT_NODE) {
                                          Element tracks = (Element) node1;

                                          track2 = new Track();



                                          NodeList carNameList = tracks.getElementsByTagName("title");

                                          for (int x = 0; x < carNameList.getLength(); x++) {
                                                  Node node12 = carNameList.item(x);

                                                  if (node12.getNodeType() == node12.ELEMENT_NODE) {
                                                          Element track = (Element) node12;
                                                          track2.setTitle(track.getTextContent());

                                                          listTracks.add(track2);
                                                  }

                                          }

                                          NodeList trackPreviewList = tracks.getElementsByTagName("preview");

                                          for (int x = 0; x < trackPreviewList.getLength(); x++) {
                                                  Node node12 = trackPreviewList.item(x);

                                                  if (node12.getNodeType() == node12.ELEMENT_NODE) {
                                                          Element track = (Element) node12;

                                                          track2 = listTracks.get(count);
                                                          track2.setPreview(track.getTextContent());
                                                          listTracks.set(count, track2);
                                                  }

                                          }

                                  }
                          }

                  } catch (Exception e) {
                          e.printStackTrace();
                  }

                  log.debug(">>find");
                  return listTracks;
          }
}
