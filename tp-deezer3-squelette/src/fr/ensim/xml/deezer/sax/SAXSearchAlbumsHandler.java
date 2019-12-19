package fr.ensim.xml.deezer.sax;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.ensim.xml.deezer.data.Album;
import fr.ensim.xml.deezer.data.Artist;

/**
 * @author Denis Apparicio
 * 
 */
public class SAXSearchAlbumsHandler extends DefaultHandler {
  private static final Logger LOG       = Logger.getLogger(SAXSearchAlbumsHandler.class);
 
  private List<Album>   listAlbum = new ArrayList<Album>();

  private boolean isAlbum;
  private boolean isArtist;
  private Album album;
  private Artist artist;
  private String valeur;

  /**
   * Restitue la liste des albums.
   * 
   * @return la liste des albums.
   */
  public List<Album> getListAlbum() {
    return listAlbum;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
   * java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  @Override
  public void startElement(String uri,
                           String localName,
                           String qName,
                           Attributes attributes) throws SAXException {
	  
	  if(localName.equals("album")){
		 	this.isAlbum = true ;
		 	this.album = new Album();
		 }
	  
	  if(localName.equals("artist")){
		 	this.isArtist = true ;
		 	this.artist = new Artist();
		 }
	  
	  this.valeur = localName;
	  
		 LOG.debug("startElement localName <" + localName + ">"); 
		 
		
		 

  }

  /*
   * (non-Javadoc) BufferedReader
   * 
   * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {

		 switch (localName) {
		 	case"album":
		 		this.listAlbum.add(album);
		 		this.isAlbum = false;
		 		break;
		 	case"artist":
		 		this.album.setArtist(artist);
		 		this.isArtist = false;
		 		break;
		 }
		 
		 LOG.debug("endElement localName <" + localName + ">");	
  }
		 
  

  

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
   */
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
	 String val = new String(ch, start, length);
	 if (val.trim().length() == 0) {
		 return;
	 }
	 if (isAlbum && !isArtist) {
			switch(this.valeur) {
		 	case "id" : 
		 		album.setId(val);
		 	break;
		 	
		 	case "title" : 
		 		album.setTitle(val);
		 	break;
		 	
		 	case "cover" : 
		 		album.setCover(val);
		 	break;
		 	
		}
			}
	 
	 if (isArtist) {
		 switch(valeur)
		 {
	 case "id" : 
	 		this.artist.setId(val);
	 	break;
	 	
	 case "name" : 
	 		this.artist.setName(val);
	 	break;
	 	
	 case "link" : 
	 		this.artist.setLink(val);
	 	break;
	 	
	 case "picture" : 
	 		this.artist.setPicture(val);
	 	break;
		}
	 }
	 
	 
	 LOG.debug("characters <" + val + ">");
  }
  }

