package fr.ensim.xml.deezer.dom;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.ensim.xml.deezer.AbstractSearchAlbum;
import fr.ensim.xml.deezer.data.Album;
import fr.ensim.xml.deezer.data.Artist;
import fr.ensim.xml.deezer.data.Track;

/**
  * @author Denis Apparicio
  *
  */
public class DOMSearchAlbums extends AbstractSearchAlbum {
   private static final Logger LOG = 
Logger.getLogger(DOMSearchAlbumsTest.class);

   /*
    * (non-Javadoc)
    *
    * @see
    * fr.ensim.xml.deezer.AbstractSearchAlbum#readAlbums(java.io.InputStream)
    */
   @Override
   public List<Album> readAlbums(InputStream in) throws ParserConfigurationException,
                                                SAXException,
                                                IOException {
     LOG.debug(">>readAlbums");

     List<Album> listAlbums = new ArrayList<Album>();
     
     Document doc = null;
     DocumentBuilderFactory factory = null;
     try {
     	
     	factory = DocumentBuilderFactory.newInstance();
     	//création d'un parseur
     	DocumentBuilder builder = factory.newDocumentBuilder();
     	// parser le fichier
     	doc = builder.parse(in);
     	

     	String title = doc.getElementsByTagName("title").item(0).getTextContent();
     	String id = doc.getElementsByTagName("id").item(0).getTextContent();
     	String cover = doc.getElementsByTagName("cover").item(0).getTextContent();
     	
     	
     	// extract artist
     	Element contributors = (Element)doc.getElementsByTagName("contributors").item(0);
     	Element artistElement = (Element)contributors.getElementsByTagName("artist").item(0);
     	
     	String artistId = artistElement.getElementsByTagName("id").item(0).getTextContent();
     	String artistName = artistElement.getElementsByTagName("name").item(0).getTextContent();
     	String artistLink = artistElement.getElementsByTagName("link").item(0).getTextContent();
     	String artistPicture = artistElement.getElementsByTagName("picture").item(0).getTextContent();
     	
     	Artist artist = new Artist();
     	artist.setId(artistId);
     	artist.setName(artistName);
     	artist.setPicture(artistPicture);
     	artist.setLink(artistLink);
     	
     	// extract tracks
     	Element tracks = (Element)doc.getElementsByTagName("tracks").item(0);
     	Element data = (Element)tracks.getElementsByTagName("data").item(0);
     	NodeList track = data.getChildNodes();
     	ArrayList<Track> arrayList = new ArrayList<Track>();
     	for(int i = 0; i<track.getLength(); i++) 
     	{
     		
     		if(track.item(i).getNodeType() == Node.ELEMENT_NODE) {
     			Element tr = (Element)track.item(i);
     			
     			String trackTitle = tr.getElementsByTagName("title").item(0).getTextContent();
     			String trackPreview = tr.getElementsByTagName("preview").item(0).getTextContent();
     			
     			
     			Track tra = new Track();
     			tra.setPreview(trackPreview);
     			tra.setTitle(trackTitle);
     			
     			arrayList.add(tra);
     		}
     		
     	}

     	Album album = new Album();
     	album.setTitle(title);
     	album.setId(id);
     	album.setCover(cover);
     	album.setArtist(artist);
     	album.setTracks(arrayList);
     	
     	listAlbums.add(album);
     	
     } catch (Exception e) {
         e.printStackTrace();
       }
     
     LOG.debug("<<readAlbums");
     return listAlbums;
   }
}
